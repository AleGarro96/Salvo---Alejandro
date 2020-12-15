package com.codeoftheweb.Salvo.model.dto;

import com.codeoftheweb.Salvo.model.GamePlayer;
import com.codeoftheweb.Salvo.model.Salvo;
import com.codeoftheweb.Salvo.model.Ship;
import com.codeoftheweb.Salvo.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class HitsDTO
{

    List<String> hitLocations = new ArrayList<>();
    int carrierHits = 0;
    int battleshipHits = 0;
    int submarineHits = 0;
    int destroyerHits = 0;
    int patrolBoatHits = 0;

    int carrierDamage = 0;
    int battleshipDamage = 0;
    int submarineDamage = 0;
    int destroyerDamage = 0;
    int patrolBoatDamage = 0;

    int missed = 0;

    public HitsDTO()
    {

    }/*
    public List<Map<String,Object>> makeHitsDTO(GamePlayer gamePlayer)
    {
        List<Map<String,Object>> hits = new ArrayList<>();
        GamePlayer gamePlayerOpponent = Util.opponent(gamePlayer).get();

        List<Salvo> salvos = gamePlayerOpponent.getSalvoes().stream().sorted(Comparator.comparingDouble(Salvo::getTurn)).collect(Collectors.toList());
        List<Ship> ships = gamePlayer.getShips().stream().collect(Collectors.toList());

        Map<String, Object> dto = new LinkedHashMap<>();

        salvos.forEach(salvo ->
        {
            dto.put("turn",salvo.getTurn());
            missed = salvo.getLocations().size();
            hitLocations = new ArrayList<>();

            ships.forEach(ship ->
            {
                int numHitsShip = getHitsShip(ship.getLocations(), salvo.getLocations());
                if (numHitsShip > 0)
                {
                    switch (ship.getType())
                    {
                        case "carrier":
                            carrierHits = numHitsShip;
                            carrierDamage += numHitsShip;
                            break;
                        case "battleship":
                            battleshipHits = numHitsShip;
                            battleshipDamage += numHitsShip;
                            break;
                        case "submarine":
                            submarineHits = numHitsShip;
                            submarineDamage += numHitsShip;
                            break;
                        case "destroyer":
                            destroyerHits = numHitsShip;
                            destroyerDamage += numHitsShip;
                            break;
                        case "patrolBoat":
                            patrolBoatHits = numHitsShip;
                            patrolBoatDamage += numHitsShip;
                            break;
                    }
                    //dto.put(ship.getType(), numHitsShip);
                   // hits.add(dto);
                    missed -= numHitsShip;
                }
            });
            dto.put("hitLocations", hitLocations);
            Map<String,Object> damage = new LinkedHashMap<>();
            damage.put("carrierHits",carrierHits);
            damage.put("battleshipHits",battleshipHits);
            damage.put("submarineHits",submarineHits);
            damage.put("destroyerHits",destroyerHits);
            damage.put("patrolBoatHits",patrolBoatHits);
            damage.put("carrierDamage",carrierDamage);
            damage.put("battleshipDamage",battleshipDamage);
            damage.put("submarineDamage",submarineDamage);
            damage.put("destroyerDamage",destroyerDamage);
            damage.put("patrolBoatDamage",patrolBoatDamage);

            dto.put("damages",damage);
            dto.put("missed",missed);
            hits.add(dto);
        });

        return hits;
    }
    private Integer getHitsShip(List<String> lships, List<String> lsalvo) {

        int numHits = 0;
        for (String shipPos : lships)
        {
            for (String salvoPos : lsalvo)
            {
                if (shipPos.equals(salvoPos))
                {
                    hitLocations.add(salvoPos);
                    numHits++;
                }
            }
        }
        return numHits;
    }*/
    public List<Map<String, Object>> makeHitsDTO(GamePlayer gamePlayer) {
        List<Map<String, Object>> hits = new ArrayList<>();
        long carrierDamage = 0;
        long battleshipDamage = 0;
        long submarineDamage = 0;
        long destroyerDamage = 0;
        long patrolboatDamage = 0;


        List<String> carrierLocations = Util.getLocatiosByType("carrier",gamePlayer);
        List<String> battleshipLocations = Util.getLocatiosByType("battleship",gamePlayer);
        List<String> submarineLocations = Util.getLocatiosByType("submarine",gamePlayer);
        List<String> destroyerLocations = Util.getLocatiosByType("destroyer",gamePlayer);
        List<String> patrolBoatLocations = Util.getLocatiosByType("patrolboat",gamePlayer);


        for (Salvo salvoShot : Util.opponent(gamePlayer).get().getSalvoes()) {
            Map<String, Long> damagesPerTurn = new LinkedHashMap<>();

            Map<String, Object> hitsMapPerTurn = new LinkedHashMap<>();
            List<String> hitCellsList = new ArrayList<>();
            int missedShots = salvoShot.getLocations().size();
            long carrierHitsInTurn = 0;
            long battleshipHitsInTurn = 0;
            long submarineHitsInTurn = 0;
            long destroyerHitsInTurn = 0;
            long patrolboatHitsInTurn= 0;

            for (String location : salvoShot.getLocations())
            {
                if (carrierLocations.contains(location))
                {
                    hitCellsList.add(location);
                    carrierHitsInTurn++;
                    carrierDamage++;
                    missedShots--;
                }

                if (battleshipLocations.contains(location))
                {
                    hitCellsList.add(location);
                    battleshipHitsInTurn++;
                    battleshipDamage++;
                    missedShots--;
                }

                if (submarineLocations.contains(location))
                {
                    hitCellsList.add(location);
                    submarineHitsInTurn++;
                    submarineDamage++;
                    missedShots--;
                }

                if (destroyerLocations.contains(location))
                {
                    hitCellsList.add(location);
                    destroyerHitsInTurn++;
                    destroyerDamage++;
                    missedShots--;
                }

                if (patrolBoatLocations.contains(location))
                {
                    hitCellsList.add(location);
                    patrolboatHitsInTurn++;
                    patrolboatDamage++;
                    missedShots--;
                }



            }
            damagesPerTurn.put("carrierHits", carrierHitsInTurn);
            damagesPerTurn.put("battleshipHits", battleshipHitsInTurn);
            damagesPerTurn.put("submarineHits", submarineHitsInTurn);
            damagesPerTurn.put("destroyerHits", destroyerHitsInTurn);
            damagesPerTurn.put("patrolboatHits", patrolboatHitsInTurn);
            hitsMapPerTurn.put("turn", salvoShot.getTurn());
            hitsMapPerTurn.put("hitLocations", hitCellsList);
            hitsMapPerTurn.put("damages", damagesPerTurn);
            hitsMapPerTurn.put("missed", missedShots);
            hits.add(hitsMapPerTurn);
            damagesPerTurn.put("carrier", carrierDamage);
            damagesPerTurn.put("battleship", battleshipDamage);
            damagesPerTurn.put("submarine", submarineDamage);
            damagesPerTurn.put("destroyer", destroyerDamage);
            damagesPerTurn.put("patrolboat", patrolboatDamage);

        }

        return hits;
    }
    public int makeDagame(GamePlayer gamePlayer)
    {
        List<String>carrierLocations= new ArrayList<String>();
        List<String>battleshipLocations = new ArrayList<>();
        List<String>submarineLocations = new ArrayList<>();
        List<String>destroyerLocations = new ArrayList<>();
        List<String>patrolBoatLocations = new ArrayList<>();

        carrierLocations= Util.getLocatiosByType("carrier", gamePlayer);
        battleshipLocations=Util.getLocatiosByType("battleship", gamePlayer);
        submarineLocations=Util.getLocatiosByType("submarine", gamePlayer);
        destroyerLocations=Util.getLocatiosByType("destroyer",gamePlayer);
        patrolBoatLocations=Util.getLocatiosByType("patrolboat", gamePlayer);

        int countImpact=0;

        for(Salvo salvoShot: Util.opponent(gamePlayer).get().getSalvoes())
        {
            for(String location: salvoShot.getLocations())
            {
                if (carrierLocations.contains(location))
                {
                    countImpact++;
                }
                if (battleshipLocations.contains(location))
                {
                    countImpact++;
                }
                if (submarineLocations.contains(location))
                {
                    countImpact++;
                }
                if (destroyerLocations.contains(location))
                {
                    countImpact++;
                }
                if (patrolBoatLocations.contains(location))
                {
                    countImpact++;
                }
            }
        }
        return countImpact++;

    }
}
