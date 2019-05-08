package com.app.mocks;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.app.Board;
import com.app.Player;
import com.app.Ranking;
import com.app.db.RoundData;
import com.app.db.RoundsCollection;
import com.app.db.RoundsService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MockitoDBTest {

    private int roundNumber;
    private List<Player> players;
    private Board board;
    private Ranking ranking;
    private int lastRow;
    private int lastColumn;
    private boolean player1Turn;
    private boolean gameEnded;

    private RoundData round;

    @Mock
    private RoundsCollection roundsCollection;

    @InjectMocks
    private RoundsService roundsService = new RoundsService();

    @BeforeEach
    void setUp(){
        players = mock(List.class);
        board = spy(new Board(6, 7));
        ranking = mock(Ranking.class);
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
    void getRound_ShouldReturnData_IfRoundNumberExistsInDB(){
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        doReturn(round).when(roundsCollection).getRoundByNumber(roundNumber);

        assertThat(roundsService.getRound(roundNumber)).isEqualTo(round);
    }

    @Test
    void getRound_ShouldBeNull_IfRoundNumberDoesNotExistInDB(){
        assertThat(roundsService.getRound(roundNumber)).isNull();
    }

    @Test
    void getRound_ShouldHaveTwoPlayers_IfRoundExistsInDB(){
        players = spy(new ArrayList<>());
        doReturn(2).when(players).size();

        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        doReturn(round).when(roundsCollection).getRoundByNumber(roundNumber);

        assertThat(roundsService.getRound(roundNumber).getPlayers().size()).isEqualTo(round.getPlayers().size());
    }

    @Test
    void getRound_ShouldReturnLastRow_IfRoundExistsInDB(){
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        doReturn(round).when(roundsCollection).getRoundByNumber(roundNumber);

        assertThat(roundsService.getRound(roundNumber).getLastColumn()).isEqualTo(lastColumn);
    }

    @Test
    void getRound_ShouldReturnLastColumn_IfRoundExistsInDB(){
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        doReturn(round).when(roundsCollection).getRoundByNumber(roundNumber);

        assertThat(roundsService.getRound(roundNumber).getLastRow()).isEqualTo(lastRow);
    }

    @Test
    void getAllRounds_ShouldBeEmpty_IfDBIsEmpty(){
        assertThat(roundsService.getAllRounds()).isEmpty();
    }

    @Test
    void getAllRounds_ShouldContainRounds_IfDBIsNotEmpty(){
        List<RoundData> roundDataList = spy(new ArrayList<>());
        doReturn(3).when(roundDataList).size();

        doReturn(roundDataList).when(roundsCollection).getRoundsList();

        assertThat(roundsService.getAllRounds().size()).isEqualTo(roundDataList.size());
    }

    @Test
    void insertRound_ShouldInsert_Properly(){
        List<RoundData> rounds = new ArrayList<>();
        doAnswer(invocation -> {
            rounds.add(invocation.getArgument(0));
            return null;
        }).when(roundsCollection).add(isA(RoundData.class));

        roundsService.insertRound(roundNumber++, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);
        roundsService.insertRound(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        assertThat(rounds.size()).isEqualTo(2);
    }

    @AfterEach
    void tearDown(){
        players = null;
        board = null;
        ranking = null;
    }

}
