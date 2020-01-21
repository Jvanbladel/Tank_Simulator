package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class DraftPane extends GraphicsPane{
	private MainApplication program; 
	
	private GButton X;
	private GImage tutorial;
	private GImage finished;
	
	private int width;
	private int height;
	boolean is1player;
	int dif;
	
	private GImage draftLabel;
	
	private GImage player1Label;
	private GImage player2Label;
	
	private TransparentImage p1pick;
	private TransparentImage p2pick;
	
	private GImage background;
	
	private ArrayList<GRect> outlines;
	private ArrayList<GImage> pickbox;
	private ArrayList<GImage> ammos;

	private GRect ammoName;
	private GParagraph ammoDescription;
	
	private Draft draft;
	private DraftTutorial tutorialObj;
	
	public DraftPane(MainApplication app, int width, int height, boolean is1player, int dif) {
		super();
		program = app;
		
		this.width = width;
		this.height = height;
		
		this.is1player = is1player;
		this.dif = dif;
		
		p1pos = 0;
		p2pos = 0;
		
		this.draft = new Draft(is1player, dif);
		
		X = new GButton("X", width - 50, 0, 50, 50);
		X.setFillColor(Color.RED);
		
		tutorial = new GImage("../media/Tutorial/tutorial_button.png");
		tutorial.setSize(50, 50);
		tutorial.setLocation(0, 0);
		
		background = new GImage("../media/BackGroundImages/meadowsBackGroundImage.png");
		background.setSize(width, height);
		
		drawDraft();
		
		tutorialObj = new DraftTutorial(program);
	}
	
	private ArrayList<projectileType> draftTypes;
	
	private void drawDraft()
	{
		draftLabel = new GImage("TitleImage/TITLE_DRAFT.png", 250,25);
		
		player1Label = new GImage("TitleImage/TITLE_PLAYER1.png", 20,115);
		player1Label.setSize(180,50);
		
		
		if(is1player)
		{
			player2Label = new GImage("TitleImage/TITLE_COMPUTER.png",width-200,115);
		}
		else
		{
			player2Label = new GImage("TitleImage/TITLE_PLAYER2.png",width-200,115);
		}
		
		
		player2Label.setSize(180,50);
		
		draftTypes = draft.getAmmoForGame();
		
		outlines = new ArrayList<GRect>();
		pickbox = new ArrayList<GImage>();
		ammos = new ArrayList<GImage>();
		
		ammoName = new GRect(800,600,300,150);
		ammoName.setFilled(true);
		ammoName.setFillColor(Color.GRAY);
		
		ammoDescription = new GParagraph("", 200,200);
		
		p1pick = new TransparentImage("otherImages/PICK_AREA.png");
		p1pick.changeTransparent(0.3);
		p1pick.setLocation(10,100);
		
		p2pick = new TransparentImage("otherImages/PICK_AREA.png");
		p2pick.changeTransparent(0.3);
		p2pick.setLocation(width-210, 100);
		
		
		
		for(int i = 0; i < draftTypes.size(); i++)
		{
			if(i < 5)
			{
				GRect box = new GRect(50,50);
				box.setLocation(340, 190 + (75*i));
				outlines.add(box);
				
				GImage pick = new GImage("otherImages/PICK_1.jpg");
				pick.setLocation(15,170 + (60 *i));
				pick.setSize(185,50);
				pickbox.add(pick);
				
				GImage ammo = new GImage("../media/Ammo/" + draftTypes.get(i).toString() + ".png");
				ammo.setSize(30, 30);
				ammo.setLocation(350, 200 + (75*i));
				ammos.add(ammo);	
			}
			else
			{
				GRect box = new GRect(50,50);
				box.setLocation(415, 190 + (75*(i-5)));
				outlines.add(box);
				
				GImage pick = new GImage("otherImages/PICK_1.jpg");
				pick.setLocation(width- 200, 170 + (60 *(i-5)));
				pick.setSize(185,50);
				pickbox.add(pick);
				
				GImage ammo = new GImage("../media/Ammo/" + draftTypes.get(i).toString() + ".png");
				ammo.setSize(30, 30);
				ammo.setLocation(425, 200 + (75*(i-5)));
				ammos.add(ammo);
			}
		}
	}
	
	

	@Override
	public void showContents() 
	{
		program.add(p1pick);
		program.add(p2pick);
		program.add(X);
		program.add(tutorial);
		program.add(player1Label);
		program.add(player2Label);
		program.add(draftLabel);
		for(int i = 0; i < outlines.size(); i++)
		{
			program.add(outlines.get(i));
			program.add(pickbox.get(i));
		}
		for(int i = 0; i < ammos.size(); i++)
		{
			program.add(ammos.get(i));
		}
		program.add(ammoName);
		program.add(ammoDescription);
	}

	@Override
	public void hideContents() 
	{
		program.removeAll();
		program.add(background);
	}

	private GObject obj;
	private int p1pos = 0;
	private int p2pos = 0;
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		obj = program.getElementAt(e.getX(), e.getY());
		
		if(obj == null)
			return;
		
		if(obj == X)
		{
			if(is1player)
				program.switchToDif();
			else
				program.startGame();
		}
		else if(obj == tutorial && !tutorialObj.istimerRunning())
		{
			tutorialObj.showDraftTutorial();
		}
		
		if(!tutorialObj.isInTutorial())
		{
			if(is1player && draft.isPlayer1Turn())
			{
				for(int i = 0; i < outlines.size(); i++)
				{
					if(obj.getX() > 215 && obj.getX() < 585)
					{
						if(obj == outlines.get(i) || obj == ammos.get(i))
						{
							outlines.get(i).setSize(185, 50);
							outlines.get(i).setLocation(800,600);
							
							ammos.get(i).setLocation(25, 180 + (60 *p1pos));
							
							GLabel shot = new GLabel(draftTypes.get(i).getDraftName());
							shot.setFont("Arial-Bold-14");
							shot.setLocation(80, 200 + (60 *p1pos));
							program.add(shot);
							
							draft.playerSelect(draftTypes.get(i));
							p1pos++;
							ammoName.setLocation(800,600);
							ammoDescription.setLocation(800,600);
							haveComputerSelect();
						}
					}
				}
			}
			
			if(!is1player)
			{
				if(draft.isPlayer1Turn())
				{
					for(int i = 0; i < outlines.size(); i++)
					{
						if(obj.getX() > 215 && obj.getX() < 585)
						{
							if(obj == outlines.get(i) || obj == ammos.get(i))
							{
								outlines.get(i).setSize(185, 50);
								outlines.get(i).setLocation(800,600);
								ammos.get(i).setLocation(25, 180 + (60 *p1pos));
								
								GLabel shot = new GLabel(draftTypes.get(i).getDraftName());
								shot.setFont("Arial-Bold-14");
								shot.setLocation(80, 200 + (60 *p1pos));
								program.add(shot);
	
								draft.playerSelect(draftTypes.get(i));
								p1pos++;
								ammoName.setLocation(800,600);
								ammoDescription.setLocation(800,600);
							}
						}		
					}
				}
				else
				{
					for(int i = 0; i < outlines.size(); i++)
					{
						if(obj.getX() > 215 && obj.getX() < 585)
						{
							if(obj == outlines.get(i) || obj == ammos.get(i))
							{
								outlines.get(i).setSize(185, 50);
								outlines.get(i).setLocation(800,600);
								ammos.get(i).setLocation(width- 190, 180 + (60 *p2pos));
								
								GLabel shot = new GLabel(draftTypes.get(i).getDraftName());
								shot.setFont("Arial-Bold-14");
								shot.setLocation(width - 135, 200 + (60 *p2pos));
								program.add(shot);
								
								draft.playerSelect(draftTypes.get(i));
								p2pos++;
								ammoName.setLocation(800,600);
								ammoDescription.setLocation(800,600);
								
								if(p1pos + p2pos == 10)
								{
									finished = new GImage("Button/begin0.png", 300, 250);
									program.add(finished);
								}
							}
						}	
					}
				}
			}	
			
			if (obj == finished)
			{
				program.worldChooser(draft.getp1(), draft.getp2(), is1player, dif);
			}
		}
		
		if(!tutorialObj.istimerRunning())
		{
			tutorialObj.doNext(obj);
		}
	}
	
	private boolean haveComputerSelect()
	{
		projectileType selection = draft.computerSelect();
		
		for(int i = 0; i <draftTypes.size(); i++)
		{
			if(draftTypes.get(i) == selection)
			{
				if(outlines.get(i).getX() > 215 && outlines.get(i).getX() < 585)
				{
						outlines.get(i).setSize(185, 50);
						outlines.get(i).setLocation(800,600);
						
						ammos.get(i).setLocation(width- 190, 180 + (60 *p2pos));
						
						GLabel shot = new GLabel(draftTypes.get(i).getDraftName());
						shot.setFont("Arial-Bold-14");
						shot.setLocation(width - 135, 200 + (60 *p2pos));
						program.add(shot);
						
						draft.playerSelect(selection);
						
						p2pos++;
						
						if(p1pos + p2pos == 10)
						{
							finished = new GImage("Button/begin0.png", 300, 250);
							program.add(finished);
						}
						return true;
				}
			}	
		}
		return false;
	}
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (p1pos + p2pos == 10) {
			if (obj == finished) {
				finished.setImage("Button/begin1.png");
			}
			else {
				finished.setImage("Button/begin0.png");
			}
		}
		if (e.getX() >= 340 && e.getX() <= 465) {
			for (int i = 0; i < ammos.size(); i++) {
				if(obj == ammos.get(i) || obj == outlines.get(i)) {
					ammoName.setLocation(e.getX()+10, e.getY()-60);
					ammoName.sendToFront();
					String temp = draftTypes.get(i).getDraftName() + "\n" + draftTypes.get(i).descriptionOfAmmo();
					ammoDescription.setText(temp);
					ammoDescription.setLocation(e.getX() - 180, e.getY()-230);
					ammoDescription.setFont("Arial-18");
					ammoDescription.sendToFront();
					break;
				}
				else {
					ammoName.setLocation(800,600);
					ammoDescription.setLocation(800,600);
				}
			}
		}
		else {
			ammoName.setLocation(800,600);
			ammoDescription.setLocation(800,600);
		}
		
	}

	@Override 
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			program.console.run();
		}
	}
}
