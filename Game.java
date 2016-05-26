import java.io.*;
import java.util.*;

public class Game {
    Stack<Thing> backpack;
    String current_place_name;
    HashMap<String, Place> places;

    public Game(String fileName) {
        places = new HashMap<String, Place>();
        backpack = new Stack<Thing>();
        current_place_name = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts[0].equals("place")) {
                    places.put(parts[1], new Place(parts[1], parts[2]));

                    if (current_place_name == null) {
                        current_place_name = parts[1];
                    }
                } else if (parts[0].equals("object")) {
                    Thing thing = new Thing(parts[1], parts[2], parts[3]);

                    // Testing that objects are being added to their place
                    // System.out.println("Putting " +thing.name+ " in "+parts[3]);

                    String thing_place = parts[3];
                    Place current_place = places.get(thing_place);
                    current_place.things.put(parts[1], thing);



                } else if (parts[0].equals("command")) {

                    LinkedList<String> need_things_list = new LinkedList<String>();
                    if (parts.length > 4) {
                        String[] needthings = parts[4].split(",");
                        for (String item: needthings) {
                            need_things_list.append(item);
                        }
                    }
                    Action action = new Action(parts[1], parts[2], parts[3], need_things_list);
                    String from_place = parts[2];
                    Place current_place = places.get(from_place);
                     // Testing that commands are being added to their places
                    // System.out.println("Adding "+parts[1]+" from "+ from_place +" to "+parts[3]);
                    current_place.actions.put(parts[1], action);

                }
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public boolean search(String start, String end) {
        Place current = places.get(start);
        boolean found = false;
        HashSet<String> visited = new HashSet<String>();
        Deque<Place> queue = new Deque<Place>();
        // LinkedList<String> shortest_path = new LinkedList<String>();
        queue.append_front(current);

        while (!queue.isEmpty()) {
            current = queue.pop_front();
            // Testing that loop is going through each place
            // System.out.println("Visiting: " + current.name + "...");
            if (current.name.equals(end)) {
                System.out.println("Path exists to "+end);
                System.out.println(visited.toString());
                found = true;
                break;
            }
            if (! visited.contains(current.name)) {
                // System.out.println("   expanding: " + current.name + "...");
                visited.add(current.name);
                // expand:
                for (Action action: current.actions.values()) {
                    Place toplace = places.get(action.toplace);
                    queue.append_front(toplace);
                }
            }
        }
        if (!found) {
            System.out.println("No path exists to "+end);
        }
        return found;
    }

    public void printObject(HashMap<String, Thing> current_objects) {


        // Objects:

        // HashMap<String, Thing> current_objects = current_place.things;


        for (String object_name : current_objects.keySet()) {
            Thing object = current_objects.get(object_name);
            if (! backpack.contains(object.name)) {
                System.out.println(" > Object here: "+object.name+" - "+object.description+"\n");
            }

        }


    }
    // Method to print the contents of the backpack
    public void printBackpack() {
        if (backpack.stack == null) {
            System.out.println("Backpack is empty");
        } else {
            System.out.println("Backpack contains: "+backpack.print());
        }
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        String input = null;
        try {
            BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return input;
    }

    public void play() {
        System.out.println("Welcome to the game. Type help if you need it.");
        Place current_place = places.get(current_place_name);


        HashMap<String, Thing> current_objects = current_place.things;

        System.out.println("Location:    " + current_place.name+ ": \n");
        System.out.println(current_place.description);

        System.out.println("__________________________________________________________________");

        printObject(current_objects);

        System.out.println("Available actions: "+ current_place.actions.keySet());
        printBackpack();


        while (true) {

            // Begin Game Loop

            // Input:
            String action = getInput("--> ");
            int length = action.length();

            // Gets input action from action hashmap
            Action current_action = current_place.actions.get(action);

            // If the action is "drop"
            if ((length >= 4) && (action.substring(0,4).equals("drop"))) {
                if (! backpack.empty()) {
                    Thing drop = backpack.pop();
                    System.out.println("Dropped "+drop.name);
                } else {
                    System.out.println("Backpack is already empty");
                }
                System.out.println("_______________________________");
                printObject(current_objects);
                System.out.println("Available actions: "+ current_place.actions.keySet());
                printBackpack();

            // If the action is "pick up"
            } else if ((length >= 8) && (action.substring(0,7).equals("pick up"))) {

                String object_name = action.substring(8, action.length());
                Thing object = current_objects.get(object_name);
                if (object != null) {
                    if (! backpack.contains(object.name)) {
                        backpack.push(object);
                        System.out.println("Added "+object_name+" to backpack");

                        System.out.println("_______________________________");
                        printObject(current_objects);
                        System.out.println("Available actions: "+ current_place.actions.keySet());
                        printBackpack();
                    } else {
                        System.out.println("You already have "+object.name);
                        System.out.println("_______________________________");
                        printObject(current_objects);
                        System.out.println("Available actions: "+ current_place.actions.keySet());
                        printBackpack();
                    }

                }  else {
                    System.out.println("Sorry, could not find "+object_name);
                    System.out.println("_______________________________");
                    printObject(current_objects);
                    System.out.println("Available actions: "+ current_place.actions.keySet());
                    printBackpack();

                }
            // If the action is "search"
            } else if ((length >= 7) && (action.substring(0,6).equals("search"))) {
                String start = current_place.name;
                String end = action.substring(7, action.length());
                search(start, end);

            // If the action is "help"
            } else if ((length >= 4) && (action.substring(0,4).equals("help"))) {
                System.out.println("Help: ");
                System.out.println("Move from place to place by typing the available actions then pressing enter.");
                System.out.println("Some places require items. Pick up an item by typing pick up + item name.");
                System.out.println("Drop an item by typing the word drop. This drops the most recently picked up item.");
                System.out.println("Type 'search' + place name to see if a path exists");
                System.out.println("_______________________________");
                printObject(current_objects);
                System.out.println("Available actions: "+ current_place.actions.keySet());
                printBackpack();

            // If the action is null
            } else if (current_action == null) {
                System.out.println(action+" is an invalid command");
                System.out.println("_______________________________");
                printObject(current_objects);
                System.out.println("Available actions: "+ current_place.actions.keySet());
                printBackpack();

            } else {

                // Actually go to place

                // Check if you have required item in backpack
                LinkedList<String> current_need_things = current_action.needThings;

                if (current_need_things.isEmpty()) {

                    String to_place_name = current_action.toplace;
                    current_place = places.get(to_place_name);

                    current_objects = current_place.things;


                    System.out.println(current_place.name+": \n");
                    System.out.println(current_place.description);
                } else {

                    boolean next = true;

                    for (String item_name : current_need_things.iterate()) {

                        if (! backpack.contains(item_name)) {
                            System.out.println("Sorry, you need: "+item_name);
                            next = false;
                        }
                    }

                    if (next) {
                        String to_place_name = current_action.toplace;
                        current_place = places.get(to_place_name);

                        // update current objects
                        current_objects = current_place.things;

                        System.out.println(current_place.name+":");
                        if (!current_need_things.isEmpty()) {
                            System.out.print("(Item(s) needed to get here: "+current_need_things.iterate().toString()+")");         \

                        }
                        System.out.println("\n");



                        System.out.println(current_place.description);
                    }

                }

                System.out.println("_______________________________");
                printObject(current_objects);
                System.out.println("Available actions: "+ current_place.actions.keySet());

                printBackpack();


            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game(args[0]);
        game.play();
    }
}



