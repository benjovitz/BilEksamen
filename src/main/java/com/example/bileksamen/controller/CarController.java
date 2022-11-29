package com.example.bileksamen.controller;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Costumer;
import com.example.bileksamen.model.Lease;
import com.example.bileksamen.service.CarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CarController {

  private CarService carService = new CarService();


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
    System.out.println(leaseStart);
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
    Lease lease = new Lease(car, costumer, (String) session.getAttribute("leaseStart"), (String) session.getAttribute("leaseEnd"));
    carService.getCarRepository().createLease(lease);
    return "create-lease";
  }


}

