package com.app.db;

import com.app.Board;
import com.app.Player;
import com.app.Ranking;

import java.rmi.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoundsService {
    private DBService rounds;

    public RoundsService(){
        initDb();
    }

    public RoundsService(boolean isFake){
        if(isFake) rounds = new MockSystemCollection();
        else initDb();
    }

    private void initDb(){
        try {
            Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
            mongoLogger.setLevel(Level.SEVERE);
            rounds = new RoundsCollection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void insertRound(int turnNumber, List<Player> players, Board board, Ranking ranking, int lastRow, int lastColumn, boolean player1Turn, boolean gameEnded){
        if(board.getBoard().length < lastRow) throw new IllegalArgumentException("Last row was out of range!");
        if(board.getBoard()[0].length < lastColumn) throw new IllegalArgumentException("Last column was out of range!");
        if(gameEnded) throw new IllegalArgumentException("You can't save in database ended game rounds!");
        rounds.add(new RoundData(turnNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded));
    }

    public void deleteAllRounds(){
        if(rounds.isEmpty()) throw new IllegalArgumentException("You can't erase empty database!");
        rounds.clearDB();
    }

    public List<RoundData> getAllRounds(){
        return rounds.getRoundsList();
    }

    public RoundData getRound(int roundNumber){
        return rounds.getRoundByNumber(roundNumber);
    }

    public void deleteRound(int roundNumber) {
        if(getRound(roundNumber) == null) throw new IllegalArgumentException("You can't delete non existing round!");
        rounds.deleteByRoundNumber(roundNumber);
    }

    public boolean isDatabaseEmpty(){
        return rounds.isEmpty();
    }
}
