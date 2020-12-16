package com.codeoftheweb.Salvo.controller;

import com.codeoftheweb.Salvo.model.GamePlayer;
import com.codeoftheweb.Salvo.model.Salvo;
import com.codeoftheweb.Salvo.model.Ship;
import com.codeoftheweb.Salvo.repository.GamePlayerRepository;
import com.codeoftheweb.Salvo.repository.PlayerRepository;
import com.codeoftheweb.Salvo.repository.SalvoRepository;
import com.codeoftheweb.Salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.codeoftheweb.Salvo.util.Util.makeMap;

@RestController
@RequestMapping("/api")
public class SalvoController
{
    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(path = "games/players/{gamePlayerId}/salvoes", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addSalvoes(@PathVariable Long gamePlayerId, @RequestBody Salvo salvo, Authentication authentication)
    {
        if(Util.isGuest(authentication))
        {
            return new ResponseEntity<>(Util.makeMap("error","Not user logged"), HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).get();

        if (gamePlayer == null)
        {
            return new ResponseEntity<>(makeMap("error", "not game player"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer.getPlayer().getId() != playerRepository.findByEmail(authentication.getName()).getId())
        {
            return new ResponseEntity<>(Util.makeMap("error", "Not current player game" + gamePlayerId), HttpStatus.UNAUTHORIZED);
        }
        GamePlayer opponent = new GamePlayer();
        if (Util.opponent(gamePlayer).isPresent())
        {
            opponent = Util.opponent(gamePlayer).get();
        }
        long myTurn = gamePlayer.getSalvoes().size();
        long opponentTurn = opponent.getSalvoes().size();

        if(myTurn > opponentTurn)
        {
            return new ResponseEntity<>(Util.makeMap("error", "opponent turn"), HttpStatus.FORBIDDEN);
        }

        Set<Salvo> salvos = gamePlayer.getSalvoes();

        if (salvo.getLocations().size() > 5)
        {
            return new ResponseEntity<>(Util.makeMap("error", "you can only fire 5 shots per turn"), HttpStatus.FORBIDDEN);
        }else
            {
                Long turnRepeat = salvos.stream().filter(salvo1 -> salvo1.getTurn() == salvo.getTurn()).collect(Collectors.counting());

                if (turnRepeat > 0)
                {
                   return new ResponseEntity<>(Util.makeMap("error", "you can only fire one salvo per turn"),HttpStatus.FORBIDDEN);
                }else {
                        salvo.setTurn(myTurn+1);
                        gamePlayer.addSalvo(salvo);
                        salvoRepository.save(salvo);

                        return new ResponseEntity<>(Util.makeMap("addSalvoes", "Salvos added"),HttpStatus.CREATED);
                }
            }
    }
}
