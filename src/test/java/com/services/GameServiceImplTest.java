package com.services;

import com.configurations.WebConfiguration;
import com.entity.GameplayEntity;
import com.repository.GameplayRepository;
import com.wire.ResponseGame;
import com.wire.ResponseMessages;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("com.configurations")
@ContextConfiguration(classes = {WebConfiguration.class})
public class GameServiceImplTest {

    private GameService gameService;

    private GameplayRepository gameplayRepository;


    @Autowired
    public void setGameplayRepository(GameplayRepository gameplayRepository) {
        this.gameplayRepository = gameplayRepository;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @After
    public void after() {
        this.gameplayRepository = null;
        this.gameService = null;
    }

    @Test
    public void givenWriteGuessNumberAndWriteId_whenMethodGameplayCalled_thenReturnWin() {

        gameplayRepository.save(new GameplayEntity(1, 1));
        ResponseGame gameplay = gameService.gameplay(1, 1);
        assertEquals("Your guess is: 1", gameplay.getGuess());
        assertEquals("W_I_N_N_E_R_!", gameplay.getResult());
    }

    @Test
    public void givenWrongGuessNumberAndWriteId_whenMethodGameplayCalled_thenReturnLoose() {

        gameplayRepository.save(new GameplayEntity(1, 1));
        ResponseGame gameplay = gameService.gameplay(2, 1);
        assertEquals("Your guess is: 2", gameplay.getGuess());
        assertEquals("L_O_O_S_E", gameplay.getResult());
    }

    @Test(expected = com.exception.NotValidInformationException.class)
    public void givenGuessNumberAndWrongId_whenMethodGameplayCalled_thenReturnException() {

        gameplayRepository.save(new GameplayEntity(1, 1));

        gameService.gameplay(1, 7);
    }

    @Test
    public void givenNumber_whenSetNumberIntoDatabaseAndReturnId_thenReturnId() {

        ResponseMessages responseMessages = gameService.setNumberIntoDatabaseAndReturnId(1);
        Integer s = valueOf(responseMessages.getMessage().substring(5));
        GameplayEntity one = gameplayRepository.findOne(s);
        assertEquals("ID = "+  one.getId(), responseMessages.getMessage());
    }

    @Test
    public void givenRandomNumber_whenSetRandomNumberIntoDatabaseAndReturnId_thenReturnId() {
        ResponseMessages responseMessages = gameService.setRandomNumberIntoDatabaseAndReturnId();
        Integer s = valueOf(responseMessages.getMessage().substring(5));
        GameplayEntity one = gameplayRepository.findOne(s);
        assertEquals("ID = "+  one.getId(), responseMessages.getMessage());
    }
}