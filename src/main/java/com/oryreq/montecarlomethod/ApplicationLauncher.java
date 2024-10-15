package com.oryreq.montecarlomethod;

import java.util.Arrays;

public class ApplicationLauncher {

    public static void main(String[] args) {
        System.out.println("AAA");
        Arrays.stream(args).forEach(System.out::println);
        Application.main(args);
    }

}
