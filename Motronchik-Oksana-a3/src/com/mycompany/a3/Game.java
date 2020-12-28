package com.mycompany.a3;

import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.charts.util.ColorUtil;


public class Game extends Form implements Runnable
{
	private GameWorld gw;
	private MapView mv; // new in A2
	private ScoreView sv; // new in A2
	private AccelerateCommand accelerateCommand;
	private BrakeCommand brakeCommand;
	private LeftTurnCommand leftTurnCommand;
	private RightTurnCommand rightTurnCommand;
	private ExitCommand exitCommand;
	private ChangeStrategiesCommand changeStrategiesCommand;
	private AboutCommand aboutCommand;
	private HelpCommand helpCommand;
	private SoundCommand soundCommand;
	private PauseCommand pauseCommand;
	private PositionCommand positionCommand;
	private UITimer timer;
	private int millisec = 20;
	private boolean isPaused = false;
	private Button accelerateButton;
	private Button brakeButton;
	private Button leftButton;
	private Button rightButton;
	private Button strategyButton;
	private Button pauseButton;
	private Button positionButton;

	
	public Game()
	{
		gw = new GameWorld();
		mv = new MapView(gw); // create an Observer for the map
		sv = new ScoreView(gw); // create anObserver for the game/playercyborg
		// state data
		gw.addObserver(mv); // register the map observer
		gw.addObserver(sv); // register the score observer
		
		this.setLayout(new BorderLayout());
		timer = new UITimer(this);
		
		//create Command objects for each command
		accelerateCommand = new AccelerateCommand(gw);
		brakeCommand = new BrakeCommand(gw);
		leftTurnCommand = new LeftTurnCommand(gw);
		rightTurnCommand = new RightTurnCommand(gw);
		exitCommand = new ExitCommand(gw);
		changeStrategiesCommand = new ChangeStrategiesCommand(gw);
		aboutCommand = new AboutCommand(gw);
		helpCommand = new HelpCommand(gw);
		exitCommand = new ExitCommand(gw);
		soundCommand = new SoundCommand(gw);
		pauseCommand = new PauseCommand(this);
		positionCommand = new PositionCommand(gw);
				
		//crate buttons
		accelerateButton = new Button("Accelerate");
		brakeButton = new Button("Brake");
		leftButton = new Button("Left");
		rightButton = new Button("Right");
		strategyButton = new Button("Change Strategies");
		pauseButton = new Button("Pause");
		positionButton = new Button("Position");
		
		//set commands
		accelerateButton.setCommand(accelerateCommand);
		brakeButton.setCommand(brakeCommand);
		leftButton.setCommand(leftTurnCommand);
		rightButton.setCommand(rightTurnCommand);
		strategyButton.setCommand(changeStrategiesCommand);
		pauseButton.setCommand(pauseCommand);
		positionButton.setCommand(positionCommand);
		
		//sound checkbox
		CheckBox sound = new CheckBox("Sound");
		sound.getAllStyles().setBgTransparency(255);
        sound.setSelected(false);
        sound.setCommand(soundCommand);
		
		//key listeners
		addKeyListener('a', accelerateCommand);
		addKeyListener('b', brakeCommand);
		addKeyListener('l', leftTurnCommand);
		addKeyListener('r', rightTurnCommand);

		//toolbar with title
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);
		myToolbar.setTitle("Sili-Challenge Game");
		myToolbar.addCommandToSideMenu(accelerateCommand);
        myToolbar.addComponentToSideMenu(sound);
		myToolbar.addCommandToSideMenu(aboutCommand);
		myToolbar.addCommandToSideMenu(exitCommand);
		myToolbar.addCommandToRightBar(helpCommand);
		
		//button styles
		//accelerate
		accelerateButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		accelerateButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		accelerateButton.getAllStyles().setBgTransparency(255);
		accelerateButton.getAllStyles().setPadding(Component.TOP, 5);
		accelerateButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		accelerateButton.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.BLACK));
		//left
		leftButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		leftButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		leftButton.getAllStyles().setBgTransparency(255);
		leftButton.getAllStyles().setPadding(Component.TOP, 5);
		leftButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		leftButton.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.BLACK));
		//strategy
		strategyButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		strategyButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		strategyButton.getAllStyles().setBgTransparency(255);
		strategyButton.getAllStyles().setPadding(Component.TOP, 5);
		strategyButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		strategyButton.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.BLACK));
		//brake
		brakeButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		brakeButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		brakeButton.getAllStyles().setBgTransparency(255);
		brakeButton.getAllStyles().setPadding(Component.TOP, 5);
		brakeButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		brakeButton.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.BLACK));
		//right
		rightButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		rightButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		rightButton.getAllStyles().setBgTransparency(255);
		rightButton.getAllStyles().setPadding(Component.TOP, 5);
		rightButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		rightButton.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.BLACK));
		//pause
		pauseButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		pauseButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		pauseButton.getAllStyles().setBgTransparency(255);
		pauseButton.getAllStyles().setPadding(Component.TOP, 5);
		pauseButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		pauseButton.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.BLACK));
		//position button
		positionButton.getAllStyles().setFgColor(ColorUtil.WHITE);
		positionButton.getAllStyles().setBgColor(ColorUtil.BLUE);
		positionButton.getAllStyles().setBgTransparency(127);
		positionButton.getAllStyles().setPadding(Component.TOP, 5);
		positionButton.getAllStyles().setPadding(Component.BOTTOM, 5);
		positionButton.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.BLACK));

		//West Container
		Container westContainer = new Container();
		westContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		westContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		westContainer.getAllStyles().setAlignment(Component.CENTER);
		westContainer.getAllStyles().setPadding(Component.TOP, 150);
		westContainer.addComponent(accelerateButton);
		westContainer.addComponent(leftButton);
		westContainer.addComponent(strategyButton);
		
		//East Container 
		Container eastContainer = new Container();
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		eastContainer.setLayout(new BoxLayout(2));
		eastContainer.getAllStyles().setPadding(Component.TOP, 150);
		eastContainer.addComponent(brakeButton);
		eastContainer.addComponent(rightButton);
		
		//South Container
		Container southContainer = new Container();
		southContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		southContainer.setLayout(new FlowLayout(Component.CENTER));
		southContainer.addComponent(positionButton);
		southContainer.addComponent(pauseButton);
		
		//add containers to form
		this.add(BorderLayout.WEST, westContainer);
		this.add(BorderLayout.EAST, eastContainer);
		this.add(BorderLayout.SOUTH, southContainer);
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);
		//code here to query MapViews width and height and set them as worlds width and height
		gw.setMapHeight(mv.getHeight());
		gw.setMapWidth(mv.getWidth());		

		this.show();
		GWInfo.setGameSize(mv.getWidth() , mv.getHeight());
		gw.init();
		
        timer.schedule(millisec, true, this);
	
	}
	
	@Override
	public void run() 
	{
		gw.clockTick(millisec);	

	}

	public boolean isPaused() 
	{
		return isPaused;
	}

	public void resumeGame() 
	{
		System.out.println("Game Continues!");
        timer.schedule(millisec, true, this);
		isPaused = false;
		gw.setPaused(false);
		pauseButton.setText("Pause");
		
		accelerateCommand.setEnabled(true);
		accelerateButton.setEnabled(true);
		accelerateButton.getAllStyles().setBgTransparency(255);
		brakeButton.setEnabled(true);
		brakeButton.getAllStyles().setBgTransparency(255);
		leftButton.setEnabled(true);
		leftButton.getAllStyles().setBgTransparency(255);
		rightButton.setEnabled(true);
		rightButton.getAllStyles().setBgTransparency(255);
		strategyButton.setEnabled(true);
		strategyButton.getAllStyles().setBgTransparency(255);
		positionButton.setEnabled(false);
		positionButton.getAllStyles().setBgTransparency(127);
		removeKeyListener('a', accelerateCommand);
		removeKeyListener('b', brakeCommand);
		removeKeyListener('l', leftTurnCommand);
		removeKeyListener('r', rightTurnCommand);
		
	}
	
	public void pauseGame() 
	{
		System.out.println("game has paused!");
		timer.cancel();
		isPaused = true;
		gw.setPaused(true);
		pauseButton.setText("Play");
		
		accelerateCommand.setEnabled(false);
		accelerateButton.setEnabled(false);
		accelerateButton.getAllStyles().setBgTransparency(127);
		brakeButton.setEnabled(false);
		brakeButton.getAllStyles().setBgTransparency(127);
		leftButton.setEnabled(false);
		leftButton.getAllStyles().setBgTransparency(127);
		rightButton.setEnabled(false);
		rightButton.getAllStyles().setBgTransparency(127);
		strategyButton.setEnabled(false);
		strategyButton.getAllStyles().setBgTransparency(127);
		positionButton.setEnabled(true);
		positionButton.getAllStyles().setBgTransparency(255);
		removeKeyListener('a', accelerateCommand);
		removeKeyListener('b', brakeCommand);
		removeKeyListener('l', leftTurnCommand);
		removeKeyListener('r', rightTurnCommand);
	}

}
