package com.mycompany.a3;
import java.util.Observable;
import java.util.Random;
import com.codename1.charts.util.ColorUtil;

public class GameWorld extends Observable
{
	Random rand = new Random();
	int number = rand.nextInt();
	private Cyborg cyborg;
	private int cyborgSize = 40;
	private int gameClock = 0;
	private GameObjectCollection worldObjects = new GameObjectCollection();
	private int mapHeight;
	private int mapWidth;
	private boolean soundOn = false;
	private boolean isPaused = false;
	private Sound energySound = new Sound("energy_collide.wav");
	private Sound cyborgsSound = new Sound("cyborgs_collide.wav");
	private Sound lifeLostSound = new Sound("life_loss.wav");
	private BGSound background = new BGSound("background.wav");
	
	public void setMapHeight(int height) 
	{
		this.mapHeight = height;
	}
	
	public void setMapWidth(int width) 
	{
		this.mapWidth = width;
	}
	
	//sets the initial state of the game
	public void init()
	{
		//worldObjects.clear();
		//bases
		worldObjects.add(new Base(50, 1, 275, 500));
		worldObjects.add(new Base(50, 2, 980, 250));
		worldObjects.add(new Base(50, 3, 600, 700));
		worldObjects.add(new Base(50, 4, 955, 850));
		//energy stations
		worldObjects.add(new EnergyStation(randSize(), randX(), randY()));
		worldObjects.add(new EnergyStation(randSize(), randX(), randY()));
		//drones
		worldObjects.add(new Drone(randSize(), randX(), randY()));
		worldObjects.add(new Drone(randSize(), randX(), randY()));
		//Player cyborg
		createCyborg();

		
		//create 3 npc
		createNPCs();
		this.setChanged();
		this.notifyObservers();
		
	}
	//creates 3 npcs
	private void createNPCs() 
	{
		NonPlayerCyborg npc1 = new NonPlayerCyborg(cyborgSize, 710, 205);
		NonPlayerCyborg npc2 = new NonPlayerCyborg(cyborgSize, 310, 415);
		NonPlayerCyborg npc3 = new NonPlayerCyborg(cyborgSize, 950, 755);
		
		worldObjects.add(npc1);
		worldObjects.add(npc2);
		worldObjects.add(npc3);
		
		npc1.setStrategy(new NextBaseStrategy(npc1, worldObjects));
		npc2.setStrategy(new NextBaseStrategy(npc2, worldObjects));
		npc3.setStrategy(new AttackStrategy(npc3));
		
		this.setChanged();
		this.notifyObservers();
	}
	
	//returns random size int
	private int randSize() 
	{
		return 10+rand.nextInt(40);
	}
	
	//returns random float x
	private float randX() 
	{
		return (float)(rand.nextInt(1000));
	}
	
	//returns random float y
	private float randY() 
	{
		return (float)(rand.nextInt(1000));
	}
	
	//exit program by using System.exit(0);
	public void exit() 
	{	
		System.out.println("Exiting");
		System.exit(0);
	}
	
	//accelerates if a is entered
	public void accelerate() 
	{
		if(!(PlayerCyborg.getPlayerCyborg().getSpeed()>30) && !(PlayerCyborg.getPlayerCyborg().getSpeed()<0)) 
		{
			PlayerCyborg.getPlayerCyborg().setSpeed(PlayerCyborg.getPlayerCyborg().getSpeed()+2);
			System.out.println("Accelerating");
			notifyobs();
		}
	}
	
	//brakes if b is entered
	public void brake() 
	{
		if(PlayerCyborg.getPlayerCyborg().getSpeed()!=0 && !(PlayerCyborg.getPlayerCyborg().getSpeed()<0)) 
		{
			PlayerCyborg.getPlayerCyborg().setSpeed(PlayerCyborg.getPlayerCyborg().getSpeed()-2);
			System.out.println("Breaking");
			notifyobs();
		}

	}
	
	
	public void turnRight() 
	{
		PlayerCyborg.getPlayerCyborg().steerRight();
		notifyobs();
	}
	
	public void turnLeft() 
	{
		PlayerCyborg.getPlayerCyborg().steerLeft();
		notifyobs();
	}
	
  	//pretend cyborg collided with another cyborg if c is entered
	public void cybCollide() 
	{
		PlayerCyborg.getPlayerCyborg().cybCollide();
		playCyborgSound();
		System.out.println("Collided with a non player cyborg");
		notifyobs();
	}
	
	//pretend cyborg collided with another cyborg if e is entered
	public void energyCollide() 
	{
		IIterator station = worldObjects.getIterator();
		System.out.println("Collided with an energy station");
		
		while(station.hasNext()) 
		{
			GameObject currentObject = (GameObject)station.getNext();
			if(currentObject instanceof EnergyStation) 
			{
				if(((EnergyStation) currentObject).getCapacity()!=0) 
				{	
					PlayerCyborg.getPlayerCyborg().setEnergyLevel(PlayerCyborg.getPlayerCyborg().getEnergyLevel() +((EnergyStation) currentObject).getCapacity());
					((EnergyStation) currentObject).setCapacity();
					((EnergyStation)currentObject).setColor(ColorUtil.rgb(0, 255, 0));
					playEnergySound();
					break;
				}
			}
		}
		worldObjects.add(new EnergyStation(randSize(), randX(), randY()));
		notifyobs();
	}
	
	//collided with drone if g entered
	public void droneCollide() 
	{
		PlayerCyborg.getPlayerCyborg().droneCollide();
		System.out.println("Collided with an drone");
		notifyobs();
	}
	
	//called each clock tick
	public void clockTick(int gameclock) 
	{
		IIterator allObjects = worldObjects.getIterator();
		while(allObjects.hasNext()) 
		{
			GameObject currentObject = (GameObject)allObjects.getNext();
			if (currentObject instanceof Moveable) {
				Moveable m = (Moveable)currentObject;
				m.move(gameclock);
				notifyObservers();
			}
			if (checkIfLifeLost())
				break;	
		}
		checkForCollisions();
		gameClock+=gameclock;
		setChanged();
		super.notifyObservers();
		checkIfGameOver();
	}
	
	private void checkForCollisions() 
	{
		IIterator allObjects = worldObjects.getIterator();
		while (allObjects.hasNext()) 
		{
			GameObject currentObject = (GameObject)allObjects.getNext();
			if(currentObject instanceof Cyborg) 
			{
				Cyborg currentCyborg = (Cyborg)currentObject;
				
				IIterator otherObjects = worldObjects.getIterator();
				
				while (otherObjects.hasNext()) 
				{
					GameObject currentOtherObject = (GameObject)otherObjects.getNext();
					if (currentCyborg == currentOtherObject) {} 
					else if(currentCyborg.collidesWith(currentOtherObject) && currentOtherObject.collidesWith(currentCyborg)) 
					{
						if ((currentOtherObject instanceof EnergyStation) && (((EnergyStation)currentOtherObject).getCapacity() != 0))
						{
							playEnergySound();
							currentCyborg.handleCollision(currentOtherObject);
							currentOtherObject.handleCollision(currentCyborg);
							worldObjects.add(new EnergyStation(randSize(), randX(), randY()));
						}
						if ((currentOtherObject instanceof NonPlayerCyborg))
						{
							playCyborgSound();
							currentCyborg.handleCollision(currentOtherObject);
							currentOtherObject.handleCollision(currentCyborg);
						}
						if ((currentOtherObject instanceof Drone))
						{
							currentCyborg.handleCollision(currentOtherObject);
							currentOtherObject.handleCollision(currentCyborg);
						}
						else
						{
							currentCyborg.handleCollision(currentOtherObject);
						}
						notifyObservers();
					}
				}
			}
		}
		
	}
	

	//resets the cyborg
	private void reInitialize() 
	{
		PlayerCyborg.getPlayerCyborg().reInitializeCyborg();
	}
	
	//displays game state
	public void displayCurState() 
	{
		System.out.println("Game state:");
		System.out.println("You have "+PlayerCyborg.getPlayerCyborg().getLives()+ " lives left.");
		System.out.println("Current clock value : "+gameClock); 
		System.out.println("Highest base reached: "+PlayerCyborg.getPlayerCyborg().getLastBaseReached());
		System.out.println("Energy level: "+PlayerCyborg.getPlayerCyborg().getEnergyLevel());
		System.out.println("Damage level out of 30 is : "+PlayerCyborg.getPlayerCyborg().getDamageLevel()); 
	}
	
	//displays game map
	public void displayMap() 
	{
		System.out.println("Game map: ");
		IIterator i = worldObjects.getIterator();
		while(i.hasNext()) 
		{
			GameObject o = (GameObject)i.getNext();
			System.out.println(o.toString());
			
		}
	}
	
	//collides with base
	public void baseCollide(int base) 
	{
		PlayerCyborg.getPlayerCyborg().baseCollide(base);
		System.out.println("Collided with base # " + base);
		notifyobs();
	}
	
	//get time
	public int getElapsedTime() 
	{
		return gameClock;
	}
	
	//check if last base is reached and if lives are less than 0
	private void checkIfGameOver() 
	{
		if (getCyborgLastBaseReached() == 4) 
		{
			System.out.println("Last base reached, you Win!");
			System.out.println("Total time: " + gameClock);
			System.exit(0);
		}
		if (getLives() < 0) 
		{
			System.out.println("Game over, you failed!");
			System.exit(0);
		}
		
		IIterator allObjects = worldObjects.getIterator();
		while(allObjects.hasNext()) 
		{
			GameObject currentObject = (GameObject)allObjects.getNext();
			if(currentObject instanceof NonPlayerCyborg) {
				NonPlayerCyborg npc = (NonPlayerCyborg)currentObject;
				if(npc.getLastBaseReached() == 4) {
					System.out.println("NPC reached last base, you lose!");
					System.exit(0);
				}
			}
		}
	}
	
	//get lives
	public int getLives() 
	{
		return PlayerCyborg.getPlayerCyborg().getLives();
	}
	
	//get last base reached
	public int getCyborgLastBaseReached() 
	{
		return PlayerCyborg.getPlayerCyborg().getLastBaseReached();
	}
	//get energy level
	public int getEnergyLevel() 
	{
		return PlayerCyborg.getPlayerCyborg().getEnergyLevel();
	}
	//get damage level
	public int getDamageLevel() 
	{
		return PlayerCyborg.getPlayerCyborg().getDamageLevel();
	}
	//return sound value
	public boolean isSoundOn() 
	{
		return soundOn; 
	}
	//set sound
	public void setSound() 
	{
		this.soundOn = !(this.soundOn);

		if(!isSoundOn()) 
		{
			background.pause();
		}
		else if(!isPaused())
		{
			background.play();
		}
		notifyobs();
	}
	
	//ceate one player cyborg
	private void createCyborg() 
	{
		PlayerCyborg.resetCyborg();
		worldObjects.add(PlayerCyborg.getPlayerCyborg());
		notifyObservers();
	}
	
	//returns object collection
	public GameObjectCollection getObjectCollection() 
	{
		return worldObjects;
	}
	
	//lose life if damage level is greater than 100, energy level is less than 0, speed is less than 0
	private boolean checkIfLifeLost() 
	{
		if((PlayerCyborg.getPlayerCyborg().getEnergyLevel()) > 0 || (PlayerCyborg.getPlayerCyborg().getDamageLevel()) >100 )
		{
			return false;
		}
		else 
		{	
			reInitialize();
			System.out.println("\nLost 1 life");
			playLifeLostSound();
			return true;
		}
	}
	
	public void playEnergySound()
	{
		if(isSoundOn() && !(isPaused())) 
		{
			energySound.play();
		}
	}
	
	public void playLifeLostSound()
	{
		if(isSoundOn() && !(isPaused())) 
		{
			lifeLostSound.play();
		}
	}
	
	public void playCyborgSound()
	{
		if(isSoundOn() && !(isPaused())) 
		{
			cyborgsSound.play();
		}
	}
	
	public boolean isPaused() 
	{
		return isPaused; 
	}
	
	//gets paused value
	public void setPaused(boolean tf) 
	{
		isPaused = tf;
		if(isSoundOn()) 
		{
			if(isPaused) 
			{
				stopSounds();
				background.pause();
			}
			else 
			{
				background.play();
			}
		}
		notifyobs();
	}

	//position command for fixed objects
	public void position() 
	{
		System.out.println("Position command invoked.");
		IIterator allObjects = worldObjects.getIterator();
		while(allObjects.hasNext()) 
		{
			GameObject o = (GameObject) allObjects.getNext();
			if(o instanceof Fixed && ((Fixed) o).isSelected()) 
			{
				System.out.println("object selected");
				((Fixed) o).willBeMoved();
			}
		}
	}
	
	//deselect all objects
	public void deselectAll() 
	{
		IIterator allObjects = worldObjects.getIterator();
		while(allObjects.hasNext()) 
		{
			GameObject object = (GameObject)allObjects.getNext();
			if(object instanceof Fixed) 
			{
				Fixed fixedObj = (Fixed)object;
				fixedObj.setSelected(false);
			}
		}
	}
/*	
	public void test() {
		for(int i = 1; i <20; i++) 
		{
		int randomNum = 1+ rand.nextInt(2); 
		System.out.println("Test :"+ String.valueOf(randomNum)); 
		}
	}
*/
	
	//change strategies	
	public void changeStrategies() 
	{
		IIterator allObjects = worldObjects.getIterator();
		
		while(allObjects.hasNext()) 
		{
			GameObject currentObject = (GameObject)allObjects.getNext();
			if(currentObject instanceof NonPlayerCyborg) 
			{
				NonPlayerCyborg nonPlayerCyborg = (NonPlayerCyborg)currentObject;
				Strategy oldStrategy = nonPlayerCyborg.getStrategy();
				Strategy newStrategy = null;
				if(oldStrategy == null)
				{
					nonPlayerCyborg.setStrategy(new NextBaseStrategy(nonPlayerCyborg, worldObjects));
				}
				else
				{
					while((newStrategy == null) || (newStrategy.getClass().equals(oldStrategy.getClass()))) 
					{
						int randomNum = 1+ rand.nextInt(2); 
						
						if(randomNum == 1) 
						{
							newStrategy = new AttackStrategy(nonPlayerCyborg);
						}
						else if(randomNum == 2) 
						{
							newStrategy = new NextBaseStrategy(nonPlayerCyborg, worldObjects);
						}
						nonPlayerCyborg.setStrategy(newStrategy);
					}
				}
			}
		}
		notifyobs();
	}
	
	
	private void stopSounds() 
	{
		energySound.stop();
		cyborgsSound.stop();
		lifeLostSound.stop();
	}
	
	private void notifyobs() 
	{
		this.setChanged();
		this.notifyObservers();
	}

	


}
