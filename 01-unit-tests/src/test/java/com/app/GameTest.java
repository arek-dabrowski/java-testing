package com.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
    Game game;
    Player player1;
    Player player2;
    String filePath;
    int boardHeight = 9;
    int boardWidth = 8;

    @BeforeEach
    void setUp(){
        filePath = ".//src//test//resources//test-ranking.csv";
        player1 = new Player("Adam", Color.RED);
        player2 = new Player("Jan", Color.GREEN);
        game = new Game(player1, player2, boardHeight, boardWidth, filePath);
    }

    @Test
    void trivialTest(){
        assertThat(true, is(true));
    }

    @Test
    void game_Should_CreateSuccessfully(){
        assertThat(game, is(notNullValue()));
    }

    @Test
    void game_ThrowsException_IfBothPlayersHaveSameColor(){
        Player redPlayer1 = new Player("Piotr", Color.RED);
        Player redPlayer2 = new Player("Pawel", Color.RED);

        assertThrows(IllegalArgumentException.class, () -> {
            Game exceptionGame = new Game(redPlayer1, redPlayer2, boardHeight, boardWidth, filePath);
        });
    }

    @Test
    void game_ThrowsException_IfBothPlayersHaveSameName(){
        Player redPlayer1 = new Player("Piotr", Color.RED);
        Player redPlayer2 = new Player("Piotr", Color.GREEN);

        assertThrows(IllegalArgumentException.class, () -> {
            Game exceptionGame = new Game(redPlayer1, redPlayer2, boardHeight, boardWidth, filePath);
        });
    }

    @Test
    void getBoard_ShouldNotBeNull(){
        assertThat(game.getBoard(), is(notNullValue()));
    }

    @Test
    void makeMove_ShouldBeSameColor_IfPlayer1MadeMove(){
        game.makeMove(3);

        Color expected = player1.getCoinColor();
        Color actual = game.getBoard().getCoin(boardHeight - 1, 3).getColor();

        assertThat(actual, is(expected));
    }

    @Test
    void makeMove_ShouldBeSameColor_IfPlayer2MadeMove(){
        game.makeMove(3);
        game.makeMove(3);

        Color expected = player2.getCoinColor();
        Color actual = game.getBoard().getCoin(boardHeight - 2, 3).getColor();

        assertThat(actual, is(expected));
    }

    @Test
    void makeMove_ShouldBeFalse_IfPlayerDidNotWin(){
        boolean actual = game.makeMove(3);

        assertThat(actual, is(false));
    }

    @Test
    void makeMove_ShouldBeTrue_IfPlayer1Won(){
        initPlayer1WonGame();
        boolean actual = game.makeMove(3);

        assertThat(actual, is(true));
    }

    @Test
    void makeMove_ShouldBeTrue_IfPlayer2Won(){
        initPlayer2WonGame();
        boolean actual = game.makeMove(3);

        assertThat(actual, is(true));
    }

    @Test
    void makeMove_ShouldBeFalse_IfGameIsDraw(){
        Game drawGame = new Game(player1, player2, 4, 4, filePath);
        initDrawGame(drawGame);

        boolean actual = drawGame.makeMove(2);

        assertThat(actual, is(false));
    }

    @Test
    void makeMoveAndHasGameEnded_ShouldBeTrue_IfGameIsDraw(){
        Game drawGame = new Game(player1, player2, 4, 4, filePath);
        initDrawGame(drawGame);
        boolean wonGame = drawGame.makeMove(2);

        boolean actual = (!wonGame && drawGame.hasGameEnded());

        assertThat(actual, is(true));
    }

    @Test
    void undoLastMove_ShouldUndo_IfPlayerUndoMove(){
        game.makeMove(3);
        game.undoLastMove();

        Coin actual = game.getBoard().getCoin(boardHeight - 1, 3);

        assertThat(actual, is(nullValue()));
    }

    @Test
    void makeMove_ShouldBeSameColor_IfPlayer1UndoAndMadeMove(){
        game.makeMove(3);
        game.undoLastMove();
        game.makeMove(4);

        Color expected = player1.getCoinColor();
        Color actual = game.getBoard().getCoin(boardHeight - 1, 4).getColor();

        assertThat(actual, is(expected));
    }

    @Test
    void isPlayer1Turn_ShouldBeTrue_IfItIsPlayer1Turn(){
        boolean actual = game.isPlayer1Turn();

        assertThat(actual, is(true));
    }

    @Test
    void isPlayer1Turn_ShouldBeFalse_IfItIsPlayer2Turn(){
        game.makeMove(4);
        boolean actual = game.isPlayer1Turn();

        assertThat(actual, is(false));
    }

    @Test
    void getCurrentPlayer_ShouldReturnPlayer1_IfItIsPlayer1Turn(){
        String actual = game.getCurrentPlayer().toString();

        assertThat(actual, equalToIgnoringCase(player1.toString()));
    }

    @Test
    void getCurrentPlayer_ShouldReturnPlayer2_IfItIsPlayer2Turn(){
        game.makeMove(4);
        String actual = game.getCurrentPlayer().toString();

        assertThat(actual, equalToIgnoringCase(player2.toString()));
    }

    @Test
    void hasGameEnded_ShouldReturnTrue_IfGameHasEnded(){
        initPlayer1WonGame();
        game.makeMove(3);

        boolean actual = game.hasGameEnded();

        assertThat(actual, is(true));
    }

    @Test
    void hasGameEnded_ShouldReturnFalse_IfGameHasNotEnded(){
        game.makeMove(3);

        boolean actual = game.hasGameEnded();

        assertThat(actual, is(false));
    }

    @Test
    void resetGame_ShouldResetGameSuccessfully_IfGameHasEnded(){
        initPlayer1WonGame();
        game.makeMove(3);
        game.resetGame();

        Coin actual = game.getBoard().getCoin(boardHeight - 1, 3);

        assertThat(actual, is(nullValue()));
    }

    @Test
    void resetGame_ThrowsException_IfGameHasNotEnded(){
        initPlayer1WonGame();

        assertThrows(IllegalArgumentException.class, () -> {
            game.resetGame();
        });
    }

    @AfterEach
    void tearDown(){
        game = null;
    }

    private void initPlayer1WonGame(){
        game.makeMove(3);
        game.makeMove(2);
        game.makeMove(3);
        game.makeMove(4);
        game.makeMove(3);
        game.makeMove(5);
    }

    private void initDrawGame(Game game){
        game.makeMove(0);
        game.makeMove(0);
        game.makeMove(0);
        game.makeMove(0);

        game.makeMove(1);
        game.makeMove(1);
        game.makeMove(1);
        game.makeMove(1);

        game.makeMove(2);
        game.makeMove(3);
        game.makeMove(2);
        game.makeMove(3);

        game.makeMove(3);
        game.makeMove(2);
        game.makeMove(3);
    }

    private void initPlayer2WonGame(){
        game.makeMove(2);
        game.makeMove(3);
        game.makeMove(2);
        game.makeMove(3);
        game.makeMove(4);
        game.makeMove(3);
        game.makeMove(5);
    }

}
