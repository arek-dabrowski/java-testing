package com.app;

import com.app.db.RoundsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player player1;
    private Player player2;
    private List<Player> players;
    private Board board;
    private Ranking ranking;

    private int lastRow = -1;
    private int lastColumn = -1;
    private int turnNumber = 1;

    private boolean player1Turn = true;
    private boolean gameEnded = false;

    private RoundsService db = new RoundsService(true);

    public Game(Player player1, Player player2, int boardHeight, int boardWidth, String rankingPath){
        if(player1.getCoinColor() == player2.getCoinColor()) throw new IllegalArgumentException("Players cannot have same coin color!");
        if(player1.toString().equalsIgnoreCase(player2.toString())) throw new IllegalArgumentException("Players cannot have same name!");

        this.player1 = player1;
        this.player2 = player2;

        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        this.board = new Board(boardHeight, boardWidth);

        this.ranking = new Ranking(rankingPath);
        try {
            ranking.loadRankingFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Game(Player player1, Player player2, int boardHeight, int boardWidth, String rankingPath, RoundsService db){
        this(player1, player2, boardHeight, boardWidth, rankingPath);
        this.db = db;
        if(!this.db.isDatabaseEmpty()) this.db.deleteAllRounds();
    }


    public Board getBoard(){
        return this.board;
    }

    public boolean makeMove(int column){
        lastColumn = column;
        if(player1Turn) lastRow = board.putCoinInColumn(new Coin(player1.getCoinColor()), column);
        else lastRow = board.putCoinInColumn(new Coin(player2.getCoinColor()), column);

        if(doesLastMoveWins(lastRow, lastColumn)) {
            gameEnded = true;
            savePlayersScore();
            return true;
        }
        else if(isGameDrawn()){
            gameEnded = true;
        }
        else{
            player1Turn ^= true;
            saveRound();
            turnNumber++;
        }

        return false;
    }

    public void undoLastMove(){
        board.resetCoin(lastRow, lastColumn);
        player1Turn ^= true;
        turnNumber--;
        db.deleteRound(turnNumber);
    }

    private boolean doesLastMoveWins(int row, int column){
        return board.checkIfRowWins(row, column) || board.checkIfColumnWins(row, column) || board.checkIfDiagonalsWin(row, column);
    }

    private boolean isGameDrawn(){
        return board.isBoardFull();
    }

    public boolean isPlayer1Turn(){
        return player1Turn;
    }

    public Player getCurrentPlayer(){
        if(isPlayer1Turn()) return player1;
        return player2;
    }

    public boolean hasGameEnded(){
        return gameEnded;
    }

    public void resetGame(){
        if(hasGameEnded()){
            board.cleanBoard();
            player1Turn = true;
            gameEnded = false;
            lastRow = lastColumn = -1;
            turnNumber = 1;
            players.clear();
            db.deleteAllRounds();
        } else throw new IllegalArgumentException("Can't reset running game!");
    }

    private void savePlayersScore(){
        if(isPlayer1Turn()) {
            player1.setWins(player1.getWins() + 1);
            player2.setLoses(player2.getLoses() + 1);
        } else {
            player2.setWins(player2.getWins() + 1);
            player1.setLoses(player1.getLoses() + 1);
        }

        ranking.savePlayerScore(player1);
        ranking.savePlayerScore(player2);

        try {
            ranking.saveRankingToFile();
        } catch (IOException e) { }
    }

    public void printRanking(){
        ranking.printRanking();
    }

    private void saveRound(){
        db.insertRound(turnNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);
    }

    public RoundsService getDb() {
        return db;
    }
}
