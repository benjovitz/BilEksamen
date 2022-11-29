package com.example.bileksamen.service;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.repository.CarRepository;

import java.time.LocalDate;

public class CarService {


  //Forbinder denne klasse med repositoryet, så den har adgang til flåden af biler mm. gennem en attribut og derefter constructor af serviceklassen.
  //Lasse Dall Mikkelsen
  private CarRepository carRepository;

  public CarService() {
    this.carRepository = new CarRepository();
  }

  public CarRepository getCarRepository() {
    return carRepository;
  }
}
