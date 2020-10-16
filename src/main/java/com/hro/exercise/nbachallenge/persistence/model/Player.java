package com.hro.exercise.nbachallenge.persistence.model;

import javax.persistence.Entity;

@Entity
public class Player extends AbstractModel{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
