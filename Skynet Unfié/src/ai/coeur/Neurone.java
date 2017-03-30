package ai.coeur;

import java.io.Serializable;
import java.util.ArrayList;

import ai.coeur.entree.FonctionEntree;
import ai.coeur.entree.SommeImportance;
import ai.coeur.transfers.Etape;
import ai.coeur.transfers.FonctionTransfers;

public class Neurone implements Serializable, Cloneable {

	private static final long serialVersionUID = 8822213784188310686L;

	protected Niveau niveauParent;

	protected ArrayList<Lien> liensEntree;

	protected ArrayList<Lien> liensSortie;

	protected transient double totalEntrees;
	protected transient double sortie;
	protected transient double erreur;

	protected FonctionEntree fonctionEntree;
	protected FonctionTransfers fonctionTransfers;

	private String nom;

	public Neurone() {
		this.fonctionEntree = new SommeImportance();
		this.fonctionTransfers = new Etape();
		this.liensEntree = new ArrayList<>();
		this.liensSortie = new ArrayList<>();
	}

	public Neurone(FonctionEntree fonctionEntree, FonctionTransfers fonctionTransfers) {
		if (fonctionEntree == null) {
			throw new IllegalArgumentException("Fonction d'entrée ne peut être nulle!");
		}

		if (fonctionTransfers == null) {
			throw new IllegalArgumentException("Fonction de transfers ne peut être nulle!");
		}

		this.fonctionEntree = fonctionEntree;
		this.fonctionTransfers = fonctionTransfers;
		this.liensEntree = new ArrayList<>();
		this.liensSortie = new ArrayList<>();
	}

	public void calculer() {

		this.totalEntrees = fonctionEntree.getSortie(liensEntree);
		this.sortie = fonctionTransfers.getSortie(totalEntrees);

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

	public boolean aLiensEntree() {
		return (0 < this.liensEntree.size());
	}

	public boolean aLiensSortie() {
		return (0 < this.liensSortie.size());
	}

	public boolean aLienVers(Neurone neurone) {
		boolean so = false;
		for (Lien lien : liensSortie) {
			if (lien.jusquANeurone == neurone) {
				so = true;
				break;
			}
		}
		return so;
	}

	public boolean aLienAPartirDe(Neurone neurone) {
		boolean so = false;

		for (Lien lien : liensSortie) {
			if (lien.aPartirDeNeurone == neurone) {
				so = true;
				break;
			}
		}

		return so;
	}

	public void ajouterLienEntree(Lien lien) {
		if (lien == null) {
			throw new IllegalArgumentException("Tentative d'ajouter un lien null à la neurone!");
		}
		if (lien.getJusquANeurone() != this) {

			throw new IllegalArgumentException("Impossible d'ajouter le lien - mauvaise jusquANeurone spécifiée!");
		}

		if (this.aLienAPartirDe(lien.getAPartirDeNeurone())) {
			System.out.println("le lien existe déjà");
			return;
		}
		this.liensEntree.add(lien);

		Neurone aPartirDeNeurone = lien.aPartirDeNeurone;
		aPartirDeNeurone.ajouterLienSortie(lien);

	}

	public void ajouterLienEntree(Neurone aPartirDeNeurone) {
		Lien lien = new Lien(aPartirDeNeurone, this);
		this.ajouterLienEntree(lien);
	}

	public void ajouterLienEntree(Neurone aPartirDeNeurone, double valImportance) {
		Lien lien = new Lien(aPartirDeNeurone, this, valImportance);
		this.ajouterLienEntree(lien);
	}

	public void ajouterLienSortie(Lien lien) {
		if (lien == null) {
			throw new IllegalArgumentException("Tentative d'ajouter un lien null à la neurone!");
		}

		if (lien.getAPartirDeNeurone() != this) {
			throw new IllegalArgumentException("Impossible d'ajouter le lien - mauvaise aPartirDeNeurone spécifiée!");
		}

		if (this.aLienVers(lien.getJusquANeurone())) {
			return;
		}

		this.liensSortie.add(lien);
	}

	public ArrayList<Lien> getLiensEntree() {
		return liensEntree;
	}

	public ArrayList<Lien> getLiensSortie() {
		return liensSortie;
	}

	public void retirerLienEntree(Lien lien) {
		liensEntree.remove(lien);
	}

	public void retirerLienEntree(Neurone aPartirDeNeurone) {
		for (Lien lien : liensEntree) {
			if (lien.getAPartirDeNeurone() == aPartirDeNeurone) {
				aPartirDeNeurone.retirerLienSortie(lien);
				this.retirerLienEntree(lien);
				// ne jamais assumer qu'un paire de neurones n'a pas
				// malencontreusement plusieurs fois un lien entre elles
			}
		}
	}

	public void retirerLienSortie(Lien lien) {
		liensSortie.remove(lien);
	}

	public void retirerLienSortie(Neurone neurone) {
		for (Lien lien : liensSortie) {
			if (lien.getAPartirDeNeurone() == neurone) {
				neurone.retirerLienSortie(lien);
				this.retirerLienEntree(lien);
				// ne jamais assumer qu'un paire de neurones n'a pas
				// malencontreusement plusieurs fois un lien entre elles
			}
		}
	}

	public void retirerTousLiensEntree() {
		liensEntree.clear();
	}

	public void retirerTousLiensSortie() {
		liensSortie.clear();
	}

	public void retirerTousLiens() {
		retirerTousLiensEntree();
		retirerTousLiensSortie();
	}

	public Lien getLienAPartirDeNeurone(Neurone neurone) {
		Lien lienAPartirDeNeurone = null;
		for (Lien lien : liensEntree) {
			if (lien.getAPartirDeNeurone() == neurone) {
				lienAPartirDeNeurone = lien;
				break;
			}
		}
		return lienAPartirDeNeurone;
	}

	public void setFonctionEntree(FonctionEntree fonctionEntree) {
		this.fonctionEntree = fonctionEntree;
	}

	public void setFonctionTransfers(FonctionTransfers fonctionTransfers) {
		this.fonctionTransfers = fonctionTransfers;
	}

	public FonctionEntree getFonctionEntree() {
		return this.fonctionEntree;
	}

	public FonctionTransfers getFonctionTransfers() {
		return this.fonctionTransfers;
	}

	public Niveau getNiveauParent() {
		return this.niveauParent;
	}

	public void setNiveauParent(Niveau niveauParent) {
		this.niveauParent = niveauParent;
	}

	public Importance[] getImportancesEntree() {
		Importance[] importances = new Importance[liensEntree.size()];

		for (int i = 0; i < liensEntree.size(); i++) {
			importances[i] = liensEntree.get(i).getImportance();
		}

		return importances;
	}

	public double getErreur() {
		return erreur;
	}

	public void setErreur(double erreur) {
		this.erreur = erreur;
	}

	public void setSortie(double sortie) {
		this.sortie = sortie;
	}

	/**
	 * Mets la valeur de tous les liens entrants à la même selon la valeur
	 * spécifiée
	 * 
	 * @param valImportance
	 *            double, la valeur spécifiée
	 */
	public void initialiserImportanceLiensEntree(double valImportance) {
		for (Lien lien : liensEntree) {
			lien.getImportance().setValImportance(valImportance);
		}
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
	}
}
