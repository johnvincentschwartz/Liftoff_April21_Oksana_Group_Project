package launchcode.liftoff_project.Model.dto;

import java.util.*;

public class TrailFilterDTO {

    private String searchLocation;

    private Double minLength = 0.0;

    private Double maxLength = 250.0;

    public List<Integer> difficulty = new ArrayList<>();

    private Integer minRating = 0;

    private Boolean dogFriendly = false;

    private Boolean kidFriendly = false;

    private Boolean bikeFriendly = false;

    private Boolean nearWater = false;

    private Boolean nearWoods = false;

    private List<String> trailSurface = new ArrayList<>();

    private String sort;

    public String getSearchLocation() {
        return searchLocation;
    }

    public void setSearchLocation(String searchLocation) {
        this.searchLocation = searchLocation;
    }

    public Double getMinLength() {
        return minLength;
    }

    public void setMinLength(Double minLength) {
        this.minLength = minLength;
    }

    public Double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Double maxLength) {
        this.maxLength = maxLength;
    }

    public List<Integer> getDifficulty() {
        if (difficulty.isEmpty()){
            difficulty.add(1);
            difficulty.add(2);
            difficulty.add(3);
            difficulty.add(4);
            difficulty.add(5);
        }
        return difficulty;
    }

    public void setDifficulty(List<Integer> difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getMinRating() {
        return minRating;
    }

    public void setMinRating(Integer minRating) {
        this.minRating = minRating;
    }

    public Boolean getDogFriendly() {
        return dogFriendly;
    }

    public void setDogFriendly(Boolean dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    public Boolean getKidFriendly() {
        return kidFriendly;
    }

    public void setKidFriendly(Boolean kidFriendly) {
        this.kidFriendly = kidFriendly;
    }

    public Boolean getBikeFriendly() {
        return bikeFriendly;
    }

    public void setBikeFriendly(Boolean bikeFriendly) {
        this.bikeFriendly = bikeFriendly;
    }

    public Boolean getNearWater() {
        return nearWater;
    }

    public void setNearWater(Boolean nearWater) {
        this.nearWater = nearWater;
    }

    public Boolean getNearWoods() {
        return nearWoods;
    }

    public void setNearWoods(Boolean nearWoods) {
        this.nearWoods = nearWoods;
    }

    public List<String> getTrailSurface() {
        if (trailSurface.isEmpty()){
            trailSurface.add("paved");
            trailSurface.add("partial_paved");
            trailSurface.add("gravel");
            trailSurface.add("natural");
        }
        return trailSurface;
    }

    public void setTrailSurface(List<String> trailSurface) {
        this.trailSurface = trailSurface;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "TrailFilterDTO{" +
                "searchLocation='" + searchLocation + '\'' +
                ", minLength=" + minLength +
                ", maxLength=" + maxLength +
                ", difficulty=" + difficulty +
                ", minRating=" + minRating +
                ", dogFriendly=" + dogFriendly +
                ", kidFriendly=" + kidFriendly +
                ", bikeFriendly=" + bikeFriendly +
                ", nearWater=" + nearWater +
                ", nearWoods=" + nearWoods +
                ", trailSurface=" + trailSurface +
                ", sort='" + sort + '\'' +
                '}';
    }
}
