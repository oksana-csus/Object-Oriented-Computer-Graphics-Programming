package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ChangeStrategiesCommand extends Command
{
private GameWorld gw;
	
	public ChangeStrategiesCommand(GameWorld gw) 
	{
		super("Change Strategies");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		gw.changeStrategies();
	}

}
