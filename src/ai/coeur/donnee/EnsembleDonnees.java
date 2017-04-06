package ai.coeur.donnee;

import java.util.ArrayList;
import java.util.Iterator;

public class EnsembleDonnees {

	private ArrayList<LigneEnsembleDonnees> lignes;

	private int tailleEntree = 0;

	private int tailleSortie = 0;

	private String[] nomsLignes;

	private boolean estSupervise = false;

	private String nom;

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
		} else if ((this.tailleSortie != 0) && (ligne.getSortieDesiree().length != this.tailleSortie)) {
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

	public void clear() {
		this.lignes.clear();
	}

}
