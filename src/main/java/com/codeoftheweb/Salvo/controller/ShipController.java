package com.codeoftheweb.Salvo.controller;

import com.codeoftheweb.Salvo.model.GamePlayer;
import com.codeoftheweb.Salvo.model.Ship;
import com.codeoftheweb.Salvo.repository.GamePlayerRepository;
import com.codeoftheweb.Salvo.repository.PlayerRepository;
import com.codeoftheweb.Salvo.repository.ShipRepository;
import com.codeoftheweb.Salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ShipController
{
    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(path = "/games/players/{gpId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addShips(@PathVariable Long gpId, @RequestBody List<Ship> lship, Authentication authentication) {

        GamePlayer gamePlayer = gamePlayerRepository.getOne(gpId);

        if(Util.isGuest(authentication))
        {
            return new ResponseEntity<>(Util.makeMap("error","Not user logged"),HttpStatus.UNAUTHORIZED);
        }
        String email = authentication.getName();
        if (gamePlayer.getPlayer().getId() != playerRepository.findByEmail(email).getId())
        {
            return new ResponseEntity<>(Util.makeMap("error", "Not current player game" + gpId), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer.getShips().size() >= 5)
        {
            return new ResponseEntity<>(Util.makeMap("error","Max ships count reached"),HttpStatus.FORBIDDEN);
        }
        Long value = gamePlayer.getId();
        if (value == null)
        {
            return new ResponseEntity<>(Util.makeMap("error","GamePlayer null"),HttpStatus.UNAUTHORIZED);
        }
        else
            {
                Set<Ship> ships = gamePlayer.getShips();
                if (!ships.isEmpty())
                {
                    return new ResponseEntity<>(Util.makeMap("error", "user already has ships placed"), HttpStatus.FORBIDDEN);
                }
                else
                {
                    for (Ship ship : lship)
                    {
                        Ship sh = new Ship();
                        sh.setType(ship.getType());
                        sh.setLocations(ship.getLocations());
                        gamePlayer.addShip(sh);
                        shipRepository.save(sh);
                    }
                    return new ResponseEntity<>(Util.makeMap("OK", "Added ships"), HttpStatus.CREATED);
                }
            }
        }
}
