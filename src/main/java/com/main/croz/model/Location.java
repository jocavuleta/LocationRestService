package com.main.croz.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Location {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String state;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String capital;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String population;

    public Location() {
    }

    public Location(String state, String capital, String population) {
        this.state = state;
        this.capital = capital;
        this.population = population;
    }

    public Location(String capital, String population) {
        this.capital = capital;
        this.population = population;
    }

    public Location(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "State: " + state + ", capital of the state: " + capital + ", number of population: " + population;
    }
}
