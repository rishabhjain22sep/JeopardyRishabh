package com.example.gosecure;

public class Leader {
    private String name, image;
    private int jeopardyPoints;

    public Leader(String name, String image, int jeopardyPoints) {
        this.name = name;
        this.image = image;
        this.jeopardyPoints = jeopardyPoints;
    }
    public Leader() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getJeopardyPoints() {
        return jeopardyPoints;
    }

    public void setJeopardyPoints(int jeopardyPoints) {
        this.jeopardyPoints = jeopardyPoints;
    }
}
