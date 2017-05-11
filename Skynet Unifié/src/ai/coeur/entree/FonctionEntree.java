package ai.coeur.entree;

import java.io.Serializable;
import java.util.ArrayList;

import ai.coeur.Lien;
import ai.coeur.Neurone;

/**
 * Classe de base pour les fonctions d'entré.
 * <P>
 * Une fonction d'entrée sert à déterminer la valeur d'entrée de la
 * <code>Neurone</code> qui possède la fonction d'entrée.
 * 
 * @see Neurone
 */
public abstract class FonctionEntree implements Serializable {

	private static final long serialVersionUID = 4694723480230488445L;

	/**
	 * Calcule la valeur d'entré de la <code>Neurone</code> selon les liens
	 * d'entrée de la neurone.
	 * 
	 * @param liensEntree
	 *            <code>ArrayList&ltLien&gt</code>, la liste des liens d'entré
	 *            de la <code>Neurone</code>.
	 * @return <code>double</code>, la valeur d'entrée de la
	 *         <code>Neurone</code>.
	 */
	public abstract double getSortie(ArrayList<Lien> liensEntree);

}
