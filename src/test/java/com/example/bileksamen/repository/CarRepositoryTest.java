package com.example.bileksamen.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

  //Unit test af metoderne stringToLocalDate i CarRepository
  //Lasse Dall Mikkelsen

  LeaseRepository leaseRepository;

  //Arrange
  @BeforeEach
  void setUp() {
    leaseRepository = new LeaseRepository();
  }


  @Test
  void stringToLocalDate() {

    //Act
    //Metoden tager en String på formen "yyyy-mm-dd" og laver den til et LocalDate objekt. Det vil jeg i det følgende teste.
    LocalDate localDate = leaseRepository.stringToLocalDate("2022-06-12");

    //Assert
    //Nu tester jeg med assertEquals om metoden returnere det forventede år, den forventede måned og den forventede dag.
    assertEquals(2022, localDate.getYear());
    assertEquals(6, localDate.getMonthValue());
    assertEquals(12, localDate.getDayOfMonth());
  }

  //Begge tests bliver godkendt, hvilket betyder at de returnerer de forventede og ønskede værdier.
}