import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        task1();
//        task2();
//        task3();
        task4();
    }

    public static void task1() throws InterruptedException {
        LinkedBlockingQueue<Runnable> listOfWaitingThreads = new LinkedBlockingQueue<>();
        CAS cas1 = new CAS(listOfWaitingThreads, "cas1");
        CAS cas2 = new CAS(listOfWaitingThreads, "cas2");

        for (int i = 0; i < 5; i++) {
            new Thread(new CASThread(cas1)).start();
            new Thread(new CASThread(cas2)).start();
        }

        // Notify
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            cas1.NOTIFY();
        }

        // NotifyAll
//        for (int i = 0; i < 5; i++) {
//            Thread.sleep(1000);
//            cas1.NOTIFYALL();
//        }

    }

    public static void task2() throws InterruptedException {
        SkipList skipList = new SkipList(10);
        Thread[] arr = new Thread[10];

        for (int i = 0; i < 10; i++) {
            arr[i] = new Thread(new SkipListThread(skipList));
            arr[i].start();
        }

        for (int i = 0; i < 10; i++) {
            arr[i].join();
        }
        skipList.print();
    }

    public static void task3() throws InterruptedException {
        MichaelScottQueue michaelScottQueue = new MichaelScottQueue();
        Thread[] arr = new Thread[10];

        for (int i = 0; i < 10; i++) {
            arr[i] = new Thread(new MichaelScottQueueThread(michaelScottQueue));
            arr[i].start();
        }

        for (int i = 0; i < 10; i++) {
            arr[i].join();
        }

        michaelScottQueue.print();
    }

    public static void task4() throws InterruptedException {
        HarrisLinkedList harrisLinkedList = new HarrisLinkedList();
        Thread[] arr = new Thread[10];

        for (int i = 0; i < 10; i++) {
            arr[i] = new Thread(new HarrisLinkedListThread(harrisLinkedList));
            arr[i].start();
        }

        for (int i = 0; i < 10; i++) {
            arr[i].join();
        }

        harrisLinkedList.print();
    }
}
