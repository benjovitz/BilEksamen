package com.example.bileksamen.controller;

import com.example.bileksamen.model.Damage;
import com.example.bileksamen.repository.DamageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class DamageController {

    DamageRepository damageRepository;

    public DamageController(){
        damageRepository = new DamageRepository();
    }

    @GetMapping("/damage-report")
    public String damageReport(){

        return "damage-report";
    }

    @PostMapping("/damage-report")
    public String damageReport(Damage damage){
        damageRepository.create(damage);
        return "redirect:/";
    }

}
