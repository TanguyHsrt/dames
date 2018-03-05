package dames;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JPanel;

public class Pion extends JPanel {

	private static final long serialVersionUID = 1436178861615738480L;

	private Couleur couleur;
	private boolean monte;
	private boolean dame;

	public Pion(Couleur couleur, boolean monte) {
		this.monte = monte;
		this.couleur = couleur;
		this.dame = false;
		setOpaque(false);
		switch (couleur) {
		case BLANC:
			setForeground(Color.WHITE);
			setBackground(new Color(235, 199, 158));
			break;

		case MARRON:
			setBackground(new Color(104, 59, 16));
			break;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Paint paint;
		Graphics2D g2d;
		if (g instanceof Graphics2D) {
			g2d = (Graphics2D) g;
		} else {
			System.out.println("Error");
			return;
		}
		paint = new GradientPaint(0, 0, getBackground(), getWidth(), getHeight(), getForeground());
		g2d.setPaint(paint);
		g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);

	}

	public Couleur getCouleur() {
		return couleur;
	}

	public boolean isMonte() {
		return monte;
	}

	public void setDame(boolean b) {
		this.dame = b;
	}

	public void setMonte(boolean monte) {
		this.monte = monte;
	}

	public boolean isDame() {

		return this.dame;
	}
	
	@Override
	public String toString() {
		Plateau p = (Plateau) this.getParent().getParent();
		return "Pion en : [ " + p.getLigne((Case) this.getParent()) + ", " + p.getColonne((Case)this.getParent()) + " ] et de couleur : " + this.getCouleur() ;
	}
}
