package ai.coeur;

import java.io.Serializable;
import java.util.Objects;

import utilitaires.Sortable;

/**
 * La classe <code>Lien</code> permet de lier entre elles les neurones. Un lien
 * possède une neurone à partir de laquelle il provient, une neurone jusqu'à
 * laquelle il se rend et une importance.
 * </p>
 * Le lien sert à faire passer la valeur de sortie de la neurone à partir de
 * laquelle il provient jusqu'à celle où il se rend. L'importance, qui est un
 * instance de la classe <code>Importance</code>, du lien sert à rendre se
 * dernier plus ou moins fort.
 * 
 * @see Importance
 * @see Neurone
 */
public class Lien implements Serializable, Sortable {

	private static final long serialVersionUID = 2484872430905593388L;

	/**
	 * La neurone à partir de laquelle le lien provient.
	 */
	protected Neurone aPartirDeNeurone;

	/**
	 * La neurone jusqu'à laquelle le lien se rend.
	 */
	protected Neurone jusquANeurone;

	/**
	 * L'importance du lien.
	 */
	private Importance importance;

	/**
	 * Le nom du lien.
	 */
	private String nom;

	/**
	 * Crée le lien entre les deux neurones spécifiées.
	 * 
	 * @param aPartirDeNeurone
	 *            <code>Neurone</code>, la neurone à partir de laquelle le lien
	 *            part.
	 * @param jusquANeurone
	 *            <code>Neurone</code>, la neurone qui reçoit le lien.
	 */
	public Lien(Neurone aPartirDeNeurone, Neurone jusquANeurone) {
		super();
		this.aPartirDeNeurone = aPartirDeNeurone;
		this.jusquANeurone = jusquANeurone;
		this.importance = new Importance();
		this.setNom();
	}

	/**
	 * Crée le lien entre les deux neurones spécifiées avec une importance
	 * spécifiée.
	 * 
	 * @param aPartirDeNeurone
	 *            <code>Neurone</code>, la neurone à partir de laquelle le lien
	 *            part.
	 * @param jusquANeurone
	 *            <code>Neurone</code>, la neurone qui reçoit le lien.
	 * @param importance
	 *            <code>Importance</code>, l'importance du lien créé.
	 */
	public Lien(Neurone aPartirDeNeurone, Neurone jusquANeurone, Importance importance) {
		this(aPartirDeNeurone, jusquANeurone);
		this.importance = importance;
	}

	/**
	 * Crée le lien entre les deux neurones spécifiées avec une importance
	 * spécifiée par un double.
	 * 
	 * @param aPartirDeNeurone
	 *            <code>Neurone</code>, la neurone à partir de laquelle le lien
	 *            part.
	 * @param jusquANeurone
	 *            <code>Neurone</code>, la neurone qui reçoit le lien.
	 * @param valImportance
	 *            <code>double</code>, la valeur de l'improtance du lien.
	 */
	public Lien(Neurone aPartirDeNeurone, Neurone jusquANeurone, double valImportance) {
		this(aPartirDeNeurone, jusquANeurone, new Importance(valImportance));
	}

	/**
	 * Retourne l'importance du lien.
	 * 
	 * @return <code>Importance</code>, l'importance du lien.
	 */
	public Importance getImportance() {
		return importance;
	}

	/**
	 * Définit l'importance du lien.
	 * 
	 * @param importance
	 *            <code>Importance</code>, l'importance voulue.
	 */
	public void setImportance(Importance importance) {
		this.importance = importance;
	}

	/**
	 * Retourne la valeur de sortie de la neurone à partir de laquelle le lien
	 * provient, et donc la valeur que reçoit la neuronne jusqu'à laquelle le
	 * lien se rend.
	 * 
	 * @return <code>double</code>, la valeur entrant dans le lien.
	 */
	public double getEntree() {
		return this.aPartirDeNeurone.getSortie();
	}

	/**
	 * Retourne la valeur passant par le lien multipliée par l'importance du
	 * lien.
	 * 
	 * @return <code>double</code>, la valeur passant par le lien selon son
	 *         importance.
	 */
	public double getEntreeSelonImportance() {
		return this.aPartirDeNeurone.getSortie() * importance.getValImportance();
	}

	/**
	 * Retourne la neurone à partir de laquelle le lien provient.
	 * 
	 * @return <code>Neurone</code>, la neurone à partir de laquelle le lien
	 *         provient.
	 */
	public Neurone getAPartirDeNeurone() {
		return aPartirDeNeurone;
	}

	/**
	 * Retourne la neurone jusqu'à laquelle le lien se rend.
	 * 
	 * @return <code>Neurone</code>, la neurone jusqu'à laquelle le lien se
	 *         rend.
	 */
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
		} else if (obj == null) {
			so = false;
		} else if (getClass() != obj.getClass()) {
			so = false;
		} else {
			final Lien other = (Lien) obj;
			if (!Objects.equals(this.aPartirDeNeurone, other.aPartirDeNeurone)) {
				so = false;
			} else if (!Objects.equals(this.jusquANeurone, other.jusquANeurone)) {
				so = false;
			} else if (!this.importance.equals(other.getImportance())) {
				so = false;
			}
		}
		return so;
	}

	@Override
	public String toString() {
		return "Lien{" + "aPartirDeNeurone=" + aPartirDeNeurone + ", jusquANeurone=" + jusquANeurone + ", importance="
				+ importance + '}';
	}

	/**
	 * Définit automatiquement le nom du lien à des fins pratiques, soit la même
	 * chose que le toString du lien, mais sans nommer l'importance.
	 */
	public void setNom() {
		this.nom = "Lien{" + "aPartirDeNeurone=" + aPartirDeNeurone + ", jusquANeurone=" + jusquANeurone + '}';
	}

	/**
	 * Retourne le nom du lien.
	 * 
	 * @return <code>String</code>, le nom du lien.
	 */
	public String getNom() {
		return nom;
	}

	@Override
	public int getValeurLorsSort() {
		return getNom().hashCode();
	}

}
