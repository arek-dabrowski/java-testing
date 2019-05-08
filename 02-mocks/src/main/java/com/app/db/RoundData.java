package com.app.db;

import com.app.Board;
import com.app.Player;
import com.app.Ranking;

import java.util.List;

public class RoundData {
    private int turnNumber;
    private List<Player> players;
    private Board board;
    private Ranking ranking;

    private int lastRow;
    private int lastColumn;

    private boolean player1Turn;
    private boolean gameEnded;

    public RoundData(int turnNumber, List<Player> players, Board board, Ranking ranking, int lastRow, int lastColumn, boolean player1Turn, boolean gameEnded) {
        this.players = players;
        this.board = board;
        this.ranking = ranking;
        this.lastRow = lastRow;
        this.lastColumn = lastColumn;
        this.turnNumber = turnNumber;
        this.player1Turn = player1Turn;
        this.gameEnded = gameEnded;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public int getLastRow() {
        return lastRow;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public boolean hasGameEnded() {
        return gameEnded;
    }
}
