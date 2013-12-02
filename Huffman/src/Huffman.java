import java.util.*;
import java.io.*;
 
public class Huffman {  // Written by: Yancy Vance Paredes, October 17, 2013
 
    public static PriorityQueue<Noeud> q;
    public static HashMap<Character, String> charToCode;
    public static HashMap<String, Character> codeToChar;
    
    public static void main(String[] args) throws IOException {
    	
    	ProgressDialog frame = new ProgressDialog();
        frame.pack();
        frame.setVisible(true);
    	
        // Read all the contents of the file
        String text = "";// = new Scanner(new File("test.txt")).useDelimiter("\\A").next(); // nextLine(); // change it if you only want to read a single line without the new line character.

        //String compressed = "";
        // Count the frequency of all the characters
        HashMap<Character, Integer> dict = new HashMap<Character, Integer>();
        String fichier = "./vider.wav";
        InputStream inputstream = new FileInputStream(fichier);
		
        long octets = new File(fichier).length();
		int data = inputstream.read(), nbOctetsCompte = 0,tmp = 0,tmp2 = 0;
		//System.out.println("0%");
		while(data != -1) {
			
			char a = (char)data;
			text += a;
			//System.out.println(nbOctetsCompte/octets);
			
			/** Pourcentage */
			tmp2 = (int) (nbOctetsCompte/octets);
			if(tmp < tmp2) {System.out.println(tmp2+"%");frame.setPourcent(tmp2);}
			tmp = tmp2;
			nbOctetsCompte+=100;
			
			
			
			data = inputstream.read();
			//System.out.println(nbOctetsCompte/100);
			
		  if(dict.containsKey(a))
              dict.put(a, dict.get(a)+1);
          else
              dict.put(a, 1);
		}

 
        // Create a forest (group of trees) by adding all the nodes to a priority queue
        q = new PriorityQueue<Noeud>(100, new ComparatorFreq());
        int n = 0;
 
        for(Character c : dict.keySet()) {
            q.add(new Noeud(c, dict.get(c)));
            n++;
        }
 
        // Identify the root of the tree
      //  Noeud root = huffman(n);
        
        for(int i = 0; i < n-1; i++) {
            Noeud z = new Noeud();
            z.setLeft(q.poll()); 
            z.setRight(q.poll()); 
            z.setFreq(z.getLeft().getFreq() + z.getRight().getFreq()); 
            q.add(z);
        }
        
        charToCode = new HashMap<Character, String>();
        codeToChar = new HashMap<String, Character>();
 
        postorder(q.poll(), new String());
        
        // Build the table for the variable length codes
       // buildTable(root);
 
        String compressed = compress(text);
        System.out.println("The compressed used a total of " + compressed.length() + " bits");
        System.out.println(compressed);
 
        String decompressed = decompress(compressed);
        System.out.println("The original text used a total of " + decompressed.length() + " characters équilavent à " + decompressed.length()*8 +" bits");
       // System.out.println(decompressed);
        
        
        System.out.println("Taux de compression : " + (double)((100-(100*compressed.length()/(decompressed.length()*8))))+"%");
        saveToFile(compressed);
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
    public static void postorder(Noeud n, String s) {
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
    public static String compress(String s) {
        String c = new String();
       
        for(int i = 0; i < s.length(); i++)
        	
			c = c + charToCode.get(s.charAt(i));
        
        return c;
    }
 
    // This method assumes that the tree and dictionary are already built
    public static String decompress(String s) {
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
 
    public static void saveToFile(String l) throws FileNotFoundException {
        PrintWriter oFile = new PrintWriter("output.txt");
 
//        for(String s : codeToChar.keySet())
//            oFile.println(s + "->" + codeToChar.get(s));
 
        oFile.println(l);
 
        oFile.close();
    }
 
}
 

 
