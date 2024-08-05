package org.example.ecommercefashion.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonParser {
    private static ObjectMapper mObjectMapper;

    private static ObjectMapper getMapper() {
        if (mObjectMapper == null) {
            mObjectMapper = new ObjectMapper();
            mObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return mObjectMapper;
    }

    public static <T> T entity(String json, Class<T> tClass) {
        try {
            return (T)getMapper().readValue(json, tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List setToList(Set param) {
        List list = new ArrayList();
        list.addAll(param);
        return list;
    }

    public static <T> ArrayList<T> arrayList(String json, Class<T> tClass) {
        try {
            TypeFactory typeFactory = getMapper().getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(ArrayList.class, tClass);
            return (ArrayList<T>)getMapper().readValue(json, (JavaType)collectionType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Integer> intArrayList(int[] ints) {
        return IntStream.of(ints).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    public static int[] listToIntArray(List<Integer> list) {
        return list.stream().mapToInt(i -> i.intValue()).toArray();
    }

    public static Map<String, Object> objectToMap(Object object) {
        try {
            Map<String, Object> maps = (Map<String, Object>)getMapper().convertValue(object, new TypeReference<Map<String, Object>>() {

            });
            return maps;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static Map<String, Object> objectToMapSnackCase(Object object) {
        Map<String, String> maps = (Map<String, String>)getMapper().convertValue(object, new TypeReference<Map<String, String>>() {

        });
        Map<String, Object> mapSnack = new HashMap<>();
        for (Map.Entry<String, String> entry : maps.entrySet())
            mapSnack.put(camelToSnakeCase(entry.getKey()), entry.getValue());
        return mapSnack;
    }

    public static Map<String, Object> toMapSnackCaseIgnoreNull(Object object) {
        Map<String, Object> maps = (Map<String, Object>)getMapper().convertValue(object, new TypeReference<Map<String, Object>>() {

        });
        Map<String, Object> mapSnack = new HashMap<>();
        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            if (Objects.isNull(entry.getValue()))
                continue;
            mapSnack.put(camelToSnakeCase(entry.getKey()), entry.getValue());
        }
        return mapSnack;
    }

    public static String camelToSnakeCase(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                if (i > 0)
                    snakeCase.append('_');
                snakeCase.append(Character.toLowerCase(currentChar));
            } else {
                snakeCase.append(currentChar);
            }
        }
        return snakeCase.toString();
    }

    public static String mapToQueryStringUTF8(Map<String, Object> map) {
        try {
            List<Comparable> fieldNames = new ArrayList(map.keySet());
            Collections.sort(fieldNames);
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<Comparable> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String)itr.next();
                String fieldValue = (map.get(fieldName) == null) ? "" : map.get(fieldName).toString();
                if (fieldValue != null && fieldValue.length() > 0) {
                    stringBuilder.append(fieldName);
                    stringBuilder.append('=');
                    stringBuilder.append(URLDecoder.decode(fieldValue, "UTF-8"));
                    if (itr.hasNext())
                        stringBuilder.append('&');
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String mapToQueryString(Map<String, String> map) {
        try {
            List<Comparable> fieldNames = new ArrayList(map.keySet());
            Collections.sort(fieldNames);
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<Comparable> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String)itr.next();
                String fieldValue = map.get(fieldName);
                if (fieldValue != null && fieldValue.length() > 0) {
                    stringBuilder.append(fieldName);
                    stringBuilder.append('=');
                    stringBuilder.append(fieldValue);
                    if (itr.hasNext())
                        stringBuilder.append('&');
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }
}