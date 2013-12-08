import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import javax.swing.JOptionPane;
 
public class Huffman {  
	
	//Priority queue est une liste qui lors d'une modification 
	//quelconque tri ses �l�ments selon un comparateur pr�c�dement impl�ment�
    private  PriorityQueue<Noeud> bytesList;
    
    //Les Tableaux de transformation pour le Codage et decodage
    private  HashMap<Character, String> codageMap;
    private  HashMap<String, Character> decodageMap;
    
    //Pour l'�criture de l'arbre
    private PrintWriter motifs ;
    
    
    public Huffman(String m) throws FileNotFoundException{
    	super();
    	codageMap = new HashMap<Character, String>();
        decodageMap = new HashMap<String, Character>();
        bytesList = new PriorityQueue<Noeud>(1, new ComparatorFreq());
        motifs = new PrintWriter(m);
    	
    }
   
    
    public void compression(String entree,String sortie) throws IOException{	
    	
    	//tableau de fr�quence
    	int[] freqTab = new int[256];
    	//fen�tre d'interface
        ProgressDialog frame = new ProgressDialog("Creation de l'arbre");
        frame.pack();
        frame.setVisible(true);
        
        
        InputStream inputFile = new FileInputStream(entree);
        long nbOctets = new File(entree).length();
		int data = inputFile.read(), nbOctetsCompte = 0;
																	
		//Cas d'erreur le fichier est vide
		if(nbOctets == 0) {
        	frame.dispose();
			JOptionPane.showMessageDialog(null, "Le fichier est vide " + entree + " le programme va se fermer", "Erreur", JOptionPane.ERROR_MESSAGE);
        	System.exit (0); 
		}
				
		//tant que le fichier d'entr�e � des octets � lire
		while(data != -1) {
			frame.setPourcent((int) (100*nbOctetsCompte/nbOctets));
			nbOctetsCompte++;
			//on compte la frequence de chaque octets
			freqTab[data]++;
			data = inputFile.read();
		}
		
		//On rempli la liste avec chaque caract�res et sa fr�quence
        int nbCarac = 0;
        int i = 0;
        for( i = 0; i < 256 ; i++) {
           if(freqTab[i] != 0){
        	   bytesList.add(new Noeud((char)i,freqTab[i]));
        	   nbCarac++;
           }
            
        }
        
        //Creation de l'arbre, ici la fonction poll() retirer la t�te de liste 
        //et re-organise l'ordre de la liste en fonction
        for( i = 0; i < nbCarac-1; i++) {
        	
        	//Principe de huffman, on enl�ve les deux noeuds avec les plus petites fr�quences pour 
        	//en faire un noeud additionnant leur deux fr�quence et ayant pour feuilles les deux noeud pr�c�dents
            Noeud currentNoeud = new Noeud();
            currentNoeud.setGauche(bytesList.poll()); 
            currentNoeud.setDroite(bytesList.poll()); 
            currentNoeud.setFreq(currentNoeud.getGauche().getFreq() + currentNoeud.getDroite().getFreq()); 
            bytesList.add(currentNoeud);
        }
        
        //Ici l'arbre est cr�e nous allons cr�e un codage de mani�re recursif en parcourant l'arbre
        if(nbCarac != 1) postorder(bytesList.poll(), new String());
        //Si le fichier d'entr�e ne contenait qu'un seul caract�re on lui donne directement une valeur
        else {
        	Noeud n = bytesList.poll();
        	String code = "0";
        	System.out.println(n.getCarac() + " -> " + code);
        	codageMap.put(n.getCarac(), code);
            decodageMap.put(code, n.getCarac());
        }
        
        motifs.close();
        
        String compresse = new String();
        frame.setTitle("Codage");
        frame.setPourcent(0);
        inputFile.close();
        inputFile = new FileInputStream(entree);
 
        data = inputFile.read(); 
        nbOctetsCompte = 0;
        
        //L'arbre est cr�e on peux compresser le fichier en utilsant les codages correspondant � chaque caract�res
        while(data != -1) {
        	//pour chaque octet compresse recoit le codage de l'octet compress�
        	compresse = compresse + codageMap.get((char)data);
			frame.setPourcent((int) (100*nbOctetsCompte/nbOctets));
			nbOctetsCompte++;
			data = inputFile.read();
		}
        frame.dispose();
        
        String info = "";
        info += "Fichier d'entr�e : " + entree;
        if(compresse.length() <8 ) info += "\nTaille du fichier de sortie : " + compresse.length() + " bits\n" ;
        else info += "\nTaille du fichier de sortie : " + compresse.length()/8 + " octets\n" ;
        info += "Taille du fichier d'entr�e : " + nbOctets + " octets\n";
        info += "Taux de compression : " + (double)((100-(100*compresse.length()/(nbOctets*8))))+"%\n";
        info += "Fichier de sortie : " + sortie;
        JOptionPane.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);
        
        //sauvegarde du compress� dans un fichier texte pour la visibilit�
        PrintWriter outputFile = new PrintWriter(sortie);
        outputFile.println(compresse);
        outputFile.close();
        
        
        
        //Fonction de decompression pour test, le codage devra �tre impl�ment� en header si possible plus tard
      
//        String tmp = new String();
//        String decompresse = new String();
// 
//        for(i = 0; i < compresse.length(); i++) {
//            tmp = tmp + compresse.charAt(i);
// 
//            if(decodageMap.containsKey(tmp)) {
//                decompresse = decompresse + decodageMap.get(tmp);
//                tmp = new String();
//            }
//        }
//        System.out.println(decompresse);
        inputFile.close();
    }

	//Cette fonction cr�e le codage en parcourant recursivement l'arbre, il sera �crit dans le fichier motifs.txt
    private void postorder(Noeud n, String s) {
        if(n == null)
            return;
        
        //si ce n'est pas un noeud le code du caract�re de gauche prend 0 celui de droit 1 et ainsi de suite
        postorder(n.getGauche(), s+"0");
        postorder(n.getDroite(), s+"1");
 
        if(n.getCarac() != '\0') {
        	//si on est au bout rempli les Map avec le code correspondant
        	motifs.println("\'" + n.getCarac() + "\' -> " + s);
            codageMap.put(n.getCarac(), s);
            decodageMap.put(s, n.getCarac());
        }
    }

    private class ComparatorFreq implements Comparator<Noeud> {
   	 
        public int compare(Noeud a, Noeud b) {
            int freqA = a.getFreq();
            int freqB = b.getFreq();
     
            return freqA-freqB;
        }
     
    }
}


 

 
