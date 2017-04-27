package ai.apprentissage.nonsupervise.competitionInter.competitionInterLiens;

import java.io.Serializable;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GetValModification implements Serializable {

	private static final long serialVersionUID = -2318675117191416730L;

	private double val;
	private transient Label label;
	private transient Slider io;
	private transient VBox vBox;
	private transient Scene scene;
	private transient Stage stage;

	/**
	 * 
	 */
	public GetValModification() {
		this.val = 10;
		setIo();
		this.label = new Label();
		this.label.textProperty().bind(this.io.valueProperty().asString());
		this.vBox = new VBox();
		this.vBox.getChildren().add(label);
		this.vBox.getChildren().add(io);
		this.scene = new Scene(vBox);
		this.stage = new Stage();
		this.stage.setScene(scene);
		setStage();
	}

	public double getVal() {
		if (vBox == null || scene == null || stage == null) {
			setIo();
			this.label = new Label();
			this.label.textProperty().bind(this.io.valueProperty().asString());
			this.vBox = new VBox();
			this.vBox.getChildren().add(label);
			this.vBox.getChildren().add(io);
			this.scene = new Scene(vBox);
			this.stage = new Stage();
			this.stage.setScene(scene);
			setStage();
		}
		this.stage.showAndWait();
		return val;
	}

	private void setIo() {
		this.io = new Slider(0.00001, 10, val);
		this.io.setMajorTickUnit(0.001);
		this.io.setMinorTickCount(100);
		io.setSnapToTicks(true);
		this.io.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@SuppressWarnings("incomplete-switch")
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					if (event.isControlDown()) {
						if (event.isAltDown()) {
							io.setValue(io.getValue() + 0.01);
						} else {
							io.setValue(io.getValue() + 0.001);
						}
					} else {
						io.setValue(io.getValue() + 0.00001);
					}
					break;

				case DOWN:
					if (event.isControlDown()) {
						if (event.isAltDown()) {
							io.setValue(io.getValue() - 0.01);
						} else {
							io.setValue(io.getValue() - 0.001);
						}
					} else {
						io.setValue(io.getValue() - 0.00001);
					}
					break;
				}

			}
		});
	}

	private void setStage() {
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				val = io.getValue();
			}
		});
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		GetValModification clone = new GetValModification();
		clone.val = this.val;
		clone.label = new Label();
		clone.io = new Slider(0.00001, 10, clone.val);
		clone.setIo();
		clone.label.textProperty().bind(clone.io.valueProperty().asString());
		clone.vBox = new VBox();
		clone.vBox.getChildren().add(clone.label);
		clone.vBox.getChildren().add(clone.io);
		clone.scene = new Scene(clone.io);
		clone.stage = new Stage();
		clone.stage.setScene(clone.scene);
		return clone;
	}

}
