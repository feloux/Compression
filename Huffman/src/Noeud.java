
public class Noeud {
 
    private char carac;
    private int freq;
    private Noeud left, right;
 
    public Noeud(char a, int f) {
        carac = a;
        freq = f;
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

	public Noeud getLeft() {
		return left;
	}

	public void setLeft(Noeud left) {
		this.left = left;
	}

	public Noeud getRight() {
		return right;
	}

	public void setRight(Noeud right) {
		this.right = right;
	}

	public Noeud() {
 
    }
 
    public String toString() {
        return carac + " " + freq;
    }
 
}