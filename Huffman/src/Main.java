import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;


public class Main {
		public static void main(String[] argv) throws FileNotFoundException{
		String entree = "./test.txt";
		String sortie = "./output.txt";
		String motifs = "./motifs.txt";
		
		File f = new File(entree);
		if(!f.exists()) { 
			JOptionPane.showMessageDialog(null, "Le fichier test.txt n'existe pas le programme va se fermer", "Erreur", JOptionPane.ERROR_MESSAGE);
			System.exit (0); 
		}
		else{
			Huffman h = new Huffman(motifs);
			try {
				h.compression(entree,sortie);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Une erreur inattendue est survenue, le programme va se fermer", "Erreur", JOptionPane.ERROR_MESSAGE);
				System.exit (0); 
			}
			
			
		}
		
		
	}
}
