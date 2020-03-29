package com.tic.tac.toe.ui.home;

import com.tic.tac.toe.ui.GameFragment;
import com.tic.tac.toe.ui.GameModes;

public class HomeFragment extends GameFragment {

    public HomeFragment()
    {
        this.setMode(GameModes.TWO_PLAYER);
    }
}
