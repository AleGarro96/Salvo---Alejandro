package com.codeoftheweb.Salvo.util;

import com.codeoftheweb.Salvo.model.GamePlayer;
import com.codeoftheweb.Salvo.model.dto.HitsDTO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Util
{
    public static boolean isGuest(Authentication authentication)
    {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
    public static Map<String, Object> makeMap(String key, Object value)
    {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }
    public static Optional<GamePlayer> opponent(GamePlayer gamePlayer)
    {
        return gamePlayer.getGame().getGamePlayers().stream().filter(gamePlayer1 -> gamePlayer1.getId() != gamePlayer.getId()).findFirst();
    }
    public static Map<String, Integer> shipTypes = Stream.of(new Object[][]
            {
            {"carrier", 5},
            {"battleship", 4},
            {"submarine", 3},
            {"destroyer", 3},
            {"patrolboat", 2}
        }).collect(toMap(data -> (String)data[0], data -> (Integer)data[1]));

    public static List<String> getLocatiosByType(String type, GamePlayer self)
    {
        return self.getShips().size() == 0 ? new ArrayList<>() : self.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst().get().getLocations();
    }
    public static String getStateGame(GamePlayer gamePlayer)
    {
        if (gamePlayer.getShips().isEmpty())
        {
            return "PLACESHIPS";
        }
        if(gamePlayer.getGame().getGamePlayers().size()== 1 || opponent(gamePlayer).get().getShips().size() == 0)
        {
            return "WAITINGFOROPP";
        }

        long myTurn = gamePlayer.getSalvoes().size();
        long enemyTurnSalvo = opponent(gamePlayer).get().getSalvoes().size();

        if (myTurn > enemyTurnSalvo)
        {
            return "WAIT";
        }

        if(gamePlayer.getGame().getGamePlayers().size()==2)
        {
            HitsDTO dtoHit= new HitsDTO();
            int mySelfImpact= dtoHit.makeDagame(gamePlayer);
            int opponentImpact= dtoHit.makeDagame(opponent(gamePlayer).get());
            if(mySelfImpact==17 && opponentImpact==17)
            {
                return  "TIE";
            }else if(mySelfImpact==17)
            {
                return "LOSE";
            }else if(opponentImpact==17)
            {
                return "WON";
            }
        }
        return "PLAY";
    }
}
