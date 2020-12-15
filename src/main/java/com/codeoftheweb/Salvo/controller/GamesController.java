package com.codeoftheweb.Salvo.controller;

import com.codeoftheweb.Salvo.model.dto.GameDTO;
import com.codeoftheweb.Salvo.model.dto.PlayerDTO;
import com.codeoftheweb.Salvo.model.Game;
import com.codeoftheweb.Salvo.model.GamePlayer;
import com.codeoftheweb.Salvo.model.Player;
import com.codeoftheweb.Salvo.repository.GamePlayerRepository;
import com.codeoftheweb.Salvo.repository.GameRepository;
import com.codeoftheweb.Salvo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeoftheweb.Salvo.util.Util.isGuest;
import static com.codeoftheweb.Salvo.util.Util.makeMap;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class GamesController
{
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePlayerRepository gamePlayerRepository;


    @RequestMapping(path = "/games", method = RequestMethod.GET)
    public Map<String, Object> getGamesData(Authentication authentication)
    {
        Map<String, Object> data = new LinkedHashMap<>();
        GameDTO gameDTO = new GameDTO();
        PlayerDTO playerDTO = new PlayerDTO();
        data.put("player", !isGuest(authentication) ? playerDTO.makeDTO(playerRepository.findByEmail(authentication.getName())) : "Guest");
        data.put("games", gameRepository.findAll().stream().map(g -> gameDTO.makeDTO(g)).collect(toList()));
        return data;
    }
    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> Create(Authentication authentication)
    {
        if(isGuest(authentication))
        {
            return new ResponseEntity<>(makeMap("error", "You are not logged in."), HttpStatus.UNAUTHORIZED);
        }

        Player player = playerRepository.findByEmail(authentication.getName());

        if(player == null)
        {
            return new ResponseEntity<>(makeMap("error", "Database error. Player not found."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Game game = new Game();
        GamePlayer gamePlayer = new GamePlayer(game,player);
        gameRepository.save(game);
        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }
    @RequestMapping(path = "/game/{gpId}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> Join(@PathVariable Long gpId,  Authentication authentication)
    {
        if(isGuest(authentication))
        {
            return new ResponseEntity<>(makeMap("error", "You are not logged in."), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByEmail(authentication.getName());
        if(player == null)
        {
            return new ResponseEntity<>(makeMap("error", "Database error. Player not found."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Game game = gameRepository.findById(gpId).orElse(null);
        if(game == null)
        {
            return new ResponseEntity<>(makeMap("error", "Game not found."), HttpStatus.FORBIDDEN);
        }
        if (game.getPlayers().contains(player))
        {
            return new ResponseEntity<>(makeMap("error", "You are already in the game!"), HttpStatus.FORBIDDEN);
        }
        if(game.getPlayers().size() >= 2)
        {
            return new ResponseEntity<>(makeMap("error", "Game is full!"), HttpStatus.FORBIDDEN);
        }
        GamePlayer gamePlayer = new GamePlayer(game,player);
        if(gamePlayer == null)
        {
            return new ResponseEntity<>(makeMap("error", "Database error. Couldn't create GamePlayer."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }
}