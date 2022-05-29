package com.example.isafe.databaseHandling;

public class ParticipantModel {
    private int id;
    private String name;
    private String number;

    public ParticipantModel(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Name: " + name +"\nNumber: " + number;
    }
}
