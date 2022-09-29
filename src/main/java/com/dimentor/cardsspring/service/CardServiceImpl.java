package com.dimentor.cardsspring.service;

import com.dimentor.cardsspring.model.Card;
import com.dimentor.cardsspring.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    @Autowired
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card getCardById(long id) {
        return this.cardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Card> getAll() {
        return this.cardRepository.findAll();
    }

    @Override
    public void add(Card card) {
        try {
            this.cardRepository.save(card);
        } catch (Exception e) {
            throw new IllegalArgumentException("Duplicate");
        }
    }

    @Override
    public void update(Card card) {
        //см другие
        long id = card.getId();
        Card cardById = this.getCardById(id);
        if (cardById == null) throw new IllegalArgumentException("Card does not exist");
        this.cardRepository.save(card);
    }

    @Override
    public Card delete(long id) {
        Card cardById = this.getCardById(id);
        if (cardById == null) return null;
        else this.cardRepository.deleteById(id);
        return cardById;
    }
}
