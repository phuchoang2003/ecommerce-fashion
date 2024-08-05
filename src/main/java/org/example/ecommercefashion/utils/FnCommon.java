package org.example.ecommercefashion.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Tuple;
import java.beans.FeatureDescriptor;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.example.ecommercefashion.exceptions.ExceptionHandle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class FnCommon {
  public static boolean emailValidate(String email) {
    return true;
  }

  public static void copyProperties(Object target, Object source) {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  public static void coppyNonNullProperties(Object target, Object source) {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  public static List<?> listOrEmptyList(List<?> list) {
    return (list == null) ? new ArrayList() : list;
  }

  public static List<?> convertToEntity(List<Tuple> input, Class<?> dtoClass) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return input.stream()
        .map(
            tuple -> {
              Map<String, Object> temp = new HashMap<>();
              tuple
                  .getElements()
                  .forEach(
                      element -> {
                        if (element != null) {
                          temp.put(element.getAlias(), element);
                        }
                      });
              return objectMapper.convertValue(temp, dtoClass);
            })
        .collect(Collectors.toList());
  }

  public static <T> T copyProperties(Class<T> clazz, Object source) {
    try {
      Constructor<?> targetIntance = clazz.getDeclaredConstructor(new Class[0]);
      targetIntance.setAccessible(true);
      T target = (T) targetIntance.newInstance(new Object[0]);
      BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
      return target;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static <T> T copyNonNullProperties(Class<T> clazz, Object source) {
    try {
      Constructor<?> targetIntance = clazz.getDeclaredConstructor(new Class[0]);
      targetIntance.setAccessible(true);
      T target = (T) targetIntance.newInstance(new Object[0]);
      BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
      return target;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void copyNonNullProperties(Object target, Object source) {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);

    return Stream.of(wrappedSource.getPropertyDescriptors())
        .filter(pd -> pd.getWriteMethod() != null)
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }

  public static <T> boolean isEmpty(List<T> list) {
    return (list == null || list.size() == 0);
  }

  public static boolean checkBlankString(String str) {
    return (str == null || str.isEmpty());
  }

  public static Map<String, Object> parameters(Object obj) {
    Map<String, Object> map = new HashMap<>();
    for (Field field : obj.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        map.put(field.getName(), field.get(obj));
      } catch (Exception exception) {
      }
    }
    return map;
  }

  public static <V, K> Map<K, List<V>> convertListToMap(List<V> list, Function<V, K> keyExtractor) {
    Map<K, List<V>> result = new HashMap<>();
    for (V v : list) {
      K key = keyExtractor.apply(v);
      ((List<V>) result.computeIfAbsent(key, k -> new ArrayList())).add(v);
    }
    return result;
  }

  public static String randomCode(String prefix, int randomLen) {
    Random random = new Random();
    return prefix
        + ((StringBuilder)
                random
                    .ints(97, 123)
                    .limit(randomLen)
                    .<StringBuilder>collect(
                        StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append))
            .toString()
            .toUpperCase();
  }

  public static String randomNumberCode(String prefix, int leng) {
    String minStr = "10000000000000000";
    String maxStr = "99999999999999999";
    int min = Integer.parseInt(minStr.substring(0, leng));
    int max = Integer.parseInt(maxStr.substring(0, leng));
    return prefix + (int) (Math.random() * (max - min) + min);
  }

  public static String gencode(String prefix, int lenght, long order) {
    String codeDefault = "0000000000000000000" + order;
    if (prefix == null) prefix = "";
    return prefix + codeDefault.substring(codeDefault.length() - lenght + prefix.length());
  }

  public static void main(String[] args) {
    System.out.println(gencode("a", 10, 1L));
  }

  public static <T extends Enum<T>> T getEnumValueFromString(Class<T> enumType, String name) {
    Enum[] arr$ = (Enum[]) enumType.getEnumConstants();
    int len$ = arr$.length;
    for (int i$ = 0; i$ < len$; i$++) {
      Enum enum_ = arr$[i$];
      if (enum_.name().compareToIgnoreCase(name) == 0) return (T) enum_;
    }
    throw new ExceptionHandle(HttpStatus.BAD_REQUEST, "Invalid state");
  }

  public static List<Long> stringToListLong(String ids) {
    if (!StringUtils.hasLength(ids)) {
      return new ArrayList<>();
    }
    try {
      return Arrays.stream(ids.split(","))
          .map(String::trim)
          .map(Long::parseLong)
          .collect(Collectors.toList());
    } catch (NumberFormatException e) {
      throw new ExceptionHandle(HttpStatus.BAD_REQUEST, "Parameter 'ids' contains invalid numbers");
    }
  }

  public static List<Long> stringToListLongOrNull(String ids) {
    if (!StringUtils.hasLength(ids)) {
      return null;
    }
    try {
      return Arrays.stream(ids.split(","))
          .map(String::trim)
          .map(Long::parseLong)
          .collect(Collectors.toList());
    } catch (NumberFormatException e) {
      throw new ExceptionHandle(HttpStatus.NOT_FOUND, "Parameter 'ids' contains invalid numbers");
    }
  }

  public static <T> boolean listHasValue(List<T> list) {
    return (list != null && !list.isEmpty());
  }

  public static <T> boolean setHasValue(Set<T> set) {
    return (set != null && !set.isEmpty());
  }

  public static <T> T deepCopy(T object) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(object);
      oos.close();

      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
      ObjectInputStream ois = new ObjectInputStream(bais);
      T copy = (T) ois.readObject();
      ois.close();

      return copy;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }
}
