package com.services;


import com.repository.GameplayRepository;
import com.wire.ResponseGame;
import com.wire.ResponseMessages;

public interface GameService {

    ResponseGame gameplay(int guessNumber, int id);

    ResponseMessages setNumberIntoDatabaseAndReturnId(int number);

    void setGameplayRepository(GameplayRepository gameplayRepository);

    void setNumberGeneratorImpl (NumberGeneratorImpl numberGenerator);

    ResponseMessages setRandomNumberIntoDatabaseAndReturnId();
}


