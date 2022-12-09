package com.example.bileksamen.service;

import com.example.bileksamen.model.Pickup;
import com.example.bileksamen.repository.PickupRepositoy;

//Daniel Benjovitz
public class PickupService {
    private PickupRepositoy pickupRepositoy;

    public PickupService(){
        this.pickupRepositoy=new PickupRepositoy();
    }
    public PickupRepositoy getPickupRepositoy(){
        return pickupRepositoy;
    }

    public Pickup getPickupByID(int pickupID){
        for (Pickup pickup: pickupRepositoy.getAllPickups()) {
            if(pickupID ==pickup.getPickupID()){
                return pickup;
            }
        }
        return null;
    }
}
