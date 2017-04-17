package ai.coeur;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * Détermine l'importance des liens.
 */
public class Importance implements Serializable, Cloneable {

	private static final long serialVersionUID = 6067923657536531357L;

	/**
	 * La valeur de l'importance.
	 */
	public double valImportance;

	/**
	 * La valeur du changement d'importance.
	 * </p>
	 * N'est pas utilisée par certaines règles d'apprentissage.
	 */
	public transient double changementImportance;

	/**
	 * L'info tampon d'exercice contient des données algorythmiques spécifiques
	 * variées qui sont utilisées pour ajuster cette valeur d'importance pendant
	 * l'entrainement.
	 * </p>
	 * Ce paramêtre n'est presque jamais utilisé. Il ne l'est qu'avec certaines
	 * règles d'apprentissage.
	 */
	private transient Object donneeEntrainement;

	/**
	 * Crée un instance de connection d'importance avec une valeur d'importance
	 * d'une portée de [0..1].
	 */
	public Importance() {
		this.valImportance = Math.random() - 0.5d;
		this.changementImportance = 0;
	}

	/**
	 * Crée une importance selon la valeur spécifiée.
	 * 
	 * @param valImportance
	 *            double, la valeur de l'importance lors de la cr�ation
	 */
	public Importance(double valImportance) {
		this.valImportance = valImportance;
	}

	/**
	 * Augmente l'importance de la valeur indiquée.
	 * 
	 * @param quantite
	 *            double, la valImportance à ajouter à l'importance.
	 */
	public void augmenterVal(double quantite) {
		this.valImportance += quantite;
	}

	/**
	 * Diminue l'importance de la valeur indiquée.
	 * 
	 * @param quantite
	 *            double, la valeur à soustraire à l'importance.
	 */
	public void diminuerVal(double quantite) {
		this.valImportance -= quantite;
	}

	/**
	 * Définit la valeur de l'importance.
	 * 
	 * @param valImportance
	 *            double, la valeur spécifiée.
	 */
	public void setValImportance(double valImportance) {
		this.valImportance = valImportance;
	}

	/**
	 * Retourne la valeur de l'importance.
	 * 
	 * @return double, la valeur de l'importance.
	 */
	public double getValImportance() {
		return this.valImportance;
	}

	/**
	 * donne une valImportance aléatoire à l'importance d'une portée de [0..1]
	 */
	public void setImportanceAleatoire() {
		this.valImportance = Math.random() - 0.5d;
	}

	/**
	 * donne une valeur aléatoire à l'importance d'une portée donnée
	 * 
	 * @param min
	 *            double, la valeur minimale de la portée possible de
	 *            l'importance
	 * @param max
	 *            double, la valeur maximale de la portée possible de
	 *            l'importance
	 */
	public void setImportanceAleatoire(double min, double max) {
		this.valImportance = min + Math.random() * (max - min);
	}

	/**
	 * Définit une importance aléatoire avec l'aide d'un Random spécifié.
	 * 
	 * @param randomizator
	 *            Random, le Random spécifié.
	 */
	public void setImportanceAleatoire(Random randomizator) {
		this.valImportance = randomizator.nextDouble();
	}

	/**
	 * Retourne la donnée d'entrainement de l'importance.
	 * 
	 * @return Object, la donnée d'entrainement.
	 */
	public Object getDonneeEntrainement() {
		return donneeEntrainement;
	}

	/**
	 * Définit la donnée d'entrainement de l'importance.
	 * 
	 * @param donneeEntrainement
	 *            Object, la donnée d'entrainement.
	 */
	public void setDonneeEntrainement(Object donneeEntrainement) {
		this.donneeEntrainement = donneeEntrainement;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Importance cloned = (Importance) super.clone();
		cloned.setDonneeEntrainement(new Object());
		return cloned;
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

			final Importance autre = (Importance) obj;

			if (Double.doubleToLongBits(this.valImportance) != Double.doubleToLongBits(autre.valImportance)) {
				so = false;
			} else if (Double.doubleToLongBits(this.changementImportance) != Double
					.doubleToLongBits(autre.changementImportance)) {
				so = false;
			} else if (!Objects.equals(this.donneeEntrainement, autre.donneeEntrainement)) {
				so = false;
			}
		}

		return so;

	}

	@Override
	public String toString() {
		return String.valueOf(valImportance);
	}
}
