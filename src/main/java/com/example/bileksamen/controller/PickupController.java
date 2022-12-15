package com.example.bileksamen.controller;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Driver;
import com.example.bileksamen.model.Pickup;
import com.example.bileksamen.service.CarService;
import com.example.bileksamen.service.DriverService;
import com.example.bileksamen.service.PickupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//Daniel
@Controller
public class PickupController {

  private DriverService driverService = new DriverService();
  private PickupService pickupService = new PickupService();


  //Daniel Benjovitz
  @GetMapping("/create-driver")
  public String createDriver(){
    return "create-driver";
  }

  //Daniel Benjovitz
  @PostMapping("/create-driver")
  public String postCreateDriver(@RequestParam("driver_first_name") String firstName, @RequestParam("driver_last_name") String lastName, RedirectAttributes redirectAttributes){
    redirectAttributes.addAttribute("firstName",firstName);
    redirectAttributes.addAttribute("lastName",lastName);
    driverService.getDriverRepository().createDriver(new Driver(firstName,lastName));
    return "redirect:/all-drivers";
  }

  //Daniel Benjovitz
  @GetMapping("/all-drivers")
  public String getAllDrivers(Model model){
    model.addAttribute("drivers",driverService.getDriverRepository().getAllDrivers());
    return "all-drivers";
  }

  //Daniel Benjovitz
  @GetMapping("/add-driver")
  public String addDriverToSession(HttpSession session, @RequestParam int id){
    session.setAttribute("driver",driverService.getDriverByID(id));
    System.out.println(session.getAttribute("driver"));
    return "create-pickup";
  }

  //Daniel Benjovitz
  @GetMapping("/create-pickup")
  public String createPickup(){
    return "create-pickup";
  }

  //Daniel Benjovitz
  @PostMapping("/create-pickup")
  public String postCreatePickup(@RequestParam String location, @RequestParam String date, RedirectAttributes redirectAttributes, HttpSession session){
    if(session.getAttribute("driver") ==null || session.getAttribute("car")==null){
      System.out.println("car or driver is null");
      return "redirect:/medarbejder";
    }
    redirectAttributes.addAttribute("location",location);
    redirectAttributes.addAttribute("date",date);
    redirectAttributes.addAttribute("pickedUp",false);
    Car car = (Car) session.getAttribute("car");
    int carID = car.getCarID();

    Driver driver = (Driver) session.getAttribute("driver");
    int driverID = driver.getDriverID();
    Pickup pickup = new Pickup(carID,location,date,driverID,false);
    pickupService.getPickupRepositoy().createPickup(pickup);
    return "redirect:/medarbejder";
  }

  //Daniel Benjovitz
  @GetMapping("/update-driver")
  public String getUpdateDriver(@RequestParam int driverID, HttpSession session, Model model){
    session.setAttribute("driver",driverService.getDriverByID(driverID));
    model.addAttribute("driver", session.getAttribute("d"));
    return "update-driver";
  }

  //Daniel Benjovitz
  @PostMapping("/update-driver")
  public String postUpdateDriver(@RequestParam String firstName, @RequestParam String lastName, HttpSession session){
    Driver driver = (Driver) session.getAttribute("driver");
    System.out.println(driver);
    driverService.getDriverRepository().updateDriver(driver.getDriverID(),firstName,lastName);
    return "redirect:/all-drivers";
  }

  //Daniel Benjovitz
  @GetMapping("/update-pickup")
  public String getUpdatePickup(@RequestParam int pickupID, HttpSession session, Model model){
    session.setAttribute("pickup",pickupService.getPickupByID(pickupID));
    model.addAttribute("pickup",session.getAttribute("pickup"));
    return "update-pickup";
  }

  //Daniel Benjovitz
  @PostMapping("/update-pickup")
  public String postUpdatePickup(@RequestParam String location, @RequestParam String date, HttpSession session){
    Pickup pickup  =(Pickup) session.getAttribute("pickup");
    pickupService.getPickupRepositoy().updatePickup(location,date,pickup.getPickupID());
    return "redirect:/all-pickups";
  }

  //Daniel Benjovitz
  @GetMapping("/register-pickup")
  public String registerPickup(@RequestParam int pickupID){
    Pickup pickup = pickupService.getPickupByID(pickupID);

    if(pickup.isPickedUp()==true){
      pickupService.getPickupRepositoy().registerPickup(pickupID,false);
    } else{
      pickupService.getPickupRepositoy().registerPickup(pickupID,true);
    }
    return "redirect:/all-pickups";
  }

  //Daniel Benjovitz
  @GetMapping("/all-pickups")
  public String getAllPickups(Model model){
    model.addAttribute("pickups",pickupService.getPickupRepositoy().getAllPickups());
    return "all-pickups";
  }

  //Daniel Benjovitz
  @GetMapping("/delete-driver")
  public String deleteDriver(@RequestParam int driverID) {
    driverService.getDriverRepository().removeDriver(driverService.getDriverByID(driverID));
    return "redirect:/all-drivers";
  }

  //Daniel Benjovitz
  @GetMapping("/delete-pickup")
  public String deletePickup(@RequestParam int pickupID){
    pickupService.getPickupRepositoy().removePickup(pickupID);
    return "redirect:/all-pickups";
  }
  @GetMapping("pick-driver")
  public String pickDriver(Model model){
    model.addAttribute("drivers",driverService.getDriverRepository().getAllDrivers());
    return "pick-driver";
  }
}
