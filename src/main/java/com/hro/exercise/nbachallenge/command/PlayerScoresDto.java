package com.hro.exercise.nbachallenge.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * PlayerScores Dto
 * Assures security/filter when providing PlayerScores data to the user
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerScoresDto {

    @JsonIgnore
    private Integer id;

    @JsonProperty("First Name")
    private String firstName;

    @JsonProperty("Last Name")
    private String lastName;

    private Integer score;

    @JsonProperty("player")
    private void nestedObject(Map<String, Object> objectMap) {
        setFirstName((String) objectMap.get("first_name"));
        setLastName((String) objectMap.get("last_name"));
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("Points")
    public Integer getScore() {
        return score;
    }

    @JsonProperty("pts")
    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PlayerScoresDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return (o == this) || (o instanceof PlayerScoresDto && (((PlayerScoresDto) o).id) == id);
    }
}
