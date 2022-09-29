package com.dimentor.cardsspring.controllers;

import com.dimentor.cardsspring.model.Card;
import com.dimentor.cardsspring.model.Category;
import com.dimentor.cardsspring.service.CardService;
import com.dimentor.cardsspring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private CardService cardService;
    private CategoryService categoryService;

    @Autowired
    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //по id карточки
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Card> getCardById(@PathVariable long id) {
        Card cardById = this.cardService.getCardById(id);
        if (cardById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(cardById, HttpStatus.OK);
    }

    //по id категории
    @GetMapping(value = "/cat/{id}", produces = "application/json")
    public ResponseEntity<List<Card>> getCardsByCategoryId(@PathVariable long id) {
        Category categoryById = this.categoryService.getCategoryById(id);
        if (categoryById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else {
            List<Card> cards = categoryById.getCards();
            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
    }

    @GetMapping(produces = "application/json")
    public List<Card> getAllCards() {
        return this.cardService.getAll();
    }

    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Card> addCardByCategoryId(@PathVariable long id, @RequestBody Card card) {
        Category categoryById = this.categoryService.getCategoryById(id);
        if (categoryById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else {
            card.setCategory(categoryById);
            card.setCreationDate(new Date());
            try {
                this.cardService.add(card);
                return new ResponseEntity<>(card, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Card> updateCategory(@RequestBody Card card) {
        long id = card.getId();
        Card cardById = this.cardService.getCardById(id);
        if (cardById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else {
            this.cardService.update(card);
            return new ResponseEntity<>(card, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Card> deleteCardById(@PathVariable long id) {
        Card delete = this.cardService.delete(id);
        if (delete == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}
