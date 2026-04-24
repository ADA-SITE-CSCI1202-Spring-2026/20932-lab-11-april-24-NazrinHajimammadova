public class Main {

    static class MathTask implements Runnable {
        @Override
        public void run() {
            long sum = 0;

            for (int i = 0; i < 10_000_000; i++) {
                sum += (long) i * i * i + i;
            }

            System.out.println(Thread.currentThread().getName() + " finished. Sum = " + sum);
        }
    }

    public static void runTest(int threadCount) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new MathTask());
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Threads used: " + threadCount);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        System.out.println("-------------------------");
    }

    public static void main(String[] args) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();

        System.out.println("Available logical processors: " + cores);
        System.out.println();

        runTest(1);
        runTest(cores);
    }
}