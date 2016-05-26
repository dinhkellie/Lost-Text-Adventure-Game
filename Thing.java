public class Thing {
    String name;
    String description;
    String thing_place;

    public Thing(String name, String description, String thing_place) {
        this.name = name;
        this.description = description;
        this.thing_place = thing_place;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return name;
    }

    public int compareTo(String target) {
        return this.name.compareTo(target);
    }
}
