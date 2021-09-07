package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/** Интерфейс JpaRepository – это интерфейс фреймворка Spring Data
 * предоставляющий набор стандартных методов JPA для работы с БД.
 * */

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
