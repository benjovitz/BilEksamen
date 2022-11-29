package com.example.bileksamen.service;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Driver;
import com.example.bileksamen.repository.DriverRepository;
//Daniel Benjovitz
public class DriverService {

    private DriverRepository driverRepository;

    public DriverService(){
        this.driverRepository = new DriverRepository();
    }
    public DriverRepository getDriverRepository(){
        return driverRepository;
    }
  public Driver getDriverByID(int driverID){
      for (Driver driver: driverRepository.getDrivers()) {
          if(driver.getDriverID()==driverID){
              return driver;
          }
      } return null;
  }
}
