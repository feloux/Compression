import java.util.Comparator;


public class ComparatorFreq implements Comparator<Noeud> {
	 
    public int compare(Noeud a, Noeud b) {
        int freqA = a.getFreq();
        int freqB = b.getFreq();
 
        return freqA-freqB;
    }
 
}