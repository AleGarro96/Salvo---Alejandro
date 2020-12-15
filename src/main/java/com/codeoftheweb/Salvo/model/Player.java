package com.codeoftheweb.Salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
public class Player
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> scores;

    public Player() {}
    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gamePlayers = new HashSet<>();
        this.scores = new HashSet<>();
    }

    public Long getId() {
        return id;
    }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<GamePlayer> getGamePlayers() { return gamePlayers; }
    public void setGamePlayers(Set<GamePlayer> gamePlayers) { this.gamePlayers = gamePlayers; }
    public Set<Score> getScores() {
        return scores;
    }
    public void setScores(Set<Score> scores) { this.scores = scores; }

    public void addGamePlayer(GamePlayer gamePlayer)
    {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }
    public void addScore(Score score)
    {
        score.setPlayer(this);
        scores.add(score);
    }
    @JsonIgnore
    public List<Game> getGames()
    {
        return getGamePlayers().stream().map(sub -> sub.getGame()).collect(toList());
    }
    public Score getScore(Game game)
    {
        return scores.stream().filter(p -> p.getGame().getId() == game.getId()).findFirst().orElse(null);
    }
    public double totalScore()
    {
        double total = getScores().stream().mapToDouble(Score::getScore).sum();
        return total;
    }
    public Long winScore() {
        return getScores().stream().filter(score -> score != null && score.getScore() == 1).count();
    }
    public Long lostScore() {
        return getScores().stream().filter(score -> score != null && score.getScore() == 0).count();
    }
    public Long tieScore()
    {
        return getScores().stream().filter(score -> score != null && score.getScore() == 0.5).count();
    }
}
