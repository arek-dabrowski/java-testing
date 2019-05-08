package com.app.db;

import java.util.List;

public interface DBService {
    void add(RoundData roundData);
    void clearDB();
    void deleteByRoundNumber(int roundNumber);
    List<RoundData> getRoundsList();
    RoundData getRoundByNumber(int roundNumber);
    boolean isEmpty();
}
