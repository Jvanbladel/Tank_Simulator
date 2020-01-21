package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class LoadingPane extends GraphicsPane implements ActionListener{
	private MainApplication program; 
	
	private GLabel credits;

	public static final int DELAY_MS = 5;
	private int i = 0;
	private boolean a = false;
	private int count =0;

	Timer t;

	public LoadingPane(MainApplication app) {
		super();
		program = app;
		
		credits = new GLabel("Ctrl-Z Presents", 275, 275);
		credits.setFont("Arial-Bold-36");
		credits.setColor(new Color(0,0,0));
		program.setBackground(Color.black);
		t = new Timer(DELAY_MS, this);
		t.start();
	}

	@Override
	public void showContents() 
	{
		program.add(credits);
	}

	@Override
	public void hideContents() 
	{
		program.remove(credits);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Color temp = new Color (i, i, i);
		
		if (i == 4 || i == 1) 
		{
			a = false;
			count++;
		}
		else if (i == 255 || i == 254) 
		{
			a = true;
		}
		
		if (a == true) 
		{
			i-=5;
		}
		else 
		{
			i+=2;
		}
		
		if (count == 2) 
		{
			t.stop();
			program.switchToMenu();
		}

		credits.setColor(temp);	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		t.stop();
		program.switchToMenu();
	}
}
