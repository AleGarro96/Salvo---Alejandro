package com.codeoftheweb.Salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
public class Game
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime date;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    @OrderBy
    private Set<GamePlayer> gamePlayers;

    @OrderBy
    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Score> scores;

    public Game()
    {
        this.gamePlayers = new HashSet<GamePlayer>();
    }
    public Game(LocalDateTime date)
    {
        this.date = date;
        this.gamePlayers = new HashSet<>();
        this.scores = new HashSet<>();
    }

    public long getId() { return id; }
    public LocalDateTime getCreated() { return date; }
    public void setCreated(LocalDateTime created) { this.date = created; }
    public Set<GamePlayer> getGamePlayers() { return gamePlayers; }
    public void setGamePlayers(Set<GamePlayer> gamePlayers) { this.gamePlayers = gamePlayers; }
    public Set<Score> getScores() { return scores; }
    public void setScores(Set<Score> scores) { this.scores = scores; }

    public void addGamePlayer(GamePlayer gamePlayer)
    {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }
    public void addScore(Score score)
    {
        score.setGame(this);
        scores.add(score);
    }
    public List<Player> getPlayers()
    {
        return getGamePlayers().stream().map(gamePlayer -> gamePlayer.getPlayer()).collect(toList());
    }
}