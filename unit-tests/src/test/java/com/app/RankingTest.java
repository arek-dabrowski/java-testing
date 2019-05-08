package com.app;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class RankingTest {
    File file;
    Ranking ranking;
    String filepath = ".//src//test//resources//fake-ranking.csv";

    @BeforeEach
    void setUp(){
        file = new File(filepath);
        try {
            file.createNewFile();
        } catch (IOException e) { }

        ranking = new Ranking(filepath);
    }

    @Test
    void trivialTest(){
        assertThat(true).isTrue();
    }

    @Test
    void ranking_Should_CreateSuccessfully(){
        assertThat(ranking).isNotNull();
    }

    @Test
    void getRanking_ShouldNotBeNull(){
        assertThat(ranking.getRanking()).isNotNull();
    }

    @Test
    void savePlayer_ShouldAdd_NonExistingPlayer(){
        Player player = new Player("Piotr", Color.RED);
        int initSize = ranking.getRanking().size();

        ranking.savePlayerScore(player);
        int actualSize = ranking.getRanking().size();

        assertThat(actualSize).isEqualTo(initSize + 1);
    }

    @Test
    void savePlayer_ShouldUpdate_ExistingPlayer(){
        Player player = new Player("Piotr", Color.RED);
        player.setWins(3);
        player.setLoses(4);
        ranking.savePlayerScore(player);
        int initSize = ranking.getRanking().size();

        player.setWins(1);
        player.setLoses(3);
        ranking.savePlayerScore(player);
        int actualSize = ranking.getRanking().size();

        assertThat(actualSize).isEqualTo(initSize);
        assertThat(ranking.getRecord(player)[1]).isEqualTo(String.valueOf(4));
        assertThat(ranking.getRecord(player)[2]).isEqualTo(String.valueOf(7));
    }

    @Test
    void getRecord_ShouldBeNull_IfPlayerIsNotInRanking(){
        Player player = new Player("Piotr", Color.RED);

        assertThat(ranking.getRecord(player)).isNull();
    }

    @Test
    void getRecord_ShouldNotBeNull_IfPlayerIsInRanking(){
        Player player = new Player("Piotr", Color.RED);
        ranking.savePlayerScore(player);

        assertThat(ranking.getRecord(player)).contains("Piotr", "0");
    }

    @Test
    void loadRankingFromFile_ThrowsException_WhenFileDoesNotExist(){
        Ranking newRanking = new Ranking("wrongpath.csv");

        assertThatExceptionOfType(NoSuchFileException.class).isThrownBy(() -> { newRanking.loadRankingFromFile(); });
    }

    @Test
    void loadRankingFromFile_ShouldLoadSuccessfully_IfFileExists(){
        String newPath = ".//src//test//resources//test-ranking.csv";
        Ranking newRanking = new Ranking(newPath);
        long expected = 0;
        try {
            newRanking.loadRankingFromFile();
            expected = Files.lines(Paths.get(newPath)).count();

        } catch (IOException e) { }

        long actual = newRanking.getRanking().size();

        assertThat(actual).isEqualTo(expected - 1);
    }

    @Test
    void saveRankingToFile_ThrowsException_WhenFileDoesNotExist(){
        Ranking newRanking = new Ranking("wrongpath.csv");
        newRanking.savePlayerScore(new Player("Adam", Color.RED));

        assertThatExceptionOfType(FileNotFoundException.class).isThrownBy(() -> { newRanking.saveRankingToFile(); });
    }

    @Test
    void saveRankingToFile_ShouldSaveSuccessfully_IfFileExists(){
        ranking.savePlayerScore(new Player("Adam", Color.RED));
        ranking.savePlayerScore(new Player("Piotr", Color.RED));
        long initFileSize = -1;
        long fileSize = -1;
        try {
            initFileSize = Files.lines(Paths.get(filepath)).count();
            ranking.saveRankingToFile();
            fileSize = Files.lines(Paths.get(filepath)).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(fileSize).isEqualTo(initFileSize + 3);
    }

    @AfterEach
    void tearDown(){
        ranking = null;
        file.delete();
    }
}
