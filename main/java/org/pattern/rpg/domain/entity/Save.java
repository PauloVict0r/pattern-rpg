package org.pattern.rpg.domain.entity;

public class Save {

    private int id;
    private String[] items;
    private int floor;
    private int score;

    public Save(String[] items, int floor, int score) {
        this.items = items;
        this.floor = floor;
        this.score = score;
    }

    public Save(int id, String[] items, int floor, int score) {
        this.id = id;
        this.items = items;
        this.floor = floor;
        this.score = score;
    }

    public int getId() { return id; }
    public String[] getItems() { return items; }
    public int getFloor() { return floor; }
    public int getScore() { return score; }
}