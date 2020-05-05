package com.sortable.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Application application = new Application();

        Scanner scanner = new Scanner(System.in);
        StringBuilder inputString = new StringBuilder();

        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split("\\s");
            for (String token : tokens) {
                inputString.append(token);
            }
        }

        scanner.close();

        application.runAuction(inputString.toString());

        try {
            System.out.println(application.toJson());
        } catch (JsonProcessingException e) {
            System.out.println("Unable to print highest bidders: " + e.getMessage());
        }
    }
}
