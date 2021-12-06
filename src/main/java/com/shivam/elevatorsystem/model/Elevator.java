package com.shivam.elevatorsystem.model;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

public class Elevator {

    @Getter private Direction direction;
    private AtomicInteger currentFloor;
    private Door door;
    private HashSet<Integer> upRequests;
    private HashSet<Integer> downRequests;
    @Getter private String elevatorLabel;
    private int totalFloors;

    public Elevator(int numberOfFloors, int currentFloor, String elevatorLabel) {
        this.elevatorLabel = elevatorLabel;
        this.totalFloors = numberOfFloors;
        this.currentFloor = new AtomicInteger(currentFloor);
        this.door = new Door();
        this.upRequests = new HashSet<>();
        this.downRequests = new HashSet<>();
        this.direction = Direction.UP;
    }

    private void moveUp() throws InterruptedException {
        currentFloor.incrementAndGet();
        System.out.println("Elevator: " + elevatorLabel + " Reached floor: " + currentFloor.get());
        if (currentFloor.get() == totalFloors) {
            direction = Direction.DOWN;
        }
        if (upRequests.contains(currentFloor.get())) {
            upRequests.remove(currentFloor.get());
            openAndCloseDoor();
        }
    }

    private void moveDown() throws InterruptedException {
        currentFloor.decrementAndGet();
        System.out.println("Elevator: " + elevatorLabel + " Reached floor: " + currentFloor.get());
        if (currentFloor.get() == 0) {
            direction = Direction.UP;
        }

        if (downRequests.contains(currentFloor.get())) {
            downRequests.remove(currentFloor.get());
            openAndCloseDoor();
        }
    }

    public void pressButton(int buttonLabel) {
        if(buttonLabel < 0 && buttonLabel > totalFloors){
            throw new RuntimeException("Invalid button label");
        }

        if(buttonLabel < currentFloor.get()){
            downRequests.add(buttonLabel);
        }else if(buttonLabel > currentFloor.get()){
            upRequests.add(buttonLabel);
        }
    }

    public void move() throws InterruptedException {
        if (direction == Direction.UP && upRequests.size() > 0) {
            moveUp();
        } else if (direction == Direction.DOWN && downRequests.size() > 0) {
            moveDown();
        }else if(direction == Direction.UP && downRequests.size() > 0){
            direction =  Direction.DOWN;
            moveDown();
        }else if(direction == Direction.DOWN && upRequests.size() > 0){
            direction =  Direction.UP;
            moveUp();
        }
    }

    public int getCurrentFloor(){
        return currentFloor.get();
    }

    public void addRequest(int floorNumber){
        if(currentFloor.get() > floorNumber){
            downRequests.add(floorNumber);
        }else if(currentFloor.get() < floorNumber){
            upRequests.add(floorNumber);
        }
    }

    private void openAndCloseDoor() throws InterruptedException {
        door.open();
        Thread.sleep(2000);
        door.close();
    }


}
