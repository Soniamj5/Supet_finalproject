package com.animalhelpapp.supet_finalproject;

public class PetInfo {
    String petName, title, description;

    public PetInfo() {
    }

    public PetInfo(String petName, String title, String description) {
        this.petName = petName;
        this.title = title;
        this.description = description;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
