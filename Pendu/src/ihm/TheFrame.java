package ihm;
import game.Pendu;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class TheFrame extends JFrame implements ActionListener{
	
	public final String GENERATE = "create";
	public final String CHOOSE = "choose";
	public final String TRY = "try";
	public String secret;
	private File fichierTxt;
	private ArrayList<String> words = new ArrayList<String>();
	private JButton chooseFile;
	private JButton trying;
	private JPanel generating;
	private JPanel pFileChooser;
	private JPanel pPlay;
	private JTextField fill;
	private JTextField fichierName;
	private JButton generate;
	private Pendu myGame;
	private JLabel textSecret;
	private JPanel pSecret;
	public TheFrame() {
		
		this.fichierTxt = null;
		JMenuBar menuBar = new JMenuBar();
		JMenu main = new JMenu("Le jeu");
		JMenuItem exit = new JMenuItem("quitter");
		JMenuItem newGame = new JMenuItem("Nouvelle game");
		main.add(newGame);
		main.add(exit);
		menuBar.add(main);
		
		this.pSecret = new JPanel(); this.pSecret.setLayout(new BorderLayout());
		this.trying = new JButton("Tenter");
		this.trying.setSize(60, 50);
		this.trying.setActionCommand(TRY);
		this.trying.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		this.trying.addActionListener(this);
		this.fill = new JTextField(1);
		
		this.pPlay = new JPanel();
		this.pPlay.setLayout(new GridLayout(2, 1));		
		this.pPlay.add(this.fill); this.pPlay.add(this.trying);
		this.textSecret = new JLabel();
		this.pSecret.add(textSecret);
		
		generating = new JPanel();
		generating.setLayout(new BorderLayout());
		
		generate = new JButton("generate");
		generate.setActionCommand(GENERATE);
		generate.addActionListener(this);
		pFileChooser = new JPanel();
		pFileChooser.setLayout(new FlowLayout());
		fichierName = new JTextField("oui");
		this.chooseFile = new JButton("browse");
		this.chooseFile.setActionCommand(CHOOSE);
		this.chooseFile.addActionListener(this);
		pFileChooser.add(fichierName);
		pFileChooser.add(this.chooseFile);
		
		generating.add(menuBar, BorderLayout.NORTH);
		generating.add(pFileChooser, BorderLayout.CENTER);
		generating.add(generate, BorderLayout.SOUTH);
		
		
		
		
		this.add(generating);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch(e.getActionCommand()) {
			case GENERATE:
					this.copyWords();
					this.generateRandomWord();
					this.myGame = new Pendu(12, this.secret);
					this.setSize(500, 500);
					this.textSecret.setText(String.valueOf(this.myGame.getMaskWord()));
					this.textSecret.setHorizontalAlignment(JLabel.CENTER);
					this.textSecret.setFont(new Font("Consolas", Font.BOLD, 24));
					this.textSecret.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					this.generating.remove(this.pFileChooser); this.generating.remove(this.generate);
					this.generating.add(this.pSecret, BorderLayout.CENTER);
					this.generating.add(this.pPlay, BorderLayout.SOUTH);
					break;
			case CHOOSE:
				JFileChooser choice = new JFileChooser();
				int returnValue = choice.showOpenDialog(null);
		        if (returnValue == choice.APPROVE_OPTION) {
		          File selectedFile = choice.getSelectedFile();
		          fichierTxt = selectedFile;
		          System.out.println(selectedFile.getName());
		        }
		        break;
		        
			case TRY:
				if(!this.fill.getText().equals("") || !this.fill.getText().startsWith(" ") || this.fill.getText().length() > 1) {
					this.myGame.toTry(this.fill.getText().charAt(0));
				}
				this.textSecret.setText(String.valueOf(this.myGame.getMaskWord()));
				
				
		}
		
		
	}
	public void copyWords() {
		try
		{
		    FileReader fr = new FileReader (this.fichierTxt);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try
		    {
		        String line = br.readLine();
		        this.words.add(new String(line));
		        while (line != null)
		        {
		            System.out.println (line);
		            line = br.readLine();
		            if(line != null)
		            	this.words.add(new String(line));
		        }
		 
		        br.close();
		        fr.close();
		    }
		    catch (IOException exception)
		    {
		        System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception)
		{
		    System.out.println ("Le fichier n'a pas �t� trouv�");
		}
	}
	
	public void generateRandomWord() {
		Random generator = new Random();
		int generatInt = generator.nextInt(this.words.size());
		this.secret = this.words.get(generatInt);
	}
}
