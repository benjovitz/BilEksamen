package com.example.bileksamen.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

  //Unit test af metoderne stringToLocalDate og sellCar i CarRepository
  //Lasse Dall Mikkelsen

  CarRepository carRepository;

  //Arrange
  @BeforeEach
  void setUp() {
    carRepository = new CarRepository();
  }

  @Test
  void sellCar() {

    //Act
    //Jeg ved at prisen på salget af bilen med carID 6 i databasen har en pris på er 1, og jeg vil i det følgende teste om metoden sellCar returnere 1, hvis jeg indsætter carID 6 som parameter.
    int value = carRepository.sellCar(6);

    //Assert
    //Nu tester jeg med assertEquals om metoden returnere 1 som forventet.
    assertEquals(1, value);
  }

  @Test
  void stringToLocalDate() {

    //Act
    //Metoden tager en String på formen "yyyy-mm-dd" og laver den til et LocalDate objekt. Det vil jeg i det følgende teste.
    LocalDate localDate = carRepository.stringToLocalDate("2022-06-12");

    //Assert
    //Nu tester jeg med assertEquals om metoden returnere det forventede år, den forventede måned og den forventede dag.
    assertEquals(2022, localDate.getYear());
    assertEquals(6, localDate.getMonthValue());
    assertEquals(12, localDate.getDayOfMonth());
  }

  //Begge tests bliver godkendt, hvilket betyder at de returnerer de forventede og ønskede værdier.
}