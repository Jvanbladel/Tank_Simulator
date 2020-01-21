package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class DraftTutorial {

	private boolean isShowingTutorial;
	private boolean inTutorial;
	private boolean inPart1OfTutorial;
	private int tutorialCounter;
	
	private MainApplication program;
	private Timer tutorialTimer;
	
	public DraftTutorial(MainApplication program)
	{
		this.isShowingTutorial = false;
		this.inTutorial = false;
		this.inPart1OfTutorial = true;
		this.tutorialCounter = 0;
		this.program = program;
		drawDraftTutorial();
		
		tutorialTimer = new Timer(15, null);
		
		//TIMER t2 actionPreformed()
		/*
		 * This method is the timer to have the tank shoot a selected button
		 */
		
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
					program.add(textBox1);
					program.add(text1);
				}
				else if (tutorialCounter > 123)
				{
					tutorialTimer.stop();
				}
				tutorialCounter++;
			}
		});
	}
	
	
	public void showDraftTutorial()
	{
		if(!isShowingTutorial)
		{
			inTutorial = true;
			inPart1OfTutorial = true;
			drawDraftTutorial();
			
			program.add(soldier);
			tutorialCounter = 0;
			tutorialTimer.start();
			isShowingTutorial = true;
		}
		else
		{
			inTutorial = false;
			
			program.remove(soldier);
			program.remove(textBox1);
			program.remove(text1);
			program.remove(text2);
			isShowingTutorial = false;
		}
	}
	
	private GImage soldier;
	private GImage textBox1;
	private GParagraph text1;
	private GParagraph text2;
	
	private void drawDraftTutorial()
	{
		soldier = new GImage("../media/Tutorial/soldier.png");
		soldier.setSize(350, 600);
		soldier.setLocation(-25, 800);
		
		textBox1 = new GImage("../media/Tutorial/normal.png");
		textBox1.setSize(500, 250);
		textBox1.setLocation(200, 120);
		
		text1 = new GParagraph(""
				+ "Welcome to the war zone Commander!\n"
				+ "     An enemy tank on the horizon.\n"
				+ "     Your mission is to destroy the\n"
				+ "          opposition at any cost!\n",235, 173);
		
		text2 = new GParagraph(""
				+ "    Choose the supplies best fit to\n"
				+ "  complete the mission. Beware the\n"
				+ "enemy can steal the ammo you want.\n"
				+ " To select an ammo, click on an icon.",235, 173);

		text1.setFont("Arial-Bold-24");
		text2.setFont("Arial-Bold-24");
	}
	
	public void toggleText(boolean firstText)
	{
		if(firstText)
		{
			program.remove(text1);
			program.add(text2);
			inPart1OfTutorial = false;
		}
		else
		{
			showDraftTutorial();
		}
	}
	
	public boolean istimerRunning()
	{
		return tutorialTimer.isRunning();
	}
	
	public boolean isInTutorial()
	{
		return inTutorial;
	}
	
	public void doNext(GObject obj)
	{
		if(obj == soldier)
		{
			toggleText(inPart1OfTutorial);
		}
		else if (obj == textBox1)
		{
			toggleText(inPart1OfTutorial);
		}
		else if(obj == text1)
		{
			toggleText(inPart1OfTutorial);
		}
		else if(obj == text2)
		{
			showDraftTutorial();
		}
	}
}
