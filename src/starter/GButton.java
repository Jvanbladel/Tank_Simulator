package starter;


/*
 * File: GButton.java
 * ------------------
 * Provides a simple way to create a button with text.
 * The button will automatically set the size of the text
 * Based on the width and height of the button.
 * Users can also set the Color of the button's background
 * as well as the color of the text.
 */

import java.awt.Color;
import java.awt.Font;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRoundRect;

public class GButton extends GCompound {
	private GRoundRect rect;
	private GOval oval;
	private GLabel message;

	public static final int BUFFER = 20;

	public GButton(String label, double x, double y, double width, double height) {
		this(label, x, y, width, height, Color.white);
	}

	public GButton(String label, double x, double y, double width, double height, int r, int g, int b) {
		this(label, x, y, width, height, new Color(r, g, b));
	}
	
	//Default
	public GButton(String label, double x, double y, double width, double height, Color col) {
		super();
		setLocation(x, y);
		rect = new GRoundRect(0, 0, width, height);
		rect.setFilled(true);
		rect.setFillColor(col);
		add(rect);
		message = new GLabel(label);
		sizeLabelFont(message, width - BUFFER, height - BUFFER);
		double centerX = width / 2 - message.getWidth() / 2;
		double centerY = height / 2 + message.getAscent() / 4;
		add(message, centerX, centerY);
	}
	
	//Creates a GButton Circle with centered Text
	public GButton(String label, double x, double y, double diameter, Color col) {
		super();
		setLocation(x, y);
		oval = new GOval(0, 0, diameter, diameter);
		oval.setFilled(true);
		oval.setFillColor(col);
		add(oval);
		message = new GLabel(label);
		sizeLabelFont(message, diameter - BUFFER, diameter - BUFFER);
		double centerX = diameter / 2 - message.getWidth() / 2;
		double centerY = diameter / 2 + message.getAscent() / 4;
		add(message, centerX, centerY);
	}
	
	//Creates a GButton Circle with text in upper quadrant bases on boolean t
	public GButton(String label, double x, double y, double diameter, Color col, boolean t) {
		super();
		setLocation(x, y);
		oval = new GOval(0, 0, diameter, diameter);
		oval.setFilled(true);
		oval.setFillColor(col);
		add(oval);
		message = new GLabel(label);
		sizeLabelFont(message, diameter/3 - BUFFER, diameter/3 - BUFFER);
		double centerX = 0;
		double centerY = 0;
		if(t)
		{
			centerX = diameter - message.getWidth() * 2;
			centerY = diameter/2 / 1.75 + message.getAscent() / 4;
		}
		else
		{
			centerX = diameter - message.getWidth()*4;
			centerY = diameter/2 / 1.75 + message.getAscent() / 4;
		}
		
		add(message, centerX, centerY);
	}

	private void sizeLabelFont(GLabel label, double width, double height) {
		int size, style;
		String name;
		Font f = label.getFont();
		name = f.getFontName();
		style = f.getStyle();
		size = f.getSize();
		while (label.getWidth() < width && label.getHeight() < height) {
			f = label.getFont();
			size = f.getSize();
			label.setFont(new Font(name, style, size + 1));
		}
		label.setFont(new Font(name, style, size - 1));
	}

	public void setFillColor(Color col) {
		rect.setFillColor(col);
	}

	public void setColor(Color col) {
		message.setColor(col);
	}
	
	public void setFontSize(String s) {
		message.setFont(s);
	}
	
	public void changeLabel(String s) {
		message.setLabel(s);
	}
}
