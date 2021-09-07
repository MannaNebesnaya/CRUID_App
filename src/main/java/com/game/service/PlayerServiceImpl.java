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
                                           Integer minLevel, Integer maxLevel, PlayerOrder order,
                                           Integer pageNumber, Integer pageSize) {


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

        //кол-во игроков на отображении
        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 3;

        allPlayerFilterList = allPlayerFilterList.stream()
                .sorted(comparator(order))
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        return allPlayerFilterList;
    }

    @Override
    public Player getPlayer(Long id) {

        if (!isValidId(id)) throw new BadRequestException();
        if (!playerRepository.existsById(id)) throw new NotFoundException();

        return playerRepository.findById(id).orElse(null);
    }


//    !! ====== Вспомогательные методы ====== !!

    //    Реализация компаратора для сортировки отображения
//    Странно, что в order'e есть ещё level, а на странице его нельзя выбрать
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
        return id != null &&
                id > 0;

    }
}
