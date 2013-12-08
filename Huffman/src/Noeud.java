
public class Noeud {
 
    private char carac;
    private int freq;
    private Noeud gauche, droite;
 
    public Noeud(char a, int f) {
        carac = a;
        freq = f;
    }
    
    public Noeud() {
    	 
    }
 
    public char getCarac() {
		return carac;
	}

	public void setCarac(char carac) {
		this.carac = carac;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public Noeud getGauche() {
		return gauche;
	}

	public void setGauche(Noeud gauche) {
		this.gauche = gauche;
	}

	public Noeud getDroite() {
		return droite;
	}

	public void setDroite(Noeud droite) {
		this.droite = droite;
	}
 
}