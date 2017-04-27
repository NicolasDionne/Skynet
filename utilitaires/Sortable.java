package utilitaires;

/**
 * L'interface <code>Sortable</code> est utilisée pour que la classe qui
 * l'implémente puisse retourner une valeur sous forme de int. À ce moment, la
 * méthode qui triera un vecteur ou un objet de cette classe n'aura pas besoin
 * de comparer les objet contenus dans le vecteur ou le tableau pour le trier.
 * <P>
 * Cependant, pour trier le vecteur ou le tableau, il faudra utiliser une
 * méthode pour trier le vecteur ou le tableau autre que celle de base au lieu
 * de celle par défaut, par exemple utiliser
 * <code>vecteur = ClasseQuiSort.sort(liste);</code> ou
 * <code>ClasseQuiSort.sort(liste);</code> au lieu de
 * <code>vecteur.sort();</code>
 * <P>
 * <code>Sortable</code> est surtout pratique si le vecteur ou le tableau à
 * classer contient plusieurs objets qui sont des instances de sous-classes d'un
 * même classe parent ou s'il est plus facile de classer le vecteur ou le
 * tableau comme ça.
 */
public interface Sortable {

	/**
	 * Retoourne la valeur que donne l'objet à classer.
	 * 
	 * @return <code>int</code> la valeur qui sera utilisée pour classer le
	 *         vecteur ou le tableau.
	 */
	public int getValeurLorsSort();

}
