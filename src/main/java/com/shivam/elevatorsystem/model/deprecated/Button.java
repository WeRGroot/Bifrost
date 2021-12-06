package com.shivam.elevatorsystem.model.deprecated;

public abstract class Button {
    private boolean isIlluminate;

    public void illuminateOn(){
        if(!isIlluminate){
            System.out.println("Illumination is ON");
            isIlluminate = true;
        }
    }

    public void illuminateOff(){
        if(isIlluminate){
            System.out.println("Illumination is OFF");
            isIlluminate = false;
        }
    }

    abstract void placeRequest();

}
