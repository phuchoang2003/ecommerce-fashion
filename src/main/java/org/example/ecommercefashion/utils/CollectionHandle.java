package org.example.ecommercefashion.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionHandle {
  public static <T> Set<T> findDuplicateInStream(Stream<T> stream) {
    Set<T> items = new HashSet<>();
    return (Set<T>) stream.filter(n -> !items.add(n)).collect(Collectors.toSet());
  }
}
