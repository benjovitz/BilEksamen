package com.example.bileksamen.service;

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
}
