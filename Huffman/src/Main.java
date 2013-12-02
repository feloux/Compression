import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;


public class Main {
		public static void main(String[] argv){
		String entree = "./src/Huffman.java";
		String sortie = "./output.txt";
		
		File f = new File(entree);
		if(!f.exists()) { 
			JOptionPane erreurFrame = new JOptionPane();
			erreurFrame.showMessageDialog(null, "Le fichier n'existe pas le programme va se fermer", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.exit (0); 
		}
		else{
			Huffman h = new Huffman();
			try {
				h.compression(entree,sortie);
			} catch (IOException e) {
				JOptionPane erreurFrame = new JOptionPane();
				erreurFrame.showMessageDialog(null, "Une erreur inattendue est survenue, le programme va se fermer", "Erreur", JOptionPane.ERROR_MESSAGE);
				System.exit (0); 
			}
			
			
		}
		
		
	}
}
