package com.dimentor.cardsspring.service;

import com.dimentor.cardsspring.model.Card;
import com.dimentor.cardsspring.model.User;

import java.util.List;

public interface CardService {
    Card getCardById(long id);
    void add(Card card);
    void update(Card card);
    Card delete(long id);
    List<Card> getAll();

}
