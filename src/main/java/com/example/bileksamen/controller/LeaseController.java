package com.example.bileksamen.controller;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Costumer;
import com.example.bileksamen.model.Lease;
import com.example.bileksamen.service.CarService;
import com.example.bileksamen.service.LeaseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

//Lasse Dall Mikkelsen
@Controller
public class LeaseController {

  private LeaseService leaseService = new LeaseService();
  private CarService carService = new CarService();

  //Get mapping for car-list, som sender den i cookie gemte costumer samt den samlede flåde af biler videre til html gennem en model.
  //Lasse Dall Mikkelsen
  @GetMapping("/car-list")
  public String createList(Model carModel, HttpSession session) {
    Costumer costumer = (Costumer) session.getAttribute("costumer");
    leaseService.getLeaseRepository().createCostumer(costumer);
    Costumer costumerWithID = leaseService.getLeaseRepository().getCostumers().get(leaseService.getLeaseRepository().getCostumers().size()-1);
    session.setAttribute("costumer", costumerWithID);
    carModel.addAttribute(costumerWithID);
    String leaseStart = (String) session.getAttribute("leaseStart");
    String leaseEnd = (String) session.getAttribute("leaseEnd");
    ArrayList<Car> availableFleet = leaseService.getLeaseRepository().findAvailableCars(leaseStart, leaseEnd, carService.getCarRepository().getFleet());
    carModel.addAttribute("available_fleet", availableFleet);
    return "car-list";
  }

  //Lasse Dall Mikkelsen
  @GetMapping("/availability")
  public String availability() {
    return "availability";
  }

  //Lasse Dall Mikkelsen
  @PostMapping("/availability")
  public String postAvailability(@RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate, Model carModel) {
    ArrayList<Car> availableCars = leaseService.getLeaseRepository().findAvailableCars(startDate, endDate, carService.getCarRepository().getFleet());
    carModel.addAttribute("availableCars", availableCars);
    return "available-cars";
  }

  //Get og post mapping for create-lease, hvor de indtastede parametre i post bliver redirectet og en HttpSession gemmer parametrene i en cookie
  //Lasse Dall Mikkelsen
  @GetMapping("/create-lease")
  public String createLease() {
    return "create-lease";
  }

  //Lasse Dall Mikkelsen
  @PostMapping("/create-lease")
  public String postCreateLease(@RequestParam("costumer_first_name") String firstName, @RequestParam("costumer_last_name") String lastName, @RequestParam("costumer_email") String email, @RequestParam("costumer_phone") String phone, @RequestParam("lease_start") String leaseStart, @RequestParam("lease_end") String leaseEnd, HttpSession session) {
    Costumer costumer = new Costumer(0, firstName, lastName, email, phone, null);
    session.setAttribute("costumer", costumer);
    session.setAttribute("leaseStart", leaseStart);
    session.setAttribute("leaseEnd", leaseEnd);
    return "redirect:/car-list";
  }

  //Get mapping for new-lease, der opretter et lease med den valgte bil gennem en path variable, der sender bilens ID videre i url'en.
  //Lasse Dall Mikkelsen
  @GetMapping("/new-lease/{carID}")
  public String newLease(HttpSession session, @PathVariable("carID") int carID) {
    Costumer costumer = (Costumer) session.getAttribute("costumer");
    Lease lease = new Lease(carID, costumer.getCostumerID(), leaseService.getLeaseRepository().stringToLocalDate((String) session.getAttribute("leaseStart")), leaseService.getLeaseRepository().stringToLocalDate((String) session.getAttribute("leaseEnd")));
    leaseService.getLeaseRepository().createLease(lease);
    carService.getCarRepository().changeAvailability(carID);
    return "redirect:/medarbejder";
  }

}
