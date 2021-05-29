package launchcode.liftoff_project.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id") //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion Resolves infinite recursion error related to Rating class
@Entity
public class Trail implements Comparable<Trail>{

    public enum Water {
        none("none"),
        lake("lake"),
        river("river"),
        creek("creek"),
        pond("pond");


        public final String label;

        private Water(String label){
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

    public enum Type {
        natural("natural"),
        paved("paved"),
        gravel("gravel"),
        partial_paved("partial_paved");

        public final String label;

        private Type(String label){
            this.label = label;
        }

        @Override
        public String toString(){
            return this.label;
        }
    }

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

    private Boolean dogs;

    private Boolean family;

    private Boolean bikes;

    private Boolean woods;

    @Enumerated(EnumType.STRING)
    private Water water;

    @Enumerated(EnumType.STRING)
    private Type type;

    @JsonIgnore //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion Section 5
    @OneToMany(mappedBy = "trail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @JsonIgnore //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion Section 5
    @ManyToMany(mappedBy = "favoriteTrails")
    private Set<User> usersFavorited;

    private Double averageRating;

    public Trail(String name, String city, String state, Float length, int difficulty) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.length = length;
        this.difficulty = difficulty;
        this.google_id = null;
        this.google_name = null;
        this.lat = null;
        this.lng = null;
        this.type = null;
        this.water = null;
        this.woods = null;
        this.dogs = null;
        this.family = null;
        this.bikes = null;
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

    public Boolean getDogs() {
        return dogs;
    }

    public void setDogs(Boolean dogs) {
        this.dogs = dogs;
    }

    public Boolean getFamily() {
        return family;
    }

    public void setFamily(Boolean family) {
        this.family = family;
    }

    public Boolean getBikes() {
        return bikes;
    }

    public void setBikes(Boolean bikes) {
        this.bikes = bikes;
    }

    public Boolean getWoods() {
        return woods;
    }

    public void setWoods(Boolean woods) {
        this.woods = woods;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void addRating(User user, int ratingValue){
        Rating rating = new Rating(user, this, ratingValue);
        ratings.add(rating);
        user.getRatings().add(rating);
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Set<User> getUsersFavorited() {
        return usersFavorited;
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
