package ihm;
import game.Pendu;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
	
	public final String NEWGAME = "new";
	public final String GENERATE = "create";
	public final String CHOOSE = "choose";
	public final String TRY = "try";
	public final String GO_GENERATE = "goGenerate";
	
	
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
	private JLabel yourChances;
	public TheFrame() {
		
		this.yourChances = new JLabel();
		this.fichierTxt = null;
		JMenuBar menuBar = new JMenuBar();
		JMenu main = new JMenu("Le jeu");
		JMenuItem exit = new JMenuItem("quitter");
		JMenuItem newGame = new JMenuItem("Nouvelle game");
		JMenuItem generateOption = new JMenuItem("Generer un autre dico");
		generateOption.setActionCommand(GO_GENERATE);
		generateOption.addActionListener(this);
		newGame.setActionCommand(NEWGAME);
		newGame.addActionListener(this);
		main.add(newGame);
		main.add(exit);
		main.add(generateOption);
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
					this.yourChances.setText("Compteur: " + this.myGame.chance);
					this.generating.add(this.yourChances, BorderLayout.EAST);
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
					this.textSecret.setText(String.valueOf(this.myGame.getMaskWord()));
					this.yourChances.setText("Compteur: " + this.myGame.chance);
					if(this.myGame.getSecretWord().equals(this.textSecret.getText())) {
							System.out.println("you win");
							int retWin = JOptionPane.showConfirmDialog(this, "The answer was "+this.myGame.getSecretWord() +"\nDo you want to continue?", "You Win!!!!", JOptionPane.OK_CANCEL_OPTION);
							if(retWin == JOptionPane.OK_OPTION) {
								this.generateRandomWord();
								this.myGame = new Pendu(12, this.secret);
								this.textSecret.setText(String.valueOf(myGame.getMaskWord()));
								this.yourChances.setText("Compteur: " + this.myGame.chance);
							}
					}else if(this.myGame.chance == 0) {
						System.out.println("you lose");
						int ret = JOptionPane.showConfirmDialog(this, "The aswner was "+ this.myGame.getSecretWord()+"\nDo you want to continue?", "You lose", JOptionPane.OK_CANCEL_OPTION);
						if(ret == JOptionPane.OK_OPTION) {
							this.generateRandomWord();
							this.myGame = new Pendu(12, this.secret);
							this.textSecret.setText(String.valueOf(myGame.getMaskWord()));
							this.yourChances.setText("Compteur: " + this.myGame.chance);
						}
					}
				}
				
				break;
			case NEWGAME:
				this.generateRandomWord();
				this.myGame = new Pendu(12, this.secret);
				this.textSecret.setText(String.valueOf(myGame.getMaskWord()));
				this.yourChances.setText("Compteur: " + this.myGame.chance);
				break;
				
			case GO_GENERATE:
				this.generating.remove(this.yourChances);
				this.generating.add(this.pFileChooser, BorderLayout.CENTER); this.generating.add(this.generate, BorderLayout.SOUTH);
				this.generating.remove(this.pSecret);
				this.generating.remove(this.pPlay);
				this.pack();
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
		    System.out.println ("Le fichier n'a pas été trouvé");
		}
	}
	
	public void generateRandomWord() {
		Random generator = new Random();
		int generatInt = generator.nextInt(this.words.size());
		this.secret = this.words.get(generatInt);
	}
}
