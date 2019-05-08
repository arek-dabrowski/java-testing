package com.app;

import java.io.IOException;

public class Game {
    private String rankingPath;
    private Player player1;
    private Player player2;
    private Board board;
    private Ranking ranking;

    private int lastRow = -1;
    private int lastColumn = -1;

    private boolean player1Turn = true;
    private boolean gameEnded = false;

    public Game(Player player1, Player player2, int boardHeight, int boardWidth, String rankingPath){
        if(player1.getCoinColor() == player2.getCoinColor()) throw new IllegalArgumentException("Players cannot have same coin color!");
        if(player1.toString().equalsIgnoreCase(player2.toString())) throw new IllegalArgumentException("Players cannot have same name!");

        this.player1 = player1;
        this.player2 = player2;

        this.board = new Board(boardHeight, boardWidth);

        this.rankingPath = rankingPath;
        this.ranking = new Ranking(this.rankingPath);
        try {
            ranking.loadRankingFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        }

        return false;
    }

    public void undoLastMove(){
        board.resetCoin(lastRow, lastColumn);
        player1Turn ^= true;
    }

    private boolean doesLastMoveWins(int row, int column){
        if(board.checkIfRowWins(row, column) || board.checkIfColumnWins(row, column) || board.checkIfDiagonalsWin(row, column)) return true;
        return false;
    }

    private boolean isGameDrawn(){
        if(board.isBoardFull()) return true;
        return false;
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
}
