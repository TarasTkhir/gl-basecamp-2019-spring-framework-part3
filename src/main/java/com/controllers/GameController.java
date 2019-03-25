package com.controllers;

import com.services.GameService;
import com.wire.ResponseGame;
import com.wire.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.services.GameServiceImpl;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@RestController
@Validated
public class GameController {

    private GameService game;

    @Autowired
    public void setGame(GameServiceImpl game) {
        this.game = game;
    }


    @PostMapping("/add/{number}")
    public ResponseEntity<ResponseMessages> putMapping(@PathVariable @Min(value = 0,
            message = "Too small number! Must be 0 or greater then 0") @Max(value = 5,
            message = "Too big number! Must be 5 or less then 5") int number) {
        ResponseMessages responseMessages =
                game.setNumberIntoDatabaseAndReturnId(number);
        responseMessages.setStatus(HttpStatus.OK.toString());
        return ResponseEntity.ok().body(responseMessages);
    }

    @GetMapping("/find/{number}/{id}")
    public ResponseEntity<ResponseGame> findMapping(@PathVariable int number, @PathVariable int id) {

        return ResponseEntity.ok().body(game.gameplay(number, id));
    }

    @PostMapping("/randomNumber")
    public ResponseEntity<ResponseMessages> putRandomMapping() {
        ResponseMessages responseMessages =
                game.setRandomNumberIntoDatabaseAndReturnId();
        responseMessages.setStatus(HttpStatus.OK.toString());
        return ResponseEntity.ok().body(responseMessages);
    }


    @GetMapping("/")
    public ResponseEntity<ResponseMessages> startPageMapping() {
        return ResponseEntity.ok().body(new ResponseMessages(HttpStatus.OK.toString(),
                "_____H_E_L_L_O___M_Y___F_R_I_E_N_D____"));
    }
}