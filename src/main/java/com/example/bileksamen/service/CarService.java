package com.example.bileksamen.service;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.repository.CarRepository;

public class CarService {


  //Forbinder denne klasse med repositryet, så den har adgang til flåden af biler mm. gennem en attribut og derefter constructor af serviceklassen.
  //Lasse Dall Mikkelsen
  private CarRepository carRepository;

  public CarService() {
    this.carRepository = new CarRepository();
  }



  //Returnere en bil ud fra carID
  //Lasse Dall Mikkelsen
  public Car findCarByID(int carID) {
    for (Car car: carRepository.getFleet()) {
      if (car.getCarID() == carID) {
        return car;
      }
    }
    return null;
  }

  public CarRepository getCarRepository() {
    return carRepository;
  }
}
