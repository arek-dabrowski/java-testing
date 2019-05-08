package com.app;

public class Board {
    private Coin[][] board;
    private int height;
    private int width;
    private final static int COINS_TO_WIN = 4;

    public Board(int height, int width){
        if(height < COINS_TO_WIN || width < COINS_TO_WIN) throw new IllegalArgumentException("Can't create board smaller than 4x4!");
        this.height = height;
        this.width = width;
        this.board = new Coin[height][width];
    }

    public Coin[][] getBoard(){
        return this.board;
    }

    public Coin getCoin(int row, int column){
        return this.board[row][column];
    }

    public void resetCoin(int row, int column){
        if(getCoin(row, column) == null) throw new IllegalArgumentException("Can't reset null coin!");
        this.board[row][column] = null;
    }

    public int putCoinInColumn(Coin coin, int column){
        if(getCoin(0, column) != null) throw new IllegalArgumentException("Coin cannot be put into full column!");

        int i;
        for(i = this.height - 1; i >= 0; i--){
            if(getCoin(i, column) == null){
                this.board[i][column] = coin;
                break;
            }
        }

        return i;
    }

    public boolean checkIfRowWins(int row, int column){
        if(checkIfCoinIsNull(row, column)) throw new IllegalArgumentException("Can't check if row wins when there is no coin!");

        Color color = getCoin(row, column).getColor();

        return countCoinsInRow(color, row, column) >= COINS_TO_WIN;
    }

    public boolean checkIfColumnWins(int row, int column){
        if(checkIfCoinIsNull(row, column)) throw new IllegalArgumentException("Can't check if column wins when there is no coin!");

        Color color = getCoin(row, column).getColor();

        return countCoinsInColumn(color, row, column) >= COINS_TO_WIN;
    }

    public boolean checkIfDiagonalsWin(int row, int column){
        if(checkIfCoinIsNull(row, column)) throw new IllegalArgumentException("Can't check if diagonals win when there is no coin!");

        return checkIfRightDiagonalWins(row, column) || checkIfLeftDiagonalWins(row, column);

    }

    private boolean checkIfRightDiagonalWins(int row, int column){
        Color color = getCoin(row, column).getColor();

        return countCoinsInRightDiagonal(color, row, column) >= COINS_TO_WIN;
    }

    private boolean checkIfLeftDiagonalWins(int row, int column){
        Color color = getCoin(row, column).getColor();

        return countCoinsInLeftDiagonal(color, row, column) >= COINS_TO_WIN;
    }

    private int countCoinsInRow(Color color, int row, int column){
        int counter = 1;

        counter = countRight(color, row, column, counter);
        counter = countLeft(color, row, column, counter);

        return counter;
    }

    private int countCoinsInColumn(Color color, int row, int column){
        int counter = 1;

        counter = countUp(color, row, column, counter);
        counter = countDown(color, row, column, counter);

        return counter;
    }

    private int countCoinsInRightDiagonal(Color color, int row, int column){
        int counter = 1;

        counter = countUpRight(color, row, column, counter);
        counter = countDownLeft(color, row, column, counter);

        return counter;
    }

    private int countCoinsInLeftDiagonal(Color color, int row, int column){
        int counter = 1;

        counter = countUpLeft(color, row, column, counter);
        counter = countDownRight(color, row, column, counter);

        return counter;
    }

    private int countUp(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentCounter = counter;

        for(int j = row - 1; j >= 0; j--){
            currentCoin = getCoin(j, column);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;
        }

        return currentCounter;
    }

    private int countDown(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentCounter = counter;

        for(int i = row + 1; i < this.height; i++){
            currentCoin = getCoin(i, column);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;
        }

        return currentCounter;
    }

    private int countRight(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentCounter = counter;

        for(int i = column + 1; i < this.width; i++){
            currentCoin = getCoin(row, i);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;
        }

        return currentCounter;
    }

    private int countLeft(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentCounter = counter;

        for(int j = column - 1; j >= 0; j--){
            currentCoin = getCoin(row, j);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;
        }

        return currentCounter;
    }

    private int countUpRight(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentRow;
        int currentCounter = counter;

        currentRow = row - 1;
        for(int i = column + 1; i < width; i++){
            if(currentRow < 0) break;
            currentCoin = getCoin(currentRow, i);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;

            currentRow--;
        }

        return currentCounter;
    }

    private int countUpLeft(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentRow;
        int currentCounter = counter;

        currentRow = row - 1;
        for(int i = column - 1; i >= 0; i--){
            if(currentRow < 0) break;
            currentCoin = getCoin(currentRow, i);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;

            currentRow--;
        }

        return currentCounter;
    }

    private int countDownRight(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentRow;
        int currentCounter = counter;

        currentRow = row + 1;
        for(int j = column + 1; j < width; j++){
            if(currentRow == this.height) break;
            currentCoin = getCoin(currentRow, j);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;

            currentRow++;
        }

        return currentCounter;
    }

    private int countDownLeft(Color color, int row, int column, int counter){
        Coin currentCoin;
        int currentRow;
        int currentCounter = counter;

        currentRow = row + 1;
        for(int j = column - 1; j >= 0; j--){
            if(currentRow == this.height) break;
            currentCoin = getCoin(currentRow, j);
            if(currentCoin == null || currentCoin.getColor() != color) break;
            else currentCounter++;

            currentRow++;
        }

        return currentCounter;
    }

    public String printBoard(){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < this.height; i++){
            result.append(i);
            result.append("|");
            for(int j = 0; j < this.width; j++){
                if(this.board[i][j] == null) result.append(".");
                else result.append(this.board[i][j]);
            }
            result.append("\n");
        }

        result.append("  ");
        for(int l = 0; l < this.width; l++){
            result.append("-");
        }

        result.append("\n  ");
        for(int k = 0; k < this.width; k++){
            result.append(k);
        }

        return result.toString();
    }

    private boolean checkIfCoinIsNull(int row, int column){
        return getCoin(row, column) == null;
    }

    public boolean isBoardFull(){
        for(int i = 0; i < width; i++){
            if(getCoin(0, i) == null) return false;
        }
        return true;
    }

    public void cleanBoard(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(getCoin(i, j) != null) resetCoin(i, j);
            }
        }
    }

}
