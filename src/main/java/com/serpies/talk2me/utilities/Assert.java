package com.serpies.talk2me.utilities;

import java.util.List;
import java.util.Map;

public class Assert {

    public static void isNull(Object obj, String message){

        if (obj == null){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isNull(Object obj, RuntimeException exception){

        if (obj == null){
            throw exception;
        }

    }

    public static void ifCondition(boolean condition, String message){

        if (condition){
            throw new IllegalArgumentException(message);
        }

    }

    public static void ifCondition(boolean condition, RuntimeException exception){

        if (condition){
            throw exception;
        }

    }

    public static void isEmpty(List<?> obj, String message){

        if (obj.isEmpty()){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(Map<?, ?> obj, String message){

        if (obj.isEmpty()){
            throw new IllegalArgumentException(message);
        }

    }

    public static <T> void isEmpty(T[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(int[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(double[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }


    public static void isEmpty(float[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(boolean[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(char[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(byte[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(long[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

    public static void isEmpty(short[] obj, String message){

        if (obj.length == 0){
            throw new IllegalArgumentException(message);
        }

    }

}
