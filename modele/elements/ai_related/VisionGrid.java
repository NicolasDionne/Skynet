package modele.elements.ai_related;

import modele.elements.hitbox.HitBox;
import modele.elements.hitbox.MotionPoint;

import java.util.ArrayList;

/**
 * Classe qui permet de créer une grille autour d'un joueur, qui sert à agir de
 * "vision" pour une intelligence artificielle.
 */

public class VisionGrid {

    public static final short NB_ROWS_DEFAULT = 4;
    public static final short NB_COLUMNS_DEFAULT = 10;
    public static final short CELL_HEIGHT = 40;
    public static final short CELL_WIDTH = 80;

    private ArrayList<HitBox> hitBoxes = new ArrayList<>();

    public VisionGrid(HitBox originBox, short nbRows, short nbColumns) {
        MotionPoint c = originBox.getCenterPoint();
        float topY = (CELL_HEIGHT * nbRows / 2.0f) - (CELL_HEIGHT / 2.0f);
        float leftX = CELL_WIDTH / 2.0f;

        for (int i = 0; i < nbColumns; i++) {
            for (int j = 0; j < nbRows; j++) {
                HitBox hb = new HitBox(CELL_WIDTH, CELL_HEIGHT);
                hb.setCanHitOthers(true);

                hb.getCenterPoint().xProperty().bind(c.xProperty().add(CELL_WIDTH * i).add(leftX));
                hb.getCenterPoint().yProperty().bind(c.yProperty().subtract(topY).add(CELL_HEIGHT * j));

                hitBoxes.add(hb);
            }
        }
    }

    public VisionGrid(HitBox originBox) {
        this(originBox, NB_ROWS_DEFAULT, NB_COLUMNS_DEFAULT);
    }

    /**
     * @return la liste des hitbox que contient la grille.
     */
    public ArrayList<HitBox> getHitBoxes() {
        return hitBoxes;
    }
}
