package com.app.db;

import java.util.ArrayList;
import java.util.List;

public class MockSystemCollection implements DBService{
    private List<RoundData> rounds = new ArrayList<>();

    @Override
    public void add(RoundData roundData) {
        rounds.add(roundData);
    }

    @Override
    public void clearDB() {
        rounds.clear();
    }

    @Override
    public void deleteByRoundNumber(int roundNumber) {
        RoundData roundToDelete = null;

        for(RoundData roundData : rounds){
            if(roundData.getTurnNumber() == roundNumber) {
                roundToDelete = roundData;
                break;
            }
        }

        rounds.remove(roundToDelete);
    }

    @Override
    public List<RoundData> getRoundsList() {
        return rounds;
    }

    @Override
    public RoundData getRoundByNumber(int roundNumber) {
        for(RoundData round : rounds){
            if(round.getTurnNumber() == roundNumber){
                return round;
            }
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return rounds.size() == 0;
    }
}
