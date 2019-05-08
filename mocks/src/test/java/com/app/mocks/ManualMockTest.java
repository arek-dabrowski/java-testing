package com.app.mocks;

import static org.assertj.core.api.Assertions.*;

import com.app.*;
import com.app.db.RoundData;
import com.app.db.RoundsCollection;
import com.app.db.RoundsService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualMockTest {
    private int roundNumber;
    private List<Player> players;
    private Board board;
    private String rankingPath;
    private Ranking ranking;
    private int lastRow;
    private int lastColumn;
    private boolean player1Turn;
    private boolean gameEnded;

    private Game game;

    //System Under Test
    private RoundsService roundsService;

    @BeforeEach
    void setUp(){
        roundsService = new RoundsService(true);
        players = new ArrayList<>(Arrays.asList(
                new Player("Adam", Color.RED),
                new Player("Ewa", Color.GREEN)
        ));
        board = new Board(6, 10);
        rankingPath = ".//src//test//resources//test-ranking.csv";
        ranking = new Ranking(rankingPath);
        lastRow = 3;
        lastColumn = 5;
        player1Turn = true;
        gameEnded = false;
        roundNumber = 1;
    }

    @Test
    void trivialTest(){
        assertThat(true).isTrue();
    }

    @Test
    void insertRound_ShouldBeSuccessful_IfAddedMultipleData(){
        initList();

        assertThat(roundsService.getAllRounds().size()).isEqualTo(5);
    }

    @Test
    void insertRound_ShouldThrowException_IfLastColumnIsOutOfRange(){
        lastColumn = 11;

        assertThatIllegalArgumentException().isThrownBy(() -> { roundsService.insertRound(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded); })
                .withMessage("%s", "Last column was out of range!");
    }

    @Test
    void insertRound_ShouldThrowException_IfLastRowIsOutOfRange(){
        lastRow = 7;

        assertThatIllegalArgumentException().isThrownBy(() -> { roundsService.insertRound(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded); })
                .withMessage("%s", "Last row was out of range!");
    }

    @Test
    void insertRound_ShouldThrowException_IfGameHasEnded(){
        gameEnded = true;

        assertThatIllegalArgumentException().isThrownBy(() -> { roundsService.insertRound(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded); })
                .withMessage("%s", "You can't save in database ended game rounds!");
    }

    @Test
    void deleteRound_ShouldDelete_IfTurnNumberExists(){
        initList();

        int listSize = roundsService.getAllRounds().size();
        roundsService.deleteRound(3);

        assertThat(roundsService.getAllRounds().size()).isEqualTo(listSize - 1);
    }

    @Test
    void deleteRound_ShouldThrowException_IfTurnNumberDoesNotExist(){
        initList();

        assertThatIllegalArgumentException().isThrownBy(() -> { roundsService.deleteRound(8); })
                .withMessage("%s", "You can't delete non existing round!");
    }

    @Test
    void isDatabaseEmpty_ShouldReturnTrue_IfDatabaseIsEmpty(){
        assertThat(roundsService.isDatabaseEmpty()).isTrue();
    }

    @Test
    void resetGame_ShouldClearDatabase_IfGameHasEnded(){
        game = new Game(players.get(0), players.get(1), 9, 8, rankingPath);
        GameTest.initPlayer1WonGame(game);
        game.makeMove(3);
        game.resetGame();

        assertThat(game.getDb().isDatabaseEmpty()).isTrue();
    }

    @Test
    void newGame_ShouldClearDatabase_WhenCreated(){
        initList();

        game = new Game(players.get(0), players.get(1), 9, 8, rankingPath, roundsService);

        assertThat(game.getDb().isDatabaseEmpty()).isTrue();
    }

    @AfterEach
    void tearDown(){
        players = null;
        board = null;
        ranking = null;
    }

    private void initList(){
        for(int i = 1; i < 6; i++){
            roundsService.insertRound(i, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);
        }
    }
}
