package ru.ntology;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class PhoneBook {
    private final Map<String, String> phoneBook = new TreeMap<>();

    public int add(String name, String number) {
        phoneBook.put(name, number);
        return phoneBook.size();
    }

    public String findByNumber(String number) {
        return phoneBook.entrySet()
                .stream()
                .filter((x) -> x.getValue().equals(number))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }

    public String findByName(String name) {
        return  phoneBook.get(name);
    }

    public ArrayList<String> printAllName() {
        return null;
    }
}
