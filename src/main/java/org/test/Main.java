package org.test;

import org.test.model.Plane;
import org.test.service.QueryService;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        QueryService queryService = new QueryService();
        Map<Integer, Plane> planesMap = new HashMap<>();

        Scanner scanner = new Scanner(System.in);

        int routes = scanner.nextInt();
        int questions = scanner.nextInt();

        for (int i = 1; i <= routes; i++) {
            int maxPassengers = scanner.nextInt();
            LinkedHashMap<Integer, Integer> passengersWithDays = new LinkedHashMap<>();
            passengersWithDays.put(maxPassengers, 1);

            Plane plane = new Plane(i, passengersWithDays);
            planesMap.put(i, plane);

        }

        for (int x = 0; x < questions; x++) {
            String query = scanner.next();
            queryService.processQuery(query, scanner, planesMap);
        }

        scanner.close();
    }



}
