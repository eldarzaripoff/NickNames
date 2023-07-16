package org.example;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static CountDownLatch latch = new CountDownLatch(3);
    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);


    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        new Thread(() -> {
            for (String text : texts) {
                switch (text.length()) {
                    case 3 -> {
                        if(isAllTheSame(text))
                            counter3.incrementAndGet();
                    }
                    case 4 -> {
                        if (isAllTheSame(text))
                            counter4.incrementAndGet();
                    }
                    case 5 -> {
                        if (isAllTheSame(text))
                            counter5.incrementAndGet();
                    }
                }
            }
            latch.countDown();
        }).start();
        new Thread(() -> {
            for (String text : texts) {
                switch (text.length()) {
                    case 3 -> {
                        if(isItPalindrome(text))
                            counter3.incrementAndGet();
                    }
                    case 4 -> {
                        if(isItPalindrome(text))
                            counter4.incrementAndGet();
                    }
                    case 5 -> {
                        if(isItPalindrome(text))
                            counter5.incrementAndGet();
                    }
                }
            }
            latch.countDown();
        }).start();
        new Thread(() -> {
            for (String text : texts) {
                switch (text.length()) {
                    case 3 -> {
                        if(isLettersIncrease(text))
                            counter3.incrementAndGet();
                    }
                    case 4 -> {
                        if(isLettersIncrease(text))
                            counter4.incrementAndGet();
                    }
                    case 5 -> {
                        if(isLettersIncrease(text))
                            counter5.incrementAndGet();
                    }
                }
            }
            latch.countDown();
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Красивых слов длинной 3: " + counter3.get() + " шт");
        System.out.println("Красивых слов длинной 4: " + counter4.get() + " шт");
        System.out.println("Красивых слов длинной 5: " + counter5.get() + " шт");
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
    public static boolean isAllTheSame (String text) {
        char firstChar = text.charAt(0);
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == firstChar) {
                return true;
            }
        }
        return false;
    }
    public static boolean isItPalindrome(String text) {
        for (int i = 0; i < text.length() / 2; i++) {
            if(text.charAt(i) != text.charAt(text.length() - i - 1))
                return false;
        }
        return true;
    }
    public static boolean isLettersIncrease(String text) {
        char previous = 'a' - 1;
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current < 'a' || current > 'c' || current <= previous) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}