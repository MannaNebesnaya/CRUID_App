package com.game.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {

    //  ID игрока
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Имя персонажа (до 12 знаков включительно)

    @Column
    private String name;

//    Титул персонажа (до 30 знаков включительно)

    @Column
    private String title;


//    Раса персонажа
//    Для корректной обработки переменной Enum, требуется
//    использовать аннотацию @Enumerated
    @Column
    @Enumerated(EnumType.STRING)
    private Race race;

//    Профессия персонажа. Так же история с Enum'ом.
    @Column
    @Enumerated(EnumType.STRING)
    private Profession profession;


//    Опыт персонажа. Диапазон значений 0..10_000_000

    @Column
    private Integer experience;

//    Уровень персонажа
    @Column
    private Integer level;

//    Остаток опыта до следующего уровня
    @Column
    private Integer untilNextLevel;

//    Дата регистрации. Диапазон значений года 2000..3000 включительно

    @Column
    private Date birthday;

//    Забанен/ не забанен
    @Column
    private Boolean banned;

}
