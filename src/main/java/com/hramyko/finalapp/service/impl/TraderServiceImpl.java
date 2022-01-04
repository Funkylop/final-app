package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.Trader;
import com.hramyko.finalapp.repository.TraderRepository;
import com.hramyko.finalapp.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TraderServiceImpl implements TraderService {

    private final TraderRepository traderRepository;

    @Autowired
    TraderServiceImpl(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }

    @Override
    public List<Trader> findAll() {
        return traderRepository.findAll();
    }

    @Override
    public Trader save(Trader trader) {
        return traderRepository.save(trader);
    }

    @Override
    public List<Trader> getTopTraders() {
        List<Trader> traders = traderRepository.findAll();
        traders.sort(Comparator.comparing(Trader::getRating));
        return traders;
    }

    @Override
    public double getUserRating(int id) {
        Optional<Trader> traderOptional = traderRepository.findById(id);
        if (traderOptional.isPresent()) {
            return traderOptional.get().getRating();
        } else
        {
            throw new RuntimeException("This user isn't a Trader");
        }
    }
}
