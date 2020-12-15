package com.codeoftheweb.Salvo.model.dto;

import com.codeoftheweb.Salvo.model.Salvo;

import java.util.LinkedHashMap;
import java.util.Map;

public class SalvoDTO
{
    public SalvoDTO()
    {

    }
    public Map<String, Object> makeDTO(Salvo salvo){
        Map<String, Object> dto = new LinkedHashMap<>();
        // dto.put("id", salvo.getId());
        dto.put("turn", salvo.getTurn());
        dto.put("player", salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations", salvo.getLocations());
        return dto;
    }
}
