public class SkipListThread implements Runnable {

    private SkipList skipList;

    public SkipListThread(SkipList skipList) {
        this.skipList = skipList;
    }

    @Override
    public void run() {
        String currThreadName = Thread.currentThread().getName();
        while (!skipList.add(currThreadName)) {
            System.out.println(currThreadName + " failed to add");
        }

        System.out.println(currThreadName + " adding Success!");
    }
}
