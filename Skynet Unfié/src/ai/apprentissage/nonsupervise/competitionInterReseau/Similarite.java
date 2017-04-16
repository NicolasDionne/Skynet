package ai.apprentissage.nonsupervise.competitionInterReseau;

public class Similarite {

	private String nomLien;
	private double valAAppliquer;

	public Similarite(String nomLien, double valAAppliquer) {
		this.nomLien = nomLien;
		this.valAAppliquer = valAAppliquer;
	}

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
