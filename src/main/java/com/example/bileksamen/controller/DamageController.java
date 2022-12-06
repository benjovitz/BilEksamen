package com.example.bileksamen.controller;

import com.example.bileksamen.model.Damage;
import com.example.bileksamen.repository.DamageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DamageController {

    DamageRepository damageRepository;

    //Anna
    public DamageController(){
        damageRepository = new DamageRepository();
    }

    //Anna
    @GetMapping("/damage-report")
    public String damageReport(Model model, @RequestParam int id){
        model.addAttribute("ID",id);
        return "damage-report";
    }

    //Anna
    @PostMapping("/damage-report")
    public String damageReport(Damage damage){
        damageRepository.create(damage);
        return "damageList";
    }

    //Anna
    @GetMapping("/damageList")
    public String getAllDamages(Model model){
        model.addAttribute("damages",damageRepository.getAllDamageDim());
        return "damageList";
    }



}
