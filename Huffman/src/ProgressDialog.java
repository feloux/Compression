
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Afficher une barre de progression dans un JFrame
 */
public class ProgressDialog extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressbar;

    public ProgressDialog(String title) {
        super();
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());
        progressbar = new JProgressBar(0, 100);
        progressbar.setValue(0);
        progressbar.setStringPainted(true);
        pane.add(progressbar);
        setContentPane(pane);
    }

    /**
     * Afficher la progression de tache
     */

   
    public void setPourcent(int position){
    	progressbar.setValue(position);
    	
    	
    	
    }

   
}