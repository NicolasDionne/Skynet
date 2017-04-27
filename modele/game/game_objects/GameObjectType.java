package modele.game.game_objects;

import javafx.scene.paint.Color;

public enum GameObjectType {
    HUMAN(Color.RED, "images/joueur.png"), AI(Color.BLUE, "images/joueur.png"), ENEMY(Color.BLACK, "images/obstacle.png"), VOID(Color.GRAY, "");

    Color color = null;
    String url = "";

    GameObjectType(Color color, String url) {
        this.color = color;
        this.url = url;
    }

    public Color getColor() {
        return color;
    }

    public String getURL() {
        return url;
    }
}