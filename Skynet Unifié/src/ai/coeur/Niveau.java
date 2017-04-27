package ai.coeur;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe Niveau sert de couche intermédiaire entre les entrées et les
 * sorties d'un réseau.
 * <P>
 * La classe Niveau n'est pas nécessaire dans un réseau, puisqu'un réseau peut
 * avoir ses entrées connectées directement à ses sorties. Par contre, il est
 * très conseillé qu'un réseau ait des niveaux. Plus un réseau à de niveaux et
 * plus les niveaux ont des neurones, plus le réseau contient de liens, et donc
 * plus il peut avoir de versions différentes créées par les changements
 * d'importance possible.
 * 
 * @see Neurone
 * @see Reseau
 * @see Lien
 */
public class Niveau implements Serializable {

	private static final long serialVersionUID = 7356903300583621306L;

	/**
	 * Le réseau dans lequel se trouve le niveau.
	 */
	private Reseau reseauParent;

	/**
	 * La liste des neurones que contient le niveau.
	 */
	protected ArrayList<Neurone> listeNeuronesNiveau;

	/**
	 * Le nom du niveau.
	 */
	private String nom;

	/**
	 * Crée un niveau qui ne contient aucune neurone.
	 */
	public Niveau() {
		listeNeuronesNiveau = new ArrayList<>();
	}

	/**
	 * Crée un niveau contenant un nombre spécifique de neurones.
	 * 
	 * @param nbrNeurones
	 *            le nombre de neurones que contient le niveau.
	 */
	public Niveau(int nbrNeurones) {
		listeNeuronesNiveau = new ArrayList<>(nbrNeurones);
		for (int i = 0; i < nbrNeurones; i++) {
			listeNeuronesNiveau.add(new Neurone());
		}
	}

	/**
	 * Retourne le réseau dans lequel se trouve le niveau.
	 * 
	 * @return Reseau, le réseau dans lequel se trouve le niveau.s
	 */
	public Reseau getReseauParent() {
		return reseauParent;
	}

	/**
	 * Définit le réseau dans lequel se trouve le niveau.
	 * 
	 * @param reseauParent
	 *            Reseau, le réseau spécifié.
	 */
	public void setReseauParent(Reseau reseauParent) {
		this.reseauParent = reseauParent;
	}

	/**
	 * Retourne la liste de neurones que contient le niveau.
	 * 
	 * @return <code>ArrayList&ltNeurone&gt</code>, la liste de neurones que
	 *         contient le niveau.
	 */
	public ArrayList<Neurone> getListeNeuronesNiveau() {
		return listeNeuronesNiveau;
	}

	/**
	 * Ajoute une neurone au niveau à la fin de la liste des neurones et
	 * spécifie à la neurone qu'elle est contenue dans ce niveau.
	 * 
	 * @param neurone
	 *            <code>Neurone</code>, la neurone à aujouter au niveau.
	 */
	public void ajouterNeurone(Neurone neurone) {
		if (neurone != null) {
			listeNeuronesNiveau.add(new Neurone());
			neurone.setNiveauParent(this);
		} else {
			throw new IllegalArgumentException("La neurone ne peut égaler null");
		}

		neurone.setNiveauParent(this);
		listeNeuronesNiveau.add(neurone);

	}

	/**
	 * Ajoute une neurone au niveau à une position spécifique de la liste des
	 * neurones.
	 * 
	 * @param position
	 *            <code>int</code>, la position à laquelle la neurone est
	 *            ajoutée.
	 * @param neurone
	 *            <code>Neurone</code>, la neurone à ajouter.
	 */
	public void ajouterNeurone(int position, Neurone neurone) {
		if (neurone == null) {
			throw new IllegalArgumentException("La neurone ne peut égaler null");
		}

		listeNeuronesNiveau.add(position, neurone);

		neurone.setNiveauParent(this);

	}

	/**
	 * Définit une neurone à l'emplacement spécifié comme étant la neurone
	 * spécifiée.
	 * 
	 * @param position
	 *            <code>int</code>, la position de la neurone.
	 * @param neurone
	 *            <code>Neurone</code>, la nouvelle identité de la neurone.
	 */
	public void setNeurone(int position, Neurone neurone) {
		if (neurone == null) {
			throw new IllegalArgumentException("La neurone ne peut égaler null");
		}

		listeNeuronesNiveau.set(position, neurone);

		neurone.setNiveauParent(this);

	}

	/**
	 * Retire la neurone spécifiée du niveau.
	 * 
	 * @param neurone
	 *            <code>Neurone</code>, la neurone à retirer.
	 */
	public void retirerNeurone(Neurone neurone) {
		listeNeuronesNiveau.remove(positionDe(neurone));
	}

	/**
	 * Retire la neurone se trouvant à la position spécifiée.
	 * 
	 * @param position
	 *            <code>int</code>, la position de la neurone à retirer.
	 */
	public void retirerNeuroneA(int position) {
		listeNeuronesNiveau.remove(position);
	}

	/**
	 * Retire toutes les neurones du niveau.
	 */
	public void retirerToutesNeurones() {
		listeNeuronesNiveau.clear();
	}

	/**
	 * Retourne la neurone se trouvant à la position spécifiée.
	 * 
	 * @param position
	 *            <code>int</code>, la position spécifiée.
	 * @return <code>Neurone</code>, la neurone se trouvant à cette position.
	 */
	public Neurone getNeuroneA(int position) {
		return listeNeuronesNiveau.get(position);
	}

	/**
	 * Retourne la position à laquelle se trouve la neurone spécifiée.
	 * 
	 * @param neurone
	 *            <code>Neurone</code>, la neurone dont on veut connaitre la
	 *            position.
	 * @return <code>int</code>, la position à laquelle se trouve la neurone.
	 */
	public int positionDe(Neurone neurone) {
		return listeNeuronesNiveau.indexOf(neurone);
	}

	/**
	 * Retourne le nombre de neurones se trouvant dans ce niveau.
	 * 
	 * @return <code>int</code>, le nombre de neurones se trouvant dans ce
	 *         niveau.
	 */
	public int getNombreDeNeurones() {
		return listeNeuronesNiveau.size();
	}

	/**
	 * Lance la méthode {@link Neurone#calculer()} de toutes les neurones du
	 * réseau.
	 */
	public void calculer() {
		for (Neurone neurone : listeNeuronesNiveau) {
			neurone.calculer();
		}
	}

	/**
	 * Réinitialise toutes les neurones du niveau en lançant leur méthode
	 * {@link Neurone#reinitialiser()}.
	 */
	public void reinitialiser() {
		for (Neurone neurone : listeNeuronesNiveau) {
			neurone.reinitialiser();
		}
	}

	/**
	 * Initialise l'importance des liens d'entrée de toutes les neurones du
	 * niveau à une valeur spécifiée.
	 * 
	 * @param valImportance
	 *            <code>double</code>, la valeur de l'importance de tous les
	 *            liens d'entrée de toutes les neurones du niveau.
	 */
	public void initialiserImportanceLiensEntree(double valImportance) {
		for (Neurone neurone : listeNeuronesNiveau) {
			neurone.initialiserImportanceLiensEntree(valImportance);
		}
	}

	/**
	 * Retourne le nom du niveau.
	 * 
	 * @return <code>String</code>, le nom du niveau.
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Définit le nom du niveau.
	 * 
	 * @param nom
	 *            <code>String</code>, le nom du niveau.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Vérifie si le niveau ne contient aucune neurone.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si le niveau ne contient
	 *         pas de neurones, sinon <code>false</code>.
	 */
	public boolean isVide() {
		return listeNeuronesNiveau.isEmpty();
	}

	/**
	 * Définit automatiquement le nom des neurones du niveau à des fins
	 * pratiques.
	 */
	public void renommerNeurones() {
		for (int i = 1; i <= listeNeuronesNiveau.size(); i++) {
			Neurone neurone = listeNeuronesNiveau.get(i - 1);
			neurone.setNom(this.getNom() + "Neur" + i);
		}
	}

	/**
	 * Retire tous les liens de toutes les neurones du niveau.
	 */
	public void retirerTousLiens() {
		for (int i = 0; i < listeNeuronesNiveau.size(); i++) {
			Neurone neurone = listeNeuronesNiveau.get(i);
			neurone.retirerTousLiens();
		}
	}

	/**
	 * Retourne la liste de tous les liens de toutes les neurones du niveau.
	 * 
	 * @return <code>ArrayList&ltLien&gt</code>, la liste de tous les liens de
	 *         toutes les neurones du niveau.
	 */
	public ArrayList<Lien> getListeLiens() {
		ArrayList<Lien> listeLiens = new ArrayList<>();

		for (Neurone neurone : listeNeuronesNiveau) {
			ArrayList<Lien> listeLiensNeurone = neurone.getLiensEntree();
			for (Lien lien : listeLiensNeurone) {
				listeLiens.add(lien);
			}
		}

		return listeLiens;
	}

}
