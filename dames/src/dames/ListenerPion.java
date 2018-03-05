package dames;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ListenerPion implements MouseListener {
	
	private Plateau plateau;
	private Pion pion;
	
	public ListenerPion(Pion pion, Plateau plateau){
		this.plateau=plateau;
		this.pion=pion;
	}

	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
	
	}

	public void mouseExited(MouseEvent arg0) {
		
	}
	
	/**
	 * Si clique sur un pion, on affiche ses possibilitées de jeu
	 */
	public void mousePressed(MouseEvent arg0) {
		plateau.afficherPossibilitees(pion);
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

}
