package ai.coeur;

import java.io.Serializable;
import java.util.ArrayList;

public class Neurone implements Serializable, Cloneable {
	// TODO la classe
	private static final long serialVersionUID = 8822213784188310686L;

	protected Niveau niveauParent;

	protected ArrayList<Lien> liensEntrees;

	protected ArrayList<Lien> liensSorties;

	protected transient double totalEntrees;
	protected transient double sortie;
	protected transient double erreur;

	// TODO FonctionEntree fonctionEntree
	// TODO FonctionTransfers fonctionTransfers

	private String nom;

	public Neurone() {
		// this.fonctionEntree = new SommeImportance();
		// this.fonctionTransfers = new Etape();
		this.liensEntrees = new ArrayList<>();
		this.liensSorties = new ArrayList<>();
	}
	// TODO constructeur après que les deux fonctions sont faites
	/*
	 * public Neurone(FonctionEntree fonctionEntree, FonctionTransfers
	 * fonctionTransfers) { if (fonctionEntree == null) { throw new
	 * IllegalArgumentException("Fonction d'entrée ne peut être nulle!"); }
	 * 
	 * if (fonctionTransfers == null) { throw new
	 * IllegalArgumentException("Fonction de transfers ne peut être nulle!"); }
	 * 
	 * this.fonctionEntree = fonctionEntree; this.fonctionTransfers =
	 * fonctionTransfers; this.liensEntrees = new ArrayList<>();
	 * this.liensSorties = new ArrayList<>(); }
	 */

	public void calculer() {
		// TODO en meme temps que constructeur
		/*
		 * this.totalInput = inputFunction.getOutput(inputConnections);
		 * this.output = transferFunction.getOutput(totalInput);
		 */
	}

	public void setInput(double entree) {
		this.totalEntrees = entree;
	}

	public double getEntreesNettes() {
		return this.totalEntrees;
	}

	public double getSortie() {
		return this.sortie;
	}

	public boolean aLiensEntrees() {
		return (this.liensEntrees.size() > 0);
	}

	public boolean aLienVers(Neurone neurone) {
		boolean so = false;
		for (Lien lien : liensSorties) {
			if (lien.jusquANeurone == neurone) {
				so = true;
				break;
			}
		}
		return so;
	}

	public boolean aLienAPartirDe(Neurone neurone) {
		boolean so = false;

		for (Lien lien : liensSorties) {
			if (lien.aPartirDeNeurone == neurone) {
				so = true;
				break;
			}
		}

		return so;
	}

	public void ajouterLienEntree(Neurone aPartirDeNeurone, double valImportance) {
		Lien lien = new Lien(aPartirDeNeurone, this, valImportance);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO clone()
		Object clone = null;
		return clone;
	}
}
