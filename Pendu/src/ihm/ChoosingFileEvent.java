package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
public class ChoosingFileEvent implements ActionListener{
	private File localFile;
	public ChoosingFileEvent(File myFile) {
		localFile = myFile;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser choice = new JFileChooser();
		int returnValue = choice.showOpenDialog(null);
        if (returnValue == choice.APPROVE_OPTION) {
          File selectedFile = choice.getSelectedFile();
          localFile = selectedFile;
          System.out.println(selectedFile.getName());
        }
		
	}
	
}
