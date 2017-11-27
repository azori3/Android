package com.example.alimethnani.pidev;

/**
 * Created by ali methnani on 24/11/2017.
 */

public class Jobs {


    private String firstName;
    private int mimo;
    private String names;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getMimo() {
        return mimo;
    }

    public void setMimo(int mimo) {
        this.mimo = mimo;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Jobs(String firstName, int mimo, String names, int id) {
        this.firstName = firstName;
        this.mimo = mimo;
        this.names = names;
        this.id = id;
    }
}
