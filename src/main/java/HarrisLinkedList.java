import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

public class HarrisLinkedList{

    private Node head = new Node(null, new AtomicReference<>(null));

    static class Node{

        public String  data;
        public AtomicReference<Node> next;

        public Node(String data, AtomicReference<Node> next) {
            this.data = data;
            this.next = next;
        }
    }

    public boolean remove(String data) {
        if (isNull(data)) {
            return false;
        }

        Node previousElement = head;
        while (nonNull(previousElement.next.get())) {
            Node currentElement = previousElement.next.get();
            Node nextElement = currentElement.next.get();

            if (currentElement.data.compareTo(data) == 0) {
                if (currentElement.next.compareAndSet(nextElement, null) && previousElement.next.compareAndSet(currentElement, nextElement)) {
                    return true;
                }
            } else {
                previousElement = currentElement;
            }
        }

        return false;
    }

    public void add(String data) {
        if (isNull(data)) {
            return;
        }

        Node newElement = new Node(data, new AtomicReference<>(null));
        Node currentElement = head;

        while (true) {
            Node nextElement = currentElement.next.get();

            if (nonNull(nextElement)) {
                if (nextElement.data.compareTo(data) >= 0) {
                    newElement.next = new AtomicReference<>(nextElement);
                    if (currentElement.next.compareAndSet(nextElement, newElement)) {
                        return;
                    }
                } else {
                    currentElement = nextElement;
                }
            } else if (currentElement.next.compareAndSet(null, newElement)) {
                return;
            }
        }
    }

    public boolean contains(String data) {
        Node currentElement = head.next.get();

        while (nonNull(currentElement)) {
            if (currentElement.data.compareTo(data) == 0) {
                return true;
            }

            currentElement = currentElement.next.get();
        }

        return false;
    }

    public void print() {
        Node current = head.next.get();
        while (nonNull(current)) {
            System.out.println(current.data);
            current = current.next.get();
        }
    }
}
