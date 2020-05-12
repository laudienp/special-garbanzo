package com.example.birdstagram.data.tools;

public class Specie {

    private int id;
    private String englishName;
    private String frenchName;
    private String description;

    public Specie(int id, String englishName, String frenchName, String description) {
        this.id = id;
        this.englishName = englishName;
        this.frenchName = frenchName;
        this.description = description;
    }

    public Specie(String englishName, String frenchName, String description) {
        this.englishName = englishName;
        this.frenchName = frenchName;
        this.description = description;
    }

    public Specie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public void setFrenchName(String frenchName) {
        this.frenchName = frenchName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
