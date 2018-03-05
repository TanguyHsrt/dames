import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dames.Case;
import dames.Couleur;
import dames.Pion;
import dames.Plateau;

class testIA {

	@Test
	void test() {

		// Plateau de départ
		Plateau p = new Plateau();
		// Liste pions jouables
		List<Pion> pions = new ArrayList<>();
		// Liste coups jouable par pion
		Map<Pion, List<Case>> coupAJouerTotal = new HashMap<>();
		// Arbre de l'algo MinMax
		Map<Plateau, List<Plateau>> arbre = new HashMap<>();

		List<Plateau> plats = new ArrayList<>();
		List<Plateau> tmp = new ArrayList<>();

		// get list des pion jouables
		if (p.isTourNoir()) {
			pions = p.getListPionJouable(Couleur.MARRON);
		} else {
			pions = p.getListPionJouable(Couleur.BLANC);
		}
		// coup par pion
		for (Pion pion : pions)
			coupAJouerTotal.put(pion, p.getDeplacementPossible(pion));
		// ajout d'un etage à l'arbre comportant les coups jouer ( obtenu grace aux
		// coups par pion au dessus)
		for (Pion pion : pions) {
			List<Case> c = coupAJouerTotal.get(pion);
			for (Case c2 : c) {
				plats.add(p.deplacerPionPlateau(pion, c2));
				// System.out.println(c2);
			}
			arbre.put(p, plats);
			tmp = plats;
			plats.clear();
			// System.out.println("Fin deplacement possible pour : " + pion);
		}
		// Maintenant on recommence sur les plats
		for(Plateau p3 : plats) {
			while (p3!= null) {
				pions.clear();
				coupAJouerTotal.clear();
	
				for (Plateau p2 : plats) {
					if (p.isTourNoir()) {
						pions = p2.getListPionJouable(Couleur.MARRON);
					} else {
						pions = p2.getListPionJouable(Couleur.BLANC);
					}
					// coup par pion
					for (Pion pion : pions)
						coupAJouerTotal.put(pion, p.getDeplacementPossible(pion));
					// ajout d'un etage à l'arbre comportant les coups jouer ( obtenu grace aux
					// coups par pion au dessus)
					for (Pion pion : pions) {
						List<Case> c = coupAJouerTotal.get(pion);
						for (Case c2 : c) {
							plats.add(p.deplacerPionPlateau(pion, c2));
							// System.out.println(c2);
						}
						arbre.put(p, plats);
						plats.clear();
						// System.out.println("Fin deplacement possible pour : " + pion);
					}
				}
			}
		}
	}

}
/*
 * for (Pion pion : pions) { List<Case> c = coupAJouerTotal.get(pion); for (Case
 * c2 : c) { System.out.println(pion + " Peut aller en " + c2); }
 * System.out.println("Fin deplacement possible pour : " + pion); }
 */