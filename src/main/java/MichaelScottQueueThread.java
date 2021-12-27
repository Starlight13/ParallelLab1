public class MichaelScottQueueThread implements Runnable {

    private MichaelScottQueue michaelScottQueue;

    public MichaelScottQueueThread(MichaelScottQueue michaelScottQueue) {
        this.michaelScottQueue = michaelScottQueue;
    }

    @Override
    public void run() {
        michaelScottQueue.add(Thread.currentThread().getName());
    }
}
