public class Deque<T> {
    Node<T> head;
    Node<T> tail;

    public void append_front(T item) {
    // appends to front of list:
        Node<T> node = new Node<T>(item);
        node.next = head;
        head = node;
    }

    public void append_end(T item) {
        // appends to tail of list:
        Node<T> node = new Node<T>(item);
        node.prev = tail;
        tail = node;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public T pop_front() {
        Node<T> temp = head;
        head = head.next;
        return temp.data;
    }

    public T pop_end() {
        Node<T> temp = tail;
        tail = tail.prev;
        return temp.data;
    }


}
