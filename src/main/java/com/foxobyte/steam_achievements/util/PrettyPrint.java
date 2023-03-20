package com.foxobyte.steam_achievements.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class PrettyPrint {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void print(String message) {
        System.out.println(message);
    }

    public static void print(Exception e) {
        System.out.println(e.getMessage());
        Arrays.stream(e.getStackTrace()).forEach(System.out::println);
    }

    public static void print(String message, Object object) {
        try {
            System.out.println(message);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(System.out::println);
        }
    }
}
