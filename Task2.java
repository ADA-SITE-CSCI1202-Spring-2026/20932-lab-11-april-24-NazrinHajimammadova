class SharedResource {
    private int value;
    private boolean bChanged = false;

    public synchronized int get() {
        while (!bChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }
        }

        bChanged = false;
        notify();
        return value;
    }

    public synchronized void set(int value) {
        while (bChanged) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }

        this.value = value;
        bChanged = true;
        notify();
    }
}

class Producer implements Runnable {
    private SharedResource resource;

    public Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.set(i);
            System.out.println("Produced: " + i);
        }
    }
}

class Consumer implements Runnable {
    private SharedResource resource;

    public Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            int value = resource.get();
            System.out.println("Consumed: " + value);
        }
    }
}

public class Task2 {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread producer = new Thread(new Producer(resource));
        Thread consumer = new Thread(new Consumer(resource));

        consumer.start();
        producer.start();
    }
}