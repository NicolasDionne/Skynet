package modele.game;

import controleur.Controleur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import modele.elements.HitBox;
import modele.game.game_objects.Bias;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.Player;
import modele.game.game_objects.PlayerType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game implements Bias {

	public static final short MAX_NB_PLAYERS = 20;

	public static final float SPAWN_ENEMY_BIAS = 0.1f;

	private ArrayList<Player> playersSet = new ArrayList<>();
	private LinkedList<Enemy> enemiesSet = new LinkedList<>();
	private GameState gameState = GameState.STOPPED;
	private HitBoxGenerator hbGen;

	public Game(short nbHumans, short nbAI) {
		hbGen = new HitBoxGenerator();
		short trueNbHumans = filterNbHumans(nbHumans);

		for (int i = 0; i < trueNbHumans; i++) {
			createPlayer(PlayerType.HUMAN);
		}
		for (int i = trueNbHumans; i < trueNbHumans + nbAI; i++) {
			createPlayer(PlayerType.AI);
		}
	}

	public void doActions() {

		if (isRunning()) {

			List<Player> playerBufferList = new ArrayList<>();
			List<Enemy> enemyBufferList = new ArrayList<>();

			playersSet.forEach(p -> {
				p.checkObjectBeyondEdges();
				handleMovement(p.getHitBox());
			});
			enemiesSet.forEach(e -> {
				handleMovement(e.getHitBox());
				if (e.checkObjectBeyondEdges())
					enemyBufferList.add(e);
			});
			playersSet.forEach(p -> enemiesSet.forEach(e -> {
				if (p.getHitBox().checkCollision(e.getHitBox())) {
					// System.out.println("collision");
					playerBufferList.add(p);
				}
			}));

			playerBufferList.forEach(pB -> playersSet.remove(pB));
			enemyBufferList.forEach(eB -> enemiesSet.remove(eB));

			playerBufferList.clear();
			enemyBufferList.clear();

			if (playersSet.size() == 0)
				stop();
		}
	}

	public boolean isRunning() {
		return gameState == GameState.RUNNING;
	}

	public boolean isPaused() {
		return gameState == GameState.PAUSED;
	}

	public boolean isStopped() {
		return gameState == GameState.STOPPED;
	}

	public void run() {
		gameState = GameState.RUNNING;
	}

	public void pause() {
		gameState = GameState.PAUSED;
	}

	public void stop() {
		gameState = GameState.STOPPED;
	}

	public Enemy spawnEnemy() {
		HitBox hb = hbGen.spawn(Enemy.ENEMY_DIM);
		Enemy toSpawn = new Enemy(hb);
		enemiesSet.add(toSpawn);

		return toSpawn;
	}

	private void handleMovement(HitBox hb) {
		hb.getCenterPoint().move();
		hb.moveOrigin();
		hb.rotateSelf();
		hb.getCenterPoint().rotate(hb.getOrigin());
	}

	public void createPlayer(PlayerType pType) {
		HitBox hb = new HitBox(Player.PLAYER_DIM, Player.PLAYER_DIM, 50, Controleur.MID_HEIGHT);

		Player p = new Player(pType, hb);
		playersSet.add(p);

	}

	public ArrayList<Player> getPlayersSet() {
		return playersSet;
	}

	public LinkedList<Enemy> getEnemiesSet() {
		return enemiesSet;
	}

	enum GameState {
		RUNNING, PAUSED, STOPPED;
	}

	private short filterNbHumans(short nbHumans) {
		return (short) (nbHumans == 0 ? 0 : 1);
	}
}
