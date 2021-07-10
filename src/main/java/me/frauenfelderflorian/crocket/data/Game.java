package me.frauenfelderflorian.crocket.data;

import me.frauenfelderflorian.crocket.App;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Game implements Serializable {
    private final LocalDate date;
    private final String difficulty;
    private final Map<String, Integer> points;

    public Game(LocalDate date, String difficulty) {
        this.date = Objects.requireNonNullElseGet(date, LocalDate::now);
        if (difficulty != null && !difficulty.equals("")) this.difficulty = difficulty;
        else this.difficulty = "not set";
        points = new HashMap<>();
    }

    public void addPlacePoints(String player, int place) {
        String[] points = App.getPreference(App.preferenceKey.POINTS).split(",");
        this.points.put(player, Integer.parseInt(points[place]));
    }

    public void addPoints(String player, int points) {
        this.points.put(player, points);
    }

    public Map<String, Integer> getPoints() {
        return points;
    }

    public int getPointsPlayer(String player) {
        try {
            return points.get(player);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public String getDifficulty() {
        return difficulty;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Game from: " + date + " with difficulty: \"" + difficulty + "\" and points: " + points;
    }
}
