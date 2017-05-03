package ai.apprentissage.nonsupervise.competitionInterReseau;

import ai.apprentissage.nonsupervise.competitionInter.CompetitionInterReseaux;

/**
 * La classe <code>Similarite</code> sert à garder en mémoire quel
 * <code>Lien</code> fonctionne bien selon quelle valeur d'importance.
 * 
 * @see CompetitionInterReseaux
 *
 */
public class Similarite {

	/**
	 * Le nom des liens sur lesquels la valeur sera appliquée.
	 */
	private String nomLien;

	/**
	 * La valeur à appliquer aux liens.
	 */
	private double valAAppliquer;

	/**
	 * Crée la <code>Similarite</code> trouvée selon le nom des liens et la
	 * valeur qui sera appliquée aux descendants de ces liens.
	 * 
	 * @param nomLien
	 *            <code>String</code>, le nom du <code>Lien</code> ayant une
	 *            similarité dans un autre réseau.
	 * @param valAAppliquer
	 *            <code>double</code>, la valeur qui sera appliquée aux
	 *            descendants.
	 */
	public Similarite(String nomLien, double valAAppliquer) {
		this.nomLien = nomLien;
		this.valAAppliquer = valAAppliquer;
	}

	/**
	 * Retourne le nom des liens sur lesquels la valeur sera appliquée.
	 * 
	 * @return <code>String</code>, le nom du <code>Lien</code> ayant une
	 *         similarité dans un autre <code>Reseau</code>.
	 */
	public String getNomLien() {
		return nomLien;
	}

	/**
	 * Retourne la valeur <code>double</code> de l'<code>Importance</code> à
	 * appliquer aux <code>Liens</code> des <code>Reseau</code> ayant le moins
	 * bien performé ayant le même nom.
	 * 
	 * @return <code>double</code>, la valeur à appliquer.
	 */
	public double getValAAppliquer() {
		return valAAppliquer;
	}

	@Override
	public String toString() {
		return "Similarite{nomLien: " + nomLien + ", valAAppliquer: " + valAAppliquer + "}";
	}

}
