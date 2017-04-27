package ai.coeur.donnee;

import java.util.List;

import ai.utilitaire.SeparateurEnTableau;

/**
 * Cette classe est une seule ligne dans <code>EnsembleDonnees</code>. Elle est
 * utilisée pour entrainer le <code>Reseau</code>.
 * <P>
 * Elle contient la liste d'entrées représentant une situation possibles du
 * <code>Reseau</code> pour son apprentissage.
 * <P>
 * Si la <code>RegleApprentissage</code> est supervisée, il y a aussi les
 * sorties attendues lors de cette situation. Si la
 * <code>RegleApprentissage</code> n'est pas suppervisée, il y a seulement des
 * entrés.
 * 
 * @see EnsembleDonnees
 */
public class LigneEnsembleDonnees {

	/**
	 * Le tableau représentant la liste des entrées de cette situation.
	 */
	protected double[] entrees;

	/**
	 * Les sorties désirés lors de cette situation.
	 */
	protected double[] sortiesDesirees;

	/**
	 * Le nom de cette situation.
	 */
	protected String nom;

	/**
	 * Crée une nouvelle situation avec les entrées de cette situation et les
	 * sorties désirées lors de cette situation.
	 * 
	 * @param entrees
	 *            <code>String</code>, le tableau des entrées lors de cette
	 *            situation sous formes d'un <code>String</code> de nombres
	 *            séparés par un espace.
	 * @param sortieDesiree
	 *            <code>String</code>, le tableau des sorties désirées lors de
	 *            cette situation sous formes d'un <code>String</code> de
	 *            nombres séparés par un espace.
	 */
	public LigneEnsembleDonnees(String entrees, String sortieDesiree) {
		this.entrees = SeparateurEnTableau.separateurArrayDouble(entrees);
		this.sortiesDesirees = SeparateurEnTableau.separateurArrayDouble(sortieDesiree);
	}

	/**
	 * Crée une nouvelle situation avec les entrées de cette situation et les
	 * sorties désirées lors de cette situation.
	 * 
	 * @param entrees
	 *            <code>double[]</code>, le tableau des entrées lors de cette
	 *            situation.
	 * @param sortieDesiree
	 *            <code>double[]</code>, le tableau des sorties désirées lors de
	 *            cette situation.
	 */
	public LigneEnsembleDonnees(double[] entrees, double[] sortieDesiree) {
		this.entrees = entrees;
		this.sortiesDesirees = sortieDesiree;
	}

	/**
	 * Crée une nouvelle situation avec les entrées de cette situation
	 * énumérées.
	 * 
	 * @param entrees
	 *            <code>double...</code>, l'énumération des entrées de cette
	 *            situation.
	 */
	public LigneEnsembleDonnees(double... entrees) {
		this.entrees = entrees;
	}

	/**
	 * Crée une nouvelle situation avec les entrées de cette situation dans une
	 * <code>List</code>.
	 * 
	 * @param entrees
	 *            <code>List&ltDouble&gt<code>, les entrées de cette situation.
	 */
	public LigneEnsembleDonnees(List<Double> entrees) {
		this.entrees = SeparateurEnTableau.toDoubleArray(entrees);
	}

	/**
	 * Crée une nouvelle situation avec les entrées et les sorties désirées de
	 * cette situation dans une <code>List</code>.
	 * 
	 * @param entrees
	 *            <code>List&ltDouble&gt</code>, la liste des entrées de cette
	 *            situation.
	 * @param sortieDesiree
	 *            <code>List&ltDouble&gt</code>, la liste des sorties désirées
	 *            de cette situation.
	 */
	public LigneEnsembleDonnees(List<Double> entrees, List<Double> sortieDesiree) {
		this.entrees = SeparateurEnTableau.toDoubleArray(entrees);
		this.sortiesDesirees = SeparateurEnTableau.toDoubleArray(sortieDesiree);
	}

	/**
	 * Retourne les entrées de cette situation dans un tableau de
	 * <code>double</code>.
	 * 
	 * @return <code>double[]</code>, le tableau des entrées de cette situation.
	 */
	public double[] getEntrees() {
		return entrees;
	}

	/**
	 * Définit les entrées de cette situation.
	 * 
	 * @param entrees
	 *            <code>double[]</code>, le tableau des entrées de cette
	 *            situation.
	 */
	public void setEntrees(double[] entrees) {
		this.entrees = entrees;
	}

	/**
	 * Retourne les sorties désirées de cette situation dans un tableau de
	 * <code>double</code>.
	 * 
	 * @return <code>double[]</code>, le tableau des sorties désirées de la
	 *         situation.
	 */
	public double[] getSortiesDesirees() {
		return sortiesDesirees;
	}

	/**
	 * Définit les sorties désirées de cette situation.
	 * 
	 * @param sortiesDesirees
	 *            <code>double[]</code>, les sorties désirées désirées.
	 * @category cette javadoc est d'une redondance redondante
	 */
	public void setSortiesDesirees(double[] sortiesDesirees) {
		this.sortiesDesirees = sortiesDesirees;
	}

	/**
	 * Retourne le nom de cette situation.
	 * 
	 * @return <code>String</code>, le nom de cette situation.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Définit le nom de cette situation.
	 * 
	 * @param nom
	 *            <code>String</code>, le nom de cette situation.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Vérifie si cette situation est supervisée.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si le tableau de sorties
	 *         désirées est <code>null</code>, sinon <code>false</code>.
	 */
	public boolean estSupervise() {
		return (sortiesDesirees != null);
	}

}
