package com.example.demo.helpers;

import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.model.User;

import java.util.Random;

public class DataHelper {

    public static String getAlphabeticString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String getNumeric(int targetStringLength) {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 57; // letter 'z'
        Random random = new Random();
        if (targetStringLength<1) {
            throw new IllegalArgumentException("Wrong");
        }

            return "+" + String.valueOf(random.nextInt((int) Math.pow(10, targetStringLength - 1),
                    (int) Math.pow(10, targetStringLength) - 1));
    }


    public static Integer getNumber(int targetStringLength) {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 57; // letter 'z'
        Random random = new Random();
        if (targetStringLength<1) {
            throw new IllegalArgumentException("Wrong");
        }

        return random.nextInt((int) Math.pow(10, targetStringLength - 1),
                (int) Math.pow(10, targetStringLength) - 1);
    }


    public  static News.NewsBuilder<?,?> prepareNews() {
        return News.builder().title("testNews").content("test");
    }

    public  static Category.CategoryBuilder<?,?> prepareCategory() {
        return Category.builder().name("test1");
    }

    public  static Category.CategoryBuilder<?,?> preparePlainCategory() {
        return Category.builder();
    }

    public  static User.UserBuilder<?,?> prepareUser() {
        return User.builder().username("Test");
    }
}
