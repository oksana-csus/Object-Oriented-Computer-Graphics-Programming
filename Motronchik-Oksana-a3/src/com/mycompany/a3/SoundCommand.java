package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.CheckBox;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command
{
	private GameWorld gameWorld;

	public SoundCommand(GameWorld gw) 
	{
		super("Sound");
		gameWorld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		gameWorld.setSound();
	}

}
