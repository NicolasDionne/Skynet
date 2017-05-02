package utilitaires.parametres;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.VBox;

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
		nbLignesSp = new IntegerSpinnerValueFactory(2, 6);
		nbColonnesSp = new IntegerSpinnerValueFactory(2, 10);
		nbNiveauxSp = new IntegerSpinnerValueFactory(1, 6);
		nbNeuronesPNSp = new IntegerSpinnerValueFactory(2, 7);

		spinnerNbLignes = new Spinner<>(nbLignesSp);
		spinnerNbColonnes = new Spinner<>(nbColonnesSp);
		spinnerNbNiveaux = new Spinner<>(nbNiveauxSp);
		spinnerNbNeuronesPN = new Spinner<>(nbNeuronesPNSp);

		lignesVBox.getChildren().add(spinnerNbLignes);
		colonnesVBox.getChildren().add(spinnerNbColonnes);
		niveauxVBox.getChildren().add(spinnerNbNiveaux);
		neuronesVBox.getChildren().add(spinnerNbNeuronesPN);
	}

	public Spinner<Integer> getSpinnerNbLignes() {
		return spinnerNbLignes;
	}

	public Spinner<Integer> getSpinnerNbColonnes() {
		return spinnerNbColonnes;
	}

	public Spinner<Integer> getSpinnerNbNiveaux() {
		return spinnerNbNiveaux;
	}

	public Spinner<Integer> getSpinnerNbNeuronesPN() {
		return spinnerNbNeuronesPN;
	}
}
