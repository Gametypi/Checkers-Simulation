package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import checkers.GameLogic;
import checkers.GameLogic.Situations;
import network.NetworkManager;
import utilities.FileUtilities;

/**
 * Main class of the program and the main user interface.
 * @author Till
 *
 */
public class GUI extends JFrame{
	/**
	 * Reference to the current GameLogic.
	 */
	private GameLogic gmlc;
	//settings that are outsourced to different frames to improve codestructure/readability
	public Moves movesWindow;
	public NetworkManager networkmanager;
	public ColorSettings colorsettings;
	public GameSettings gamesettings;
	public SoundSettings soundsettings;
	public JFileChooser filechooser;
	public FileFilter filter;

	public Console console;
	public PlayfieldPanel playfieldpanel;

	public ImageIcon dameWhite;

	public JMenuItem resume;
	public JMenuItem pause;
	public JMenuItem stop;
	
	JRadioButtonMenuItem slow;
	JRadioButtonMenuItem medium;
	JRadioButtonMenuItem fast;
	
	public JCheckBoxMenuItem displayEnabled;
	public enum AISpeed{SLOW, MEDIUM, FAST, NOTACTIVE}
	public AISpeed aiSpeed;
	public GUI(GameLogic gamelogic){
		super("Checker Simulation 2.0");

		gmlc = gamelogic;
		gmlc.linkGUI(this);
		initialize();
		createWindow();
		console.printInfo("The user interface was loaded successfully. Now it is ready to be explored. Have fun!","GUI");
		console.printInfo("All available commands can be found under /availableCommands", "GUI");
		
	}
	public GUI(){
		this(new GameLogic());
	}
	/**
	 * Entry point of the program.
	 * @param args Additional arguments.
	 */
	public static void main(String[] args) {
		new GUI();

	}

	public void linkGameLogic(GameLogic gamelogic) {
		gmlc = gamelogic;
	}
	private void initialize(){	
		console = new Console();
		playfieldpanel = new PlayfieldPanel(gmlc ,console);
		colorsettings = new ColorSettings(this, Color.BLACK, Color.LIGHT_GRAY);
		soundsettings = new SoundSettings(this);
		movesWindow = new Moves();
		networkmanager = new NetworkManager(this,console);
		console.setNetworkManager(networkmanager);
	}
	private void createWindow(){
		setResizable(true);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		dameWhite = new ImageIcon("resources/Icons/dame.png");
		setIconImage(dameWhite.getImage());

		setJMenuBar(createMenuBar());
		add(playfieldpanel);
		add(console.panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	private JMenuBar createMenuBar(){
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);
        JMenu game = new JMenu("Game");
        JMenuItem newgame = new JMenuItem("New Run");
        newgame.setBackground(Color.WHITE);
        JMenuItem loadgame = new JMenuItem("Load Situation");
        loadgame.setBackground(Color.WHITE);
        JMenuItem savegame = new JMenuItem("Save Situation");
        savegame.setBackground(Color.WHITE);
        JMenuItem openResources = new JMenuItem("Open Resources");
        openResources.setBackground(Color.WHITE);
        game.add(newgame);
        game.add(loadgame);
        game.add(savegame);
        game.add(openResources);
        menubar.add(game);

        JMenu preferences = new JMenu("Preferences");
        JMenuItem color = new JMenuItem("Color");
        color.setBackground(Color.WHITE);
        JMenuItem sound = new JMenuItem("Sound");
        sound.setBackground(Color.WHITE);
        JMenuItem showmoves = new JMenuItem("Moves");
        showmoves.setBackground(Color.WHITE);
        JCheckBoxMenuItem showfieldnumbers = new JCheckBoxMenuItem("show field numbers");
        showfieldnumbers.setBackground(Color.WHITE);   
        //has to be accessabled
        displayEnabled = new JCheckBoxMenuItem("display field");
        displayEnabled.setBackground(Color.WHITE);
        displayEnabled.setSelected(true);
        displayEnabled.setEnabled(false);
        JMenu speed = new JMenu("AI Speed");
        speed.setBackground(Color.WHITE);
        slow = new JRadioButtonMenuItem("Slow",false);
        slow.setBackground(Color.WHITE);
        slow.setEnabled(false);
        medium = new JRadioButtonMenuItem("Medium",false);
		medium.setBackground(Color.WHITE);
		medium.setEnabled(false);
		fast = new JRadioButtonMenuItem("Fast",false);
		fast.setBackground(Color.WHITE);
		fast.setEnabled(false);
		speed.add(slow);
		speed.add(medium);
		speed.add(fast);
		
		
        preferences.add(speed);
        preferences.add(color);
        preferences.add(sound);
        preferences.add(showmoves);
        preferences.add(showfieldnumbers);
        preferences.add(displayEnabled);
        menubar.add(preferences);
        
        JMenu run = new JMenu("Run");
        resume = new JMenuItem("Resume");
        resume.setBackground(Color.WHITE);
        resume.setEnabled(false);
        pause = new JMenuItem("Pause");
        pause.setBackground(Color.WHITE);
        pause.setEnabled(false);
        stop = new JMenuItem("Stop");
        stop.setBackground(Color.WHITE);
        stop.setEnabled(false);
        run.add(resume);
        run.add(pause);
        run.add(stop);
        menubar.add(run);
				
        JMenu help = new JMenu("Help");
        help.setBackground(Color.WHITE);
        JMenuItem guide = new JMenuItem("Guide");
        guide.setBackground(Color.WHITE);
        JMenuItem rules = new JMenuItem("Rules");
        rules.setBackground(Color.WHITE);
        help.add(guide);
        help.add(rules);
        menubar.add(help);   
        
        JMenu network = new JMenu("Network");
        network.setBackground(Color.WHITE);
        JMenuItem changeUserName = new JMenuItem("Change username"); 
        changeUserName.setBackground(Color.WHITE);
        JMenuItem createServer = new JMenuItem("Create Server");
        createServer.setBackground(Color.WHITE);
        JMenuItem connectToServer = new JMenuItem("Connect to a server");
        connectToServer.setBackground(Color.WHITE);
        JMenuItem closeConnection = new JMenuItem("Close connection");
        closeConnection.setBackground(Color.WHITE);
        JMenuItem sendGameRequest = new JMenuItem("Send a game request");
        sendGameRequest.setBackground(Color.WHITE);
        network.add(changeUserName);
        network.add(createServer);
        network.add(connectToServer);
        network.add(closeConnection);
        network.add(sendGameRequest);
        menubar.add(network);
        
        newgame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	gamesettings = new GameSettings(GUI.this);
            }
        });
        openResources.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	try {
					Desktop.getDesktop().open(new File("resources"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        loadgame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	filechooser = new JFileChooser();
            	filter = new FileNameExtensionFilter("pfs","txt");
            	filechooser.setDialogTitle("load playfield file");
            	int rueckgabeWert = filechooser.showOpenDialog(null);
            	filechooser.setCurrentDirectory(new File("resources/PlayfieldSaves"));
            	filechooser.addChoosableFileFilter(filter);
            	//File muss erst ausgewählt werden! Testfile:
            	if(rueckgabeWert == JFileChooser.APPROVE_OPTION){
            		File file = filechooser.getSelectedFile();
		        	try {
		        		gmlc.getPlayfield().setGameSituation(file);
		        		console.printInfo("Playfield was loaded successfully.");
					} catch (IOException e) {
						console.printWarning(file.getName() + " could not be loaded: " + e, "Load Playfield");
					
					}
            	}
//            	if(file.getName().substring(file.getName().length()-4, file.getName().length()) != ".pfs"){
//            		console.printInfo("File ending not .pfs!");
//            	}
//            	else{

            	}

        });
        savegame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	try {
					FileUtilities.saveGameSituation(gmlc.getPlayfield(), "resources/playfieldSaves", "" + System.currentTimeMillis());
					console.printInfo("Playfield saved.");
				} catch (IOException e) {
					console.printWarning("Playfield could not be saved");
				}
            }
        });
        color.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	colorsettings.setVisible(true);
            }
        });
        sound.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	soundsettings.setVisible(true);
            }
        });
        showmoves.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	movesWindow.setVisible(true);
            }
        });
        showfieldnumbers.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
        	playfieldpanel.buttonNumeration(showfieldnumbers.isSelected());
            }

        });
        displayEnabled.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	if(displayEnabled.isSelected()) {
            		playfieldpanel.playfield.setPlayfieldDisplay(playfieldpanel);
            		
            		
            	}else {
            		playfieldpanel.playfield.setPlayfieldDisplay(null);
            		playfieldpanel.clearField();
            	}
            }

        });
        slow.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	if(slow.isSelected()) {
            		slow.setSelected(true);
            		medium.setSelected(false);
            		medium.updateUI();
            		fast.setSelected(false);
            		fast.updateUI();
            		gmlc.setSlowness(2000);
            	}
            }
        });
        medium.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	if(medium.isSelected()) {
            		slow.setSelected(false);
            		slow.updateUI();
            		medium.setSelected(true);
            		fast.setSelected(false);
            		gmlc.setSlowness(1000);
            	}
            }
        });
        fast.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	if(fast.isSelected()) {
            		slow.setSelected(false);
            		slow.updateUI();
            		medium.setSelected(false);
            		medium.updateUI();
            		fast.setSelected(true);
            		gmlc.setSlowness(0);
            	}
            }
        });
        resume.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	resume.setEnabled(false);
            	pause.setEnabled(true);
            	gmlc.setPause(false);
            	playfieldpanel.enableAllButtons(true);
            }
        });
        pause.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {          	
            	pause.setEnabled(false);
            	resume.setEnabled(true);
            	gmlc.setPause(true);
            	playfieldpanel.enableAllButtons(false);
            }
        });
        stop.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	stop.setEnabled(false);
            	gmlc.setPause(true);
            	gmlc.finishGame(Situations.STOP,false);
            	playfieldpanel.enableAllButtons(false);
            }
        });
        
        guide.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	try {
					Desktop.getDesktop().browse(new URI("https://github.com/Gametypi/Checkers-Simulation"));
				} catch (IOException | URISyntaxException e) {
					console.printError("URL to our Github page was not found", "GUI");
					e.printStackTrace();
				}
            }
        });
        rules.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                try {
                    Desktop.getDesktop().open(new File("resources/rules_of_checkers_english.pdf"));
                } catch (IOException e) {
                	console.printWarning("gui", "could not find or open the pdf file");
                    e.printStackTrace();
                }
            }
        });
        changeUserName.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                networkmanager.changeUsername();
            }
        });
        createServer.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	networkmanager.createServer(6000);
            }
        });
        connectToServer.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	networkmanager.createClient("localhost", 6666);

            }
        });
        closeConnection.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	networkmanager.closeConnection();


            }
        });
        sendGameRequest.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
            	networkmanager.sendGameRequest();
            }
        });
        return menubar;
	}

	public void updateBackground( Color color){
		setBackground(color);
		console.updateBackground(color);
		playfieldpanel.setBackground(color);
	}

	public void updateForeground( Color color){
		setForeground(color);
		console.updateForeground(color);
		playfieldpanel.setForeground(color);
	}
	public GameLogic getGameLogic() {
		return gmlc;
	}
	public void setAISpeed(AISpeed speed) {
		aiSpeed = speed;
		switch(aiSpeed) {
		case FAST:
			fast.setSelected(true);
			break;
		case MEDIUM:
			medium.setSelected(true);
			break;
		case NOTACTIVE:
			fast.setSelected(false);
			medium.setSelected(false);
			slow.setSelected(false);
			fast.setEnabled(false);
			medium.setEnabled(false);
			slow.setEnabled(false);
			return;
		case SLOW:
			slow.setSelected(true);
			break;
		}
		slow.setEnabled(true);
		medium.setEnabled(true);
		fast.setEnabled(true);
	}
	public void setEnableResume(boolean a) {
		resume.setEnabled(a);
	}
	public void setEnablePause(boolean a) {
		pause.setEnabled(a);
	}
	public void setEnableStop(boolean a) {
		stop.setEnabled(a);
	}
	public void setDisplayEnabled(boolean enabled) {
		displayEnabled.setSelected(enabled);
		if(!enabled) {
			playfieldpanel.clearField();
		}
	}
	public void setEnableDisplayEnabled(boolean b) {
		displayEnabled.setEnabled(b);
	}
	
}
