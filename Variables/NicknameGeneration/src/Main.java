import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count3Letters = new AtomicInteger(0);
    public static AtomicInteger count4Letters = new AtomicInteger(0);
    public static AtomicInteger count5Letters = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread polindromCounter = new Thread(() -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (String text : texts) {
                if (isPolindrom(text, stringBuilder)) {
                    countIncrement(text);
                }
            }
        });

        Thread singleCharacterCounter = new Thread(() -> {
            for (String text : texts) {
                if (isSingleChar(text)) {
                    countIncrement(text);
                }
            }
        });

        Thread orderCounter = new Thread(() -> {
            for (String text : texts) {
                if (inOrder(text)) {
                    countIncrement(text);
                }
            }
        });
        polindromCounter.start();
        singleCharacterCounter.start();
        orderCounter.start();

        polindromCounter.join();
        singleCharacterCounter.join();
        orderCounter.join();

        System.out.printf("Красивых слов с длиной 3: %s шт\n" +
                "Красивых слов с длиной 4: %s шт\n" +
                "Красивых слов с длиной 5: %s шт", count3Letters, count4Letters, count5Letters);

    }

    private static void countIncrement(String text) {
        if (text.length() == 3) {
            count3Letters.getAndIncrement();
        } else if (text.length() == 4) {
            count4Letters.getAndIncrement();
        } else {
            count5Letters.getAndIncrement();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPolindrom(String text, StringBuilder stringBuilder) {
        stringBuilder.append(text)
                .reverse();
        String revers = stringBuilder.reverse().toString();
        return text.equals(revers);
    }

    public static boolean isSingleChar(String text) {
        char firstChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (firstChar != text.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean inOrder(String text) {
        char[] charArr = text.toCharArray();
        Arrays.sort(charArr);
        String sortedText = new String(charArr);
        return text.equals(sortedText);
    }
}