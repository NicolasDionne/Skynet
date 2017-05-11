package utilitaires.parametres;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.VBox;
import utilitaires.Parametres;

/**
 * Controleur pour la vue lors de la sélection des attributs de
 * <code>Parametres</code>.
 * 
 * @see Parametres
 */
public class ModificateurParametres {

	@FXML
	private VBox lignesVBox;

	@FXML
	private VBox colonnesVBox;

	@FXML
	private VBox niveauxVBox;

	@FXML
	private VBox neuronesVBox;
	Spinner<Integer> spinnerNbLignes;
	Spinner<Integer> spinnerNbColonnes;
	Spinner<Integer> spinnerNbNiveaux;
	Spinner<Integer> spinnerNbNeuronesPN;

	private IntegerSpinnerValueFactory nbLignesSp;
	private IntegerSpinnerValueFactory nbColonnesSp;
	private IntegerSpinnerValueFactory nbNiveauxSp;
	private IntegerSpinnerValueFactory nbNeuronesPNSp;

	@FXML
	public void initialize() {

	}

	public Spinner<Integer> getSpinnerNbLignes() {
		return this.spinnerNbLignes;
	}

	public Spinner<Integer> getSpinnerNbColonnes() {
		return this.spinnerNbColonnes;
	}

	public Spinner<Integer> getSpinnerNbNiveaux() {
		return this.spinnerNbNiveaux;
	}

	public Spinner<Integer> getSpinnerNbNeuronesPN() {
		return this.spinnerNbNeuronesPN;
	}

	public void setSpinners(Parametres parametres) {
		this.spinnerNbLignes = new Spinner<>(2, 6, parametres.getValNbLig());
		this.spinnerNbColonnes = new Spinner<>(2, 10, parametres.getValNbCol());
		this.spinnerNbNiveaux = new Spinner<>(1, 25, parametres.getValNbNiv());
		this.spinnerNbNeuronesPN = new Spinner<>(2, 20, parametres.getValNbNeuPN());

		this.spinnerNbLignes.setEditable(true);
		this.spinnerNbColonnes.setEditable(true);
		this.spinnerNbNiveaux.setEditable(true);
		this.spinnerNbNeuronesPN.setEditable(true);

		this.lignesVBox.getChildren().add(this.spinnerNbLignes);
		this.colonnesVBox.getChildren().add(this.spinnerNbColonnes);
		this.niveauxVBox.getChildren().add(this.spinnerNbNiveaux);
		this.neuronesVBox.getChildren().add(this.spinnerNbNeuronesPN);
	}
}
