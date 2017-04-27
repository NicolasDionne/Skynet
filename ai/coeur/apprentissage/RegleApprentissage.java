package ai.coeur.apprentissage;

import java.io.Serializable;

import ai.coeur.Reseau;
import ai.coeur.donnee.EnsembleDonnees;

/**
 * Classe de base pour tous les algorithmes d'apprentissage de
 * <code>Reseau</code>.
 * <P>
 * <code>Regleapprentissage</code> fourni la base des algorithes d'apprentissage
 * à ces derniers.
 * 
 * @see Reseau
 * @see EnsembleDonnees
 */
public abstract class RegleApprentissage implements Serializable {

	private static final long serialVersionUID = 3752688592274839787L;

	/**
	 * L'instance de <code>Reseau</code> qui possède cette
	 * <code>RegleApprentissage</code>. C'est le réseau à entrainer.
	 */
	protected Reseau<?> reseau;

	/**
	 * Les entrées possible du <code>Reseau</code>. Peut aussi contenir les
	 * sorties voulues.
	 */
	private EnsembleDonnees ensembleEntrainement;

	/**
	 * Drapeau pour arrêter l'apprentissage.
	 */
	private volatile boolean arreterApprentissage = false;

	/**
	 * Crée une nouvelle instance de <code>RegleApprentissage</code>.
	 */
	public RegleApprentissage() {
	}

	/**
	 * Retoune la liste des possibilités d'entrées du <code>Reseau</code>. Peut
	 * aussi contenir les sorties voulues.
	 * 
	 * @return <code>EnsembleDonnees</code>, la liste pour l'apprentissage.
	 */
	public EnsembleDonnees getEnsembleEntrainement() {
		return ensembleEntrainement;
	}

	/**
	 * Définit les possibilités d'entrées du <code>Reseau</code>. Peut aussi
	 * contenir les sorties voulues.
	 * 
	 * @param ensembleEntrainement
	 *            <code>EnsembleDonnees</code>, la liste pour l'apprentissage
	 *            voulue.
	 */
	public void setEnsembleEntrainement(EnsembleDonnees ensembleEntrainement) {
		this.ensembleEntrainement = ensembleEntrainement;
	}

	/**
	 * Retourne le <code>Reseau</code> utilisant cette règle d'apprentissage.
	 * 
	 * @return <code>Reseau</code>, le réseau utilisant cette règle.
	 */
	public Reseau<?> getReseau() {
		return reseau;
	}

	/**
	 * Définit le <code>Reseau</code> utilisant cette règle.
	 * 
	 * @param reseau
	 *            <code>Reseau</code>, le réseau qui utilisera cette règle.
	 */
	public void setReseau(Reseau<?> reseau) {
		this.reseau = reseau;
	}

	/**
	 * Prépare l'apprentissage du <code>Reseau</code> à débuter en mettant le
	 * drapeau d'arrêt à <code>false</code>. Si cette méthode est redéfinie, il
	 * faut s'assurer soit de passer par cette méthode parent en commençant la
	 * nouvelle méthode par <code>super();</code> ou en la faisant commencer par
	 * <code>this.arreterApprentissage = false;</code>;
	 */
	protected void lorsDemarer() {
		this.arreterApprentissage = false;
	}

	/**
	 * Invoquée après l'arrêt de l'apprentissage.
	 */
	protected void lorsArreter() {
	}

	/**
	 * Arrête l'apprentissage.
	 */
	public synchronized void arreterApprentissage() {
		this.arreterApprentissage = true;
	}

	/**
	 * Vérifie si l'apprentissage est arrêté.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si l'apprentissage est
	 *         arrêté, sinon <code>false</code>.
	 */
	public synchronized boolean estArrete() {
		return this.arreterApprentissage;
	}

	/**
	 * Lance la procédure d'apprentissage.
	 * 
	 * @param ensembleEntrainement
	 *            <code>EnsembleDonnees</code>, la liste pour l'apprentissage
	 *            voulue.
	 */
	abstract public void apprendre(EnsembleDonnees ensembleEntrainement);

}
