package ru.ntology;

import java.util.Map;
import java.util.TreeMap;

public class PhoneBook {
    private final Map<String, String> phoneBook = new TreeMap<>();

    public int add(String name, String number) {
        phoneBook.put(name, number);
        return phoneBook.size();
    }

    public String findByNumber(String number) {
        return null;
    }
}
