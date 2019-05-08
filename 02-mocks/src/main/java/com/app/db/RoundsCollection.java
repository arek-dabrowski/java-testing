package com.app.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCursor;
import org.jongo.MongoCollection;

import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundsCollection implements DBService{
    private MongoCollection rounds;

    @SuppressWarnings("deprecation")
    public RoundsCollection() throws UnknownHostException{
        DB db = new MongoClient().getDB("game");
        rounds = new Jongo(db).getCollection("rounds");
    }

    @Override
    public void add(RoundData roundData) {
        rounds.insert(roundData);
    }

    @Override
    public void clearDB() {
        rounds.drop();
    }

    @Override
    public void deleteByRoundNumber(int roundNumber) {
        rounds.remove(rounds.findOne("{roundNumber: #", roundNumber).as(ObjectId.class));
    }

    @Override
    public List<RoundData> getRoundsList() {
        List<RoundData> roundList = new ArrayList<>();
        MongoCursor<RoundData> cursor = rounds.find().as(RoundData.class);
        if(cursor == null) return Collections.emptyList();
        for(RoundData t : cursor) {
            roundList.add(t);
        }
        return roundList;
    }

    @Override
    public RoundData getRoundByNumber(int roundNumber) {
        return rounds.findOne("{roundNumber: #", roundNumber).as(RoundData.class);
    }

    @Override
    public boolean isEmpty() {
        return rounds.count() == 0;
    }
}
