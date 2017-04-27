package ai.coeur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import ai.coeur.apprentissage.ApprentissageIteratif;
import ai.coeur.apprentissage.RegleApprentissage;
import ai.coeur.donnee.EnsembleDonnees;
import ai.utilitaire.random.RandomiserALimites;
import ai.utilitaire.random.RandomizerImportance;
import utilitaires.Sortable;

/**
 * La classe <code>Reseau</code> est celle qui contient, évidement, le réseau
 * lui-même. C'est la deuxième la plus importante après la classe
 * <code>Neurone</code>.
 * <P>
 * Elle est utilisé pour démarrer l'apprentissage, l'arrêter, bref, tout faire.
 * <P>
 * Pour fonctionner, un réseau doit obligatoirement avoir au moins une neurone
 * d'entrée et au moins une de sortie. Il est conseillé d'avoir plusieurs
 * neurones d'entrée, mais pas trop, et moins de neurones de sortie que de
 * neurones d'entrée. Il est aussi conseillé d'avoir plusieurs niveaux et plus
 * de neurones par niveaux que de neurones d'entrées.
 * <P>
 * Lors du jeu, il faut constament lancer la méthode <code>calculer()</code>,
 * mais il faut avoir fini cette dernière avant de la relancer.
 * 
 * @param <R>
 *            La règle d'apprentissage du réseau.
 * @see Neurone
 * @see Niveau
 * @see RegleApprentissage
 */
public class Reseau<R extends RegleApprentissage> implements Serializable, Sortable {

	private static final long serialVersionUID = 5967360194072459874L;

	/**
	 * La liste des niveaux que contient le réseau.
	 */
	private ArrayList<Niveau> listeNiveaux;

	/**
	 * La liste des neurones d'entrée du réseau.
	 */
	private ArrayList<Neurone> neuronesEntree;

	/**
	 * La liste des neurones de sortie du réseau.
	 */
	private ArrayList<Neurone> neuronesSorties;

	/**
	 * Le tableau de la valeur de sortie des neurones de sortie.
	 */
	private double[] tableauSorties;

	/**
	 * Le score du réseau.
	 */
	private int score;

	/**
	 * La liste de tous les liens du réseau.
	 */
	private ArrayList<Lien> listeLiens;

	/**
	 * La règle d'apprentissage du réseau.
	 */
	private R regleApprentissage;

	/**
	 * Crée le réseau.
	 */
	public Reseau() {
		this.listeNiveaux = new ArrayList<>();
		this.neuronesEntree = new ArrayList<>();
		this.neuronesSorties = new ArrayList<>();
		this.score = 0;
		this.listeLiens = new ArrayList<>();
	}

	/**
	 * Ajoute au réseau le niveau spécifié à la dernière position par rapport à
	 * la liste de niveaux que contient le réseau.
	 * 
	 * @param niveau
	 *            <code>Niveau</code>, le niveau à ajouter.
	 */
	public void ajouterNiveau(Niveau niveau) {
		if (niveau == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else {
			niveau.setReseauParent(this);
			listeNiveaux.add(niveau);
		}
		renommerComposantes();
	}

	/**
	 * Ajoute au réseau un niveau spécifié à la position spécifié par rapport à
	 * la liste des niveaux que contient le réseau.
	 * 
	 * @param position
	 *            <code>int</code>, la position à laquelle le niveau est ajouté
	 *            par rapport à celles centrales.
	 * @param niveau
	 *            <code>Niveau</code>, le niveau à ajouter.
	 */
	public void ajouterNiveau(int position, Niveau niveau) {
		if (niveau == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else if (0 < position) {
			throw new IllegalArgumentException("ne peut être plus petit que 0");
		} else {
			niveau.setReseauParent(this);
			listeNiveaux.add(position, niveau);
		}
		renommerComposantes();
	}

	/**
	 * Retire le niveau spécifié.
	 * 
	 * @param niveau
	 *            <code>Niveau</code>, le niveau à retirer.
	 */
	public void retirerNiveau(Niveau niveau) {
		if (!listeNiveaux.remove(niveau)) {
			throw new RuntimeException("Le niveau n'était pas dans le réseau");
		}
		renommerComposantes();
	}

	/**
	 * Retire le niveau se trouvant à la position spécifiée par rapport à la
	 * liste de niveaux que contient le réseau.
	 * 
	 * @param position
	 *            <code>int</code>, la position à laquelle se trouve le niveau
	 *            qui sera retiré.
	 */
	public void retirerNiveauA(int position) {
		listeNiveaux.remove(position);
	}

	/**
	 * Retourne la liste des niveaux que contient le réseau.
	 * 
	 * @return <code>ArrayList&ltNiveau&gt</code>, la liste des niveaux que
	 *         contient le réseau.
	 */
	public ArrayList<Niveau> getListeNiveaux() {
		return this.listeNiveaux;
	}

	/**
	 * Retourne le niveau se trouvant à la position spécifiée par rapport à la
	 * liste des niveaux que contient le réseau.
	 * 
	 * @param position
	 *            <code>int</code>, la position spécifiée à laquelle se trouve
	 *            le niveau désiré par rapport à la liste de niveaux que
	 *            contient le réseau.
	 * @return <code>Niveau</code>, le niveau se trouvant à la position
	 *         spécifiée.
	 */
	public Niveau getNiveauA(int position) {
		return this.listeNiveaux.get(position);
	}

	/**
	 * Retourne la position à laquelle se trouve le niveau spécifié par rapport
	 * à la liste de niveaux que contient le réseau.
	 * 
	 * @param niveau
	 *            <code>Niveau</code>, le niveau se trouvant à la position
	 *            désirée par rapport à la liste de niveaux que contient le
	 *            réseau.
	 * @return <code>int</code>, la position du niveau par rapport à la liste de
	 *         niveaux que contient le réseau.
	 */
	public int positionDe(Niveau niveau) {
		return this.listeNiveaux.indexOf(niveau);
	}

	/**
	 * Retourne le nombre de niveaux que contient le réseau.
	 * 
	 * @return <code>int</code>, le nombre de niveaux que contient le réseau.
	 */
	public int getNombreNiveaux() {
		return this.listeNiveaux.size();
	}

	/**
	 * Définit les entrées spécifiées comme étant la valeur de sortie des
	 * neurones d'entrée.
	 * <P>
	 * Le nombre d'entrées en paramètre doit être le même que le nombre de
	 * neurones d'entrée. Si ce n'est pas le cas, une nouvelle exception
	 * <code>IllegalArgumentException</code> est lancée.
	 * 
	 * @param nouvellesValsEntrees
	 *            <code>double...</code>, les valeurs désirées.
	 */
	public void setValEntree(double... nouvellesValsEntrees) {
		if (nouvellesValsEntrees.length != neuronesEntree.size()) {
			throw new IllegalArgumentException("la taille n'est pas conforme");
		} else {
			for (int i = 0; i < this.neuronesEntree.size(); i++) {
				neuronesEntree.get(i).setSortie(nouvellesValsEntrees[i]);
			}
		}

	}

	/**
	 * Définit les entrées spécifiées comme étant la valeur de sortie des
	 * neurones d'entrée.
	 * <P>
	 * Le nombre d'entrées en paramètre doit être le même que le nombre de
	 * neurones d'entrée. Si ce n'est pas le cas, une nouvelle exception
	 * <code>IllegalArgumentException</code> est lancée.
	 * 
	 * @param nouvellesValsEntrees
	 *            <code>ArrayList&ltDouble&gt</code>, les nouvelles entrées dans
	 *            un <code>ArrayList</code>.
	 */
	public void setValEntree(ArrayList<Double> nouvellesValsEntrees) {
		if (nouvellesValsEntrees.size() != neuronesEntree.size()) {
			throw new IllegalArgumentException("la taille n'est pas conforme");
		} else {
			for (int i = 0; i < this.neuronesEntree.size(); i++) {
				neuronesEntree.get(i).setSortie(nouvellesValsEntrees.get(i));
			}
		}
	}

	/**
	 * Retourne le tableau contenant les valeurs de sortie des neurones de
	 * sortie du réseau.
	 * 
	 * @return <code>double[]</code>, le tableau contenant les valeurs de sortie
	 *         des neurones de sortie du réseau.
	 */
	public double[] getSortie() {
		this.tableauSorties = new double[this.neuronesSorties.size()];

		for (int i = 0; i < this.neuronesSorties.size(); i++) {
			this.tableauSorties[i] = neuronesSorties.get(i).getSortie();
		}

		return this.tableauSorties;
	}

	/**
	 * Lance la méthode <code>calculer()</code> de toutes les neurones du
	 * réseau, excepté celles d'entrée.
	 */
	public void calculer() {
		for (int i = 0; i < listeNiveaux.size(); i++) {
			listeNiveaux.get(i).calculer();
		}
		for (Neurone neurone : neuronesSorties) {
			neurone.calculer();
		}
	}

	/**
	 * Lance la méthode <code>reinitialiser()</code> de toutes les neurones du
	 * réseau.
	 */
	public void reinitialiser() {
		for (Neurone neurone : neuronesEntree) {
			neurone.reinitialiser();
		}
		for (Niveau niveau : listeNiveaux) {
			niveau.reinitialiser();
		}
		for (Neurone neurone : neuronesSorties) {
			neurone.reinitialiser();
		}
	}

	/**
	 * Commence l'apprentissage du réseau.
	 * 
	 * @param ensembleDonnees
	 *            <code>EnsembleDonnees</code>, la liste des entrées possibles.
	 */
	public void apprendre(EnsembleDonnees ensembleDonnees) {
		if (ensembleDonnees == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else {
			regleApprentissage.apprendre(ensembleDonnees);
		}
	}

	/**
	 * Commence l'apprentissage du réseau selon une règle d'apprentissage
	 * spécifiée.
	 * 
	 * @param ensembleDonnees
	 *            <code>EnsembleDonees</code>, la liste d'entrées possible du
	 *            <code>Reseau</code>. Peut aussi contenir les sorties
	 *            attendues.
	 * @param regleApprentissage
	 *            <code>RegleApprentissage</code>, la règle d'apprentissage du
	 *            <code>Reseau</code>.
	 */
	public void apprendre(EnsembleDonnees ensembleDonnees, R regleApprentissage) {
		setRegleApprentissage(regleApprentissage);
		regleApprentissage.apprendre(ensembleDonnees);
	}

	/**
	 * Fait avancer l'apprentissage du réseau d'une génération.
	 * 
	 * @param ensembleDonnees
	 *            <code>EnsembleDonnees</code>, la liste d'entrées possibles du
	 *            <code>Reseau</code>. Peut aussi contenir les sorties
	 *            attendues.
	 */
	public void apprendreUneGeneration(EnsembleDonnees ensembleDonnees) {
		if (ensembleDonnees == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else if (regleApprentissage.getClass().isInstance(ApprentissageIteratif.class)) {
			((ApprentissageIteratif) regleApprentissage).faireUneIterationApprentissage(ensembleDonnees);
		}
	}

	/**
	 * Arrête l'apprentissage du réseau si la <code>RegleApprentissage</code> du
	 * <code>Reseau</code> est une sous-classe de
	 * <code>ApprentissageIteratif</code>.
	 * 
	 * Si c'est le cas, cette méthode lancera
	 * {@link ApprentissageIteratif#pauser()}.
	 */
	public void arreterApperentissage() {
		if (regleApprentissage instanceof ApprentissageIteratif) {
			((ApprentissageIteratif) regleApprentissage).pauser();
		}
	}

	/**
	 * Résume l'apprentissage du réseau si la <code>RegleApprentissage</code>
	 * est une sous-classe de <code>ApprentissageIteratif</code>. Si c'est le
	 * cas, cette méthode lancera {@link ApprentissageIteratif#resumer()}.
	 */
	public void resumerApprentissage() {
		if (regleApprentissage instanceof ApprentissageIteratif) {
			((ApprentissageIteratif) regleApprentissage).resumer();
		}
	}

	/**
	 * Randomize la valeur d'importance de tous les liens du réseau.
	 */
	public void randomizerImportances() {
		randomizerImportances(new RandomizerImportance());
	}

	/**
	 * Randomize la valeur d'importance de tous les liens du réseau entre deux
	 * résultats.
	 * 
	 * @param min
	 *            <code>double</code>, la plus petite valeur possible.
	 * @param max
	 *            <code>double</code>, la plus grande valeur possible.
	 */
	public void randomizerImportances(double min, double max) {
		randomizerImportances(new RandomiserALimites(min, max));
	}

	/**
	 * Randomize l'impostance la valeur d'importance de tous les liens du réseau
	 * selon un <code>Random</code> spécifique.
	 * 
	 * @param randomizer
	 *            <code>Random</code>, le Random spécifique.
	 */
	public void randomizerImportances(Random randomizer) {
		randomizerImportances(new RandomizerImportance(randomizer));
	}

	/**
	 * Randomize la valeur d'importance de tous les liens du réseau selon un
	 * <code>RandomizerImportance</code> spécifique.
	 * 
	 * @param randomizerImportance
	 *            ¨<code>RandomizerImportance</code>, le randomizer d'importance
	 *            spécifié.
	 */
	public void randomizerImportances(RandomizerImportance randomizerImportance) {
		randomizerImportance.randomize(this);
	}

	/**
	 * Retourne la liste des neurones d'entrée du réseau.
	 * 
	 * @return <code><code>ArrayList&ltNeurone&gt</code></code>, la liste des
	 *         neurones d'entrée du réseau.
	 */
	public ArrayList<Neurone> getNeuronesEntree() {
		return neuronesEntree;
	}

	/**
	 * Retourne le nombre de neurones d'entrée du réseau.
	 * 
	 * @return <code>int</code>, le nombre de neurones d'entrée du réseau.
	 */
	public int getNombreEntrees() {
		return this.getNeuronesEntree().size();
	}

	/**
	 * Rajoute les neurones de la liste spécifiée à la liste de neurones
	 * d'entrée du réseau.
	 * 
	 * @param neuronesEntree
	 *            ArrayList&Neurones&gt, la liste de neurones spécifiée.
	 */
	public void setNeuronesEntree(ArrayList<Neurone> neuronesEntree) {
		for (Neurone neurone : neuronesEntree) {
			this.neuronesEntree.add(neurone);
		}
		renommerComposantes();
	}

	/**
	 * Retourne la liste des neurones de sortie du réseau.
	 * 
	 * @return <code><code>ArrayList&ltNeurone&gt</code></code>, la liste des
	 *         neurones de sortie du réseau.
	 */
	public ArrayList<Neurone> getNeuronesSorties() {
		return neuronesSorties;
	}

	/**
	 * Retourne le nombre de neurones de sortie du réseau.
	 * 
	 * @return <code>int</code>, le nombre de neurones de sortie du réseau.
	 */
	public int getNombreSorties() {
		return getNeuronesSorties().size();
	}

	/**
	 * Rajoute les neurones de la liste spécifiée à la liste de neurones de
	 * sortie du réseau.
	 * 
	 * @param neuronesSorties
	 *            <code>ArrayList&ltNeurone&gt</code>, la liste de neurones
	 *            spécifiée.
	 */
	public void setNeuronesSorties(ArrayList<Neurone> neuronesSorties) {
		for (Neurone neurone : neuronesSorties) {
			this.neuronesSorties.add(neurone);
		}
		this.tableauSorties = new double[this.neuronesSorties.size()];
		renommerComposantes();
	}

	/**
	 * Retourne la règle d'apprentissage du réseau.
	 * 
	 * @return <code>R</code>, la règle d'apprentissage du réseau.
	 */
	public R getRegleApprentissage() {
		return regleApprentissage;
	}

	/**
	 * Définit la règle d'apprentissage du réseau.
	 * 
	 * @param regleApprentissage
	 *            <code>R</code>, la règle d'apprentissage du réseau.
	 */
	public void setRegleApprentissage(R regleApprentissage) {
		this.regleApprentissage = regleApprentissage;
	}

	/**
	 * Retourne le score du réseau.
	 * 
	 * @return <code>int</code>, le score du réseau.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Définit le score du réseau.
	 * 
	 * @param score
	 *            <code>int</code>, le score du réseau.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Renomme toutes les composantes du réseau automatiquement à des fins
	 * pratiques.
	 */
	public void renommerComposantes() {
		renommerNeuronesEntrees();
		renommerNeuronesSortie();
		renommerNiveaux();
	}

	/**
	 * Renomme tous les niveaux du réseau et les composantes des niveaux du
	 * réseau automatiquement à des fins pratiques.
	 */
	public void renommerNiveaux() {
		for (int i = 1; i <= listeNiveaux.size(); i++) {
			Niveau niveau = listeNiveaux.get(i - 1);
			niveau.setNom("Niv" + i);
			niveau.renommerNeurones();
		}
	}

	/**
	 * Renomme toutes les neurones d'entrée du réseau automatiquement à des fins
	 * pratiques.
	 */
	public void renommerNeuronesEntrees() {
		for (int i = 1; i <= neuronesEntree.size(); i++) {
			Neurone neurone = neuronesEntree.get(i - 1);
			neurone.setNom("Entr" + i);
		}
	}

	/**
	 * Renomme toutes les neurones de sortie du réseau automatiquement à des
	 * fins pratiques.
	 */
	public void renommerNeuronesSortie() {
		for (int i = 1; i <= neuronesSorties.size(); i++) {
			Neurone neurone = neuronesSorties.get(i - 1);
			neurone.setNom("Sort" + i);
		}
	}

	/**
	 * Génère tous les liens.
	 */
	public void genererLiens() {
		supprimerTousLiens();
		if (listeNiveaux.isEmpty()) {
			genererLiensSansNiveax();
		} else {
			genererLiensAvecNiveax();
		}
		genererListeLiens();
	}

	/**
	 * Supprime tous les liens.
	 */
	public void supprimerTousLiens() {
		for (int i = 0; i < neuronesEntree.size(); i++) {
			Neurone neurone = neuronesEntree.get(i);
			neurone.retirerTousLiens();
		}

		for (int i = 0; i < neuronesSorties.size(); i++) {
			Neurone neurone = neuronesSorties.get(i);
			neurone.retirerTousLiens();
		}

		for (int i = 0; i < listeNiveaux.size(); i++) {
			Niveau niveau = listeNiveaux.get(i);
			niveau.retirerTousLiens();
		}
	}

	/**
	 * Génère les liens lorsque le réseau ne contient pas de niveaux.
	 */
	public void genererLiensSansNiveax() {
		for (Neurone neuroneEntree : neuronesEntree) {
			for (Neurone neurone : neuronesSorties) {
				neurone.ajouterLienEntree(neuroneEntree);
			}
		}
	}

	/**
	 * Génère les liens lorsque le réseau contient au moins un niveau.
	 */
	public void genererLiensAvecNiveax() {
		for (Neurone neuroneEntree : neuronesEntree) {
			for (Neurone neurone : listeNiveaux.get(0).listeNeuronesNiveau) {
				neurone.ajouterLienEntree(neuroneEntree);
			}
		}

		for (int i = 1; i < listeNiveaux.size(); i++) {
			for (Neurone neuroneEntree : listeNiveaux.get(i - 1).listeNeuronesNiveau) {
				for (Neurone neurone : listeNiveaux.get(i).listeNeuronesNiveau) {
					neurone.ajouterLienEntree(neuroneEntree);
				}
			}
		}

		for (Neurone neuroneEntree : listeNiveaux.get(listeNiveaux.size() - 1).listeNeuronesNiveau) {
			for (Neurone neurone : neuronesSorties) {
				neurone.ajouterLienEntree(neuroneEntree);
			}
		}
	}

	/**
	 * Retourne la liste de liens du réseau.
	 * 
	 * @return <code>ArrayList&ltLien&gt</code>, la liste de tous les liens du
	 *         réseau.
	 */
	public ArrayList<Lien> getListeLiens() {
		return listeLiens;
	}

	/**
	 * Génère la liste de tous les liens du réseau.
	 */
	public void genererListeLiens() {
		listeLiens.clear();
		for (Niveau niveau : listeNiveaux) {
			for (Lien lien : niveau.getListeLiens()) {
				listeLiens.add(lien);
			}
		}

		for (Neurone neurone : neuronesSorties) {
			for (Lien lien : neurone.getLiensEntree()) {
				listeLiens.add(lien);
			}
		}
	}

	public ArrayList<Double> getValEntrees() {
		ArrayList<Double> valEntrees = new ArrayList<>();

		for (int i = 0; i < neuronesEntree.size(); i++) {
			valEntrees.add(neuronesEntree.get(i).getSortie());
		}

		return valEntrees;
	}

	@Override
	public int getValeurLorsSort() {
		return Integer.MAX_VALUE - this.score;
	}

	public void save(String filePath) {
		
	}
	
}
