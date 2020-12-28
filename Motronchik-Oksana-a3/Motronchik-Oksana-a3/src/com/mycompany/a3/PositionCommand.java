package com.mycompany.a3;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class PositionCommand extends Command
{
private GameWorld gw;
	
	public PositionCommand(GameWorld gw) 
	{
		super("Position");
		this.gw = gw;
	}
	
	 @Override
	    public void actionPerformed(ActionEvent event) 
	 {
		gw.position();
	 }

	
}
