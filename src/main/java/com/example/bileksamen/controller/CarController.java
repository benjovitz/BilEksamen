package com.example.bileksamen.controller;

import com.example.bileksamen.model.*;
import com.example.bileksamen.repository.CarRepository;
import com.example.bileksamen.service.CarService;
import com.example.bileksamen.service.DriverService;
import com.example.bileksamen.service.PickupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.HTML;
import java.util.ArrayList;


@Controller
public class CarController {

  private CarService carService = new CarService();


  @GetMapping("/available-cars")
  public String availableCars(){return "available-cars";}

  //anna
  @GetMapping("/index")
  public String index(){return "index";}

  //anna
  @GetMapping("/medarbejder")
  public String medarbejder(){return "medarbejder";}

  //anna
  @GetMapping("/forretningsudvikler")
  public String forretningsudvikler(){return "forretningsudvikler";}

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
    return "redirect:/pick-drivers";
  }

  //Viser lager af biler
  //Lasse Dall Mikkelsen
  @GetMapping("/stock")
  public String stock(Model carModel){
    carModel.addAttribute("fleet", carService.getCarRepository().getFleet());
    return "stock";
  }

  //Lasse Dall Mikkelsen
  @GetMapping("/update-car/{carID}")
  public String updateCar(@PathVariable("carID") int carID, HttpSession session, Model carModel){
    session.setAttribute("car",carService.getCarRepository().findCarByID(carID));
    carModel.addAttribute("car", session.getAttribute("car"));
    return "update-car";
  }

  //Lasse Dall Mikkelsen
  @PostMapping("/update-car")
  public String postUpdateCar(@RequestParam("brand") String brand, @RequestParam("description") String description, @RequestParam("original_price") int originalPrice, @RequestParam("price_per_month") int pricePerMonth, HttpSession session) {
    Car car = (Car) session.getAttribute("car");
    carService.getCarRepository().updateCar(car.getCarID(), brand, description, originalPrice, pricePerMonth);
    return "redirect:/stock";
  }

  //Lasse Dall Mikkelsen
  @GetMapping("/delete-car/{carID}")
  public String deleteCar(@PathVariable("carID") int carID) {
    carService.getCarRepository().removeCar(carService.getCarRepository().findCarByID(carID));
    return "redirect:/stock";
  }

  //Lasse Dall Mikkelsen
  @GetMapping("/create-car")
  public String createCar() {
    return "create-car";
  }

  //Lasse Dall Mikkelsen
  @PostMapping("/create-car")
  public String postCreateCar(@RequestParam("brand") String brand, @RequestParam("description") String description, @RequestParam("original_price") int originalPrice, @RequestParam("price_per_month") int pricePerMonth) {
    carService.getCarRepository().addCar(brand, description, originalPrice, pricePerMonth);
    return "redirect:/stock";
  }

  //oscar storm
  @GetMapping("/analytics")
    public String createAnalytics(Model model){
    model.addAttribute(carService.getCarRepository().carProfit());
      return "analytics";
    }

  //Fælleskodning
  @GetMapping("/leased-cars")
  public String get(Model carModel){
    ArrayList<Car> list = carService.getCarRepository().leasedCars();
    carModel.addAttribute("leasedCars",list);
    carModel.addAttribute("leasedCarsSize",list.size());
    carModel.addAttribute("income",carService.getCarRepository().getTotalLeasePrice());
    return "leased-cars";
  }
}





