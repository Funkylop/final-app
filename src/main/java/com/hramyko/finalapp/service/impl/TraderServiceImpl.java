package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.Comment;
import com.hramyko.finalapp.persistence.entity.GameObject;
import com.hramyko.finalapp.persistence.entity.Trader;
import com.hramyko.finalapp.persistence.repository.TraderRepository;
import com.hramyko.finalapp.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TraderServiceImpl implements TraderService {

    private final TraderRepository traderRepository;

    @Autowired
    TraderServiceImpl(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }

    @Transactional
    @Override
    public List<Trader> findAll() {
        return traderRepository.findAll();
    }

    @Transactional
    @Override
    public Trader save(Trader trader) {
        return traderRepository.save(trader);
    }

    @Transactional
    @Override
    public Map<Trader, Double> getTopTraders() {
        List<Trader> traders = traderRepository.findAll();
        Map<Trader, Double> traderRating = new HashMap<>();
        for (Trader trader :
                traders) {
            traderRating.put(trader, setTraderRating(trader));
        }
        return (Map<Trader, Double>) traderRating.entrySet().stream().sorted(Map.Entry.<Trader, Double>comparingByValue().reversed());
    }

    @Transactional
    @Override
    public double showTraderRating(int id) {
        Optional<Trader> traderOptional = traderRepository.findById(id);
        if (traderOptional.isPresent()) {
            return setTraderRating(traderOptional.get());
        } else {
            throw new RuntimeException("This user isn't a Trader");
        }
    }

    private double setTraderRating(Trader trader) {
        double rating = 0;
        List<GameObject> gameObjects = trader.getGameObjects();
        int count = 0;
        for (GameObject gameObject :
                gameObjects) {
            List<Comment> comments = gameObject.getPost().getComments();
            count += comments.size();
            for (Comment comment :
                    comments) {
                rating += comment.getMark();
            }

        }
        return rating / count;
    }
}