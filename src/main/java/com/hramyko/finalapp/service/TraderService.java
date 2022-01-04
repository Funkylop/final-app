package com.hramyko.finalapp.service;

import com.hramyko.finalapp.entity.Trader;

import java.util.List;

public interface TraderService {
    List<Trader> findAll();
    Trader save(Trader trader);
    List<Trader> getTopTraders();
    double getUserRating(int id);
}
