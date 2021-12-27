import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SkipList {

    private int height;
    private Node head;

    static class Node {

        public String data;
        public AtomicReference<Node> right;
        public Node down;

        public Node(String data, AtomicReference<Node> right, Node down) {
            this.data = data;
            this.right = right;
            this.down = down;
        }
    }

    public SkipList(int height) {
        this.height = height;

        Node element = new Node(null, new AtomicReference<>(null), null);
        head = element;

        for (int i = 0; i < height - 1; i++) {
            Node newElementHead = new Node(null, new AtomicReference<>(null), null);
            element.down = newElementHead;
            element = newElementHead;
        }
    }

    public boolean remove(String data) {
        if (isNull(data)) {
            return false;
        }

        Node currentElement = head;
        int currentLevel = height;
        boolean flag = false;

        while (currentLevel > 0) {
            Node rightElement = currentElement.right.get();
            if (nonNull(rightElement) && rightElement.data.compareTo(data) == 0) {
                Node afterRightElement = rightElement.right.get();
                if (!flag) {
                    Node towerElement = rightElement;
                    while (nonNull(towerElement)) {
                        towerElement.right.compareAndSet(towerElement.right.get(), null);
                        towerElement = towerElement.down;
                    }
                    flag = true;
                }

                currentElement.right.compareAndSet(rightElement, afterRightElement);
            }

            if (nonNull(rightElement) && rightElement.data.compareTo(data) < 0) {
                currentElement = rightElement;
            } else {
                currentElement = currentElement.down;
                currentLevel--;
            }
        }

        return flag;
    }

    public boolean add(String data) {
        if (isNull(data)) {
            return false;
        }

        List<Node> previous = new ArrayList<>();
        List<Node> previousRight = new ArrayList<>();
        Node currentElement = head;
        int levelOfTower = randomLevel();
        int currentLevel = height;

        while (currentLevel > 0) {
            Node rightElement = currentElement.right.get();

            if (currentLevel <= levelOfTower) {
                if (isNull(rightElement) || rightElement.data.compareTo(data) >= 0) {
                    previous.add(currentElement);
                    previousRight.add(rightElement);
                }
            }

            if (nonNull(rightElement) && rightElement.data.compareTo(data) < 0) {
                currentElement = rightElement;
            } else {
                currentElement = currentElement.down;
                currentLevel--;
            }
        }

        Node downElement = null;
        for (int i = previous.size() - 1; i >= 0; i--) {
            Node newElement = new Node(data, new AtomicReference<>(previousRight.get(i)), null);

            if (nonNull(downElement)) {
                newElement.down = downElement;
            }

            if (!previous.get(i).right.compareAndSet(previousRight.get(i), newElement) && i == previous.size() - 1) {
                return false;
            }

            downElement = newElement;
        }

        return true;
    }

    public boolean contains(String data) {
        Node currentElement = head;

        while (nonNull(currentElement)) {
            Node rightElement = currentElement.right.get();
            if (nonNull(currentElement.data) && currentElement.data.compareTo(data) == 0) {
                return true;
            }
            else if (nonNull(rightElement) && rightElement.data.compareTo(data) <= 0) {
                currentElement = rightElement;
            } else {
                currentElement = currentElement.down;
            }
        }

        return false;
    }

    public void print() {
        Node current = head;

        while (nonNull(current.down)) {
            current = current.down;
        }

        current = current.right.get();

        while (nonNull(current)) {
            System.out.println(current.data);
            current = current.right.get();
        }
    }

    private int randomLevel() {
        int randomHeight = 1;

        while (randomHeight < height && Math.random() < 0.5) {
            randomHeight++;
        }

        return randomHeight;
    }
}
