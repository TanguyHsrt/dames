import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dames.Case;
import dames.Pion;
import dames.Plateau;

class test2 {
//Objectif + methode
	@Test
	void test() {

		// Plateau de départ
		Plateau p = new Plateau();

		// Arbre de l'algo MinMax
		Map<Plateau, List<Plateau>> arbre = new HashMap<>();

		List<Plateau> plateau = new ArrayList<>();

		plateau = p.jouerCoupsPossibles();
		arbre.put(p, plateau);
		
		List<Plateau> tmp = new ArrayList<>();
		boolean b = true;
		while (b) {
			for (int i = 0; i < arbre.size() - 1; ++i) {
				for (Plateau plats : arbre.get(i)) {
					tmp = plats.jouerCoupsPossibles();
					for (Plateau plat : tmp) {
						
						arbre.put(plat, plat.jouerCoupsPossibles());
					}
				}
			}
			
			
			/*
			for (List<Plateau> plats : arbre.values()) {
				for (Plateau plat : plats) {
					tmp = plat.jouerCoupsPossibles();
					
					arbre.put(plat, tmp);
					System.out.println("plateau ajouté");
				}
				
			
			
				if(tmp.get(0)==null)
					b = false;
			}*/
		}

	}

}
