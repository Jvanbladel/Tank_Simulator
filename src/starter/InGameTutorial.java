package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class InGameTutorial {
	
	private final static int SOLDIER_SIZE_X = 350;
	private final static int SOLDIER_SIZE_Y = 600;
	private final static int SOLDIER_POS_X = -25;
	private final static int SOLDIER_POS_Y = 800;
	private final static int TEXTBOX_SIZE_X = 500;
	private final static int TEXTBOX_SIZE_Y = 225;
	private final static int TEXTBOX_POS_X = 200;
	private final static int TEXTBOX_POS_Y = 120;
	private final static int TEXT_PLACEMENT_POS_X = 340;
	private final static int TEXT_PLACEMENT_POS_Y = 205;
	
	private GImage soldier;
	private GImage textBox;
	private ArrayList<GParagraph> tutorialTextList;
	private int currentTextIndex;
	private MainApplication program;
	private Timer tutorialTimer;
	private boolean showingTutorial;
	private boolean soldierMoving;
	private int tutorialCounter;

	public InGameTutorial(MainApplication program)
	{
		this.program = program;
		this.showingTutorial = false;
		this.soldierMoving = false;
		this.tutorialCounter = 0;
		
		tutorialTimer = new Timer(15, null);
		tutorialTimer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt)
			{		
				if(tutorialCounter < 110)
				{
					soldier.move(0, -5);
				}
				else if(tutorialCounter == 120)
				{
					program.add(textBox);
					program.add(tutorialTextList.get(0));
				}
				else if (tutorialCounter > 123)
				{
					tutorialTimer.stop();
					soldierMoving = false;
				}
				tutorialCounter++;
			}
		});
		drawTutorial();
	}
	
	private void drawTutorial()
	{
		
		soldier = new GImage("../media/Tutorial/soldier.png");
		soldier.setSize(SOLDIER_SIZE_X, SOLDIER_SIZE_Y);
		soldier.setLocation(SOLDIER_POS_X, SOLDIER_POS_Y);

		textBox = new GImage("../media/Tutorial/yelling.png");
		textBox.setSize(TEXTBOX_SIZE_X, TEXTBOX_SIZE_Y);
		textBox.setLocation(TEXTBOX_POS_X, TEXTBOX_POS_Y);
		
		tutorialTextList = new ArrayList<GParagraph>();
		
		GParagraph text1 = new GParagraph(""
				+ " Time is valuable on\n"
				+ "     the battlefield!\n"
				+ "     Let's be quick!\n",TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text2 = new GParagraph(""
				+ "We need you to destroy\n"
				+ "  the enemy tank seen\n"
				+ "     over the horizon!\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text3 = new GParagraph(""
				+ "  As the commander,\n"
				+ "      you have many\n"
				+ "          controls.\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text4 = new GParagraph(""
				+ "    First off, you can\n"
				+ "  adjust the power of\n"
				+ "    each shot using..\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text5 = new GParagraph(""
				+ "red and green arrows.\n"
				+ "   This changes the\n"
				+ "  strength of a shot.\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text6 = new GParagraph(""
				+ "Next, you can change\n"
				+ "the angle of the tank\n"
				+ "cannon. By dragging.. \n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text7 = new GParagraph(""
				+ "the large green arrow.\n"
				+ " The shot will launch\n"
				+ " at the angle chosen. \n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text8 = new GParagraph(""
				+ "   Your tank is also\n"
				+ " mobile. If you click \n"
				+ "      on the tank .. \n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text9 = new GParagraph(""
				+ "  green arrows will\n"
				+ "  appear letting you\n"
				+ "    move the tank.\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text10 = new GParagraph(""
				+ " As the Commander,\n"
				+ " you can move your \n"
				+ "   field of view by..\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text11 = new GParagraph(""
				+ " clicking and holding\n"
				+ "  the side of the map.\n"
				+ "    Sighting is key.\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text12 = new GParagraph(""
				+ "   Be sure to look at\n"
				+ "    the shot you are\n"
				+ "  firing. To do this..\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		GParagraph text13 = new GParagraph(""
				+ "   Hover your mouse\n"
				+ "  over the ammo icon.\n"
				+ "Goodluck Commander!\n", TEXT_PLACEMENT_POS_X, TEXT_PLACEMENT_POS_Y);
		
		tutorialTextList.add(text1);
		tutorialTextList.add(text2);
		tutorialTextList.add(text3);
		tutorialTextList.add(text4);
		tutorialTextList.add(text5);
		tutorialTextList.add(text6);
		tutorialTextList.add(text7);
		tutorialTextList.add(text8);
		tutorialTextList.add(text9);
		tutorialTextList.add(text10);
		tutorialTextList.add(text11);
		tutorialTextList.add(text12);
		tutorialTextList.add(text13);
		
		for(int i = 0; i < tutorialTextList.size(); i++)
		{
			tutorialTextList.get(i).setFont("Arial-Bold-24");
		}
		
		showingTutorial = false;
	}
	
	public void toggleTutorial(boolean show)
	{
		if(show)
		{
			soldierMoving = true;
			showingTutorial = true;
			tutorialCounter = 0;
			currentTextIndex = 0;
			soldier.setLocation(SOLDIER_POS_X, SOLDIER_POS_Y);
			program.add(soldier);
			tutorialTimer.start();
		}
		else
		{
			showingTutorial = false;
			program.remove(soldier);
			program.remove(textBox);
			for(int i = 0; i < tutorialTextList.size(); i++)
			{
				program.remove(tutorialTextList.get(i));
			}
		}
	}
	
	public void incrementTutorial()
	{
		if(currentTextIndex < tutorialTextList.size()- 1)
		{
			program.remove(tutorialTextList.get(currentTextIndex));
			program.add(tutorialTextList.get(++currentTextIndex));
		}
		else
		{
			toggleTutorial(false);
		}
	}
	
	public boolean isText(GObject obj)
	{
		for(int i = 0; i < tutorialTextList.size(); i++)
		{
			if(obj == tutorialTextList.get(i))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isTouching(GObject obj)
	{
		return (obj == soldier || obj == textBox);
	}
	
	public boolean isTutorialInAnimation()
	{
		return soldierMoving;
	}
	
	public boolean isShowingTutorial()
	{
		return showingTutorial;
	}
	
	public void setShowingTutorial(boolean s)
	{
		this.showingTutorial = s;
	}
}
