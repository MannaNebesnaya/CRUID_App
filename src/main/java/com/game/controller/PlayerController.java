package com.game.controller;


import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Совмещает в себе @Controller и @ResponseBody
@RestController
@RequestMapping("/rest/players")
public class PlayerController {
    private PlayerServiceImpl playerService;

    @Autowired
    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllSortedPlayer(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) Race race,
                                           @RequestParam(required = false) Profession profession,
                                           @RequestParam(required = false) Long after,
                                           @RequestParam(required = false) Long before,
                                           @RequestParam(required = false) Boolean banned,
                                           @RequestParam(required = false) Integer minExperience,
                                           @RequestParam(required = false) Integer maxExperience,
                                           @RequestParam(required = false) Integer minLevel,
                                           @RequestParam(required = false) Integer maxLevel,
                                           @RequestParam(required = false) PlayerOrder order,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize) {

        return playerService.getAllSortedPlayer();
    }

    @GetMapping("/count")
    public Integer getCountAllPlayers(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String title,
                                      @RequestParam(required = false) Race race,
                                      @RequestParam(required = false) Profession profession,
                                      @RequestParam(required = false) Long after,
                                      @RequestParam(required = false) Long before,
                                      @RequestParam(required = false) Boolean banned,
                                      @RequestParam(required = false) Integer minExperience,
                                      @RequestParam(required = false) Integer maxExperience,
                                      @RequestParam(required = false) Integer minLevel,
                                      @RequestParam(required = false) Integer maxLevel) {

        return 0;

    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {


        return null;
    }

    @PostMapping
    @ResponseBody
    public Player createPlayer(@RequestBody Player newPlayer) {
        return null;
    }

    @PostMapping("/{id}")
    @ResponseBody
    public Player updatePlayer(@RequestParam Player updatePlayer,  @PathVariable Long id ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {

    }
}
