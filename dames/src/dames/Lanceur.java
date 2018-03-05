package dames;

import javax.swing.JFrame;
import javax.swing.UIManager;


public class Lanceur {

	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){}
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		// pour arreter l'application lorsqu'on clique sur la croix rouge
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Place au milieu de l'écran
		frame.setLocationRelativeTo(null);
		// Créer le jeu setVisible après sinon on ne voit pas le jeu
		frame.add(new Plateau());
		frame.setVisible(true);
		
		
		
	}

}
