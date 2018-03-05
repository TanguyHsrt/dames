package dames;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ListenerCase implements MouseListener{
	
	private Case case1;
	private Plateau plateau;

	/**
	 * param du listener, associant le plateau et la case
	 * Seulement l'event press est implementé
	 * @param case1
	 * @param plateau
	 */
	public ListenerCase(Case case1, Plateau plateau) {
		super();
		this.case1 = case1;
		this.plateau = plateau;
	}


	public void mouseClicked(MouseEvent arg0) {
	
	}


	public void mouseEntered(MouseEvent arg0) {
		
	}


	public void mouseExited(MouseEvent arg0) {
		
	}

	/**
	 * Si case selectionnée on la déplace 
	 */
	public void mousePressed(MouseEvent arg0) {
		if(case1.isSelectionnee()){
			plateau.deplacer(case1);
		}
	}


	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	

}
