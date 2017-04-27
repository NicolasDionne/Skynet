package ai.coeur.entree;

import java.util.ArrayList;

import ai.coeur.Lien;

public class Et extends FonctionEntree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2828005308549763288L;

	@Override
	public double getSortie(ArrayList<Lien> liensEntree) {
		double val;
		if (0 == liensEntree.size()) {
			val = 0;
		} else {
			boolean sortie = true;
			for (Lien lien : liensEntree) {
				sortie = (0.5 < lien.getEntree()) && sortie;
			}
			val = sortie ? 1 : 0;
		}
		return val;
	}

}
