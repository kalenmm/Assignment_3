package com.blum.springbanking.service;

import com.blum.springbanking.CustomUserDetails;
import com.blum.springbanking.models.Card;
import com.blum.springbanking.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepo;
    private double balance;

    public ArrayList<Card> getUserCardList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        long userId = user.getId();

        ArrayList<Card> cardList = new ArrayList<>();
        for (Card card : cardRepo.findAll()) {
            if (card.getUser_id() == userId) {
                cardList.add(card);
                balance = balance + card.getMoney();
            }
        }
        return cardList;
    }

    public double getBalance() {
        return balance;
    }
}
