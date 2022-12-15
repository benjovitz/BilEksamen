package com.example.bileksamen.service;

import com.example.bileksamen.repository.DamageRepository;

import java.util.ArrayList;

//Anna
public class DamageService {

  private DamageRepository damageRepository;

  public DamageService() {
    damageRepository = new DamageRepository();
  }

  public DamageRepository getDamageRepository() {
    return damageRepository;
  }

}
