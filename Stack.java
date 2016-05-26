import java.util.List;
import java.util.ArrayList;

public class Stack<T> {
    Node<T> stack;


    Stack() {
        stack = null;

    }

    public T pop() { //public node pop
        if (stack != null) {
            Node<T> node = stack;
            stack = stack.next;
            return node.data; //return node
        }
        return null;
    }

    public void push(T object) {
        Node<T> node = new Node<T>(object);
        node.next = stack;
        stack = node;
    }

    public boolean contains(String target_name) {
        Node<T> current = stack;
        while (current != null) {
            T current_thing = current.data;
            String current_thing_name = current_thing.toString();
            if (current_thing_name.equals(target_name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean empty() {
        Node<T> current = stack;
        if (current == null) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> print() {
        List<String> object_names = new ArrayList<String>();        
        Node<T> current = stack;
        while (current != null) {
            T current_thing = current.data;
            String current_thing_name = current_thing.toString();
            object_names.add(current_thing_name);

            //System.out.println(current_thing);

            current=current.next;
        }

        return object_names;
    }

}

