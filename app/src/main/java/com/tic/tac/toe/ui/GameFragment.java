package com.tic.tac.toe.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.tic.tac.toe.R;

public class GameFragment extends Fragment implements View.OnClickListener {

    private Button button00, button01, button02, button10, button11, button12, button20, button21, button22, buttonResetGame;
    private Button[][] buttonArray;
    private Integer[] indices;
    private TextView textViewStatusX, textViewStatusO;
    private Game game;
    private int winner;
    private String mode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        button00 = root.findViewById(R.id.button00);
        button01 = root.findViewById(R.id.button01);
        button02 = root.findViewById(R.id.button02);
        button10 = root.findViewById(R.id.button10);
        button11 = root.findViewById(R.id.button11);
        button12 = root.findViewById(R.id.button12);
        button20 = root.findViewById(R.id.button20);
        button21 = root.findViewById(R.id.button21);
        button22 = root.findViewById(R.id.button22);

        buttonArray = new Button[3][3];
        buttonArray[0][0] = button00;
        buttonArray[0][1] = button01;
        buttonArray[0][2] = button02;
        buttonArray[1][0] = button10;
        buttonArray[1][1] = button11;
        buttonArray[1][2] = button12;
        buttonArray[2][0] = button20;
        buttonArray[2][1] = button21;
        buttonArray[2][2] = button22;

        buttonResetGame = root.findViewById(R.id.buttonResetGame);
        textViewStatusO = root.findViewById(R.id.textViewStatusO);
        textViewStatusX = root.findViewById(R.id.textViewStatusX);

        button00.setOnClickListener(this);
        button01.setOnClickListener(this);
        button02.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);
        button20.setOnClickListener(this);
        button21.setOnClickListener(this);
        button22.setOnClickListener(this);
        buttonResetGame.setOnClickListener(this);

        game = new Game();
        newGame();
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonResetGame) {
            newGame();
            return;
        }

        if (game.getWinner() !=Game.NO_WINNER_YET) {
            newGame();
            return;
        }
        int r, c, temp;
        temp = Integer.parseInt(view.getTag().toString());
        r = temp / 10;
        c = temp % 10;

        if(!game.setPlayerMove(r,c)) {
            return;  //invalid move
        }

         makeMove(r,c);
    }

    private void makeMove(int r, int c)
    {
        if(game.isXTurn())
            buttonArray[r][c].setText("X");
        else
            buttonArray[r][c].setText("O");

        winner = game.getWinner();
        if (winner == Game.X_SPACE)
            Toast.makeText(getContext(), "X wins!", Toast.LENGTH_SHORT).show();
        else if (winner == Game.O_SPACE)
            Toast.makeText(getContext(), "O Wins!", Toast.LENGTH_SHORT).show();
        else if (winner == Game.DRAW)
            Toast.makeText(getContext(), "Draw", Toast.LENGTH_SHORT).show();
        else {
            game.updateTurn();
            displayTurn();

            if(!mode.equals(GameModes.TWO_PLAYER) && !game.isXTurn())
                setComputerMove(mode);
        }

    }

    private void setComputerMove(String mode) {

        if(mode == GameModes.SINGLE_PLAYER_EASY)
            indices = game.setRandomComputerMove();
        else
            indices = game.setSmartComputerMove();

        makeMove(indices[0],indices[1]);
    }


    private void newGame() {
        for(int r=0; r<3; r++)
        {
            for(int c=0; c<3; c++)
            {
                buttonArray[r][c].setText("");
            }
        }
        game.newGame();
        displayTurn();

    }

    private void displayTurn() {

        if (game.isXTurn()) {
            textViewStatusX.setText("X's turn");
            textViewStatusO.setText("");

        } else {
            textViewStatusO.setText("O's turn");
            textViewStatusX.setText("");
        }
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }
}
