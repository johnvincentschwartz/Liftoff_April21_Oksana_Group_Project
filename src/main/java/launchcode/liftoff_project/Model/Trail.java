package launchcode.liftoff_project.Model;

import javax.persistence.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Entity
public class Trail implements Comparable<Trail>{

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String city;

    private String state;

    private Float length;

    private int difficulty;

    private String google_id;

    private String google_name;

    private Float lat;

    private Float lng;

    @OneToMany(mappedBy = "trail")
    private List<Meetup> meetups = new ArrayList<>();

//    @OneToMany(mappedBy = "trail", cascade = CascadeType.ALL)
//    private List<Meetup> meetups = new ArrayList<Meetup>();

    public Trail(String name, String city, String state, Float length, int difficulty, List<Meetup> meetups) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.length = length;
        this.difficulty = difficulty;
        this.google_id = null;
        this.google_name = null;
        this.lat = null;
        this.lng = null;
        this.meetups = meetups;
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

    public String getGoogleId() {
        return google_id;
    }

    public void setGoogleId(String google_id) {
        this.google_id = google_id;
    }

    public String getGoogleName() {
        return google_name;
    }

    public void setGoogleName(String google_name) {
        this.google_name = google_name;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public List<Meetup> getMeetups() {
        return meetups;
    }

    public void setMeetups(List<Meetup> meetups) {
        this.meetups = meetups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trail trail = (Trail) o;
        return id == trail.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + "(" + city + ", " + state + ")";
    }

    public int compareTo(Trail t) {
        int lastCmp = name.compareTo(t.name);
        return (lastCmp != 0 ? lastCmp : city.compareTo(t.city));
    }


}
