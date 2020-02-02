package com.example.police;

public class Record {
    private String Name;
    private String Age;
    private String Location;
    private String Relatives;
    private String Sources;
    private String Crime;
    private String Imageid;
    public Record() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRelatives() {
        return Relatives;
    }

    public void setRelatives(String relatives) {
        Relatives = relatives;
    }

    public String getSources() {
        return Sources;
    }

    public void setSources(String sources) {
        Sources = sources;
    }

    public String getCrime() {
        return Crime;
    }

    public void setCrime(String crime) {
        Crime = crime;
    }

    public String getImageid() {
        return Imageid;
    }

    public void setImageid(String imageid) {
        Imageid = imageid;
    }
}
