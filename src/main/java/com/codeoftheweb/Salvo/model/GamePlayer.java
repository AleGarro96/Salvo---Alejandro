package com.codeoftheweb.Salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class GamePlayer
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    @OrderBy
    private Set<Ship> ships;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    @OrderBy
    private Set<Salvo> salvoes;

    public GamePlayer() {}
    public GamePlayer(Game game,Player player)
    {
        player.addGamePlayer(this);
        game.addGamePlayer(this);
        this.ships = new HashSet<>();
        this.salvoes = new HashSet<>();
        date = LocalDateTime.now();
    }
    public long getId()
    {
        return id;
    }
    public LocalDateTime getDate()
    {
        return date;
    }
    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }
    public Player getPlayer()
    {
        return player;
    }
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    public Game getGame()
    {
        return game;
    }
    public void setGame(Game game)
    {
        this.game = game;
    }
    public Set<Ship> getShips()
    {
        return ships;
    }
    public void setShips(Set<Ship> ships)
    {
        this.ships = ships;
    }
    public Set<Salvo> getSalvoes()
    { return salvoes;
    }
    public void setSalvoes(Set<Salvo> salvos)
    {
        this.salvoes = salvos;
    }
    public void addShip(Ship ship)
    {
        ship.setGamePlayer(this);
        ships.add(ship);
    }
    public void addSalvo(Salvo salvo)
    {
        salvo.setGamePlayer(this);
        salvoes.add(salvo);
    }
    public Score getScore(double score)
    {
        if(score < 0)
            return null;
        return new Score(score, player, game);
    }
}
