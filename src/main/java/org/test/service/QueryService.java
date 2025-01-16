package org.test.service;

import org.test.model.Plane;

import java.math.BigDecimal;
import java.util.*;

public class QueryService {

    public void processQuery(String query, Scanner scanner, Map<Integer, Plane> planesMap) {
        switch (query) {
            case "P":
                updateMaxPassengers(scanner, planesMap);
                break;
            case "C":
                withdrawPlaneFromRoute(scanner, planesMap);
                break;
            case "A":
                assignPlaneToNewRoute(scanner, planesMap);
                break;
            case "Q":
                queryAvailableSeats(scanner, planesMap);
                break;
        }
    }

    private void updateMaxPassengers(Scanner scanner, Map<Integer, Plane> planesMap) {
        int plane = scanner.nextInt();
        int passengersSeats = scanner.nextInt();
        int day = scanner.nextInt();

        if (planesMap.containsKey(plane)) {
            planesMap.entrySet().stream()
                    .filter(checkPlane -> checkPlane.getValue().getId() == plane)
                    .forEach(entry -> {
                        Plane loopPlane = entry.getValue();

                        updateValidityDays(loopPlane.getMaxPassengersWithDays(), day, loopPlane);
                        loopPlane.setLastChangeDay(day);
                        loopPlane.getMaxPassengersWithDays()
                                .put(passengersSeats, day - loopPlane.getLastChangeDay());

                    });
        }

    }

    private void withdrawPlaneFromRoute(Scanner scanner, Map<Integer, Plane> planesMap) {
        int plane = scanner.nextInt();
        int day = scanner.nextInt();

        if (planesMap.containsKey(plane)) {
            planesMap.entrySet().stream()
                    .filter(checkPlane -> checkPlane.getValue().getId() == plane)
                    .forEach(entry -> {
                        Plane loopPlane = entry.getValue();
                        loopPlane.setLastChangeDay(day);
                        loopPlane.setActive(false);
                    });
        }
    }

    private void assignPlaneToNewRoute(Scanner scanner, Map<Integer, Plane> planesMap) {
        int plane = scanner.nextInt();
        int passengersSeats = scanner.nextInt();
        int day = scanner.nextInt();

        if (planesMap.containsKey(plane)) {
            planesMap.entrySet().stream()
                    .filter(checkPlane -> checkPlane.getValue().getId() == plane)
                    .forEach(entry -> {
                        Plane loopPlane = entry.getValue();
                        loopPlane.setMaxPassengersWithDays(new LinkedHashMap<>() {
                            {
                                put(passengersSeats, day - loopPlane.getLastChangeDay());
                            }
                        });
                        loopPlane.setLastChangeDay(day);
                        loopPlane.setActive(true);
                    });
        }
    }

    private void queryAvailableSeats(Scanner scanner, Map<Integer, Plane> planesMap) {
        int routeBegin = scanner.nextInt();
        int routeEnd = scanner.nextInt();
        int day = scanner.nextInt();


        BigDecimal sum = planesMap.values()
                .stream()
                .filter(plane -> plane.getId() >= routeBegin &&
                        plane.getId() <= routeEnd &&
                        plane.isActive())
                .map(plane -> {

                    Map<Integer, Integer> maxPassengersWithDays = plane.getMaxPassengersWithDays();
                    updateValidityDays(maxPassengersWithDays, day, plane);

                    return maxPassengersWithDays.entrySet()
                            .stream()
                            .map(entry -> BigDecimal.valueOf(entry.getKey())
                                    .multiply(BigDecimal.valueOf(entry.getValue())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }).reduce(BigDecimal.ZERO, BigDecimal::add);


        System.out.println(sum);
    }

    private void updateValidityDays(Map<Integer, Integer> maxPassengersWithDays, int day, Plane plane) {

        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(maxPassengersWithDays.entrySet());
        if (!entries.isEmpty()) {
            Map.Entry<Integer, Integer> lastEntry = entries.get(entries.size() - 1);
            maxPassengersWithDays.put(lastEntry.getKey(), day - plane.getLastChangeDay());
        }
    }
}
