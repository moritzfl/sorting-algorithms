package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;
import java.util.StringJoiner;

public class HeapSortNodeValue {

  private ArrayList<Integer> numbers = new ArrayList<>();

  public HeapSortNodeValue(int initialNumber) {
    numbers.add(initialNumber);
  }

  public int getNumber() {
    return numbers.get(numbers.size() - 1);
  }

  public void replaceNumber(int number) {
    this.numbers.add(number);
  }

  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner(",");

    for (int i = 0; i < numbers.size() - 1; i++) {
        joiner.add("\\st{" + Integer.toString(numbers.get(i))+ "}");
    }

    joiner.add(Integer.toString(numbers.get(numbers.size()-1)));

    return joiner.toString();
  }
}
