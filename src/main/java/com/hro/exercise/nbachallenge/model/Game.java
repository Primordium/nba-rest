package com.hro.exercise.nbachallenge.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@Entity
//@Table(name = "games")
public class Game extends AbstractModel{

    private Date gameDate;
    private String homeTeamName;
    private String awayTeamName;
    private Integer homeTeamScore;
    private Integer awayTeamScore;

    //private Map<String, Integer> playerScores;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            mappedBy = "games",
            fetch = FetchType.EAGER
    )
    private List<Comment> commentList = new ArrayList<>();

}
