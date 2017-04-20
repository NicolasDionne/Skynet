package ai.coeur.donnee;

import java.util.ArrayList;
import java.util.Iterator;

import ai.coeur.Reseau;
import ai.coeur.apprentissage.RegleApprentissage;

/**
 * Cette classe est la collection de
 * <span style="text-decoration:underline">toutes</span> les situations
 * d'entrées <b>qui peuvent arriver</b>, et ce,
 * <b><span style="text-decoration:underline">même si la probabilité frôle le 1
 * sur &#x221e</span></b>.
 * <P>
 * Cette classe est utilisée par la majorité des algorithmes d'apprentissage.
 * 
 * @see LigneEnsembleDonnees
 * @see Reseau
 * @see RegleApprentissage
 */
public class EnsembleDonnees {
	// TODO Javadoc

	private ArrayList<LigneEnsembleDonnees> lignes;

	private int tailleEntree = 0;

	private int tailleSortie = 0;

	private String[] nomsLignes;

	private boolean estSupervise = false;

	private String nom;

	/**
	 * 
	 * @param tailleEntree
	 */
	public EnsembleDonnees(int tailleEntree) {
		this.lignes = new ArrayList<>();
		this.tailleEntree = tailleEntree;
		this.estSupervise = false;
		this.nomsLignes = new String[tailleEntree];
	}

	/**
	 * @param tailleEntree
	 * @param tailleSortie
	 */
	public EnsembleDonnees(int tailleEntree, int tailleSortie) {
		this.lignes = new ArrayList<>();
		this.tailleEntree = tailleEntree;
		this.tailleSortie = tailleSortie;
		this.estSupervise = true;
		this.nomsLignes = new String[tailleEntree + tailleSortie];
	}

	public void ajouterLigne(LigneEnsembleDonnees ligne) {
		if (ligne == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else if ((this.tailleEntree != 0) && (ligne.getEntrees().length != this.tailleEntree)) {
			throw new IllegalArgumentException("la taille doit fonctionner");
		} else if ((this.tailleSortie != 0) && (ligne.getSortiesDesirees().length != this.tailleSortie)) {
			throw new IllegalArgumentException("la taille doit fonctionner");
		} else {
			this.lignes.add(ligne);
		}
	}

	public void ajouterLigne(double[] entrees) {
		if (entrees == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else if (entrees.length != tailleEntree) {
			throw new IllegalArgumentException("la taille doit fonctionner");
		} else if (estSupervise) {
			throw new IllegalArgumentException("il doit y avoir une sortie");
		} else {
			this.ajouterLigne(new LigneEnsembleDonnees(entrees));
		}
	}

	public void ajouterLigne(double[] entrees, double[] sortie) {
		this.ajouterLigne(new LigneEnsembleDonnees(entrees, sortie));
	}

	public void retirerLigneA(int index) {
		this.lignes.remove(index);
	}

	public Iterator<LigneEnsembleDonnees> iterator() {
		return this.lignes.iterator();
	}

	public ArrayList<LigneEnsembleDonnees> getLignes() {
		return this.lignes;
	}

	public LigneEnsembleDonnees getLigneA(int index) {
		return this.lignes.get(index);
	}

	/**
	 * 
	 */
	public void clear() {
		this.lignes.clear();
	}

	public boolean estVide() {
		return this.lignes.isEmpty();
	}

	public boolean estSupervise() {
		return this.estSupervise;
	}

	public int taille() {
		return this.lignes.size();
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String[] getNomsLignes() {
		return this.nomsLignes;
	}

	public void setNomsLignes(String[] nomsLignes) {
		this.nomsLignes = nomsLignes;
	}

	public String getNomLigneA(int position) {
		return this.nomsLignes[position];
	}

	public void setNomLigne(int position, String nomLigne) {
		nomsLignes[position] = nomLigne;
	}

	/**
	 * 
	 * @return
	 */
	public int getTailleEntrees() {
		return this.tailleEntree;
	}

	/**
	 * Retourne la taille du tableau contenants les sorties possibles selon les
	 * situations.
	 * 
	 * @return
	 */
	public int getTailleSortie() {
		return tailleSortie;
	}

}
