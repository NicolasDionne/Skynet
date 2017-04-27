package ai.coeur.entree;

import java.io.Serializable;
import java.util.ArrayList;

import ai.coeur.Lien;

public abstract class FonctionEntree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4694723480230488445L;

	public abstract double getSortie(ArrayList<Lien> liensEntree);

}
