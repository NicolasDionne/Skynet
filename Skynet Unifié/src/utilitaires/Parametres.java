package utilitaires;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Parametres extends SimpleObjectProperty implements Serializable {

	private static final long serialVersionUID = 8644035316408438516L;

	private IntegerProperty nbColonnes = new SimpleIntegerProperty();
	private IntegerProperty nbLignes = new SimpleIntegerProperty();
	private IntegerProperty nbNiveaux = new SimpleIntegerProperty();
	private IntegerProperty nbNeuronesParNiveau = new SimpleIntegerProperty();

	/**
	 * @param nbColonnes
	 * @param nbLignes
	 * @param nbNiveaux
	 * @param nbNeuronesParNiveau
	 */
	public Parametres(int nbColonnes, int nbLignes, int nbNiveaux, int nbNeuronesParNiveau) {
		super();
		this.nbColonnes.set(nbColonnes);
		this.nbLignes.set(nbLignes);
		this.nbNiveaux.set(nbNiveaux);
		this.nbNeuronesParNiveau.set(nbNeuronesParNiveau);
	}

	public IntegerProperty getNbColonnes() {
		return nbColonnes;
	}

	public void setNbColonnes(IntegerProperty nbColonnes) {
		this.nbColonnes = nbColonnes;
	}

	public int getValNbColonnes() {
		return nbColonnes.get();
	}

	public IntegerProperty getNbLignes() {
		return nbLignes;
	}

	public void setNbLignes(IntegerProperty nbLignes) {
		this.nbLignes = nbLignes;
	}

	public void setValNbColonnes(int nbColonnes) {
		this.nbColonnes.set(nbColonnes);
	}

	public IntegerProperty getNbNiveaux() {
		return nbNiveaux;
	}

	public void setNbNiveaux(IntegerProperty nbNiveaux) {
		this.nbNiveaux = nbNiveaux;
	}

	public int getValNbLignes() {
		return nbLignes.get();
	}

	public void setValNbLignes(int nbLignes) {
		this.nbLignes.set(nbLignes);
	}

	public int getValNbNiveaux() {
		return nbNiveaux.get();
	}

	public void setValNbNiveaux(int nbNiveaux) {
		this.nbNiveaux.set(nbNiveaux);
	}

	public IntegerProperty getNbNeuronesParNiveau() {
		return nbNeuronesParNiveau;
	}

	public void setNbNeuronesParNiveau(IntegerProperty nbNeuronesParNiveau) {
		this.nbNeuronesParNiveau = nbNeuronesParNiveau;
	}

	public int getValNbNeuronesParNiveau() {
		return nbNeuronesParNiveau.get();
	}

	public void setValNbNeuronesParNiveau(int nbNeuronesParNiveau) {
		this.nbNeuronesParNiveau.set(nbNeuronesParNiveau);
	}

}
