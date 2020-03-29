package com.tic.tac.toe.ui;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int[][] board;
    private boolean XTurn;
    private int winner;

    public static int O_SPACE = 2;
    public static int X_SPACE = 1;
    public static int BLANK_SPACE = 0;
    public static int NO_WINNER_YET = -1;
    public static int DRAW = 3;

    public Game() {
        board = new int[3][3];
        newGame();
    }

    public int checkEnd() {

        int numBlankSpaces = 0;

        if (board[0][0] != BLANK_SPACE && board[0][0] == board[0][1] && board[0][0] == board[0][2]) {
            return board[0][0];
        } else if (board[1][0] != BLANK_SPACE && board[1][0] == board[1][1] && board[1][0] == board[1][2]) {
            return board[1][0];
        } else if (board[2][0] != BLANK_SPACE && board[2][0] == board[2][1] && board[2][0] == board[2][2]) {
            return board[2][0];
        }
        //down
        else if (board[0][0] != BLANK_SPACE && board[0][0] == board[1][0] && board[0][0] == board[2][0]) {
            return board[0][0];
        } else if (board[0][1] != BLANK_SPACE && board[0][1] == board[1][1] && board[0][1] == board[2][1]) {
            return board[0][1];
        } else if (board[0][2] != BLANK_SPACE && board[0][2] == board[1][2] && board[0][2] == board[2][2]) {
            return board[0][2];
        }
        //diagonal
        else if (board[0][0] != BLANK_SPACE && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0];
        } else if (board[0][2] != BLANK_SPACE && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2];
        }

        for (int[] cellStates : board) {
            for (int cellState : cellStates) {
                if (cellState == BLANK_SPACE) {
                    numBlankSpaces++;
                }
            }
        }
        if (numBlankSpaces == 0)
            return DRAW;
        else
            return NO_WINNER_YET;
    }

    public boolean isMoveValid(int r, int c) {
        if (board[r][c] != BLANK_SPACE)
            return false; //invalid move
        return true;
    }

    public boolean setPlayerMove(int r, int c) {

        if (!isMoveValid(r, c))
            return false;
        if(isXTurn())
            board[r][c] = X_SPACE;
        else
            board[r][c] = O_SPACE;
        winner = checkEnd();
        return true;
    }

    public Integer[] setRandomComputerMove() {
        int r, c;
        do {
            r = (int) (Math.random() * 3);
            c = (int) (Math.random() * 3);
        }
        while (board[r][c] != BLANK_SPACE);

        board[r][c] = O_SPACE;
        winner = checkEnd();

        Integer[] array = new Integer[2];
        array[0] = r;
        array[1] = c;

        return array;
    }

    public Integer[] setSmartComputerMove() {

        Integer[] array = getBestMove(board);

        board[array[0]][array[1]] = O_SPACE;
        winner = checkEnd();

        return array;
    }

    public void newGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = BLANK_SPACE;
            }
        }
        XTurn = true;
        winner = NO_WINNER_YET;
    }

    public List<Integer[]> getBlankSpaces() {
        ArrayList<Integer[]> blankSpaces = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == BLANK_SPACE) {
                    blankSpaces.add(new Integer[]{i, j});
                }
            }
        }
        return blankSpaces;
    }

    public Integer[] getBestMove(int[][] board) {
        this.board = board;
        Integer[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        for (Integer[] position : getBlankSpaces()) {
            //do
            board[position[0]][position[1]] = O_SPACE;
            int score = minimax();
            //then undo
            board[position[0]][position[1]] = BLANK_SPACE;
            if (score > bestScore) {
                bestScore = score;
                bestMove = position;
            }
        }
        return bestMove;
    }

    public int minimax() {
        winner = checkEnd();
        int score;
        if (winner != NO_WINNER_YET) {
            if (winner == O_SPACE) {
                score = 1;
                return score;
            } else if (winner == DRAW) {
                score = 0;
                return score;
            } else {
                score = -1;
                return score;
            }
        }

        int computerCount = 0;
        int humanCount = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == O_SPACE) {
                    computerCount++;
                } else if (board[i][j] == X_SPACE) {
                    humanCount++;
                }
            }
        }

        int bestScore;
        if (humanCount > computerCount) {
            bestScore = -1;
        } else {
            bestScore = 1;
        }

        for (Integer[] position : getBlankSpaces()) {
            if (humanCount > computerCount)
                board[position[0]][position[1]] = O_SPACE;
            else
                board[position[0]][position[1]] = X_SPACE;
            int currentScore = minimax();
            board[position[0]][position[1]] = BLANK_SPACE;
            if (humanCount > computerCount ? currentScore > bestScore : currentScore < bestScore) {
                bestScore = currentScore;
            }
        }
        return bestScore;
    }

    public boolean isXTurn() {
        return XTurn;
    }

    public void updateTurn() {
        XTurn = !XTurn;
    }

    public int getWinner() {
        return winner;
    }
}
