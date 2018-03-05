package dames;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Plateau extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int TAILLE = 10;

	private Case caseActive;
	private int nbPieceNoir;
	private int nbPieceBlanc;
	private boolean tourNoir;
	private Couleur end = null;

	public boolean isTourNoir() {
		return tourNoir;
	}

	/**
	 * Création d'un plateau 10x10 avec les pions
	 */
	public Plateau() {
		tourNoir = false;
		nbPieceBlanc = 0;
		nbPieceNoir = 0;

		// création d'un GridLayout pour pouvoir représenter le plateau
		setLayout(new GridLayout(TAILLE, TAILLE));
		// On remplis cette grille de couleurs
		for (int i = 0; i < TAILLE; i++) {
			for (int j = 0; j < TAILLE; j++) {
				if ((j % 2 == 0 && i % 2 == 0) || (j % 2 != 0 && i % 2 != 0)) {

					ajouterCase(Couleur.MARRON);
				} else {
					ajouterCase(Couleur.BLANC);
				}
			}
		}
		// Créer les pions
		init();
	}

	/**
	 * ajoute une case
	 * 
	 * @param couleur
	 */
	private void ajouterCase(Couleur couleur) {
		// definit la case
		Case case1 = new Case(couleur);
		// créer un Listener pour la case
		case1.addMouseListener(new ListenerCase(case1, this));
		// Ajout de la case dans la liste des composants
		add(case1);
	}

	/**
	 * Création d'un pion. Monte = sens du jeu pour le pion
	 * 
	 * @param couleur
	 * @param monte
	 * @return pion
	 */
	private Pion creerPion(Couleur couleur, boolean monte) {
		Pion pion = new Pion(couleur, monte);
		pion.addMouseListener(new ListenerPion(pion, this));
		return pion;
	}

	/**
	 * initialise les cases avec un pion
	 */
	private void init() {
		for (int j = 0; j < TAILLE * 4; j += 2) {
			getCase(j).add(creerPion(Couleur.MARRON, false));
			this.nbPieceNoir++;
			getCase(TAILLE * TAILLE - j - 1).add(creerPion(Couleur.BLANC, true));
			this.nbPieceBlanc++;
			if (j == 8)
				j++;
			if (j == 19)
				j--;
			if (j == 28)
				j++;
		}
	}

	/**
	 * obtient la case de la liste des composants
	 * 
	 * @param i
	 * @param j
	 * @return la case
	 */
	public Case getCase(int i, int j) {
		return (Case) getComponent(j + i * TAILLE);
	}

	/**
	 * Retourne la ieme case
	 * 
	 * @param i
	 * @return
	 */
	public Case getCase(int i) {
		return (Case) getComponent(i);
	}

	/**
	 * permet d'afficher les possibilitées d'un pion selectionné
	 * 
	 * @param p
	 */
	public void afficherPossibilitees(Pion p) {
		if ((p.getCouleur().equals(Couleur.MARRON) && tourNoir)
				|| (p.getCouleur().equals(Couleur.BLANC) && !tourNoir)) {
			int i = 0;
			int j = 0;
			// parcours tout le plateau
			for (int k = 0; k < TAILLE * TAILLE; k++) {
				getCase(k).setSelectionnee(false);
				// si la case correspond au Pion p
				if (getCase(k).getComponentCount() != 0 && getCase(k).getComponent(0).equals(p)) {
					caseActive = getCase(k);
					i = k / TAILLE;
					j = k % TAILLE;
				}
			}
			selectionnerCases(i, j, p.getCouleur());
		}
	}

	/**
	 * Rend selectionnable une case pour pouvoir jouer
	 * 
	 * @param i
	 * @param j
	 * @param couleur
	 */
	public void selectionnerCases(int i, int j, Couleur couleur) {
		Pion pion = (Pion) (getCase(i, j).getComponent(0));
		if (pion.isDame()) {
			// Une dame peut aller de 1 en 1 en avant ou en arriere

			if (i - 1 >= 0 && j - 1 >= 0 && getCase(i - 1, j - 1).getComponentCount() == 0) {
				getCase(i - 1, j - 1).setSelectionnee(true);
				// si un pion en bas a gauche et on peut le "manger"
			} else if (i - 2 >= 0 && j - 2 >= 0 && getCase(i - 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i - 2, j - 2).setSelectionnee(true);
			}
			// a droite
			if (i - 1 >= 0 && j + 1 < TAILLE && getCase(i - 1, j + 1).getComponentCount() == 0) {
				getCase(i - 1, j + 1).setSelectionnee(true);
			} else if (i - 2 >= 0 && j + 2 < TAILLE && getCase(i - 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i - 2, j + 2).setSelectionnee(true);
			}
			if (i + 1 < TAILLE && j + 1 < TAILLE && getCase(i + 1, j + 1).getComponentCount() == 0) {
				getCase(i + 1, j + 1).setSelectionnee(true);
			} else if (i + 2 < TAILLE && j + 2 < TAILLE && getCase(i + 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i + 2, j + 2).setSelectionnee(true);
			}
			if (i + 1 < TAILLE && j - 1 >= 0 && getCase(i + 1, j - 1).getComponentCount() == 0) {
				getCase(i + 1, j - 1).setSelectionnee(true);
			} else if (i + 2 < TAILLE && j - 2 >= 0 && getCase(i + 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i + 2, j - 2).setSelectionnee(true);
			}
		} else
		// Si pion noir
		if (pion.isMonte()) {
			// si on est pas sur un bord à gauche et la case est vide ( pas de pion dessus )
			if (i - 1 >= 0 && j - 1 >= 0 && getCase(i - 1, j - 1).getComponentCount() == 0) {
				getCase(i - 1, j - 1).setSelectionnee(true);
				// si un pion en bas a gauche et on peut le "manger"
			} else if (i - 2 >= 0 && j - 2 >= 0 && getCase(i - 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i - 2, j - 2).setSelectionnee(true);
			}
			// a droite
			if (i - 1 >= 0 && j + 1 < TAILLE && getCase(i - 1, j + 1).getComponentCount() == 0) {
				getCase(i - 1, j + 1).setSelectionnee(true);
			} else if (i - 2 >= 0 && j + 2 < TAILLE && getCase(i - 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i - 2, j + 2).setSelectionnee(true);
			}
		} else {
			// pour les blanc
			if (i + 1 < TAILLE && j + 1 < TAILLE && getCase(i + 1, j + 1).getComponentCount() == 0) {
				getCase(i + 1, j + 1).setSelectionnee(true);
			} else if (i + 2 < TAILLE && j + 2 < TAILLE && getCase(i + 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i + 2, j + 2).setSelectionnee(true);
			}
			if (i + 1 < TAILLE && j - 1 >= 0 && getCase(i + 1, j - 1).getComponentCount() == 0) {
				getCase(i + 1, j - 1).setSelectionnee(true);
			} else if (i + 2 < TAILLE && j - 2 >= 0 && getCase(i + 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				getCase(i + 2, j - 2).setSelectionnee(true);
			}

		}
	}

	/**
	 * Déplace un pion sur la case demandé
	 * 
	 * @param case1
	 */
	public void deplacer(Case case1) {
		case1.add(caseActive.getComponent(0));
		for (int k = 0; k < TAILLE * TAILLE; k++) {
			getCase(k).setSelectionnee(false);
		}
		if (Math.abs(getLigne(case1) - getLigne(caseActive)) == 2) {
			int i = (getLigne(case1) + getLigne(caseActive)) / 2;
			int j = (getColonne(case1) + getColonne(caseActive)) / 2;
			getCase(i, j).removeAll();
			getCase(i, j).validate();
			getCase(i, j).repaint();
			if (tourNoir) {
				nbPieceBlanc--;
				if (nbPieceBlanc == 0) {
					JOptionPane.showMessageDialog(null, "Les noirs gagnent !", "InfoBox: Fin de partie",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				nbPieceNoir--;
				if (nbPieceNoir == 0) {
					JOptionPane.showMessageDialog(null, "Les blancs gagnent !", "InfoBox: Fin de partie",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		tourNoir = !tourNoir;
		caseActive.removeAll();
		caseActive.repaint();
		caseActive = null;
		case1.repaint();

		if (getLigne(case1) == 0) {
			Pion p = (Pion) (case1.getComponent(0));
			p.setMonte(false);
			p.setDame(true);
		}
		if (getLigne(case1) == TAILLE - 1) {
			Pion p = (Pion) (case1.getComponent(0));
			p.setMonte(true);
			p.setDame(true);
		}

	}

	int getLigne(Case case1) {
		int res = 0;
		for (int i = 0; i < TAILLE * TAILLE; i++) {
			if (getCase(i).equals(case1)) {
				res = i / TAILLE;
			}

		}
		return res;
	}

	int getColonne(Case case1) {
		int res = 0;
		for (int i = 0; i < TAILLE * TAILLE; i++) {
			if (getCase(i).equals(case1)) {
				res = i % TAILLE;
			}
		}
		return res;
	}

	public List<Pion> getListPionJouable(Couleur couleur) {
		List<Pion> res = new ArrayList<>();
		Pion p;
		// Parcours du plateau
		for (int k = 0; k < TAILLE * TAILLE; k++) {

			// Vérification que la case n'est pas vide
			int c = getCase(k).getComponentCount();
			if (!(c == 0)) {
				// Vérification de la couleur
				p = (Pion) getCase(k).getComponent(0);
				if (p.getCouleur() == couleur) {

					// Vérification de la possibilité de déplacement
					if (deplacementPossible(getLigne(getCase(k)), getColonne(getCase(k)), couleur)) {
						res.add(p);
					}

				}
			}
		}
		return res;
	}

	public boolean deplacementPossible(int i, int j, Couleur couleur) {
		Pion pion = (Pion) (getCase(i, j).getComponent(0));
		if (pion.isDame()) {
			// Une dame peut aller de 1 en 1 en avant ou en arriere

			if (i - 1 >= 0 && j - 1 >= 0 && getCase(i - 1, j - 1).getComponentCount() == 0) {
				return true;
				// si un pion en bas a gauche et on peut le "manger"
			} else if (i - 2 >= 0 && j - 2 >= 0 && getCase(i - 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}
			// a droite
			if (i - 1 >= 0 && j + 1 < TAILLE && getCase(i - 1, j + 1).getComponentCount() == 0) {
				return true;
			} else if (i - 2 >= 0 && j + 2 < TAILLE && getCase(i - 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}
			if (i + 1 < TAILLE && j + 1 < TAILLE && getCase(i + 1, j + 1).getComponentCount() == 0) {
				return true;
			} else if (i + 2 < TAILLE && j + 2 < TAILLE && getCase(i + 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}
			if (i + 1 < TAILLE && j - 1 >= 0 && getCase(i + 1, j - 1).getComponentCount() == 0) {
				return true;
			} else if (i + 2 < TAILLE && j - 2 >= 0 && getCase(i + 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}
		} else
		// Si pion noir
		if (pion.isMonte()) {
			// si on est pas sur un bord à gauche et la case est vide ( pas de pion dessus )
			if (i - 1 >= 0 && j - 1 >= 0 && getCase(i - 1, j - 1).getComponentCount() == 0) {
				return true;
				// si un pion en bas a gauche et on peut le "manger"
			} else if (i - 2 >= 0 && j - 2 >= 0 && getCase(i - 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}
			// a droite
			if (i - 1 >= 0 && j + 1 < TAILLE && getCase(i - 1, j + 1).getComponentCount() == 0) {
				return true;
			} else if (i - 2 >= 0 && j + 2 < TAILLE && getCase(i - 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}
		} else {
			// pour les blanc
			if (i + 1 < TAILLE && j + 1 < TAILLE && getCase(i + 1, j + 1).getComponentCount() == 0) {
				return true;
			} else if (i + 2 < TAILLE && j + 2 < TAILLE && getCase(i + 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}
			if (i + 1 < TAILLE && j - 1 >= 0 && getCase(i + 1, j - 1).getComponentCount() == 0) {
				return true;
			} else if (i + 2 < TAILLE && j - 2 >= 0 && getCase(i + 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 
	 * @param pion
	 * @return List<Case>
	 */
	public List<Case> getDeplacementPossible(Pion pion) {
		List<Case> res = new ArrayList<>();
		int i = getLigne((Case) pion.getParent());
		int j = getColonne((Case) pion.getParent());
		Couleur couleur = ((Case) pion.getParent()).getCouleur();
		if (pion.isDame()) {
			// Une dame peut aller de 1 en 1 en avant ou en arriere

			if (i - 1 >= 0 && j - 1 >= 0 && getCase(i - 1, j - 1).getComponentCount() == 0) {
				res.add(getCase(i - 1, j - 1));
				// si un pion en bas a gauche et on peut le "manger"
			} else if (i - 2 >= 0 && j - 2 >= 0 && getCase(i - 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i - 2, j - 2));
			}
			// a droite
			if (i - 1 >= 0 && j + 1 < TAILLE && getCase(i - 1, j + 1).getComponentCount() == 0) {
				res.add(getCase(i - 1, j + 1));
			} else if (i - 2 >= 0 && j + 2 < TAILLE && getCase(i - 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i - 2, j + 2));
			}
			if (i + 1 < TAILLE && j + 1 < TAILLE && getCase(i + 1, j + 1).getComponentCount() == 0) {
				res.add(getCase(i + 1, j + 1));
			} else if (i + 2 < TAILLE && j + 2 < TAILLE && getCase(i + 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i + 2, j + 2));
			}
			if (i + 1 < TAILLE && j - 1 >= 0 && getCase(i + 1, j - 1).getComponentCount() == 0) {
				res.add(getCase(i + 1, j - 1));
			} else if (i + 2 < TAILLE && j - 2 >= 0 && getCase(i + 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i + 2, j - 2));
			}
		} else
		// Si pion noir
		if (pion.isMonte()) {
			// si on est pas sur un bord à gauche et la case est vide ( pas de pion dessus )
			if (i - 1 >= 0 && j - 1 >= 0 && getCase(i - 1, j - 1).getComponentCount() == 0) {
				res.add(getCase(i - 1, j - 1));
				// si un pion en bas a gauche et on peut le "manger"
			} else if (i - 2 >= 0 && j - 2 >= 0 && getCase(i - 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i - 2, j - 2));
			}
			// a droite
			if (i - 1 >= 0 && j + 1 < TAILLE && getCase(i - 1, j + 1).getComponentCount() == 0) {
				res.add(getCase(i - 1, j + 1));
			} else if (i - 2 >= 0 && j + 2 < TAILLE && getCase(i - 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i - 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i - 2, j + 2));
			}
		} else {
			// pour les blanc
			if (i + 1 < TAILLE && j + 1 < TAILLE && getCase(i + 1, j + 1).getComponentCount() == 0) {
				res.add(getCase(i + 1, j + 1));
			} else if (i + 2 < TAILLE && j + 2 < TAILLE && getCase(i + 2, j + 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j + 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i + 2, j + 2));
			}
			if (i + 1 < TAILLE && j - 1 >= 0 && getCase(i + 1, j - 1).getComponentCount() == 0) {
				res.add(getCase(i + 1, j - 1));
			} else if (i + 2 < TAILLE && j - 2 >= 0 && getCase(i + 2, j - 2).getComponentCount() == 0
					&& !((Pion) (getCase(i + 1, j - 1).getComponent(0))).getCouleur().equals(couleur)) {
				res.add(getCase(i + 2, j - 2));
			}

		}
		// Map<Pion, List<Case>> map = new HashMap<>();
		// map.put(pion, res);
		return res;

	}

	public Plateau deplacerPionPlateau(Pion p, Case c) {
		Plateau plat = this;
		plat.deplacerUnPion(p, c);
		return plat;
	}

	/**
	 * Déplace un pion à la case demandée. A utiliser avec deplacerPionPlateau
	 * 
	 * @param p
	 *            Pion
	 * @param c
	 *            Case
	 */
	public void deplacerUnPion(Pion p, Case c) {
		// get la case du pion
		Case caseDuPion = (Case) p.getParent();
		// Ajoute le pion à la case
		c.add(p);

		// on effectue les traitements
		if (Math.abs(getLigne(c) - getLigne(caseDuPion)) == 2) {
			int i = (getLigne(c) + getLigne(caseDuPion)) / 2;
			int j = (getColonne(c) + getColonne(caseDuPion)) / 2;
			getCase(i, j).removeAll();
			getCase(i, j).validate();
			getCase(i, j).repaint();
			if (tourNoir) {
				nbPieceBlanc--;
				if (nbPieceBlanc == 0) {
					setEnd(Couleur.MARRON);
				}
			} else {
				nbPieceNoir--;
				if (nbPieceNoir == 0) {
					setEnd(Couleur.BLANC);
				}
			}
		}
		tourNoir = !tourNoir;
		caseDuPion.removeAll();
		caseDuPion.repaint();
		caseDuPion = null;
		c.repaint();

		if (getLigne(c) == 0) {
			Pion pion = (Pion) (c.getComponent(0));
			pion.setMonte(false);
			pion.setDame(true);
		}
		if (getLigne(c) == TAILLE - 1) {
			Pion pion = (Pion) (c.getComponent(0));
			pion.setMonte(true);
			pion.setDame(true);
		}

	}

	public void jouerCoup(Couleur couleur) {
		List<Pion> pions = new ArrayList<>();
		pions = this.getListPionJouable(couleur);
		Map<Pion, List<Case>> coupAJouerTotal = new HashMap<>();
		for (Pion pion : pions)
			coupAJouerTotal.put(pion, this.getDeplacementPossible(pion));

	}

	public Couleur getEnd() {
		return end;
	}

	public void setEnd(Couleur end) {
		this.end = end;
	}

	public List<Plateau> jouerCoupsPossibles() {
		// Liste pions jouables
		List<Pion> pions = new ArrayList<>();
		// Liste coups jouable par pion
		Map<Pion, List<Case>> coupAJouerTotal = new HashMap<>();

		List<Plateau> plats = new ArrayList<>();

		// get list des pion jouables
		if (this.isTourNoir()) {
			pions = this.getListPionJouable(Couleur.MARRON);
		} else {
			pions = this.getListPionJouable(Couleur.BLANC);
		}
		// coup par pion
		for (Pion pion : pions)
			coupAJouerTotal.put(pion, this.getDeplacementPossible(pion));
		// ajout d'un etage à l'arbre comportant les coups jouer ( obtenu grace aux
		// coups par pion au dessus)
		for (Pion pion : pions) {
			List<Case> c = coupAJouerTotal.get(pion);
			if (c.get(0) == null) {
				return null;
			}
			for (Case c2 : c) {
				plats.add(this.deplacerPionPlateau(pion, c2));
			}

		}
		return plats;
	}
}
