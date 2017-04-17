package ai.apprentissage.nonsupervise.competitionInterReseau;

/**
 * La classe Similarite sert à garder en mémoire quel lien fonctionne bien selon
 * quelle valeur d'importance.
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
	 * Crée la similarité trouvée selon le nom des liens et la valeur qui sera
	 * appliquée aux descendants de ces liens.
	 * 
	 * @param nomLien
	 *            String, le nom des liens ayant une similarité.
	 * @param valAAppliquer
	 *            double, la valeur qui sera appliquée aux descendants.
	 */
	public Similarite(String nomLien, double valAAppliquer) {
		this.nomLien = nomLien;
		this.valAAppliquer = valAAppliquer;
	}

	/**
	 * Retourne le nom des liens sur lesquels la valeur sera appliquée.
	 * 
	 * @return
	 */
	public String getNomLien() {
		return nomLien;
	}

	public double getValAAppliquer() {
		return valAAppliquer;
	}

	public String toString() {
		return "Similarite{nomLien: " + nomLien + ", valAAppliquer: " + valAAppliquer + "}";
	}

}
