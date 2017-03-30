package modele.game;


import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.exceptions.ConstructorException;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    public static final short MAX_NB_PLAYERS = 20;

    private static boolean isGameRunning = false;
    private int score;
    private ArrayList<Player> playersSet = new ArrayList<>();
    private LinkedList<Enemy> enemiesSet = new LinkedList<>();


    public Game(short numberOfPlayers) throws ConstructorException {
        for (int i = 0; i < numberOfPlayers; i++) {
            createPlayer();
        }
    }

    public void run() {
        isGameRunning = true;
        if (isGameRunning) {
            playersSet.forEach(p -> {
                p.checkObjectBeyondEdges();
                handleMovement(p.getHitBox());
            });
            enemiesSet.forEach(e -> {
                handleMovement(e.getHitBox());

                if (e.checkObjectBeyondEdges()) {
                    enemiesSet.remove(e);
                    e = null;
                }
            });
            playersSet.forEach(p -> {
                enemiesSet.forEach(e -> {
                    if (e.getHitBox().checkCollision(p.getHitBox())) {
                        isGameRunning = false;
                    }
                });
            });
        }

    }

    private void handleMovement(HitBox hb) {
        hb.getCenterPoint().move();
        hb.moveOrigin();
        hb.rotateSelf();
        hb.getCenterPoint().rotate(hb.getOrigin());
    }

    public void changePlayerDirection(int index, float accelerationFactor, double angle) {
        if (index < playersSet.size() && index >= 0) {

            MotionPoint mp = playersSet.get(index).getHitBox().getCenterPoint();
            mp.setAcceleration(mp.acceleration() + accelerationFactor);

        }
    }

    public void createEnemy() throws ConstructorException {
        Enemy e = new Enemy();
        enemiesSet.add(e);
    }

    public void createPlayer() throws ConstructorException {
        Player p = new Player();
        playersSet.add(p);
        p.getHitBox().getCenterPoint().setX(200);
        p.getHitBox().getCenterPoint().setY(200);
    }

    public static boolean isIsGameRunning() {
        return isGameRunning;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Player> getPlayersSet() {
        return playersSet;
    }


    public LinkedList<Enemy> getEnemiesSet() {
        return enemiesSet;
    }

}
