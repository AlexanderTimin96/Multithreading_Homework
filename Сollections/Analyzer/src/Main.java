import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

    public static int SIZE = 10_000;
    public static int LENGTH = 10_000;

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < SIZE; i++) {
                String s = generateText("abc", LENGTH);
                try {
                    queueA.put(s);
                    queueB.put(s);
                    queueC.put(s);
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Максимальное количество символов /a/ в строке = " + findMaxRepetition('a', queueA));
            } catch (InterruptedException e) {
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Максимальное количество символов /b/ в строке = " + findMaxRepetition('b', queueB));
            } catch (InterruptedException e) {
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Максимальное количество символов /c/ в строке = " + findMaxRepetition('c', queueC));
            } catch (InterruptedException e) {
            }
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int findMaxRepetition(char ch, BlockingQueue<String> queue) throws InterruptedException {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            String str = queue.take();
            char[] charArray = str.toCharArray();
            for (char c : charArray) {
                if (ch == c) {
                    count++;
                }
            }
        }
        return count;
    }
}