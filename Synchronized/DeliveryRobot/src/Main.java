import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Map<Integer, Integer> sizeToFreq = new TreeMap<>();
        List<Thread> threads = new ArrayList<>();

        Thread threadPrinter = new Thread(() -> {
            synchronized (sizeToFreq) {
                Map.Entry<Integer, Integer> max;
                while (!Thread.interrupted()) {
                    try {
                        sizeToFreq.wait();
                    } catch (InterruptedException e) {

                    }
                    max = findMax(sizeToFreq);
                    System.out.printf("Повторений %s (встретилось %s раз)\n", max.getKey(), max.getValue());
                }
            }
        });
        threadPrinter.start();

        for (int i = 0; i < 100; i++) {
            Thread threadCal = new Thread(() -> {
                int repeats = 0;
                String route = generateRoute("RLRFR", 100);
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) != 'R') {
                        continue;
                    }
                    repeats += 1;
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(repeats)) {
                        int frequency = sizeToFreq.get(repeats) + 1;
                        sizeToFreq.put(repeats, frequency);
                    } else {
                        sizeToFreq.put(repeats, 1);
                    }
                    sizeToFreq.notify();
                }
            });
            threads.add(threadCal);
            threadCal.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        threadPrinter.interrupt();
        printResult(sizeToFreq);
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void printResult(Map<Integer, Integer> result) {
        Map.Entry<Integer, Integer> max = findMax(result);

        System.out.printf("Самое частое количество повторений %s (встретилось %s раз)\n", max.getKey(), max.getValue());
        System.out.println("Другие размеры:");

        result.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .forEach(e -> System.out.println(" - " + e.getKey() + " (" + e.getValue() + " раз)"));
    }

    private static Map.Entry<Integer, Integer> findMax(Map<Integer, Integer> result) {
        return result.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
    }
}