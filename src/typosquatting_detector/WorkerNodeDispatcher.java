package typosquatting_detector;

import java.util.*;

public class WorkerNodeDispatcher {

    private static final int NUM_WORKERS = 10;
    private final Queue<ClientWorkImpl> workers;

    public WorkerNodeDispatcher() {
        workers = new LinkedList<>();

        for (int i = 0; i < NUM_WORKERS; i++) {
            workers.add(new ClientWorkImpl(workers));
        }
    }

    public void dispatch(List<String> urls) throws InterruptedException {
        for (String url: urls) {
            ClientWorkImpl worker;
            synchronized (workers) {
                if (workers.isEmpty()) {
                    workers.wait();
                }

                worker = workers.remove();
            }

            worker.assignURL(url);
            Thread thread = new Thread(worker);
            thread.start();
        }
    }

    // For testing
    /*public static void main(String[] args) {
        WorkerNodeDispatcher dispatcher = new WorkerNodeDispatcher();
        String[] urlArr = { "www.google.com", "www.yahoo.com", "www.facebook.com", "www.wikipedia.org", "www.stonybrook.edu",
                            "cs.stonybrook.edu", "www.reddit.com", "www.piazza.com", "www.linkedin.com", "www.bing.com",
                            "www.paypal.com", "www.amazon.com", "www.amazon.co.uk", "www.netflix.com", "drive.google.com" };

        List<String> urls = Arrays.asList(urlArr);
        try {
            dispatcher.dispatch(urls);
        } catch (InterruptedException e) {
            System.err.println("Dispatcher failed");
        }
    }*/
}