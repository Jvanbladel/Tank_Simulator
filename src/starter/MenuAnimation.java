// This includes the menu animation part of the program.
package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import acm.graphics.GImage;

//Refactored from MenuPane
public class MenuAnimation implements ActionListener{
	
	private GImage animation_tank;
	private GameImage animation_barrel;
	private GImage animation_shot;
	private TransparentImage animation_explosion;

	private double title_alpha;
	
	private TransparentImage background;
	
	private TransparentImage title;
	
	MenuPane menuPane;
	Timer t1;
	
	MainApplication program;
	
	public MenuAnimation(TransparentImage background, MainApplication program, TransparentImage title, MenuPane mp, Timer timer, double titlealpha) {
		this.background = background;
		this.program = program;
		this.title = title;
		menuPane = mp;
		t1 = timer;
		title_alpha = titlealpha;
		
		animation_barrel = new GameImage("TanksPNGS/greenTank_cannon.png", -370, 463);
		
		animation_tank= new GImage("../media/TanksPNGS/LeftSide_GreenTank.png", -350, 450);
		animation_tank.setSize(125,60);
		
		animation_shot = new GImage("Ammo/basic.png", 160, 390);
		animation_shot.setSize(20,20);
		animation_shot.setVisible(false);
		
		program.add(animation_barrel);
		program.add(animation_tank);
		program.add(animation_shot);
		
		animation_explosion = new TransparentImage("otherImages/explosion.png");
		animation_explosion.setSize(100,100);
		animation_explosion.setLocation(320,150);
		animation_explosion.setVisible(false);
		
		program.add(animation_explosion);
		program.add(background);
		program.add(title);
	}
	
	public boolean title_showing() {
		return (title_alpha >= 0.90);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (background.getTransparency() <=0.99) 
		{
			background.changeTransparent(background.getTransparency()+0.01);
			background.setSize(900,620);
			background.setLocation(-50,0);
			background.sendToBack();
		}
		else 
		{
			if (animation_tank.getX() != 50)
			{
				animation_tank.move(2.5, 0);
				animation_barrel.move(2.5, 0);
			}
			else 
			{
				if (animation_barrel.getRotation() != 295) 
				{
					animation_barrel.rotate(-1);
				}
				else 
				{
					if (animation_shot.getY() > 150) 
					{
						animation_shot.setVisible(true);
						animation_shot.move(10, -15);
					}
					else 
					{
						animation_shot.setVisible(false);
						if (animation_explosion.getWidth() < 800)
						{
							animation_explosion.setVisible(true);
							animation_explosion.setSize(animation_explosion.getWidth()+30, animation_explosion.getHeight()+30);
							animation_explosion.setLocation(animation_explosion.getX()-15, animation_explosion.getY()-15);
						}
						else 
						{
							if (title_alpha < 0.99)
							{
								program.remove(animation_explosion);
								title_alpha+=0.01;
								title.changeTransparent(title_alpha);
							}
							else 
							{
								if (title.getX() !=30) 
								{
									title.move(-2, -2);
								}
								else
								{
									if (background.getHeight() != 610) 
									{
										background.setImage("otherImages/background.png");
										background.setSize(900,610);
										background.setLocation(-50,0);
										background.sendToBack();
										program.remove(animation_explosion);
										program.remove(animation_tank);
										program.remove(animation_barrel);
									}
									else {
										t1.stop();
										menuPane.addUI();
									}
								}
							}
						}
					}
				}
			}	
		}
	}
		
}
