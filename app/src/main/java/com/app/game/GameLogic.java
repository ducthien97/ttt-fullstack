package com.app.game;

public class GameLogic {
    static public boolean checkWinner (int[] board, int playerNum){
        int[][] winnerCombinations = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8} ,{2,4,6}};

        for (int[] winnerComb : winnerCombinations) {
            if (board[winnerComb[0]] == playerNum && board[winnerComb[1]] == playerNum && board[winnerComb[2]] == playerNum){
                return true;
            }
        }

        return false;
    }

    static public boolean checkDraw (int[] board){
        for (int num : board) {
            if (num == 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidMove(int[] board, int position) {
        return position >= 0 && position <= 8 && board[position] == 0;
    }
}
