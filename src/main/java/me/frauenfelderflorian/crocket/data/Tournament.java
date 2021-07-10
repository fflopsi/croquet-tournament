package me.frauenfelderflorian.crocket.data;

import me.frauenfelderflorian.crocket.App;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Tournament implements Serializable {
    private final String semester;
    private final List<Game> games;
    private final List<String> players;

    public Tournament(String semester) {
        this.semester = semester;
        games = new ArrayList<>();
        players = new ArrayList<>(Arrays.asList(App.getPreference(App.preferenceKey.PLAYERS).split(",")));
    }

    public void save() throws IOException {
        FileOutputStream file = new FileOutputStream(App.getPreference(App.preferenceKey.DATA_DIR_PATH)
                + File.separator + "tournament_" + this.semester + ".ser");
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(this);
        out.close();
        file.close();
    }

    public static Tournament load(String semester) throws IOException, ClassNotFoundException {
        Tournament tournament;
        FileInputStream file = new FileInputStream(App.getPreference(App.preferenceKey.DATA_DIR_PATH)
                + File.separator + "tournament_" + semester + ".ser");
        ObjectInputStream in = new ObjectInputStream(file);
        tournament = (Tournament) in.readObject();
        in.close();
        file.close();
        return tournament;
    }

    public void setPlayers(List<String> players) {
        this.players.clear();
        this.players.addAll(players);
        App.putPreference(App.preferenceKey.PLAYERS, String.join(",", players));
    }

    public List<String> getPlayers() {
        return players;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public Game getGame(LocalDate date) {
        for (Game game : games) if (game.getDate() == date) return game;
        return null;
    }

    public List<Game> getGames() {
        return games;
    }

    public int getPointsPlayer(String player) {
        int totalPoints = 0;
        for (Game game : games) totalPoints += game.getPointsPlayer(player);
        return totalPoints;
    }

    public Map<String, Integer> getPoints() {
        Map<String, Integer> points = new HashMap<>();
        for (String player : players) points.put(player, getPointsPlayer(player));
        return points;
    }

    public LinkedHashMap<String, Integer> getOrder() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(getPoints().entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        LinkedHashMap<String, Integer> order = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) order.put(entry.getKey(), entry.getValue());
        return order;
    }

    public String getSemester() {
        return semester;
    }

    @Override
    public String toString() {
        List<String> gameInfo = new ArrayList<>(games.size());
        for (Game game : games) gameInfo.add(game.toString());
        return "Tournament from: " + semester + " with players: " + players + "\nGames in Tournament: " + gameInfo;
    }
}
