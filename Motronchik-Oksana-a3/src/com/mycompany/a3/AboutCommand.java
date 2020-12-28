package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.table.TableLayout;

public class AboutCommand extends Command
{
	private GameWorld gw;
	
	public AboutCommand(GameWorld gw) 
	{
		super("About");
		this.gw = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Dialog box = new Dialog("About", new TableLayout(4,1));
		Command ok = new Command("ok");
		
		box.add(new Label ("Name: Oksana Motronchik"));
		box.add(new Label ("CSC 133 A2Prj"));
		box.add(new Label ("V2"));
		
		Command c = Dialog.show("",  box, ok);
		if (c == ok) 
		{
			return;
		}
	}
}
