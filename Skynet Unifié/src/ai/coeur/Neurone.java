package ai.coeur;

import java.io.Serializable;
import java.util.ArrayList;

import ai.coeur.entree.FonctionEntree;
import ai.coeur.entree.SommeImportance;
import ai.coeur.transfers.Etape;
import ai.coeur.transfers.FonctionTransfers;

/**
 * La classe <code>Neurone</code> est celle la plus importante du réseau. Il y a
 * plusieurs intances de cette classe dans un seul réseau.
 * <P>
 * Une neurone peut être soit une entrée, soit une sortie ou soit dans un niveau
 * entre les entrées et les sorties.
 * <P>
 * Une neurone a plusieurs liens la reliant à celles des couches se trouvant
 * juste à côté de la sienne (un niveau est une couche mais une couche n'est pas
 * nécessairement un niveau; le regroupement des entrées et le regroupement des
 * sorties sont eux-aussi tous deux des couches).
 * <P>
 * Une neurone à sa manière de recevoir et de transmettre l'information sous
 * forme de double. Sa manière de recevoir l'information est définie par la
 * fonction d'entrée et sa manière de transmettre l'information est définie par
 * sa fonction de transfers.
 * 
 * @see Lien
 * @see Niveau
 * @see Reseau
 */
public class Neurone implements Serializable, Cloneable {

	private static final long serialVersionUID = 8822213784188310686L;

	/**
	 * Le niveau dans lequel se trouve la neurone.
	 */
	protected Niveau niveauParent;

	/**
	 * La liste des liens qui entrent dans cette neurone, et donc influence la
	 * valeur de cette neurone.
	 */
	protected ArrayList<Lien> liensEntree;

	/**
	 * La liste des liens qui sortent de cette neurone.
	 */
	protected ArrayList<Lien> liensSortie;

	/**
	 * La valeur d'entrée totale de cette neurone. Représente l'entrée totale de
	 * cette neurone reçue par la fonction d'entrée.
	 */
	protected transient double totalEntrees;

	/**
	 * La valeur de sortie de cette neurone.
	 */
	protected transient double sortie;

	/**
	 * La valeur d'erreur de cette neurone.
	 * <P>
	 * Elle n'est pas utilisée si la règle d'apprentissage du réseau est
	 * <code>CompetitionInterReseaux</code>.
	 */
	protected transient double erreur;

	/**
	 * La fonction d'entrée de cette neurone.
	 */
	protected FonctionEntree fonctionEntree;

	/**
	 * La fonction de transfers de cette neurone.
	 */
	protected FonctionTransfers fonctionTransfers;

	/**
	 * Le nom de cette neurone.
	 */
	private String nom;

	/**
	 * Crée une neurone avec les paramètres par default:
	 * <code>SommeImportance</code> comme fonction d'entrée et
	 * <code>Étape</code> (soit 0, soit 1) comme fonction de transfers.
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
	 *            <code>FonctionEntree</code>, la fonction d'entrée voulue.
	 * @param fonctionTransfers
	 *            <code>FonctionTransfers</code>, la fonction de transfers
	 *            voulue.
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
	 * Calcule la valeur de sortie de cette neurone.
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
	 *            <code>double</code>, la valeur d'entrée voulue.
	 */
	public void setTotalEntrees(double totalEntrees) {
		this.totalEntrees = totalEntrees;
	}

	/**
	 * Retourne la valeur d'entrée de cette neurone.
	 * 
	 * @return <code>double</code>, la valeur d'entrée de cette neurone.
	 */
	public double getTotalEntrees() {
		return this.totalEntrees;
	}

	/**
	 * Définit la valeur de sortie cette la neurone.
	 * 
	 * @param sortie
	 *            <code>double</code>, la valeur de sortie de cette neurone.
	 */
	public void setSortie(double sortie) {
		this.sortie = sortie;
	}

	/**
	 * Retourne la valeur de sortie de cette neurone.
	 * 
	 * @return <code>double</code>, la valeur de sortie de cette neurone.
	 */
	public double getSortie() {
		return this.sortie;
	}

	/**
	 * Vérifie si cette neurone a des liens d'entrée.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si cette neurone possède
	 *         des liens d'entrée, sinon <code>false</code>.
	 */
	public boolean aLiensEntree() {
		return (0 < this.liensEntree.size());
	}

	/**
	 * Vérifie si cette neurone a des liens de sortie.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si cette neurone possède
	 *         des liens de sortie, sinon <code>false</code>.
	 */
	public boolean aLiensSortie() {
		return (0 < this.liensSortie.size());
	}

	/**
	 * Vérifie si cette neurone a un <code>Lien</code> de sortie allant vers la
	 * neurone spécifiée.
	 * 
	 * @param neurone
	 *            <code>Neurone</code>, la neurone spécifiée.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si un <code>Lien</code>
	 *         de sortie de cette neurone pointe vers la celle spécifiée, sinon
	 *         <code>false</code>.
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
	 * Vérifie si cette neurone a un <code>Lien</code> d'entrée provenant de la
	 * neurone spécifiée.
	 * 
	 * @param neurone
	 *            <code>Neurone</code>, la neurone spécifiée.
	 * @return <code>boolean</code>, <code>true</code> si un <code>Lien</code>
	 *         d'entrée de cette neurone provient de celle spécifiée, sinon
	 *         <code>false</code>.
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
	 * Ajoute à cette neurone le <code>Lien<code> d'entrée spécifié.
	 * 
	 * &#64;param lien
	 *            <code>Lien</code>, le <code>Lien</code> d'entrée à ajouter.
	 */
	public void ajouterLienEntree(Lien lien) {
		if (lien == null) {
			throw new IllegalArgumentException("Tentative d'ajouter un lien null � la neurone!");
		}
		if (lien.getJusquANeurone() != this) {

			throw new IllegalArgumentException("Impossible d'ajouter le lien - mauvaise jusquANeurone sp�cifi�e!");
		}

		if (this.aLienAPartirDe(lien.getAPartirDeNeurone())) {
			System.out.println("le lien existe déjà");
			return;
		}
		this.liensEntree.add(lien);

		Neurone aPartirDeNeurone = lien.aPartirDeNeurone;
		aPartirDeNeurone.ajouterLienSortie(lien);

	}

	/**
	 * Ajoute à cette neurone un
	 * <code>Lien<code> d'entrée provenant de la neurone spécifiée.
	 * 
	 * &#64;param aPartirDeNeurone
	 *            <code>Neurone</code>, la neurone spécifiée.
	 */
	public void ajouterLienEntree(Neurone aPartirDeNeurone) {
		Lien lien = new Lien(aPartirDeNeurone, this);
		this.ajouterLienEntree(lien);
	}

	/**
	 * Ajoute à cette neurone un <code>Lien</code> d'entrée provenant de la
	 * neurone spécifiée ayant une importance donnée.
	 * 
	 * @param aPartirDeNeurone
	 *            <code>Neurone</code>, la neurone spécifiée.
	 * @param valImportance
	 *            <code>double</code>, la valeur de l'importance du lien
	 *            d'entrée à ajouter.
	 */
	public void ajouterLienEntree(Neurone aPartirDeNeurone, double valImportance) {
		Lien lien = new Lien(aPartirDeNeurone, this, valImportance);
		this.ajouterLienEntree(lien);
	}

	/**
	 * Ajoute à cette neurone un <code>Lien</code> de sortie spécifié.
	 * 
	 * @param lien
	 *            <code>Lien</code>, le <code>Lien</code> de sortie à ajouter.
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
	 * Retourne la liste des liens d'entrée de cette neurone.
	 * 
	 * @return ArrayList&ltLien>, la liste des liens d'entrée.
	 */
	public ArrayList<Lien> getLiensEntree() {
		return liensEntree;
	}

	/**
	 * Retourne la liste des liens de sortie de cette neurone.
	 * 
	 * @return ArrayList&ltLien>, la liste des liens de sortie.
	 */
	public ArrayList<Lien> getLiensSortie() {
		return liensSortie;
	}

	/**
	 * Retire à cette neurone le <code>Lien</code> d'entrée spécifié.
	 * 
	 * @param lien
	 *            <code>Lien</code>, le <code>Lien</code> d'entrée à retirer.
	 */
	public void retirerLienEntree(Lien lien) {
		liensEntree.remove(lien);
	}

	/**
	 * Retire à cette neurone le lien (ou les liens) d'entrée provenant de la
	 * neurone spécifiée.
	 * 
	 * @param aPartirDeNeurone
	 *            <code>Neurone</code>, la neurone spécifiée.
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
	 * Retire à cette neurone le <code>Lien</code> de sortie spécifié.
	 * 
	 * @param lien
	 *            <code>Lien</code>, le <code>Lien</code> de sortie à retirer.
	 */
	public void retirerLienSortie(Lien lien) {
		liensSortie.remove(lien);
	}

	/**
	 * Retire à cette neurone le lien (ou les liens) de sortie allant vers la
	 * neurone spécifiée.
	 * 
	 * @param neurone
	 *            <code>Neurone</code>, la neurone spécifiée.
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
	 * Retire à cette neurone tous ses liens d'entrée.
	 */
	public void retirerTousLiensEntree() {
		liensEntree.clear();
	}

	/**
	 * Retire à cette neurone tous ses liens de sortie.
	 */
	public void retirerTousLiensSortie() {
		liensSortie.clear();
	}

	/**
	 * Retire à cette neurone tous ses liens d'entrée et de sortie.
	 */
	public void retirerTousLiens() {
		retirerTousLiensEntree();
		retirerTousLiensSortie();
	}

	/**
	 * Retourne le <code>Lien</code> d'entrée de cette neurone provenant de la
	 * neurone spécifiée.
	 * 
	 * @param neurone
	 *            <code>Neurone</code>, la neurone spécifiée.
	 * @return <code>Lien</code>, le premier <code>Lien</code> d'entrée de la
	 *         neurone provenant de la neurone spécifiée, sinon null.
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
	 * Définit la fonction d'entrée de cette neurone.
	 * 
	 * @param fonctionEntree
	 *            <code>FonctionEntree</code>, la fonction d'entrée de cette
	 *            neurone.
	 */
	public void setFonctionEntree(FonctionEntree fonctionEntree) {
		this.fonctionEntree = fonctionEntree;
	}

	/**
	 * Définit la fonction de transfers de cette neurone.
	 * 
	 * @param fonctionTransfers
	 *            <code>FonctionTransfers</code>, la fonction de transfers de
	 *            cette neurone.
	 */
	public void setFonctionTransfers(FonctionTransfers fonctionTransfers) {
		this.fonctionTransfers = fonctionTransfers;
	}

	/**
	 * Retourne la fonction d'entrée de cette neurone.
	 * 
	 * @return <code>FonctionEntree</code>, la fonction d'entrée de cette
	 *         neurone.
	 */
	public FonctionEntree getFonctionEntree() {
		return this.fonctionEntree;
	}

	/**
	 * Retourne la fonction de transfers de cette neurone.
	 * 
	 * @return <code>FonctionTransfers</code>, la fonction de transfers de cette
	 *         neurone.
	 */
	public FonctionTransfers getFonctionTransfers() {
		return this.fonctionTransfers;
	}

	/**
	 * Retourne le niveau dans lequel se trouve cette neurone.
	 * 
	 * @return <code>Niveau</code>, le niveau dans lequel se trouve cette
	 *         neurone.
	 */
	public Niveau getNiveauParent() {
		return this.niveauParent;
	}

	/**
	 * Définit le niveau dans lequel se trouve cette neurone.
	 * 
	 * @param niveauParent
	 *            <code>Niveau</code>, le niveau spécifié.
	 */
	public void setNiveauParent(Niveau niveauParent) {
		this.niveauParent = niveauParent;
	}

	/**
	 * Retourne le tableau contenant l'importance des liens d'entrée de cette
	 * neurone.
	 * 
	 * @return <code>Importance[]</code>, le tableau d'importance des liens
	 *         d'entrée.
	 */
	public Importance[] getImportancesEntree() {
		Importance[] importances = new Importance[liensEntree.size()];

		for (int i = 0; i < liensEntree.size(); i++) {
			importances[i] = liensEntree.get(i).getImportance();
		}

		return importances;
	}

	/**
	 * Retourne la valeur d'erreur de cette neurone.
	 * 
	 * @return <code>double</code>, la valeur d'erreur de cette neurone.
	 */
	public double getErreur() {
		return erreur;
	}

	/**
	 * Définit la valeur d'erreur de cette neurone.
	 * 
	 * @param erreur
	 *            <code>double</code>, la valeur d'erreur voulue.
	 */
	public void setErreur(double erreur) {
		this.erreur = erreur;
	}

	/**
	 * Mets la valeur de tous les liens entrants à la même selon la valeur
	 * spécifiée
	 * 
	 * @param valImportance
	 *            <code>double</code>, la valeur spécifiée
	 */
	public void initialiserImportanceLiensEntree(double valImportance) {
		for (Lien lien : liensEntree) {
			lien.getImportance().setValImportance(valImportance);
		}
	}

	/**
	 * Retourne le nom de cette neurone.
	 * 
	 * @return <code>String</code>, le nom de cette neurone.
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Définit le nom de cette neurone.
	 * 
	 * @param nom
	 *            <code>String</code>, le nom voulu.
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
