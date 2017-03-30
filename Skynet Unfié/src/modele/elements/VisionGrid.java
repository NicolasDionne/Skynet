package modele.elements;

import modele.exceptions.ConstructorException;

import java.util.ArrayList;

public class VisionGrid {

    public static final int NB_ROWS = 4;
    public static final int NB_COLUMNS = 10;
    public static final int CELL_HEIGHT = 20;
    public static final int CELL_LENGTH = 20;


    public ArrayList<HitBox> hitBoxes = new ArrayList<>();

    public VisionGrid(HitBox originBox) throws ConstructorException {
        MotionPoint c = originBox.getCenterPoint();
        float topY = (CELL_HEIGHT * NB_ROWS / 2.0f) - (CELL_HEIGHT / 2.0f);
        float leftX = CELL_LENGTH * NB_COLUMNS / 2.0f - CELL_LENGTH / 2.0f;

        for (int i = 0; i < NB_ROWS; i++) {
            HitBox hb = new HitBox((short) (CELL_LENGTH * NB_COLUMNS), (short) 2);
            hb.setHitsOthers(true);
            hb.getCenterPoint().xProperty().bind(c.xProperty().add(leftX));
            hb.getCenterPoint().yProperty().bind(c.yProperty().subtract(topY).add(CELL_HEIGHT * i));

            hitBoxes.add(hb);
        }

        for (int i = 0; i < NB_COLUMNS; i++) {
            HitBox hb = new HitBox((short) (2), (short) (CELL_HEIGHT * NB_ROWS));
            hb.setHitsOthers(true);
            hb.getCenterPoint().xProperty().bind(c.xProperty().add(CELL_LENGTH * i));
            hb.getCenterPoint().yProperty().bind(c.yProperty());

            hitBoxes.add(hb);
        }
    }

}
