import java.util.List;
import java.util.ArrayList;


public class LinkedList<T> {
    Node<T> head;

    public LinkedList() {
        head = null;
    }

    public void append(T item) {
        Node<T> node = new Node<T>(item);
        node.next = head;
        head = node;
    }

    public List<T> iterate() {
        Node<T> current = head;
        List<T> return_array = new ArrayList<T>();
        while (current != null) {
            T current_name = current.data;
            return_array.add(current_name);
            current = current.next;
        }
        return return_array;
    }

    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }
}
