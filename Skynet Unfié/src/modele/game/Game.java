package modele.game;


import javafx.concurrent.Service;
import javafx.concurrent.Task;
import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.exceptions.ConstructorException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

public class Game {

    public static final short MAX_NB_PLAYERS = 20;

    private static boolean isGameRunning = false;
    private int score;
    private ArrayList<Player> playersSet = new ArrayList<>();
    private LinkedList<Enemy> enemiesSet = new LinkedList<>();

    public Game(short numberOfPlayers) throws ConstructorException {
        for (int i = 0; i < numberOfPlayers; i++) {
            Player p = new Player();
            playersSet.add(p);
        }
    }

    public void run() {
        isGameRunning = true;
        while (isGameRunning) {
            playersSet.forEach(p -> {
                handleMovement(p.getHitBox());
            });
            enemiesSet.forEach(e -> {
                handleMovement(e.getHitBox());
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
        hb.getOrigin().move();
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
