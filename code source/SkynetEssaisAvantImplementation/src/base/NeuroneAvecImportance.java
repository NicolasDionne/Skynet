package base;

public class NeuroneAvecImportance extends Neurone {
	private double valeurImportance;

	public NeuroneAvecImportance(String nom, double valeurImportance) {
		super(nom);
		setValeurImportance(valeurImportance);
	}

	public double getValeurImportance() {
		return this.valeurImportance;
	}

	/**
	 * 
	 * @param valeurImportance
	 */
	public void setValeurImportance(double valeurImportance) {
		this.valeurImportance = valeurImportance;
	}
}
