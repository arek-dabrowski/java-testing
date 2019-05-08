package com.app;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class BoardTest {
    private Board board;
    private int height = 6;
    private int width = 8;

    @BeforeEach
    void setUp(){
        board = new Board(height, width);
        initBoard();
    }

    @Test
    void trivialTest(){
        assertThat(true).isTrue();
    }

    @Test
    void board_Should_CreateSuccessfully(){
        assertThat(board).isNotNull();
    }

    @Test
    void getBoard_ShouldNotBeNull(){
        Coin[][] coins = board.getBoard();

        assertThat(coins).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("sizeArgumentsProvider")
    void board_ThrowsException_IfSizeIsTooSmall(int height, int width){
        assertThatIllegalArgumentException().isThrownBy(() -> { new Board(height, width); })
                .withMessage("%s", "Can't create board smaller than 4x4!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cleanboard-data.csv", numLinesToSkip = 1)
    void cleanBoard_ShouldCleanSuccessfully(int row, int column){
        board.cleanBoard();

        Coin actual = board.getCoin(row, column);

        assertThat(actual).isNull();
    }

    @Test
    void resetCoin_ShouldReset_IfCoinIsNotNull(){
        int row = 0;
        int column = 0;
        board.resetCoin(row, column);

        Coin actual = board.getCoin(row, column);

        assertThat(actual).isNull();
    }

    @Test
    void resetCoin_ThrowsException_IfCoinIsNull(){
        int row = 0;
        int column = 6;

        assertThatIllegalArgumentException().isThrownBy(() -> { board.resetCoin(row, column); })
                .withMessage("%s", "Can't reset null coin!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/resetcoin-data.csv", numLinesToSkip = 1)
    void resetCoin_ThrowsException_IfCoinIsOutOfArrayRange(int row, int column){
        assertThatExceptionOfType(ArrayIndexOutOfBoundsException.class).isThrownBy(() -> { board.resetCoin(row, column); });
    }

    @Test
    void putCoinInColumn_ShouldBeEqual_IfCoinWasPutProperly(){
        Coin redCoin = new Coin(Color.RED);
        board.putCoinInColumn(redCoin, 5);

        Coin actual = board.getCoin(5, 5);

        assertThat(actual).isEqualTo(redCoin);
    }

    @Test
    void putCoinInColumn_ShouldReturnRow_IfCoinWasPutProperly(){
        Coin redCoin = new Coin(Color.RED);
        int actual = board.putCoinInColumn(redCoin, 5);

        assertThat(actual).isEqualTo(5);
    }

    @ParameterizedTest
    @ValueSource(ints = {-3, 15, Integer.MAX_VALUE, Integer.MIN_VALUE, 32})
    void putCoinInColumn_ThrowsException_IfColumnIsOutOfRange(int column){
        Coin redCoin = new Coin(Color.RED);

        assertThatExceptionOfType(ArrayIndexOutOfBoundsException.class).isThrownBy(() -> { board.putCoinInColumn(redCoin, column); });
    }

    @Test
    void putCoinInColumn_ThrowsException_IfColumnIsFull(){
        Coin redCoin = new Coin(Color.RED);

        assertThatIllegalArgumentException().isThrownBy(() -> { board.putCoinInColumn(redCoin, 0); })
                .withMessage("%s", "Coin cannot be put into full column!");
    }

    @Test
    void checkIfRowWins_ShouldBeTrue_IfRowWins(){
        boolean won = board.checkIfRowWins(3, 1);

        assertThat(won).isTrue();
    }

    @Test
    void checkIfRowWins_ShouldBeFalse_IfRowDoesNotWin(){
        boolean won = board.checkIfRowWins(4, 1);

        assertThat(won).isFalse();
    }

    @Test
    void checkIfRowWins_ThrowsException_IfCheckedForEmptyElement(){
        assertThatIllegalArgumentException().isThrownBy(() -> { board.checkIfRowWins(0, 1); })
                .withMessage("%s", "Can't check if row wins when there is no coin!");
    }

    @Test
    void checkIfColumnWins_ShouldBeTrue_IfColumnWins(){
        boolean won = board.checkIfColumnWins(4, 0);

        assertThat(won).isTrue();
    }

    @Test
    void checkIfColumnWins_ShouldBeFalse_IfColumnDoesNotWin(){
        boolean won = board.checkIfColumnWins(4, 1);

        assertThat(won).isFalse();
    }

    @Test
    void checkIfColumnWins_ThrowsException_IfCheckedForEmptyElement(){
        assertThatIllegalArgumentException().isThrownBy(() -> { board.checkIfColumnWins(1, 1); })
                .withMessage("%s", "Can't check if column wins when there is no coin!");
    }

    @Test
    void checkIfDiagonalsWin_ShouldBeTrue_IfRightDiagonalWins(){
        boolean won = board.checkIfDiagonalsWin(4, 1);

        assertThat(won).isTrue();
    }

    @Test
    void checkIfDiagonalsWin_ShouldBeTrue_IfLeftDiagonalWins(){
        boolean won = board.checkIfDiagonalsWin(4, 2);

        assertThat(won).isTrue();
    }

    @Test
    void checkIfDiagonalsWin_ThrowsException_IfCheckedForEmptyElement(){
        assertThatIllegalArgumentException().isThrownBy(() -> { board.checkIfDiagonalsWin(1, 1); })
                .withMessage("%s", "Can't check if diagonals win when there is no coin!");
    }

    @Test
    void checkIfDiagonalsWin_ShouldBeFalse_IfRightDiagonalDoesNotWin(){
        boolean won = board.checkIfDiagonalsWin(5, 1);

        assertThat(won).isFalse();
    }

    @Test
    void checkIfDiagonalsWin_ShouldBeFalse_IfLeftDiagonalDoesNotWin(){
        boolean won = board.checkIfDiagonalsWin(5, 4);

        assertThat(won).isFalse();
    }

    @Test
    void isBoardFull_ShouldBeFalse_IfBoardIsNotFull(){
        boolean full = board.isBoardFull();

        assertThat(full).isFalse();
    }

    @Test
    void isBoardFull_ShouldBeTrue_IfBoardIsFull(){
        Board fullBoard = new Board(4, 4);
        initFullBoard(fullBoard);

        boolean full = fullBoard.isBoardFull();

        assertThat(full).isTrue();
    }

    @Test
    void printBoard_ShouldBeEqual_ToBoard(){
        assertThat(board.printBoard())
                .isEqualToIgnoringNewLines("0|R......." +
                        "1|R......." +
                        "2|GR.GG..." +
                        "3|GGGGR..." +
                        "4|GGGRR..." +
                        "5|GRRGR..." +
                        "  --------" +
                        "  01234567");
    }

    @AfterEach
    void tearDown(){
        board = null;
    }

    private void initBoard(){
        board.putCoinInColumn(new Coin(Color.GREEN), 0);
        board.putCoinInColumn(new Coin(Color.GREEN), 0);
        board.putCoinInColumn(new Coin(Color.GREEN), 0);
        board.putCoinInColumn(new Coin(Color.GREEN), 0);
        board.putCoinInColumn(new Coin(Color.RED), 0);
        board.putCoinInColumn(new Coin(Color.RED), 0);

        board.putCoinInColumn(new Coin(Color.RED), 1);
        board.putCoinInColumn(new Coin(Color.GREEN), 1);
        board.putCoinInColumn(new Coin(Color.GREEN), 1);
        board.putCoinInColumn(new Coin(Color.RED), 1);

        board.putCoinInColumn(new Coin(Color.RED), 2);
        board.putCoinInColumn(new Coin(Color.GREEN), 2);
        board.putCoinInColumn(new Coin(Color.GREEN), 2);

        board.putCoinInColumn(new Coin(Color.GREEN), 3);
        board.putCoinInColumn(new Coin(Color.RED), 3);
        board.putCoinInColumn(new Coin(Color.GREEN), 3);
        board.putCoinInColumn(new Coin(Color.GREEN), 3);

        board.putCoinInColumn(new Coin(Color.RED), 4);
        board.putCoinInColumn(new Coin(Color.RED), 4);
        board.putCoinInColumn(new Coin(Color.RED), 4);
        board.putCoinInColumn(new Coin(Color.GREEN), 4);
    }

    private void initFullBoard(Board board){
        board.putCoinInColumn(new Coin(Color.GREEN), 0);
        board.putCoinInColumn(new Coin(Color.GREEN), 0);
        board.putCoinInColumn(new Coin(Color.GREEN), 0);
        board.putCoinInColumn(new Coin(Color.RED), 0);

        board.putCoinInColumn(new Coin(Color.RED), 1);
        board.putCoinInColumn(new Coin(Color.GREEN), 1);
        board.putCoinInColumn(new Coin(Color.GREEN), 1);
        board.putCoinInColumn(new Coin(Color.RED), 1);

        board.putCoinInColumn(new Coin(Color.RED), 2);
        board.putCoinInColumn(new Coin(Color.GREEN), 2);
        board.putCoinInColumn(new Coin(Color.GREEN), 2);
        board.putCoinInColumn(new Coin(Color.GREEN), 2);

        board.putCoinInColumn(new Coin(Color.GREEN), 3);
        board.putCoinInColumn(new Coin(Color.RED), 3);
        board.putCoinInColumn(new Coin(Color.GREEN), 3);
        board.putCoinInColumn(new Coin(Color.GREEN), 3);
    }

    private static Stream sizeArgumentsProvider() {
        return Stream.of(
                Arguments.of(1, 2),
                Arguments.of(6, 3),
                Arguments.of(2, 7)
        );
    }
}
