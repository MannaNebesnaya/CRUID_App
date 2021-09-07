package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Service – это Java класс, который предоставляет с себя основную (Бизнес-Логику).
 * В основном сервис использует готовые DAO/Repositories или же другие сервисы, для того
 * чтобы предоставить конечные данные для пользовательского интерфейса.
 */

public interface PlayerService {
//    Получить отсортированный список игроков

    List<Player> getAllSortedPlayer(String name, String title, Race race, Profession profession,
                                    Long after, Long before, Boolean banned, Integer minExperience,
                                    Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder order,
                                    Integer pageNumber, Integer pageSize);


    // Получить игрока по id
    Player getPlayer(@PathVariable Long id);

}
