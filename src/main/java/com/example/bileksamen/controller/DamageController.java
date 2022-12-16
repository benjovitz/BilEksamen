package com.example.bileksamen.controller;

import com.example.bileksamen.model.Car;
import com.example.bileksamen.model.Damage;
import com.example.bileksamen.repository.DamageRepository;
import com.example.bileksamen.service.DamageService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class DamageController {

    DamageService damageService;

    //Anna
    public DamageController(){
        damageService = new DamageService();
    }

    //Anna
    @GetMapping("/damage-report")
    public String damageReport(Model model, @RequestParam int id){ //request parameter som læser værdi efter ?/id fra url
        model.addAttribute("ID",id);
        return "damage-report";
    }

    //Anna
    @PostMapping("/damage-report")
    public String damageReport(Damage damage){ //værdier fra tekstfelter damageID carID
        damageService.getDamageRepository().create(damage);
        return "damageList";
    }

    //Anna
    @GetMapping("/damageList")
    public String getAllDamages(Model model){
        model.addAttribute("damages",damageService.getDamageRepository().getAllDamageDim());
        return "damageList";
    }

    //Daniel Benjovitz
    @GetMapping("/all-damage")
    public String getAllDamage(Model model){
        ArrayList<Damage> damages = damageService.getDamageRepository().getDamageAndCar();
        //System.out.println(damages);
        model.addAttribute("damages",damages);
        return "all-damage";
    }
    //Daniel Benjovitz
    @GetMapping("/damage-specific-car")
    public String specificCar(@RequestParam int carID,Model model){
        ArrayList<Damage> damages = damageService.getDamageRepository().specificGetDamageAndCar(carID);
        model.addAttribute("damages",damages);
        model.addAttribute("totalPrice",damageService.getDamageRepository().totalPrice(damages));
        return "damage-specific-car";
    }

}
