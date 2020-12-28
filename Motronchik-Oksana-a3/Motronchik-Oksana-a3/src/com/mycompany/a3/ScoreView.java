package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

import java.util.Observable;
import java.util.Observer; 

public class ScoreView extends Container implements Observer
{
	private Label time = new Label("Time: 0");
	private Label livesLeft = new Label("Lives Left: 3"); 
	private Label lastBaseReached = new Label("Player Last Base Reached: 1");
	private Label energyLevel = new Label("Player Energy Level: 15");
	private Label damageLevel = new Label("Player Damage Level: 0");
	private Label sound = new Label("Sound: OFF");
	
	public ScoreView(Observable gw) 
	{
		gw.addObserver(this);

		this.setLayout(new FlowLayout(Component.CENTER));
		formatLabels();
		this.add(time);
		this.add(livesLeft);
		this.add(lastBaseReached);
		this.add(energyLevel);
		this.add(damageLevel);
		this.add(sound);

		this.getAllStyles().setMargin(Component.TOP, 20);
		this.getAllStyles().setMargin(Component.BOTTOM, 20);
		
	}
	
	private void formatLabels() 
	{
		time = formatStyle(time);
		livesLeft = formatStyle(livesLeft);
		lastBaseReached = formatStyle(lastBaseReached);
		energyLevel = formatStyle(energyLevel);
		damageLevel = formatStyle(damageLevel);
		sound = formatStyle(sound);

	}

	private Label formatStyle(Label label) 
	{
		label.getAllStyles().setPadding(Component.LEFT, 4);
		label.getAllStyles().setFgColor(ColorUtil.BLUE); 
		return label;
	}
	
	@Override
	public void update (Observable o, Object arg) 
	{
		GameWorld gw = (GameWorld)o;
		
		time.setText("Time: "+ String.valueOf(gw.getElapsedTime()));
		livesLeft.setText("Lives Left: " + String.valueOf(gw.getLives()));
		lastBaseReached.setText("Player Last Base Reached: " + String.valueOf(gw.getCyborgLastBaseReached()));
		energyLevel.setText("Player Energy Level: " + String.valueOf(gw.getEnergyLevel()));
		damageLevel.setText("Player Damage Level: " + String.valueOf(gw.getDamageLevel()));

		if(gw.isSoundOn())
			sound.setText("Sound: ON");
		else sound.setText("Sound: OFF");
		
		this.repaint();
	}

}
