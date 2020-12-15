package com.codeoftheweb.Salvo.model.dto;

import com.codeoftheweb.Salvo.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerDTO
{
    public PlayerDTO()
    {

    }
    public Map<String, Object> makeDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("name", player.getName());
        dto.put("email", player.getEmail());
        return dto;
    }
    public Map<String, Object> PlayerScoreDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getEmail());
        score.put("total", player.totalScore());
        score.put("won", player.winScore());
        score.put("tied", player.tieScore());
        score.put("lost", player.lostScore());
        dto.put("score", score);
        return dto;
    }
}
