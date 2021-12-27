public class HarrisLinkedListThread implements Runnable {

    private HarrisLinkedList harrisLinkedList;

    public HarrisLinkedListThread(HarrisLinkedList harrisLinkedList) {
        this.harrisLinkedList = harrisLinkedList;
    }

    @Override
    public void run() {
        String currThreadName = Thread.currentThread().getName();
        harrisLinkedList.add(currThreadName);
    }
}
