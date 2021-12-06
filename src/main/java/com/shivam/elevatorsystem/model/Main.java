package com.shivam.elevatorsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int numberOfFloors = 10;

        Elevator elevatorA = new Elevator(numberOfFloors, 0, "A");
        Elevator elevatorB = new Elevator(numberOfFloors, 0, "B");
        ArrayList<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(elevatorA);
        elevatorList.add(elevatorB);

        List<Floor> floorList = new ArrayList<>();
        for (int i = 0; i <= numberOfFloors; i++) {
            floorList.add(new Floor(i));
        }

        ElevatorManager elevatorManager = ElevatorManager.getInstance(elevatorList, floorList);

        elevatorManager.start();

        Thread.sleep(1000);

        floorList.get(5).pressUpButton(elevatorManager).addRequest(8);

        Thread.sleep(5000);

        floorList.get(1).pressUpButton(elevatorManager).addRequest(10);

        Thread.sleep(5000);

        floorList.get(3).pressDownButton(elevatorManager);


        Thread.sleep(20000);
        elevatorManager.shutdown();
    }
}
