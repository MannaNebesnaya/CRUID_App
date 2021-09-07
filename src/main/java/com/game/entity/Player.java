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



//          -------- Getters and Setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }


//              Переопределили toString
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                ", birthday=" + birthday +
                ", banned=" + banned +
                '}';
    }
}
