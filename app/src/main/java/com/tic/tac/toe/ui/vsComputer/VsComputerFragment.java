package com.tic.tac.toe.ui.vsComputer;
import com.tic.tac.toe.ui.GameFragment;
import com.tic.tac.toe.ui.GameModes;

public class VsComputerFragment extends GameFragment{

    public VsComputerFragment()
    {
        this.setMode(GameModes.SINGLE_PLAYER_EASY);
    }
}