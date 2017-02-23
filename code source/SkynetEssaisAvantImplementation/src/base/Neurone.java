package base;

public class Neurone {
	private String nom;

	public Neurone(String nom) {
		setNom(nom);
	}

	public String getNom() {
		return this.nom;
	}

	/**
	 * 
	 * @param nom
	 */
	private void setNom(String nom) {
		this.nom = nom;
	}

}
