package modele;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MouvementJoueur {
	private static final int DISTANCE_MOUVEMENT = 10;
	private static final double HAUTEUR_MAX = -95;
	private static final double BASSEUR_MAX = 79;
	
	public Thread threadJoueur;
	public Task taskJoueur;
	/*public static void bougerLeJoueur(Joueur joueur, Pane pane, ImageView image) {
		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP: {
					System.out.println("avant: " + image.getY());
					if (image.getY() - image.getFitHeight() / 2 - DISTANCE_MOUVEMENT >= BASSEUR_MAX ) {
						image.setY(image.getY() - DISTANCE_MOUVEMENT);
						joueur.setPosition(image.getY() - DISTANCE_MOUVEMENT);
						System.out.println("apres: " + image.getY());
					}
					break;
				}
				case DOWN: {
					{
						System.out.println("avant: " + image.getY());
						if (image.getY() + image.getFitHeight() / 2 + DISTANCE_MOUVEMENT <= HAUTEUR_MAX) {
							image.setY(image.getY() + DISTANCE_MOUVEMENT);
							joueur.setPosition(image.getY() + DISTANCE_MOUVEMENT);
							System.out.println("apres: " + image.getY());
						}
						break;
					}
				}
				default:
					break;
				}
			}
		});
	}*/

}
