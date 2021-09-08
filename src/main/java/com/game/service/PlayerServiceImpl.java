package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public List<Player> getAllSortedPlayer(String name, String title, Race race, Profession profession, Long after,
                                           Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                           Integer minLevel, Integer maxLevel) {


        List<Player> allPlayerFilterList = playerRepository.findAll();

        if (name != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getName().contains(name))
                    .collect(Collectors.toList());
        }

        if (title != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getTitle().contains(title))
                    .collect(Collectors.toList());
        }

        if (race != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getRace().equals(race))
                    .collect(Collectors.toList());
        }

        if (profession != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getProfession().equals(profession))
                    .collect(Collectors.toList());
        }

        if (after != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getBirthday().after(new Date(after)))
                    .collect(Collectors.toList());
        }

        if (before != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getBirthday().before(new Date(before)))
                    .collect(Collectors.toList());
        }

        if (banned != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getBanned().equals(banned))
                    .collect(Collectors.toList());
        }

        if (minExperience != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getExperience() >= minExperience)
                    .collect(Collectors.toList());
        }

        if (maxExperience != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getExperience() <= maxExperience)
                    .collect(Collectors.toList());
        }

        if (minLevel != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getLevel() >= minLevel)
                    .collect(Collectors.toList());
        }

        if (maxLevel != null) {
            allPlayerFilterList = allPlayerFilterList.stream()
                    .filter(s -> s.getLevel() <= maxLevel)
                    .collect(Collectors.toList());
        }

        return allPlayerFilterList;
    }


    @Override
    public Player createPlayer(Player newPlayer) {

        if (!isValidDataCreatePlayer(newPlayer)) throw new BadRequestException();

        newPlayer.setLevel(calculateLevel(newPlayer.getExperience()));
        newPlayer.setUntilNextLevel(calculateUntilNextLevel(newPlayer.getLevel(), newPlayer.getExperience()));
        return playerRepository.save(newPlayer);
    }

    @Override
    public Player updatePlayer(Player updatePlayer, Long id) {
//        if (!isValidId(id)) throw new BadRequestException();
//
//        if (!playerRepository.existsById(id)) throw new NotFoundException();
        Player editPlayer = getPlayer(id);

        String name = updatePlayer.getName();
        if (name != null) {
            if (name.equals("") || name.length() > 12) throw new BadRequestException();

            editPlayer.setName(name);
        }

        String title = updatePlayer.getTitle();
        if (title != null) {
            if (title.length() > 30) throw new BadRequestException();

            editPlayer.setTitle(title);
        }

        Race race = updatePlayer.getRace();
        if (race != null) {
            editPlayer.setRace(race);
        }

        Profession profession = updatePlayer.getProfession();
        if (profession != null) {
            editPlayer.setProfession(profession);
        }


        if (updatePlayer.getBirthday() != null) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(updatePlayer.getBirthday());
            int year = calendar.get(Calendar.YEAR);

            if (2000 > year || year > 3000 /*|| birthday.getTime() < 0*/) throw new BadRequestException();

            editPlayer.setBirthday(updatePlayer.getBirthday());
        }

        Boolean banned = updatePlayer.getBanned();
        if (banned != null) {
            editPlayer.setBanned(banned);
        }
// Возможно тут проблемы
        Integer exp = updatePlayer.getExperience();
        if (exp != null) {
            if (exp < 0 || exp > 10_000_000) throw new BadRequestException();

            editPlayer.setExperience(exp);

            editPlayer.setLevel(calculateLevel(editPlayer.getExperience()));
            editPlayer.setUntilNextLevel(calculateUntilNextLevel(editPlayer.getLevel(),
                    editPlayer.getExperience()));
        }

        return editPlayer;
    }

    @Override
    public Player getPlayer(Long id) {

        if (!isValidId(id)) throw new BadRequestException();
        if (!playerRepository.existsById(id)) throw new NotFoundException();

        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePlayer(Long id) {
        if (!isValidId(id)) throw new BadRequestException();
        if (!playerRepository.existsById(id)) throw new NotFoundException();
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> ApplyFilterListPlayer(List<Player> playerList, PlayerOrder order,
                                              Integer pageNumber, Integer pageSize) {

        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 3;

        return playerList.stream()
                .sorted(comparator(order))
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    //    !! ====== Вспомогательные методы ====== !!

    //    Реализация компаратора
    private Comparator<Player> comparator(PlayerOrder order) {
        if (order == null) {
            return Comparator.comparing(Player::getId);
        }
        Comparator<Player> comparator = null;
        switch (order.getFieldName()) {
            case "id":
                comparator = Comparator.comparing(Player::getId);
                break;
            case "name":
                comparator = Comparator.comparing(Player::getName);
                break;
            case "experience":
                comparator = Comparator.comparing(Player::getExperience);
                break;
            case "birthday":
                comparator = Comparator.comparing(Player::getBirthday);
                break;
        }
        return comparator;
    }


    // Валидность Id
    private Boolean isValidId(Long id) {
        return id != null && id > 0;
    }


    // Расчёт уровня персонажа
    // Реализация под большим вопросом
    private Integer calculateLevel(Integer exp) {
        return (int) ((Math.sqrt(2500 + 200 * exp) - 50) / 100);
    }


    // Расчёт того, сколько осталось до следующего уровня
    private Integer calculateUntilNextLevel(Integer lvl, Integer exp) {
        return 50 * (lvl + 1) * (lvl + 2) - exp;
    }


    // Валидность данных при создании игрока
    private Boolean isValidDataCreatePlayer(Player player) {

        if (player.getName() == null
                || player.getName().equals("")
                || player.getName().length() > 12
                || player.getTitle() == null
                || player.getTitle().length() > 30
                || player.getRace() == null
                || player.getProfession() == null
                || player.getBirthday() == null
                || player.getBirthday().getTime() < 0
                || player.getExperience() == null
                || player.getExperience() < 0
                || player.getExperience() > 10_000_000) return false;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(player.getBirthday());
        int year = calendar.get(Calendar.YEAR);

        return year >= 2000 && year <= 3000;
    }
}
