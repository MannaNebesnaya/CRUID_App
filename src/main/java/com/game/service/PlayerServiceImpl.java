package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public List<Player> getAllSortedPlayer(/*String name, String title, Race race, Profession profession, Long after,
                                           Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                           Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber,
                                           Integer pageSize*/) {



        return playerRepository.findAll();
    }
}
