package modele.game.game_objects;

import javafx.scene.paint.Color;

public enum GameObjectType {
    HUMAN(Color.RED), AI(Color.BLUE), ENEMY(Color.BLACK), VOID(Color.GRAY);

    Color color = null;

    GameObjectType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
