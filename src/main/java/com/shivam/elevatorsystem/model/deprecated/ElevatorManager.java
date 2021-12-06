package com.shivam.elevatorsystem.model.deprecated;

import com.shivam.elevatorsystem.model.Elevator;
import com.shivam.elevatorsystem.model.ElevatorRunner;
import com.shivam.elevatorsystem.model.Floor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorManager {

    private Map<String, Elevator> elevatorList;
    private List<Floor> floorList;
    private boolean isShutDown;

    private int totalNumberOfFloors;
    private int totalNumberOfElevators;

    private ExecutorService executorService;

    private static ElevatorManager elevatorManager = null;

    private ElevatorManager(int totalNumberOfFloors, int totalNumberOfElevators) {
        this.totalNumberOfFloors = totalNumberOfFloors;
        this.totalNumberOfElevators = totalNumberOfElevators;
    }

    public ElevatorManager getInstance(int totalNumberOfElevators, int totalNumberOfFloors){
        if(elevatorManager == null){
            elevatorManager = new ElevatorManager(totalNumberOfFloors, totalNumberOfElevators);
        }
        return elevatorManager;
    }

    public void initialize(){
        elevatorList = new HashMap<>();
        for (int i = 0; i < totalNumberOfElevators; i++) {
            elevatorList.put(('A' + i) + "", new Elevator(totalNumberOfFloors, i,('A' + i) + "" ));
        }
        this.floorList = new ArrayList<>();

        floorList.add(new GroundFloor(0));
        for (int i = 1; i < totalNumberOfFloors; i++) {
            floorList.add(new MiddleFloor(i));
        }
        floorList.add(new TopFloor(totalNumberOfFloors - 1));
        executorService = Executors.newFixedThreadPool(5);
    }

    public void start() {
        for (int i = 0; i < elevatorList.size(); i++) {
            executorService.submit(new ElevatorRunner(elevatorList.get(i)));
        }
        System.out.println("System initialization completed");
    }

    public void shutdown() {
        System.out.println("System shutting down");
        executorService.shutdown();
    }

    public Floor getFloor(int i){
        if(i < 0 || i > totalNumberOfFloors){
            throw new RuntimeException("Invalid floor number");
        }

        return floorList.get(i);
    }

    public Elevator getElevator(String label){
        if(!elevatorList.containsKey(label)){
            throw new RuntimeException("Invalid elevator label");
        }

        return elevatorList.get(label);
    }
}
