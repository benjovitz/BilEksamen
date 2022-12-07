package com.example.bileksamen.service;

import com.example.bileksamen.model.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverServiceTest {

  //Unit test af DriverRepository
  //Lasse Dall Mikkelsen

  DriverService driverService;

  //Arrange
  @BeforeEach
  void setUp() {
    driverService = new DriverService();
  }

  @Test
  void getDriverByID() {

    //Act
    //Jeg prøver at teste, om getDriverByID returnere null, hvis den får et driverID, der ikke eksistere i databasen som oparameter.
    Driver nullDriver = driverService.getDriverByID(1000);


    //Assert
    //Jeg forventer at resultatet af nullDriver er null. Derfor laver jeg en asserEquals med null og nullDriver som parameter.
    assertEquals(null, nullDriver);

    //Act
    //Jeg ved at Driver med driverID 3 hedder Lasse Dall, derfor tester jeg, om metoden returnere denne driver, hvis jeg indsætter 3 som parameter
    Driver driver3 = driverService.getDriverByID(3);

    //Assert
    //Jeg forventer at resultatet af driver er en driver med parametrene (3, "Lasse", "Dall"). Derfor laver jeg en assertEquals med fornavn "Lasse" og efternavn "Dall", hvor jeg sammenligner driver3 fornavn og efternavn.
    assertEquals("Lasse", driver3.getFirstName());
    assertEquals("Dall", driver3.getLastName());


    //Testen bliver godkendt, hvilket betyder, at metoden returnere null ved et driverID, som ikke eksisterer i databasen, og at den returner Driver med driverID 3 i databasen.
  }
}