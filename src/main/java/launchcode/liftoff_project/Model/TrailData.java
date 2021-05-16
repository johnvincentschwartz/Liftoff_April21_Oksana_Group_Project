package launchcode.liftoff_project.Model;

import java.util.Collections;
import java.util.ArrayList;

public class TrailData {
    public static ArrayList<Trail> searchValue(String value, Iterable<Trail> allTrails) {
        String lower_val = value.toLowerCase();

        ArrayList<Trail> results = new ArrayList<>();

        for (Trail trail : allTrails){
            if (trail.getName().toLowerCase().contains(lower_val)) {
                results.add(trail);
            } else if (trail.getName().toString().toLowerCase().contains(lower_val)) {
                results.add(trail);
            } else if (trail.getCity().toString().toLowerCase().contains(lower_val)) {
                results.add(trail);
            } else if (trail.getState().toString().toLowerCase().contains(lower_val)){
                results.add(trail);
            } else if (trail.toString().toLowerCase().contains(lower_val)) {
                results.add(trail);
            }
        }

        Collections.sort(results);
        return results;
    }
}
