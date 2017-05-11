package modele.game.game_objects;

import javafx.scene.paint.Color;

/**
 *
 * @author Bénange Breton
 *
 *         Énumération des différents types de GameObject possibles. Retourne
 *         une couleur et un nom de style css associé à chaque type, pour
 *         facilement modifier l'apparence des divers types d'éléments
 */
public enum GameObjectType {
	HUMAN(Color.GREEN, "joueurHumain"), AI(Color.BLUE, "joueurAI"), ENEMY(Color.BLACK,
			"obstacle"), VOID(Color.GRAY, "voidImage");

	Color color = null;
	String style = "";

	GameObjectType(Color color, String style) {
		this.color = color;
		this.style = style;
	}

	public Color getColor() {
		return color;
	}

	public String getStyle() {
		return style;
	}
}
