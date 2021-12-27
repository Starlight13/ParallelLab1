import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

public class MichaelScottQueue {

    private Node start = new Node(null, new AtomicReference<>(null));
    private AtomicReference<Node> head = new AtomicReference<>(start);
    private AtomicReference<Node> tail = new AtomicReference<>(start);

    static class Node {

        public String data;
        public AtomicReference<Node> next;

        public Node(String data, AtomicReference<Node> next) {
            this.data = data;
            this.next = next;
        }
    }

    public boolean remove() {
        while (true) {
            Node currentHead = head.get();
            Node currentTail = tail.get();
            Node nextHead = currentHead.next.get();

            if (currentHead == currentTail) {
                if (nextHead == null) {
                    System.out.println("Queue is empty!");
                    return false;
                } else {
                    tail.compareAndSet(currentTail, currentTail.next.get());
                }
            } else {
                if (head.compareAndSet(currentHead, nextHead)) {
                    return true;
                }
            }
        }
    }

    public void add(String data) {
        Node newTail = new Node(data, new AtomicReference<>(null));

        while(true) {
            Node currentTail = tail.get();

            if (currentTail.next.compareAndSet(null, newTail)) {
                tail.compareAndSet(currentTail, newTail);
                return;
            } else {
                tail.compareAndSet(currentTail, currentTail.next.get());
            }
        }
    }

    public void print() {
        Node current = head.get();

        while (current != null) {
            System.out.println(current.data);
            current = current.next.get();
        }
    }
}
