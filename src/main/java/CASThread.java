public class CASThread  implements Runnable {

    private CAS cas;

    public CASThread(CAS cas) {
        this.cas = cas;
    }

    @Override
    public void run() {
        try {
            cas.LOCK();
            cas.WAIT();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            cas.UNLOCK();
        }
    }
}
