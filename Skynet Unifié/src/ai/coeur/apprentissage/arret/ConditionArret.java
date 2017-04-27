package ai.coeur.apprentissage.arret;

/**
 * Interface pour les conditions d'arrêts des
 * <code>ApprentissageIteratif</code>.
 * <P>
 * Les classes implémentant cette interface n'ont qu'à implémenter une seule
 * méthode.
 */
public interface ConditionArret {

	/**
	 * Vérifie si la condition d'arrêt est remplie.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si la condition est
	 *         remplie, sinon <code>false</code>.
	 */
	public boolean estAtteint();
}
