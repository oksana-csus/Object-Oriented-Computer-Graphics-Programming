package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.table.TableLayout;

public class HelpCommand extends Command
{
	private GameWorld gw;
	
	public HelpCommand(GameWorld gw) 
	{
		super("Help");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	    Dialog box = new Dialog("Help", new TableLayout(10, 2));
		Command ok = new Command("ok");

	    box.add(new Label("Command"));
	    box.add(new Label("USE KEY"));
	    box.add(new Label("Accelerate"));
	    box.add(new Label("a"));
	    box.add(new Label("Brake"));
	    box.add(new Label("b"));
	    box.add(new Label("Left Turn"));
	    box.add(new Label("l"));
	    box.add(new Label("Right Turn"));
	    box.add(new Label("r"));
	    box.add(new Label("Collide with Energy"));
	    box.add(new Label("e"));
	    box.add(new Label("Collide with Drone"));
	    box.add(new Label("g"));
	    box.add(new Label("Tick"));
	    box.add(new Label("t"));
	    box.add(new Label("Exit"));
	    box.add(new Label("x"));

	    Command c = Dialog.show("",  box, ok);
		if (c == ok) 
		{
			return;
		}
	}

}
