package ai.coeur;

import java.io.Serializable;
import java.util.Objects;

public class Lien implements Serializable {

	private static final long serialVersionUID = 2484872430905593388L;
	protected Neurone aPartirDeNeurone;
	protected Neurone jusquANeurone;
	private Importance importance;

	/**
	 * Crée le lien entre les deux neurones spécifiées
	 * 
	 * @param aPartirDeNeurone
	 *            Neurone, la neurone à partir de laquelle le lien part
	 * @param jusquANeurone
	 *            Neurone, la neurone qui reçoit le lien
	 */
	public Lien(Neurone aPartirDeNeurone, Neurone jusquANeurone) {
		super();
		this.aPartirDeNeurone = aPartirDeNeurone;
		this.jusquANeurone = jusquANeurone;
	}

	/**
	 * Crée le lien entre les deux neurones spécifiées avec une importance
	 * spécifiée
	 * 
	 * @param aPartirDeNeurone
	 *            Neurone, la neurone à partir de laquelle le lien part
	 * @param jusquANeurone
	 *            Neurone, la neurone qui reçoit le lien
	 * @param importance
	 *            Importance, l'importance du lien créé
	 */
	public Lien(Neurone aPartirDeNeurone, Neurone jusquANeurone, Importance importance) {
		this(aPartirDeNeurone, jusquANeurone);
		this.importance = importance;
	}

	/**
	 * Crée le lien entre les deux neurones spécifiées avec une importance
	 * spécifiée par un double
	 * 
	 * @param aPartirDeNeurone
	 *            Neurone, la neurone à partir de laquelle le lien part
	 * @param jusquANeurone
	 *            Neurone, la neurone qui reçoit le lien
	 * @param valImportance
	 *            double, la valeur de l'improtance du lien
	 */
	public Lien(Neurone aPartirDeNeurone, Neurone jusquANeurone, double valImportance) {
		this(aPartirDeNeurone, jusquANeurone, new Importance(valImportance));
	}

	public Importance getImportance() {
		return importance;
	}

	public void setImportance(Importance importance) {
		this.importance = importance;
	}

	public double getEntree() {
		return this.aPartirDeNeurone.getSortie();
	}

	public double getEntreeSelonImportance() {
		return this.aPartirDeNeurone.getSortie() * importance.getValImportance();
	}

	public Neurone getAPartirDeNeurone() {
		return aPartirDeNeurone;
	}

	public Neurone getJusquANeurone() {
		return jusquANeurone;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Lien clone = (Lien) super.clone();
		clone.setImportance((Importance) importance.clone());
		clone.aPartirDeNeurone = (Neurone) this.aPartirDeNeurone.clone();
		clone.jusquANeurone = (Neurone) this.jusquANeurone.clone();
		return clone;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Objects.hashCode(this.aPartirDeNeurone);
		hash = 67 * hash + Objects.hashCode(this.jusquANeurone);
		hash = 67 * hash + Objects.hashCode(this.importance);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		boolean so = true;
		if (this == obj) {
			so = true;
		}
		if (obj == null) {
			so = false;
		}
		if (getClass() != obj.getClass()) {
			so = false;
		}
		final Lien other = (Lien) obj;
		if (!Objects.equals(this.aPartirDeNeurone, other.aPartirDeNeurone)) {
			so = false;
		}
		if (!Objects.equals(this.jusquANeurone, other.jusquANeurone)) {
			so = false;
		}
		if (!Objects.equals(this.importance, other.importance)) {
			so = false;
		}
		return so;
	}

	@Override
	public String toString() {
		return "Lien{" + "aPartirDeNeurone=" + aPartirDeNeurone + ", jusquANeurone=" + jusquANeurone + ", importance="
				+ importance + '}';
	}
}
