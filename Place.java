import java.util.*;

public class Place {
    String name;
    String description;
    HashMap<String, Thing> things;
    HashMap<String, Action> actions;

    public Place(String name, String description) {
        this.name = name;
        this.description = description;
        this.things = new HashMap<String, Thing>();
        this.actions = new HashMap<String, Action>();

    }
}
