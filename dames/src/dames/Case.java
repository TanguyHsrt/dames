package dames;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;

import javax.swing.JPanel;


public class Case extends JPanel {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Couleur couleur;
	private boolean selectionnee;

	public Case(Couleur couleur){
		setLayout(new GridLayout(1,0));
		this.couleur=couleur;
		initCouleur();
	}

	public Couleur getCouleur() {
		return couleur;
	}
	
	/**
	 * Permet de savoir si une case est "selectionnable"
	 * @return
	 */
	public boolean isSelectionnee() {
		return selectionnee;
	}

	/**
	 * change le statut de selectionné de la case
	 * @param selectionnee
	 */
	public void setSelectionnee(boolean selectionnee) {
		this.selectionnee = selectionnee;
		if(selectionnee){
			setBackground(Color.BLUE);
			setForeground(Color.LIGHT_GRAY);
		}
		else {
			initCouleur();
		}
	}
	/**
	 * initialisation de la couleur de la case
	 */
	private void initCouleur(){
		switch(couleur){
		case BLANC :
			setBackground(new Color(235,199,158));
			setForeground(new Color(200, 200, 200));
			break;
		case MARRON :
			setBackground(new Color(104,59,16));
			setForeground(new Color(20, 20, 20));
			break;
		}
	}
	@Override
	public String toString() {
		Plateau p = (Plateau) this.getParent();
		return "Case en : [ " + p.getLigne(this) + ", " + p.getColonne(this) + " ] et de couleur : " + this.getCouleur() ;
	}
	
/*	@Override
	public void paintComponent(Graphics g){
		Paint paint;
		Graphics2D g2d;
		if (g instanceof Graphics2D) {
			g2d = (Graphics2D) g;
		}
		else {
			System.out.println("Error");
			return;
		}
		paint = new GradientPaint(0,0, getBackground(), getWidth(), getHeight(), getForeground());
		g2d.setPaint(paint);
		g.fillRect(0, 0, getWidth(), getHeight());
	}*/
}
