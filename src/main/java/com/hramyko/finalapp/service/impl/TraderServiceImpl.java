package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.entity.Comment;
import com.hramyko.finalapp.entity.GameObject;
import com.hramyko.finalapp.entity.Trader;
import com.hramyko.finalapp.repository.TraderRepository;
import com.hramyko.finalapp.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Trader> getTopTraders() {
        List<Trader> traders = traderRepository.findAll();
        for (Trader trader:
             traders) {
            setTraderRating(trader);
        }
//        traders.sort(Comparator.comparing(Trader::getRating));
        return traders;
    }

    @Transactional
    @Override
    public double showTraderRating(int id) {
        Optional<Trader> traderOptional = traderRepository.findById(id);
        if (traderOptional.isPresent()) {
            setTraderRating(traderOptional.get());
//            return traderOptional.get().getRating();
        } else {
            throw new RuntimeException("This user isn't a Trader");
        }
        return 0;
    }

        private void setTraderRating(Trader trader) {
        double rating = 0;
        List<GameObject> gameObjects = trader.getGameObjects();
        int count = 0;
        for (GameObject gameObject:
                gameObjects) {
            List<Comment> comments = gameObject.getPost().getComments();
            count += comments.size();
            for (Comment comment:
                    comments) {
                rating += comment.getMark();
            }

        }
//        trader.setRating(rating/count);
    }
}
