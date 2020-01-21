package starter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.io.IODialog;

public class MainApplication extends GraphicsApplication implements ActionListener{
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };
	
	private MenuPane menu;
	private SettingsPane settings;
	private CreditsPane credits;
	private LoadingPane loading;
	private PauseGamePane pause;
	private PlayerSelectionPane startGame;
	private DraftPane draft;
	private GamePane gamePane;
	private WinPane win;
	private WorldChooserPane worldChoice;
	
	private DifficultyPane dif;
	private CtrlzPane team;
	private int count;
	public boolean fxToggle;
	public Console console;
	public SoundHub music;
	Timer t;

	public void init() 
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public MenuPane getMenu() {
		return menu;
	}
	public void run() 
	{
		setBackground(new Color(234, 234, 234));
		music = new SoundHub(this);
		menu = new MenuPane(this);
		
		settings = new SettingsPane(this);
		credits = new CreditsPane(this);
		loading = new LoadingPane(this);
		team = new CtrlzPane(this);
		startGame = new PlayerSelectionPane(this);
		console=new Console(this);
		switchtoloading();
		t = new Timer(10, this);
		t.start();
		fxToggle=true;
	}
	
	private ArrayList<projectileType> p1, p2;
	private boolean isSinglePlayer; 
	private int diff;

	public void switchToMenu() 
	{
		switchToScreen(menu);
	}
	
	public void switchtoloading() 
	{
		switchToScreen(loading);
	}

	public void startGame() 
	{
		switchToScreen(startGame);
	}
	
	public void startLevel(TANK_STYLE cp1, TANK_STYLE cp2, WorldType world)
	{
		
		gamePane = new GamePane(this, WINDOW_WIDTH, WINDOW_HEIGHT, 
				this.p1, this.p2, this.isSinglePlayer, 
				this.diff, cp1, cp2, world);
		switchToScreen(gamePane);
	
	}
	public void testLevel(WorldType world, String[] grouping) {
		TANK_STYLE cp1 = TANK_STYLE.GREEN;
		TANK_STYLE cp2 = TANK_STYLE.GREEN;
		ArrayList<projectileType> x1= new ArrayList<projectileType>();
		ArrayList<projectileType> y1= new ArrayList<projectileType>();
		if(grouping.length>1) {
			for(int i=1;i<grouping.length;i++) {
				x1.add(projectileType.stringToType(grouping[i]));
				y1.add(projectileType.stringToType(grouping[i]));
			}
		}else {
			x1.add(projectileType.BASIC);
			y1.add(projectileType.BASIC);
		}
		
		
		menu.stopAnimation();
		removeAll();
		gamePane = new GamePane(this, WINDOW_WIDTH, WINDOW_HEIGHT, 
				x1, y1, false, 
				this.diff, cp1, cp2, world);
		switchToScreen(gamePane);
	}
	
	public void pausegame()
	{
		pause = new PauseGamePane(this);
		switchToScreen(pause);
	}
	
	public void switchToSettings() 
	{
		switchToScreen(settings);
	}
	
	public void switchtoCredits() 
	{
		switchToScreen(credits);
	}
	
	public void switchToCtrlz()
	{
		switchToScreen(team);
	}
	public void toggleSound()
	{
		music.toggleSound();
		
	}
	public void toggleFX() {
		fxToggle = !fxToggle;
	}
	public void toggleSnoop() {
		AudioPlayer song = AudioPlayer.getInstance();
		song.playSound("sounds", "GinAndJuice.mp3");
	}
	public boolean isSoundOn()
	{
		return false;
	}
	public void loadGame()
	{
		switchToScreen(gamePane);
	}
	
	public void worldChooser(ArrayList<projectileType> p1,  ArrayList<projectileType> p2, 
			boolean isSinglePlayer, int diff)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.isSinglePlayer = isSinglePlayer;
		this.diff = diff;
		
		worldChoice = new WorldChooserPane(this, WINDOW_WIDTH, WINDOW_HEIGHT, isSinglePlayer);
		switchToScreen(worldChoice);
	}
	
	public void switchToDraft(boolean is1player , int diff)
	{
		draft = new DraftPane(this, WINDOW_WIDTH, WINDOW_HEIGHT, is1player, diff);
		switchToScreen(draft);
	}
	
	public void switchToDif()
	{
		dif = new DifficultyPane(this);
		switchToScreen(dif);
	}
	
	public void switchToWinScreen(boolean isWinnerPlayer1, boolean isSinglePlayer)
	{
		 win = new WinPane(this, WINDOW_WIDTH, WINDOW_HEIGHT, isWinnerPlayer1, isSinglePlayer);
		 switchToScreen(win);
	}
	
	public void skipToWinScreen(boolean isWinnerPlayer1, boolean isSinglePlayer)
	{
		 String[] filler = new String[0];
		 testLevel(WorldType.MEADOWS,filler);
		 win = new WinPane(this, WINDOW_WIDTH, WINDOW_HEIGHT, isWinnerPlayer1, isSinglePlayer);
		 switchToScreen(win);
	}
	
	public String getFinalTank(boolean isSinglePlayer, boolean isWinnerPlayer1) {
		return gamePane.getFinalTankImage(isSinglePlayer, isWinnerPlayer1);
	}
	
	public String getFinalCannon(GImage tank) {
		return gamePane.getFinalTankCannon(tank);
	}
	
	public GImage getTank(boolean isSinglePlayer, boolean isWinnerPlayer1) {
		return gamePane.getTank(isSinglePlayer, isWinnerPlayer1);
	}
	
	public void restartLevel()
	{
		gamePane.restartLevel();
		switchToScreen(gamePane);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		init();
	}
	@Override 
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			console.run();
		}
	}
	
}