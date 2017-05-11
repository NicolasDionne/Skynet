package ai.apprentissage.nonsupervise.competitionInter;

import java.io.Serializable;
import java.util.ArrayList;

import ai.apprentissage.nonsupervise.CompetitionInter;
import ai.apprentissage.nonsupervise.competitionInter.competitionInterLiens.GetValModification;
import ai.coeur.Importance;
import ai.coeur.Lien;
import ai.coeur.Reseau;

/**
 * Règle d'apprentissage compétitif. L'apprentissage fonctionne en trouvant quel
 * réseau a le mieux fonctionné parmi ceux en compétition. Plus le réseau a eu
 * un bon score, les liens de ses enfants auront une valeur d'importance proche
 * de celle des siennes.
 * <P>
 * Cette classe hérite de <code>CompetitionInter</code>.
 * 
 * @see CompetitionInter
 */
public class CompetitionInterLiens extends CompetitionInter implements Serializable {

	private static final long serialVersionUID = -7304761752225772866L;

	/**
	 * La valeur de modification de lors de la modification de la valeur des
	 * liens. Elle est la valeur de taille de la marge possible de la
	 * modification del'importance des liens.
	 */
	private transient double valModification;

	/**
	 * Peut être utilisée pour déterminer la valeur de modification des liens.
	 * Fait apparaitre un nouveau <code>Stage</code> possédeant un
	 * <code>Slider</code> utilisé pour sélectionner manuellement la valeur de
	 * {@link #valModification}.
	 */
	private transient GetValModification getValModification;

	/**
	 * Crée une nouvelle instance de la règle d'applentissage compétitif.
	 * 
	 * @param listeReseauxEnCompetitions
	 *            <code>ArrayList&ltReseau&gt</code>, la liste des réseaux en
	 *            compétitions.
	 * @param valModification
	 *            <code>double</code>, la valeur de modification initiale.
	 */
	public CompetitionInterLiens(ArrayList<Reseau> listeReseauxEnCompetitions, double valModification) {
		super(listeReseauxEnCompetitions);
		this.listeMeilleursReseaux.add(listeReseauxEnCompetitions.get(0));
		for (int i = 1; i < listeReseauxEnCompetitions.size(); i++) {
			listeMoinsBonsReseaux.add(listeReseauxEnCompetitions.get(i));
		}
		this.valModification = valModification;
	}

	/**
	 * Modifie la valeur des liens des réseaux ayant le moins bien performé.
	 * <P>
	 * Commence par trouver la valeur de {@link #valModification} elle la
	 * mettant plus petite lorsque le score du meilleur réseau est grand et en
	 * la mettant plus grande lorsque le score du meilleur réseau est petit.
	 * <P>
	 * Ensuite, elle lance {@link #appliquerImportanceMeilleurReseau()} puis
	 * {@link #appliquerModificationImportance()}.
	 */
	@Override
	protected void mettreAJourImportancesReseau() {
		// GetValModification getValModification = new GetValModification();
		// this.valModification = getValModification.getVal();
		this.valModification = 50.0
				* ((((double) Integer.MAX_VALUE) - ((double) listeMeilleursReseaux.get(0).getScore()))
						/ ((double) Integer.MAX_VALUE));
		appliquerImportanceMeilleurReseau();
		appliquerModificationImportance();
	}

	/**
	 * Lance {@link #classerReseaux()} puis {@link #trouverMeilleurReseau()}.
	 */
	@Override
	protected void avantEpoch() {
		classerReseaux();
		trouverMeilleurReseau();
	}

	/**
	 * Met les réseaux dans {@link #listeReseauxEnCompetitions} après que les
	 * valeurs de leur liens aient été modifiées.
	 */
	@Override
	protected void apresEpoch() {
		this.listeReseauxEnCompetitions.clear();
		this.listeReseauxEnCompetitions.addAll(listeMeilleursReseaux);
		this.listeReseauxEnCompetitions.addAll(listeMoinsBonsReseaux);
	}

	/**
	 * Trouve le meilleur réseau et le met dans
	 * {@link CompetitionInter#listeMeilleursReseaux}. Les autres sont envoyés
	 * dans {@link CompetitionInter#listeMoinsBonsReseaux}.
	 */
	protected void trouverMeilleurReseau() {
		for (int i = 0; i < listeReseauxEnCompetitions.size(); i++) {
			((i == 0) ? (listeMeilleursReseaux) : (listeMoinsBonsReseaux)).set(((i == 0) ? 0 : i - 1),
					this.listeReseauxEnCompetitions.get(i));
		}
	}

	/**
	 * Assigne les mêmes valeurs d'importance de liens à tous les réseaux de
	 * {@link CompetitionInter#listeMoinsBonsReseaux} que celles du réseau ayant
	 * le mieux performé.
	 */
	protected void appliquerImportanceMeilleurReseau() {
		Reseau meilleurReseau = listeMeilleursReseaux.get(0);
		for (Reseau reseau : listeMoinsBonsReseaux) {
			for (Lien lienMeilleurReseau : (ArrayList<Lien>) (meilleurReseau.getListeLiens())) {
				for (Lien lien : (ArrayList<Lien>) reseau.getListeLiens()) {
					if (lienMeilleurReseau.getNom().equals(lien.getNom())) {
						lien.getImportance().setValImportance(lienMeilleurReseau.getImportance().getValImportance());
						break;
					}
				}
			}
		}
	}

	/**
	 * Modifie les valeurs d'importances de tous les réseaux ayant moins bien
	 * performé. La nouvelle valeur d'importance est égale à
	 * <code>valImportance ± valModification</code> et est choisie aléatoirement
	 * en utilisant {@link Importance#setImportanceAleatoire(double, double)}
	 * sur chaque liens de chaque réseaux ayant le moins bien performé, où le
	 * premier <code>double</code> est
	 * <code>valImportance - valModification</code> et le deuxième
	 * <code>double</code> est <code>valImportance + valModification</code>.
	 */
	protected void appliquerModificationImportance() {
		for (Reseau<CompetitionInterLiens> reseau : listeMoinsBonsReseaux) {
			for (Lien lien : reseau.getListeLiens()) {
				lien.getImportance().setImportanceAleatoire(lien.getImportance().getValImportance() - valModification,
						lien.getImportance().getValImportance() + valModification);
				reajusterValeurImportance(lien);
			}
		}
	}

	/**
	 * Réajuste la nouvelle valeur aléatoire de l'importance.
	 * <P>
	 * Si elle dépasse une limite, qu'elle soit inférieure (< -100) ou
	 * supérieure(> 100), elle sera renvoyé dans les limite en (en valeur
	 * absolue) soustrayant la quantitée de laquelle elle dépasse.
	 * <P>
	 * Par exemple, si la nouvelle importance a une valeur de 103, elle sera
	 * ramenée à 97. Si elle est de 108, elle sera ramenée à 92. Si elle est de
	 * -105, elle sera remenée à -95.
	 * 
	 * @param lien
	 *            <code>Lien<code>, le lien dont la valeur de l'importance doit
	 *            être réajustée au besoin.
	 */
	protected void reajusterValeurImportance(Lien lien) {
		if (lien.getImportance().getValImportance() > importanceMax) {
			lien.getImportance()
					.setValImportance(importanceMax - (lien.getImportance().getValImportance() - importanceMax));
			if (lien.getImportance().getValImportance() > importanceMax) {
				System.out.println("Fuck…");
			}
		} else if (lien.getImportance().getValImportance() < importanceMin) {
			lien.getImportance()
					.setValImportance(importanceMin - (lien.getImportance().getValImportance() - importanceMin));
			if (lien.getImportance().getValImportance() < importanceMin) {
				System.out.println("Fuck…");
			}
		}
	}

	public double getValModification() {
		return valModification;
	}

	public void setValModification(double valModification) {
		this.valModification = valModification;
	}

}
