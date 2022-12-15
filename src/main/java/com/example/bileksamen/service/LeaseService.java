package com.example.bileksamen.service;

import com.example.bileksamen.repository.LeaseRepository;

public class LeaseService {

  private LeaseRepository leaseRepository;

  public LeaseService() {
    leaseRepository = new LeaseRepository();
  }

  public LeaseRepository getLeaseRepository() {
    return leaseRepository;
  }
}
