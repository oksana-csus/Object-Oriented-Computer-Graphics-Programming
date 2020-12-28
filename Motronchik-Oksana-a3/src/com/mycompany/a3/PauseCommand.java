package com.mycompany.a3;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command
{
	private Game game;

    public PauseCommand(Game gw) 
    {
        super("Pause");
        this.game = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
    	if(game.isPaused())
    	{
    		game.resumeGame();
    	}
    		
    	else if(!game.isPaused())
    	{
    		game.pauseGame();
    	}
    }
}
