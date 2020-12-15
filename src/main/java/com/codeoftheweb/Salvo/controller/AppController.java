package com.codeoftheweb.Salvo.controller;

import com.codeoftheweb.Salvo.model.GamePlayer;
import com.codeoftheweb.Salvo.model.Player;
import com.codeoftheweb.Salvo.model.Score;
import com.codeoftheweb.Salvo.repository.*;
import com.codeoftheweb.Salvo.model.dto.*;
import com.codeoftheweb.Salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

import static com.codeoftheweb.Salvo.util.Util.isGuest;
import static com.codeoftheweb.Salvo.util.Util.makeMap;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AppController
{
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @RequestMapping("/leaderBoard")
    public List<Map<String, Object>> getLeaderBoard()
    {
        PlayerDTO playerDTO = new PlayerDTO();
        return playerRepository.findAll().stream().sorted(Comparator
                .comparingDouble(Player::totalScore)
                .reversed()
                )
                .map(player -> playerDTO.PlayerScoreDTO(player))
                .collect(toList());
    }
    @RequestMapping(path = "/game_view/{gpId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getGameView(@PathVariable long gpId, Authentication authentication)
    {
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        Player player = playerRepository.findByEmail(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId).orElse(null);

        if(isGuest(authentication))
        {
            return new ResponseEntity<>(makeMap("error", "You are not logged in."), HttpStatus.UNAUTHORIZED);
        }
        if(player == null)
        {
            return new ResponseEntity<>(makeMap("error", "Player not found."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(player != gamePlayer.getPlayer())
        {
            return new ResponseEntity<>(makeMap("error", "Invalid game"), HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer == null)
        {
            return new ResponseEntity<>(makeMap("error", "GamePlayer not found."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(Util.getStateGame(gamePlayer) == "WON"){
            if(gamePlayer.getGame().getScores().size()<2) {
                Set<Score> scores = new HashSet<>();
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinished(Date.from(Instant.now()));
                score1.setScore(1D);
                scoreRepository.save(score1);
                Score score2 = new Score();
                score2.setPlayer(Util.opponent(gamePlayer).get().getPlayer());
                score2.setGame(gamePlayer.getGame());
                score2.setFinished(Date.from(Instant.now()));
                score2.setScore(0D);
                scoreRepository.save(score2);
                scores.add(score1);
                scores.add(score2);

                Util.opponent(gamePlayer).get().getGame().setScores(scores);
            }
        }
        if(Util.getStateGame(gamePlayer) == "TIE"){
            if(gamePlayer.getGame().getScores().size()<2) {
                Set<Score> scores = new HashSet<Score>();
                Score score1 = new Score();
                score1.setPlayer(gamePlayer.getPlayer());
                score1.setGame(gamePlayer.getGame());
                score1.setFinished(Date.from(Instant.now()));
                score1.setScore(0.5D);
                scoreRepository.save(score1);
                Score score2 = new Score();
                score2.setPlayer(Util.opponent(gamePlayer).get().getPlayer());
                score2.setGame(gamePlayer.getGame());
                score2.setFinished(Date.from(Instant.now()));
                score2.setScore(0.5D);
                scoreRepository.save(score2);
                scores.add(score1);
                scores.add(score2);

                Util.opponent(gamePlayer).get().getGame().setScores(scores);
            }
        }
        return new ResponseEntity<>(gamePlayerDTO.gameView(gamePlayer), HttpStatus.ACCEPTED);
    }
}
