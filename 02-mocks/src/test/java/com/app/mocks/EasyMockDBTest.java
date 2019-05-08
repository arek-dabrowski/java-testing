package com.app.mocks;

import static org.assertj.core.api.Assertions.*;
import static org.easymock.EasyMock.*;

import com.app.Board;
import com.app.Player;
import com.app.Ranking;
import com.app.db.RoundData;
import com.app.db.RoundsCollection;
import com.app.db.RoundsService;

import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(EasyMockExtension.class)
public class EasyMockDBTest {

    private int roundNumber;
    private List<Player> players;
    private Board board;
    private Ranking ranking;
    private int lastRow;
    private int lastColumn;
    private boolean player1Turn;
    private boolean gameEnded;

    private RoundData round;

    @TestSubject
    private RoundsService roundsService = new RoundsService();

    @Mock(type = MockType.NICE)
    private RoundsCollection roundsCollection;

    @BeforeEach
    void setUp(){
        players = createMock(List.class);
        board = createMock(Board.class);
        ranking = createMock(Ranking.class);
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
    void getRound_ShouldReturnPlayerTurn_IfRoundExistsInDB(){
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        expect(roundsCollection.getRoundByNumber(roundNumber)).andReturn(round);
        replay(roundsCollection);

        assertThat(roundsService.getRound(roundNumber).isPlayer1Turn()).isEqualTo(player1Turn);
    }

    @Test
    void getRound_ShouldReturnGameEnded_IfRoundExistsInDB(){
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        expect(roundsCollection.getRoundByNumber(roundNumber)).andReturn(round);
        replay(roundsCollection);

        assertThat(roundsService.getRound(roundNumber).hasGameEnded()).isEqualTo(gameEnded);
    }

    @Test
    void getRound_ShouldReturnProperBoard_IfRoundExistsInDB(){
        board = new Board(6, 7);
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        expect(roundsCollection.getRoundByNumber(roundNumber)).andReturn(round);
        replay(roundsCollection);

        assertThat(roundsService.getRound(roundNumber).getBoard()).isEqualTo(board);
    }

    @Test
    void getRound_ShouldReturnProperRanking_IfRoundExistsInDB(){
        ranking = new Ranking(".//src//test//resources//fake-ranking.csv");
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        expect(roundsCollection.getRoundByNumber(roundNumber)).andReturn(round);
        replay(roundsCollection);

        assertThat(roundsService.getRound(roundNumber).getRanking()).isEqualTo(ranking);
    }

    @Test
    void getRound_ShouldReturnProperTurnNumber_IfRoundExistsInDB(){
        round = new RoundData(roundNumber, players, board, ranking, lastRow, lastColumn, player1Turn, gameEnded);

        expect(roundsCollection.getRoundByNumber(roundNumber)).andReturn(round);
        replay(roundsCollection);

        assertThat(roundsService.getRound(roundNumber).getTurnNumber()).isEqualTo(roundNumber);
    }

    @Test
    void deleteAllRounds_ShouldDelete_IfDBIsNotEmpty(){
        List<RoundData> rounds = new ArrayList<>();
        rounds.add(createMock(RoundData.class));
        rounds.add(createMock(RoundData.class));

        roundsCollection.clearDB();
        expectLastCall().andAnswer((IAnswer) () -> {
            rounds.clear();
            return null;
        });
        replay(roundsCollection);

        int initialSize = rounds.size();

        roundsService.deleteAllRounds();

        assertThat(rounds.size()).isNotEqualTo(initialSize);
    }

    @Test
    void deleteAllRounds_ShouldThrowException_IfDBIsEmpty(){
        expect(roundsCollection.isEmpty()).andReturn(true);
        replay(roundsCollection);

        assertThatIllegalArgumentException().isThrownBy(() -> { roundsService.deleteAllRounds(); })
                .withMessage("%s", "You can't erase empty database!");
    }

    @Test
    void isDatabaseEmpty_ShouldReturnFalse_IfDBIsNotEmpty(){
        expect(roundsCollection.isEmpty()).andReturn(false);
        replay(roundsCollection);

        assertThat(roundsService.isDatabaseEmpty()).isFalse();
    }

    @AfterEach
    void tearDown(){
        players = null;
        board = null;
        ranking = null;
    }

}
