package ai.coeur.donnee;

import java.util.ArrayList;

import ai.utilitaire.SeparateurEnTableau;

public class LigneEnsembleDonnees {

	protected double[] entrees;

	protected double[] sortieDesiree;

	protected String nom;

	public LigneEnsembleDonnees(String entrees, String sortieDesiree) {
		this.entrees = SeparateurEnTableau.separateurArrayDouble(entrees);
		this.sortieDesiree = SeparateurEnTableau.separateurArrayDouble(sortieDesiree);
	}

	public LigneEnsembleDonnees(double[] entrees, double[] sortieDesiree) {
		this.entrees = entrees;
		this.sortieDesiree = sortieDesiree;
	}

	public LigneEnsembleDonnees(double... entrees) {
		this.entrees = entrees;
	}

	public LigneEnsembleDonnees(ArrayList<Double> entrees, ArrayList<Double> sortieDesiree) {
		this.entrees = SeparateurEnTableau.toDoubleArray(entrees);
		this.sortieDesiree = SeparateurEnTableau.toDoubleArray(sortieDesiree);
	}

	public LigneEnsembleDonnees(ArrayList<Double> entrees) {
		this.entrees = SeparateurEnTableau.toDoubleArray(entrees);
	}

	public double[] getEntrees() {
		return entrees;
	}

	public void setEntrees(double[] entrees) {
		this.entrees = entrees;
	}

	public double[] getSortieDesiree() {
		return sortieDesiree;
	}

	public void setSortieDesiree(double[] sortieDesiree) {
		this.sortieDesiree = sortieDesiree;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean estSupervise() {
		return (sortieDesiree != null);
	}

}
