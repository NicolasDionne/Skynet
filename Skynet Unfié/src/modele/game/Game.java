package modele.game;

import controleur.Controleur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import modele.elements.HitBox;
import modele.game.game_objects.Bias;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.Player;
import modele.game.game_objects.PlayerAI;
import modele.game.game_objects.PlayerType;
import modele.graphique.GraphiqueIA;
import modele.reseau.GenerateurReseau;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ai.apprentissage.nonsupervise.CompetitionInterReseaux;
import ai.coeur.Reseau;
import ai.coeur.apprentissage.RegleApprentissage;

public class Game implements Bias {

	public static final short MAX_NB_PLAYERS = 20;

	public static final float SPAWN_ENEMY_BIAS = 0.1f;

	private ArrayList<Player> playersSet = new ArrayList<>();
	private LinkedList<Enemy> enemiesSet = new LinkedList<>();
	private GameState gameState = GameState.STOPPED;
	private HitBoxGenerator hbGen;
	private CompetitionInterReseaux competitionInterReseaux;

	private ArrayList<Reseau<CompetitionInterReseaux>> listeReseau;

	public Game(short nbHumans, short nbAI, GraphiqueIA graph) {
		hbGen = new HitBoxGenerator();
		GenerateurReseau gR = new GenerateurReseau();
		short trueNbHumans = filterNbHumans(nbHumans);

		for (int i = 0; i < trueNbHumans; i++) {
			createPlayer();
		}
		gR.genererReseauCIR(nbAI, 8, 1, 4, 5, -50, 50);
		listeReseau = gR.getReseauxCIR();
		for (int i = 0; i < nbAI; i++) {
			createPlayerAI(listeReseau.get(i));
		}
	}

	public void doActions() {

		if (isRunning()) {

			List<Player> playerBufferList = new ArrayList<>();
			List<Enemy> enemyBufferList = new ArrayList<>();

			playersSet.forEach(p -> {
				p.checkObjectBeyondEdges();
				if (p.getClass() == PlayerAI.class) {
					p.changeDirection(0);
				}
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

	public void createPlayer() {
		HitBox hb = new HitBox(Player.PLAYER_DIM, Player.PLAYER_DIM, 50, Controleur.MID_HEIGHT);
		Player p = new Player(PlayerType.HUMAN, hb);

		playersSet.add(p);
	}

	public void createPlayerAI(Reseau<CompetitionInterReseaux> reseau) {
		HitBox hb = new HitBox(Player.PLAYER_DIM, Player.PLAYER_DIM, 50, Controleur.MID_HEIGHT);
		PlayerAI pAI = new PlayerAI(hb, reseau);

		playersSet.add(pAI);
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
