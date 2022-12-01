package com.example.bileksamen.controller;

import com.example.bileksamen.model.*;
import com.example.bileksamen.service.CarService;
import com.example.bileksamen.service.DriverService;
import com.example.bileksamen.service.PickupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;


@Controller
public class CarController {

  private CarService carService = new CarService();
  private DriverService driverService = new DriverService();
  private PickupService pickupService = new PickupService();



  //Get og post mapping for create-lease, hvor de indtastede parametre i post bliver redirectet og en HttpSession gemmer parametrene i en cookie
  //Lasse Dall Mikkelsen
  @GetMapping("/create-lease")
  public String createLease() {
    return "create-lease";
  }

  @PostMapping("/create-lease")
  public String postCreateLease(@RequestParam("costumer_first_name") String firstName, @RequestParam("costumer_last_name") String lastName, @RequestParam("costumer_email") String email, @RequestParam("costumer_phone") String phone, @RequestParam("lease_start") String leaseStart, @RequestParam("lease_end") String leaseEnd, HttpSession session) {
    Costumer costumer = new Costumer(0, firstName, lastName, email, phone, null);
    session.setAttribute("costumer", costumer);
    session.setAttribute("leaseStart", leaseStart);
    session.setAttribute("leaseEnd", leaseEnd);
    return "redirect:/car-list";
  }

  //Get mapping for car-list, som sender den i cookie gemte costumer samt den samlede flåde af biler videre til html gennem en model.
  //Lasse Dall Mikkelsen
  @GetMapping("/car-list")
  public String createList(Model carModel, HttpSession session) {
    Costumer costumer = (Costumer) session.getAttribute("costumer");
    carService.getCarRepository().createCostumer(costumer);
    Costumer costumerWithID = carService.getCarRepository().getCostumers().get(carService.getCarRepository().getCostumers().size()-1);
    session.setAttribute("costumer", costumerWithID);
    carModel.addAttribute(costumerWithID);
    carModel.addAttribute("fleet", carService.getCarRepository().getFleet());
    return "car-list";
  }

  //Get mapping for new-lease, der opretter et lease med den valgte bil gennem en path variable, der sender bilens ID videre i url'en.
  //Lasse Dall Mikkelsen
  @GetMapping("/new-lease/{carID}")
  public String newLease(HttpSession session, @PathVariable("carID") int carID) {
    Car car = carService.getCarRepository().findCarByID(carID);
    Costumer costumer = (Costumer) session.getAttribute("costumer");
    Lease lease = new Lease(car, costumer, carService.getCarRepository().stringToLocalDate((String) session.getAttribute("leaseStart")), carService.getCarRepository().stringToLocalDate((String) session.getAttribute("leaseEnd")));
    carService.getCarRepository().createLease(lease);
    return "create-lease";
  }
//Daniel Benjovitz
  @GetMapping("/create-driver")
  public String createDriver(){
    return "create-driver";
  }
  //Daniel Benjovitz
  @PostMapping("/create-driver")
  public String postCreateDriver(@RequestParam("driver_first_name") String firstName, @RequestParam("driver_last_name") String lastName,RedirectAttributes redirectAttributes){
    redirectAttributes.addAttribute("firstName",firstName);
    redirectAttributes.addAttribute("lastName",lastName);
     driverService.getDriverRepository().createDriver(new Driver(firstName,lastName));
    return "redirect:/create-driver";
  }
  //Daniel Benjovitz
  @GetMapping("/all-drivers")
  public String getAllDrivers(Model model){
      driverService.getDriverRepository().setDrivers(driverService.getDriverRepository().getAllDrivers());
      model.addAttribute("drivers",driverService.getDriverRepository().getDrivers());
      return "all-drivers";
    }
  //Daniel Benjovitz
  @GetMapping("/add-driver")
  public String addDriverToSession(HttpSession session, @RequestParam int id){
  session.setAttribute("driver",driverService.getDriverByID(id));
    System.out.println(session.getAttribute("driver"));
  return "redirect:/all-drivers";
  }
  //Daniel Benjovitz
  @GetMapping("/fleet")
  public String fleet(HttpSession session, Model carModel){
    carModel.addAttribute("fleet", carService.getCarRepository().getFleet());
    return "fleet";
  }
  //Daniel Benjovitz
  @GetMapping("/add-car")
  public String addCar(@RequestParam int id, HttpSession session){
    session.setAttribute("car",carService.getCarRepository().findCarByID(id));
    return "redirect:/fleet";
  }
  //Daniel Benjovitz
  @GetMapping("/create-pickup")
  public String createPickup(){
    return "create-pickup";
  }
  /*Her laver vi opretter vi en opsamling af en bil, hertil skal der vælges en bil og en vognmand.
  Først og fremmest bliver der tjekket om bilen eller vognmanden er null.
  Der bliver valgt en bil som bliver lagt i en session fra fleet via add-car.
  Det samme sker med en vognmand fra all-drivers via add-driver.
  Deres ID bliver plukket ud samt den lokation og dato som kommer fra inputfelter, bliver sat sammen til at skabe et pickup object.
  Det object bliver sat ind i MySQL databasen.
  */
  //Daniel Benjovitz
  @PostMapping("/create-pickup")
  public String postCreatePickup(@RequestParam String location, @RequestParam String date, RedirectAttributes redirectAttributes, HttpSession session){
    if(session.getAttribute("driver") ==null || session.getAttribute("car")==null){
      System.out.println("car or driver is null");
      return "redirect:/fleet";
    }
    redirectAttributes.addAttribute("location",location);
    redirectAttributes.addAttribute("date",date);
    Car car = (Car) session.getAttribute("car");
    int carID = car.getCarID();
    Driver driver = (Driver) session.getAttribute("driver");
    int driverID = driver.getDriverID();
    Pickup pickup = new Pickup(carID,location,date,driverID);
    pickupService.getPickupRepositoy().createPickup(pickup);
    return "redirect:/fleet";
  }
}

