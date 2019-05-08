package com.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        Game game = createGame(scan);
        showMenu(scan, game);

    }

    private static Game createGame(Scanner scan){
        System.out.println("Put RED player name:");
        String firstName = scan.nextLine();

        System.out.println("Put GREEN player name:");
        String secondName = scan.nextLine();

        System.out.println("Put board height:");
        int boardHeight = scan.nextInt();

        System.out.println("Put board width:");
        int boardWidth = scan.nextInt();
        scan.nextLine();

        Player player1 = new Player(firstName.trim(), Color.RED);
        Player player2 = new Player(secondName.trim(), Color.GREEN);
        String rankingPath = ".//src//main//resources//ranking.csv";

        Game game = new Game(player1, player2, boardHeight, boardWidth, rankingPath);
        return game;
    }

    private static void startGame(Scanner scan, Game game){
        System.out.println(game.getBoard().printBoard());

        while(true){
            int column;

            if(game.isPlayer1Turn()){
                System.out.println("-- RED player turn --");
            }
            else{
                System.out.println("-- GREEN player turn --");
            }

            System.out.println("Write column number to put coin in:");
            column = scan.nextInt();
            boolean lastMove = game.makeMove(column);
            if(lastMove) {
                System.out.println(game.getCurrentPlayer().toString() + " WON!");
                System.out.println(game.getBoard().printBoard());

                game.resetGame();
                break;
            }
            else if(!lastMove && game.hasGameEnded()) {
                System.out.println("DRAW!");
                System.out.println(game.getBoard().printBoard());

                game.resetGame();
                break;
            }

            System.out.println(game.getBoard().printBoard());

            System.out.println("Do you want to undo your move?");
            System.out.println("-- Write '1' if yes, any number if no --");

            int option = scan.nextInt();
            if(option == 1) {
                game.undoLastMove();
                System.out.println(game.getBoard().printBoard());
            }
        }
    }

    private static void showMenu(Scanner scan, Game game){
        boolean isRunning = true;
        while(isRunning) {
            System.out.println("\nChoose what to do:");
            System.out.println("0 - exit");
            System.out.println("1 - start game");
            System.out.println("2 - show ranking\n");

            switch (scan.nextInt()) {
                case 0:
                    isRunning = false;
                    break;
                case 1:
                    startGame(scan, game);
                    break;
                case 2:
                    game.printRanking();
                    break;
                default:
                    showMenu(scan, game);
                    break;
            }
        }
    }
}
