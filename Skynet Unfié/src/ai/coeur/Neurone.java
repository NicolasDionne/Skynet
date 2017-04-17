package ai.coeur;

import java.io.Serializable;
import java.util.ArrayList;

import ai.coeur.entree.FonctionEntree;
import ai.coeur.entree.SommeImportance;
import ai.coeur.transfers.Etape;
import ai.coeur.transfers.FonctionTransfers;

/**
 * La classe neurone est celle la plus importante du réseau. Il y a plusieurs
 * intances de cette classe dans un seul réseau.
 * </p>
 * Une neurone peut être soit une entrée, soit une sortie ou soit dans un niveau
 * entre les entrées et les sorties.
 * </p>
 * Une neurone a plusieurs liens la reliant à celles des couches se trouvant
 * juste à côté de la sienne (un niveau est une couche mais une couche n'est pas
 * nécessairement un niveau; le regroupement des entrées et le regroupement des
 * sorties sont eux-aussi tous deux des couches).
 * </p>
 * Une neurone à sa manière de recevoir et de transmettre l'information sous
 * forme de double. Sa manière de recevoir l'information est définie par la
 * fonction d'entrée et sa manière de transmettre l'information est définie par
 * sa fonction de transfers.
 */
public class Neurone implements Serializable, Cloneable {

	private static final long serialVersionUID = 8822213784188310686L;

	/**
	 * Le niveau dans lequel se trouve la neurone.
	 */
	protected Niveau niveauParent;

	/**
	 * La liste des liens qui entrent dans la neurone, et donc influence la
	 * valeur de la neurone.
	 */
	protected ArrayList<Lien> liensEntree;

	/**
	 * La liste des liens qui sortent de la neurone.
	 */
	protected ArrayList<Lien> liensSortie;

	/**
	 * La valeur d'entrée totale de la neurone. Représente l'entrée totale de la
	 * neurone reçue par la fonction d'entrée.
	 */
	protected transient double totalEntrees;

	/**
	 * La valeur de sortie de la neurone.
	 */
	protected transient double sortie;

	/**
	 * La valeur d'erreur de la neurone.
	 * </p>
	 * Elle n'est pas utilisée si la règle d'apprentissage du réseau est
	 * CompetitionInterReseaux.
	 */
	protected transient double erreur;

	/**
	 * La fonction d'entrée de la neurone.
	 */
	protected FonctionEntree fonctionEntree;

	/**
	 * La fonction de transfers de la neurone.
	 */
	protected FonctionTransfers fonctionTransfers;

	/**
	 * Le nom de la neurone.
	 */
	private String nom;

	/**
	 * Crée une neurone avec les paramètres par default: Somme des Importance
	 * comme fonction d'entrée et Étape (soit 0, soit 1) comme fonction de
	 * transfers.
	 */
	public Neurone() {
		this.fonctionEntree = new SommeImportance();
		this.fonctionTransfers = new Etape();
		this.liensEntree = new ArrayList<>();
		this.liensSortie = new ArrayList<>();
	}

	/**
	 * Crée une neurone avec les fonctions d'entrée et de transfers spécifiées.
	 * 
	 * @param fonctionEntree
	 *            FonctionEntree, la fonction d'entrée voulue.
	 * @param fonctionTransfers
	 *            FonctionTransfers, la fonction de transfers voulue.
	 */
	public Neurone(FonctionEntree fonctionEntree, FonctionTransfers fonctionTransfers) {
		if (fonctionEntree == null) {
			throw new IllegalArgumentException("Fonction d'entr�e ne peut �tre nulle!");
		}

		if (fonctionTransfers == null) {
			throw new IllegalArgumentException("Fonction de transfers ne peut �tre nulle!");
		}

		this.fonctionEntree = fonctionEntree;
		this.fonctionTransfers = fonctionTransfers;
		this.liensEntree = new ArrayList<>();
		this.liensSortie = new ArrayList<>();
	}

	/**
	 * Calcule la valeur de sortie de la neurone.
	 */
	public void calculer() {

		this.totalEntrees = fonctionEntree.getSortie(liensEntree);
		this.sortie = fonctionTransfers.getSortie(totalEntrees);

	}

	/**
	 * Réinitialise les valeurs d'entrée et de sortie de la neurone à 0.
	 */
	public void reinitialiser() {
		this.setTotalEntrees(0);
		this.setSortie(0);
	}

	/**
	 * Définit la valeur d'entrée de la neurone.
	 * 
	 * @param totalEntrees
	 *            double, la valeur d'entrée voulue.
	 */
	public void setTotalEntrees(double totalEntrees) {
		this.totalEntrees = totalEntrees;
	}

	/**
	 * Retourne la valeur d'entrée de la neurone.
	 * 
	 * @return double, la valeur d'entrée de la neurone.
	 */
	public double getTotalEntrees() {
		return this.totalEntrees;
	}

	/**
	 * Définit la valeur de sortie de la neurone.
	 * 
	 * @param sortie
	 *            double, la valeur de sortie de la neurone.
	 */
	public void setSortie(double sortie) {
		this.sortie = sortie;
	}

	/**
	 * Retourne la valeur de sortie de la neurone.
	 * 
	 * @return double, la valeur de sortie de la neurone.
	 */
	public double getSortie() {
		return this.sortie;
	}

	/**
	 * Vérifie si la neurone a des liens d'entrée.
	 * 
	 * @return boolean, true si la neurone possède des liens d'entrée, sinon
	 *         faux
	 */
	public boolean aLiensEntree() {
		return (0 < this.liensEntree.size());
	}

	/**
	 * Vérifie si la neurone a des liens de sortie.
	 * 
	 * @return boolean, true si la neurone possède des liens de sortie, sinon
	 *         faux
	 */
	public boolean aLiensSortie() {
		return (0 < this.liensSortie.size());
	}

	/**
	 * Vérifie si la neurone a un lien de sortie allant vers la neurone
	 * spécifiée.
	 * 
	 * @param neurone
	 *            Neurone, la neurone spécifiée.
	 * 
	 * @return boolean, true si un lien de sortie de la neurone pointe vers la
	 *         celle spécifiée, sinon faux.
	 */
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

	/**
	 * Vérifie si la neurone a un lien d'entrée provenant de la neurone
	 * spécifiée.
	 * 
	 * @param neurone
	 *            Neurone, la neurone spécifiée.
	 * @return boolean, true si un lien d'entrée de la neurone provient de celle
	 *         spécifiée, sinon faux.
	 */
	public boolean aLienAPartirDe(Neurone neurone) {
		boolean so = false;

		for (Lien lien : liensEntree) {
			if (lien.aPartirDeNeurone == neurone) {
				so = true;
				break;
			}
		}

		return so;
	}

	/**
	 * Ajoute à la neurone le lien d'entrée spécifié.
	 * 
	 * @param lien
	 *            Lien, le lien d'entrée à ajouter.
	 */
	public void ajouterLienEntree(Lien lien) {
		if (lien == null) {
			throw new IllegalArgumentException("Tentative d'ajouter un lien null � la neurone!");
		}
		if (lien.getJusquANeurone() != this) {

			throw new IllegalArgumentException("Impossible d'ajouter le lien - mauvaise jusquANeurone sp�cifi�e!");
		}

		if (this.aLienAPartirDe(lien.getAPartirDeNeurone())) {
			System.out.println("le lien existe d�j�");
			return;
		}
		this.liensEntree.add(lien);

		Neurone aPartirDeNeurone = lien.aPartirDeNeurone;
		aPartirDeNeurone.ajouterLienSortie(lien);

	}

	/**
	 * Ajoute à la neurone un lien d'entrée provenant de la neurone spécifiée.
	 * 
	 * @param aPartirDeNeurone
	 *            Neurone, la neurone spécifiée.
	 */
	public void ajouterLienEntree(Neurone aPartirDeNeurone) {
		Lien lien = new Lien(aPartirDeNeurone, this);
		this.ajouterLienEntree(lien);
	}

	/**
	 * Ajoute à la neurone un lien d'entrée provenant de la neurone spécifiée
	 * ayant une importance donnée.
	 * 
	 * @param aPartirDeNeurone
	 *            Neurone, la neurone spécifiée.
	 * @param valImportance
	 *            double, la valeur de l'importance du lien d'entrée à ajouter.
	 */
	public void ajouterLienEntree(Neurone aPartirDeNeurone, double valImportance) {
		Lien lien = new Lien(aPartirDeNeurone, this, valImportance);
		this.ajouterLienEntree(lien);
	}

	/**
	 * Ajoute à la neurone un lien de sortie spécifié.
	 * 
	 * @param lien
	 *            Lien, le lien de sortie à ajouter.
	 */
	public void ajouterLienSortie(Lien lien) {
		if (lien == null) {
			throw new IllegalArgumentException("Tentative d'ajouter un lien null � la neurone!");
		}

		if (lien.getAPartirDeNeurone() != this) {
			throw new IllegalArgumentException("Impossible d'ajouter le lien - mauvaise aPartirDeNeurone sp�cifi�e!");
		}

		if (this.aLienVers(lien.getJusquANeurone())) {
			return;
		}

		this.liensSortie.add(lien);
	}

	/**
	 * Retourne la liste des liens d'entrée de la neurone.
	 * 
	 * @return ArrayList&ltLien>, la liste des liens d'entrée.
	 */
	public ArrayList<Lien> getLiensEntree() {
		return liensEntree;
	}

	/**
	 * Retourne la liste des liens de sortie de la neurone.
	 * 
	 * @return ArrayList&ltLien>, la liste des liens de sortie.
	 */
	public ArrayList<Lien> getLiensSortie() {
		return liensSortie;
	}

	/**
	 * Retire à la neurone le lien d'entrée spécifié.
	 * 
	 * @param lien
	 *            Lien, le lien d'entrée à retirer.
	 */
	public void retirerLienEntree(Lien lien) {
		liensEntree.remove(lien);
	}

	/**
	 * Retire à la neurone le lien (ou les liens) d'entrée provenant de la
	 * neurone spécifiée.
	 * 
	 * @param aPartirDeNeurone
	 *            Neurone, la neurone spécifiée.
	 */
	public void retirerLienEntree(Neurone aPartirDeNeurone) {
		ArrayList<Lien> lienARetirer = new ArrayList<>();
		for (Lien lien : liensEntree) {
			if (lien.getAPartirDeNeurone() == aPartirDeNeurone) {
				aPartirDeNeurone.retirerLienSortie(lien);
				lienARetirer.add(lien);
				// ne jamais assumer qu'un paire de neurones n'a pas
				// malencontreusement plusieurs fois un lien entre elles
			}
		}

		for (Lien lien : lienARetirer) {
			retirerLienEntree(lien);
		}
	}

	/**
	 * Retire à la neurone le lien de sortie spécifié.
	 * 
	 * @param lien
	 *            Lien, le lien de sortie à retirer.
	 */
	public void retirerLienSortie(Lien lien) {
		liensSortie.remove(lien);
	}

	/**
	 * Retire à la neurone le lien (ou les liens) de sortie allant vers la
	 * neurone spécifiée.
	 * 
	 * @param neurone
	 *            Neurone, la neurone spécifiée.
	 */
	public void retirerLienSortie(Neurone neurone) {
		ArrayList<Lien> lienARetirer = new ArrayList<>();
		for (Lien lien : liensSortie) {
			if (lien.getAPartirDeNeurone() == neurone) {
				neurone.retirerLienSortie(lien);
				lienARetirer.add(lien);
				// ne jamais assumer qu'un paire de neurones n'a pas
				// malencontreusement plusieurs fois un lien entre elles
			}
		}

		for (Lien lien : lienARetirer) {
			retirerLienSortie(lien);
		}
	}

	/**
	 * Retire à la neurone tous ses liens d'entrée.
	 */
	public void retirerTousLiensEntree() {
		liensEntree.clear();
	}

	/**
	 * Retire à la neurone tous ses liens de sortie.
	 */
	public void retirerTousLiensSortie() {
		liensSortie.clear();
	}

	/**
	 * Retire à la neurone tous ses liens d'entrée et de sortie.
	 */
	public void retirerTousLiens() {
		retirerTousLiensEntree();
		retirerTousLiensSortie();
	}

	/**
	 * Retourne le lien d'entrée de la neurone provenant de la neurone
	 * spécifiée.
	 * 
	 * @param neurone
	 *            Neurone, la neurone spécifiée.
	 * @return Lien, le premier lien d'entrée de la neurone provenan de la
	 *         neurone spécifiée, sinon null.
	 */
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

	/**
	 * Définit la fonction d'entrée de la neurone.
	 * 
	 * @param fonctionEntree
	 *            FonctionEntree, la fonction d'entrée de la neurone.
	 */
	public void setFonctionEntree(FonctionEntree fonctionEntree) {
		this.fonctionEntree = fonctionEntree;
	}

	/**
	 * Définit la fonction de transfers de la neurone.
	 * 
	 * @param fonctionTransfers
	 *            FonctionTransfers, la fonction de transfers de la neurone.
	 */
	public void setFonctionTransfers(FonctionTransfers fonctionTransfers) {
		this.fonctionTransfers = fonctionTransfers;
	}

	/**
	 * Retourne la fonction d'entrée de la neurone.
	 * 
	 * @return FonctionEntree, la fonction d'entrée de la neurone.
	 */
	public FonctionEntree getFonctionEntree() {
		return this.fonctionEntree;
	}

	/**
	 * Retourne la fonction de transfers de la neurone.
	 * 
	 * @return FonctionTransfers, la fonction de transfers de la neurone.
	 */
	public FonctionTransfers getFonctionTransfers() {
		return this.fonctionTransfers;
	}

	/**
	 * Retourne le niveau dans lequel se trouve la neurone.
	 * 
	 * @return Niveau, le niveau dans lequel se trouve la neurone.
	 */
	public Niveau getNiveauParent() {
		return this.niveauParent;
	}

	/**
	 * Définit le niveau dans lequel se trouve la neurone.
	 * 
	 * @param niveauParent
	 *            Niveau, le niveau spécifié.
	 */
	public void setNiveauParent(Niveau niveauParent) {
		this.niveauParent = niveauParent;
	}

	/**
	 * Retourne le tableau contenant l'importance des liens d'entrée de la
	 * neurone.
	 * 
	 * @return Importance[], le tableau d'importance des liens d'entrée.
	 */
	public Importance[] getImportancesEntree() {
		Importance[] importances = new Importance[liensEntree.size()];

		for (int i = 0; i < liensEntree.size(); i++) {
			importances[i] = liensEntree.get(i).getImportance();
		}

		return importances;
	}

	/**
	 * Retourne la valeur d'erreur de la neurone.
	 * 
	 * @return double, la valeur d'erreur de la neurone.
	 */
	public double getErreur() {
		return erreur;
	}

	/**
	 * Définit la valeur d'erreur de la neurone.
	 * 
	 * @param erreur
	 *            double, la valeur d'erreur voulue.
	 */
	public void setErreur(double erreur) {
		this.erreur = erreur;
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

	/**
	 * Retourne le nom de la neurone.
	 * 
	 * @return String, le nom de la neurone.
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Définit le nom de la neurone.
	 * 
	 * @param nom
	 *            String, le nom voulu.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public boolean equals(Object obj) {
		boolean so = true;

		if (this == obj) {
			so = true;
		} else if (obj == null) {
			so = false;
		} else if (this.getClass() != obj.getClass()) {
			so = false;
		} else {
			if (!this.getLiensEntree().equals(((Neurone) obj).getLiensEntree())) {
				so = false;
			} else if (!this.getLiensSortie().equals(((Neurone) obj).getLiensSortie())) {
				so = false;
			} else if (this.getFonctionEntree().getClass() != ((Neurone) obj).getFonctionEntree().getClass()) {
				so = false;
			} else if (this.getFonctionTransfers().getClass() != ((Neurone) obj).getFonctionTransfers().getClass()) {
				so = false;
			}
		}

		return so;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Neurone obj = new Neurone();
		obj.setErreur(this.getErreur());
		obj.setFonctionEntree(this.getFonctionEntree());
		obj.setFonctionTransfers(this.getFonctionTransfers());
		obj.setNiveauParent(this.getNiveauParent());

		for (Lien lien : liensEntree) {
			obj.ajouterLienEntree(lien.getAPartirDeNeurone());
		}

		for (Lien lien : liensSortie) {
			lien.getJusquANeurone().ajouterLienEntree(obj);
		}

		return obj;
	}

	@Override
	public String toString() {
		return this.nom;
	}
}
