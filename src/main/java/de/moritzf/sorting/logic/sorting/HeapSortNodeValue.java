package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;
import java.util.StringJoiner;

public class HeapSortNodeValue {

    private ArrayList<Integer> numbers = new ArrayList<>();

    public HeapSortNodeValue(int initialNumbers) {
        numbers.add(initialNumbers);
    }

    public int getNumber() {
        return numbers.get(numbers.size() - 1);
    }

    public void setNumber(int number) {
        this.numbers.add(number);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("numbers = " + numbers)
                .toString();
    }
}
