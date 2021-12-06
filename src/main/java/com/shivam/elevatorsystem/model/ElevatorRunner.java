package com.shivam.elevatorsystem.model;

public class ElevatorRunner implements Runnable{

    private Elevator elevator;

    public ElevatorRunner(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        while(true){
            try {
                elevator.move();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
