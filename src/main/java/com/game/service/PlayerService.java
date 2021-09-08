package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Service – это Java класс, который предоставляет с себя основную (Бизнес-Логику).
 * В основном сервис использует готовые DAO/Repositories или же другие сервисы, для того
 * чтобы предоставить конечные данные для пользовательского интерфейса.
 */

public interface PlayerService {

    List<Player> getAllSortedPlayer(String name, String title, Race race, Profession profession,
                                    Long after, Long before, Boolean banned, Integer minExperience,
                                    Integer maxExperience, Integer minLevel, Integer maxLevel);


    Player createPlayer(Player newPlayer);

    Player updatePlayer(Player updatePlayer, Long id);


    Player getPlayer(Long id);


    List<Player> ApplyFilterListPlayer(List<Player> playerList, PlayerOrder order,
                                       Integer pageNumber, Integer pageSize);

    void deletePlayer(Long id);


}
