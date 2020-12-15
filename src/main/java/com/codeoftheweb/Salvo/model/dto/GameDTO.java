package com.codeoftheweb.Salvo.model.dto;

import com.codeoftheweb.Salvo.model.Game;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class GameDTO {

    public GameDTO()
    {

    }
    public Map<String, Object> makeDTO(Game game)
    {
        Map<String, Object> dto = new LinkedHashMap<>();
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        ScoreDTO scoreDTO = new ScoreDTO();
        dto.put("id", game.getId());
        dto.put("created", game.getCreated());
        dto.put("gamePlayers", game.getGamePlayers().stream()
                .map(gp -> gamePlayerDTO.makeDTO(gp))
                .collect(toList()));
        dto.put("scores", game.getScores().stream()
                .map(s -> scoreDTO.makeDTO(s))
                .collect(toList()));
        return dto;
    }
}
