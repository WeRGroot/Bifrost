package com.shivam.elevatorsystem.model;

import lombok.Getter;

@Getter
public class Floor {
    private int floorNumber;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Elevator pressUpButton(ElevatorManager elevatorManager){
        return elevatorManager.placeRequest(Direction.UP, floorNumber);
    }

    public Elevator pressDownButton(ElevatorManager elevatorManager){
        return elevatorManager.placeRequest(Direction.DOWN, floorNumber);
    }
}
