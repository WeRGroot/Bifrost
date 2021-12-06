package com.shivam.elevatorsystem.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ElevatorManager {

    private Map<String, Elevator> elevatorList;
    private List<Floor> floorList;

    private ExecutorService executorService;

    private static ElevatorManager elevatorManager = null;

    private PriorityQueue<Integer> upRequestQueue;
    private PriorityQueue<Integer> downRequestQueue;

    private ElevatorManager(List<Elevator> elevatorList, List<Floor> floorList) {
        this.elevatorList = new HashMap<>();
        for(Elevator elevator : elevatorList){
            this.elevatorList.put(elevator.getElevatorLabel(), elevator);
        }
        this.floorList = floorList;
        this.upRequestQueue = new PriorityQueue<>();
        this.downRequestQueue = new PriorityQueue<>();
    }

    public static ElevatorManager getInstance(List<Elevator> elevatorList,
        List<Floor> floorList) {
        if (elevatorManager == null) {
            elevatorManager = new ElevatorManager(elevatorList, floorList);
        }
        return elevatorManager;
    }

    public void start() {
        executorService = Executors.newFixedThreadPool(5, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        for (Elevator elevator : elevatorList.values()) {
            executorService.execute(new ElevatorRunner(elevator));
        }
        System.out.println("System initialization completed");
    }

    public void shutdown() {
        System.out.println("System shutting down");
        executorService.shutdown();
    }

    public Elevator placeRequest(Direction direction, int floorNumber) {
        System.out.println("Request received for floor "+floorNumber);
        for (Elevator elevator : elevatorList.values()) {
            if (direction == Direction.UP && elevator.getCurrentFloor() < floorNumber) {
                elevator.addRequest(floorNumber);
                return elevator;
            }else if (direction == Direction.DOWN && elevator.getCurrentFloor() > floorNumber){
                elevator.addRequest(floorNumber);
                return elevator;
            }
        }

        elevatorList.get("A").addRequest(floorNumber);
        return elevatorList.get("A");
    }
}