package ai.coeur;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * 
 * @author 1537391
 *
 */
public class Importance implements Serializable, Cloneable {

	private static final long serialVersionUID = 6067923657536531357L;

	/**
	 * ValImportance de l'importance
	 */
	public double valImportance;

	/**
	 * Changement d'importance
	 */
	public transient double changementImportance;

	/**
	 * l'info tampon d'exercice contient des données algorythmiques spécifiques
	 * variées qui sont utilisées pour ajuster cette valeur d'importance pendant
	 * l'entrainement
	 */
	private transient Object trainingData;

	/**
	 * Crée un instance de connection d'importance avec une valeur d'importance
	 * d'une portée de [0..1]
	 */
	public Importance() {
		this.valImportance = Math.random() - 0.5d;
		this.changementImportance = 0;
	}

	/**
	 * Crée une importance
	 * 
	 * @param valImportance
	 *            double, la valeur de l'importance lors de la création
	 */
	public Importance(double valImportance) {
		this.valImportance = valImportance;
	}

	/**
	 * augmente l'importance de la valeur indiquée
	 * 
	 * @param quantite
	 *            double, la valImportance à ajouter à l'importance
	 */
	public void augmenterVal(double quantite) {
		this.valImportance += quantite;
	}

	/**
	 * diminue l'importance de la valeur indiquée
	 * 
	 * @param quantite
	 *            double, la valeur à soustraire à l'importance
	 */
	public void diminuerVal(double quantite) {
		this.valImportance -= quantite;
	}

	public void setValImportance(double valImportance) {
		this.valImportance = valImportance;
	}

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

	public void setImportanceAleatoire(Random generator) {
		this.valImportance = generator.nextDouble();
	}

	public Object getTrainingData() {
		return trainingData;
	}

	public void setTrainingData(Object trainingData) {
		this.trainingData = trainingData;
	}

	@Override

	public Object clone() throws CloneNotSupportedException {
		Importance cloned = (Importance) super.clone();
		cloned.setTrainingData(new Object());
		return cloned;
	}

	@Override
	public boolean equals(Object obj) {
		boolean so = false;
		if (this == obj) {
			so = true;
		}
		if (obj == null) {
			so = false;
		}
		if (getClass() != obj.getClass()) {
			so = false;
		}
		final Importance autre = (Importance) obj;
		if (Double.doubleToLongBits(this.valImportance) != Double.doubleToLongBits(autre.valImportance)) {
			so = false;
		}
		if (Double.doubleToLongBits(this.changementImportance) != Double.doubleToLongBits(autre.changementImportance)) {
			so = false;
		}
		if (!Objects.equals(this.trainingData, autre.trainingData)) {
			so = false;
		}
		return so;
	}

	@Override
	public String toString() {
		return String.valueOf(valImportance);
	}
}
