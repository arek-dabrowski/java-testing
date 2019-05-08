package com.app;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Ranking {
    private String filePath;
    private List<String[]> ranking = new ArrayList<>();

    public Ranking(String filePath){
        this.filePath = filePath;
    }

    public void savePlayerScore(Player player){
        if(!playerExistsInRanking(player)) ranking.add(new String[]{player.toString(), String.valueOf(player.getWins()), String.valueOf(player.getLoses())});
        else updatePlayer(player);

        resetPlayer(player);
    }

    public List<String[]> getRanking(){
        return ranking;
    }

    public void loadRankingFromFile() throws IOException{
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
            ranking = csvReader.readAll();
        }
    }

    private boolean playerExistsInRanking(Player player){
        for(String[] record : ranking){
            if(record[0].equalsIgnoreCase(player.toString())) return true;
        }
        return false;
    }

    private void updatePlayer(Player player){
        for(String[] record : ranking){
            if(record[0].equalsIgnoreCase(player.toString())) {
                record[1] = String.valueOf(Integer.parseInt(record[1]) + player.getWins());
                record[2] = String.valueOf(Integer.parseInt(record[2]) + player.getLoses());
                player.setWins(0);
                player.setLoses(0);
                break;
            }
        }
    }

    public String[] getRecord(Player player){
        if(playerExistsInRanking(player)){
            for(String[] record : ranking){
                if(record[0].equalsIgnoreCase(player.toString())) {
                    return record;
                }
            }
        }

        return null;
    }

    private void resetPlayer(Player player){
        player.setWins(0);
        player.setLoses(0);
    }

    public void saveRankingToFile() throws IOException{
        if(Files.notExists(Paths.get(filePath))) throw new FileNotFoundException();
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(filePath));

                CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
        ) {
            String[] headerRecord = {"name", "wins", "loses"};
            csvWriter.writeNext(headerRecord);

            for(String[] record : ranking){
                csvWriter.writeNext(record);
            }
        }
    }

    public void printRanking(){
        int index = 1;
        for(String [] record : ranking){
            System.out.println(index++ + ":: " + record[0] + " W: " + record[1] + "\tL: " + record[2]);
        }
    }

}
