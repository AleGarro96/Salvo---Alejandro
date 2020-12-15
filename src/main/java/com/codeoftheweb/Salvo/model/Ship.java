package com.codeoftheweb.Salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class Ship
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gameplayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations;

    public Ship() {}
    public Ship(String type, List<String> locations,GamePlayer gamePlayer)
    {
        this.type = type;
        gamePlayer.addShip(this);
        this.locations = locations;
    }

    public long getId()
    {
        return id;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    @JsonIgnore
    public GamePlayer getGamePlayer()
    {
        return gamePlayer;
    }
    public void setGamePlayer(GamePlayer gamePlayer)
    {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getLocations()
    {
        return locations;
    }
    public void setLocations(List<String> locations)
    {
        this.locations = locations;
    }
}