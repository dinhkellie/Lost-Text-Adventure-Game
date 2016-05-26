import java.util.*;

public class Action {
    String command;
    String toplace;
    String fromplace;
    LinkedList<String> needThings;

    public Action(String command, String fromplace, String toplace, LinkedList<String> needThings) {
        this.command = command;
        this.fromplace = fromplace;
        this.toplace = toplace;
        this.needThings = needThings;
    }
}
