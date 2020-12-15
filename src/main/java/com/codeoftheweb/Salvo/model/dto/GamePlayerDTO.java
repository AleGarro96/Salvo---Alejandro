package com.codeoftheweb.Salvo.model.dto;

import com.codeoftheweb.Salvo.model.Game;
import com.codeoftheweb.Salvo.model.GamePlayer;
import com.codeoftheweb.Salvo.model.Salvo;
import com.codeoftheweb.Salvo.util.Util;
import org.springframework.web.method.HandlerTypePredicate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class GamePlayerDTO
{
    public GamePlayerDTO()
    {

    }
    public  Map<String, Object> makeDTO(GamePlayer gamePlayer)
    {
        Map<String, Object> dto = new LinkedHashMap<>();
        PlayerDTO playerDTO = new PlayerDTO();
        dto.put("id", gamePlayer.getId());
        dto.put("player", playerDTO.makeDTO(gamePlayer.getPlayer()));
        return dto;
    }
    public Map<String, Object> gameView(GamePlayer gamePlayer)
    {
        Game game = gamePlayer.getGame();
        GameDTO gameDTO = new GameDTO();
        ShipDTO shipDTO = new ShipDTO();
        SalvoDTO salvoDTO = new SalvoDTO();
        HitsDTO hitsDTO = new HitsDTO();

        Map<String,Object> hits = new LinkedHashMap<>();

        if (gamePlayer.getGame().getGamePlayers().size() == 2)
        {
            hits.put("self", hitsDTO.makeHitsDTO(gamePlayer));
            hits.put("opponent", hitsDTO.makeHitsDTO(Util.opponent(gamePlayer).get()));
        }else{
                hits.put("self", new ArrayList<>());
                hits.put("opponent", new ArrayList<>());
        }

        Map<String, Object> dto = gameDTO.makeDTO(game);
        dto.put("ships", gamePlayer.getShips().stream()
                .map(ship -> shipDTO.makeDTO(ship))
                .collect(toList()));

        Set<Salvo> salvoes = new HashSet<>();
        for(GamePlayer gamePlayer1 : game.getGamePlayers())
        {
            salvoes.addAll(gamePlayer1.getSalvoes());
        }
        dto.put("salvoes", salvoes.stream().map(salvo -> salvoDTO.makeDTO(salvo)).collect(toList()));
        dto.put("hits",hits);
        //dto.put("gameState", "PLAY");
        dto.put("gameState", Util.getStateGame(gamePlayer));
        return dto;
    }
}
