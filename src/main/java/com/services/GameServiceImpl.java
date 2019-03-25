package com.services;

import com.entity.GameplayEntity;
import com.exception.NotValidInformationException;
import com.repository.GameplayRepository;
import com.wire.ResponseGame;
import com.wire.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private NumberGeneratorImpl numberGeneratorImpl;

    private GameplayRepository gameplayRepository;

    private GameplayEntity entity;

    @Autowired
    @Override
    public void setGameplayRepository(GameplayRepository gameplayRepository) {
        this.gameplayRepository = gameplayRepository;
    }

    @Autowired
    public void setNumberGeneratorImpl(NumberGeneratorImpl numberGenerator) {
        this.numberGeneratorImpl = numberGenerator;
    }

    @Override
    public ResponseGame gameplay(int guessNumber, int id) {

        int number = 0;

        entity = gameplayRepository.findOne(id);

        try {
            number = entity.getNumber();
        } catch (NullPointerException e) {

            throw new NotValidInformationException("NO_NUMBER_WITH_SUCH_ID_FOUND");
        }
        if (number == guessNumber) {
            return new ResponseGame("Your guess is: " + guessNumber, "W_I_N_N_E_R_!");
        }
        return new ResponseGame("Your guess is: " + guessNumber, "L_O_O_S_E");
    }

    @Override
    public ResponseMessages setNumberIntoDatabaseAndReturnId(int number) {

        GameplayEntity gameplayEntity = new GameplayEntity();
        gameplayEntity.setNumber(number);
        gameplayRepository.save(gameplayEntity);
        return new ResponseMessages("-", "ID = " + gameplayEntity.getId());
    }

    @Override
    public ResponseMessages setRandomNumberIntoDatabaseAndReturnId() {

        GameplayEntity gameplayEntity = new GameplayEntity();
        gameplayEntity.setNumber(numberGeneratorImpl.numberGenerator());
        gameplayRepository.save(gameplayEntity);
        return new ResponseMessages("-", "ID = " + gameplayEntity.getId());
    }

}
