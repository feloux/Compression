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
 
public class Huffman {  // Written by: Yancy Vance Paredes, October 17, 2013
 
    private  PriorityQueue<Noeud> pq;
    private  HashMap<Character, String> charToCode;
    private  HashMap<String, Character> codeToChar;
    
    public Huffman(){
    	super();
    	charToCode = new HashMap<Character, String>();
        codeToChar = new HashMap<String, Character>();
        pq = new PriorityQueue<Noeud>(1, new ComparatorFreq());
    	
    }
   // public static void main(String[] args) throws IOException {
    public void compression(String entree,String sortie) throws IOException{	
    	
    	
        // Read all the contents of the file
       
        //String compressed = "";
        // Count the frequency of all the characters
       // HashMap<Character, Integer> dict = new HashMap<Character, Integer>();
       
       
        
      //  String text = "";// = new Scanner(new File("test.txt")).useDelimiter("\\A").next(); // nextLine(); // change it if you only want to read a single line without the new line character.
        int[] freqTab = new int[256];
        ProgressDialog frame = new ProgressDialog("Creation de l'arbre");
        frame.pack();
        frame.setVisible(true);
        InputStream inputstream = new FileInputStream(entree);
		
        long nbOctets = new File(entree).length();
		int data = inputstream.read(), nbOctetsCompte = 0;
																			///////////
	
		
		while(data != -1) {

			//char a = (char)data;
			//text += (char)data;
			
			
			/* Pourcentage */
			
			frame.setPourcent((int) (100*nbOctetsCompte/nbOctets));
			
			nbOctetsCompte++;
			
			freqTab[data]++;
			
			data = inputstream.read();
			
//		  if(dict.containsKey(a))
//              dict.put(a, dict.get(a)+1);
//          else
//              dict.put(a, 1);
		}

		if(nbOctets == 0 ) {

        	frame.dispose();
			JOptionPane erreurFrame = new JOptionPane();
        	erreurFrame.showMessageDialog(null, "Le fichier est vide le programme va se fermer", "Erreur", JOptionPane.ERROR_MESSAGE);
        	System.exit (0); 
		}
        // Create a forest (group of trees) by adding all the nodes to a priority queue
        
        int nbCarac = 0;
        int i = 0;
        for( i = 0; i < 256 ; i++) {
           if(freqTab[i] != 0){
        	   pq.add(new Noeud((char)i,freqTab[i]));
        	   nbCarac++;
           }
            
        }
 
        // Identify the root of the tree
      //  Noeud root = huffman(n);
        
        for( i = 0; i < nbCarac-1; i++) {
            Noeud currentNoeud = new Noeud();
            currentNoeud.setLeft(pq.poll()); 
            currentNoeud.setRight(pq.poll()); 
            currentNoeud.setFreq(currentNoeud.getLeft().getFreq() + currentNoeud.getRight().getFreq()); 
            pq.add(currentNoeud);
        }
        
        
        
        if(nbCarac != 1) postorder(pq.poll(), new String());
        else {
        	Noeud n = pq.poll();
        	String s = "0";
        	System.out.println("\'" + n.getCarac() + "\' -> " + s);
        	charToCode.put(n.getCarac(), s);
            codeToChar.put(s, n.getCarac());
        }
        // Build the table for the variable length codes
       // buildTable(root);
        
        String compressed = new String();
        frame.setTitle("Codage");
        frame.setPourcent(0);
        inputstream.close();
        inputstream = new FileInputStream(entree);
 
        data = inputstream.read(); nbOctetsCompte = 0;
        while(data != -1) {

        	compressed = compressed + charToCode.get((char)data);
			
			
			/* Pourcentage */
			
			frame.setPourcent((int) (100*nbOctetsCompte/nbOctets));
			
			nbOctetsCompte++;
			
		
			
			data = inputstream.read();
		}


//        for( i = 0; i < text.length(); i++){
//        	frame.setPourcent((int) (100*i/nbOctets));
//        	compressed = compressed + charToCode.get(text.charAt(i));
//			
//        }
       
        frame.dispose();
        
        JOptionPane infoFrame = new JOptionPane();
        String info = "";
        info += "Fichier d'entrée : " + entree;
        //System.out.println("The compressed used a total of " + compressed.length() + " bits");
        if(compressed.length() <8 ) info += "\nTaille du fichier de sortie : " + compressed.length() + " bits\n" ;
        else info += "\nTaille du fichier de sortie : " + compressed.length()/8 + " octets\n" ;
        //System.out.println(compressed);
 
        //String decompressed = decompress(compressed);
      //  System.out.println("The original text used a total of " + text.length() + " characters équilavent à " + text.length()*8 +" bits");
        info += "Taille du fichier d'entrée : " + nbOctets + " octets\n";
       //  System.out.println(decompressed);
       // System.out.println("Taux de compression : " + (double)((100-(100*compressed.length()/(text.length()*8))))+"%");
        info += "Taux de compression : " + (double)((100-(100*compressed.length()/(nbOctets*8))))+"%\n";
        saveToFile(compressed,sortie);
        info += "Fichier de sortie : " + sortie;
        infoFrame.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);
        
        
        
        inputstream.close();
    }
 
    // This method builds the tree based on the frequency of every characters
//    public static Noeud huffman(int n) {
//      
// 
//        for(int i = 1; i <= n-1; i++) {
//            Noeud z = new Noeud();
//            z.setLeft(q.poll()); 
//            z.setRight(q.poll()); 
//            z.setFreq(z.getLeft().getFreq() + z.getRight().getFreq()); 
//            q.add(z);
//        }
// 
//        return q.poll();
//    }
 
    // This method builds the table for the compression and decompression
//    public static void buildTable(Noeud root) {
//        charToCode = new HashMap<Character, String>();
//        codeToChar = new HashMap<String, Character>();
// 
//        postorder(root, new String());
//    }
 
    

	// This method is used to traverse from ROOT-to-LEAVES
    private void postorder(Noeud n, String s) {
        if(n == null)
            return;
 
        postorder(n.getLeft(), s+"0");
        postorder(n.getRight(), s+"1");
 
        // Visit only nodes with keys
        if(n.getCarac() != '\0') {
            System.out.println("\'" + n.getCarac() + "\' -> " + s);
            charToCode.put(n.getCarac(), s);
            codeToChar.put(s, n.getCarac());
        }
    }
 
    // This method assumes that the tree and dictionary are already built
//    public static String compress(String s,long taille) {
//        String c = new String();
//        ProgressDialog frame = new ProgressDialog("Codage");
//        frame.pack();
//        frame.setVisible(true);
//        for(int i = 0; i < s.length(); i++){
//        	frame.setPourcent((int) (100*i/taille));
//			c = c + charToCode.get(s.charAt(i));
//			
//        }
//        frame.dispose();
//        return c;
//    }
 
    // This method assumes that the tree and dictionary are already built
    public String decompress(String s) {
        String temp = new String();
        String result = new String();
 
        for(int i = 0; i < s.length(); i++) {
            temp = temp + s.charAt(i);
 
            if(codeToChar.containsKey(temp)) {
                result = result + codeToChar.get(temp);
                temp = new String();
            }
        }
 
        return result;
    }
 
    private void saveToFile(String l,String sortie) throws FileNotFoundException {
        PrintWriter oFile = new PrintWriter(sortie);
 
//        for(String s : codeToChar.keySet())
//            oFile.println(s + "->" + codeToChar.get(s));
 
        oFile.println(l);
 
        oFile.close();
    }
    
    private class ComparatorFreq implements Comparator<Noeud> {
   	 
        public int compare(Noeud a, Noeud b) {
            int freqA = a.getFreq();
            int freqB = b.getFreq();
     
            return freqA-freqB;
        }
     
    }
}


 

 
