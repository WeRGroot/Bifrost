package com.shivam.elevatorsystem.model;

public class Door {
    private boolean isOpen;

    public void open(){
        if(!isOpen){
            System.out.println("Door is opening");
            isOpen = true;
        }
    }

    public void close(){
        if(isOpen){
            System.out.println("Door is closing");
            isOpen = false;
        }
    }
}
