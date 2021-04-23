package launchcode.liftoff_project.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class Trail {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String city;

    private String state;

    private Float length;

    private int difficulty;

    public Trail(String name, String city, String state, Float length, int difficulty) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.length = length;
        this.difficulty = difficulty;
    }

    public Trail(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


}
