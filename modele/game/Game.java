package modele.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import controleur.Controleur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import modele.elements.hitbox.HitBox;
import modele.elements.visuals.ExtendedRectangle;
import modele.game.game_objects.Bias;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.GameObjectType;
import modele.game.game_objects.Player;
import modele.game.game_objects.PlayerAI;
import modele.graphique.GraphiqueIA;

public class Game implements Bias, Update, Render {

	public static final short MAX_NB_PLAYERS = 20;
	public static final float SPAWN_ENEMY_BIAS = 0.1f;

	private List<Player> playersSet = new LinkedList<>();
	private List<Enemy> enemiesSet = new LinkedList<>();
	private List<ExtendedRectangle> playerImagesSet = new LinkedList<>();
	private List<ExtendedRectangle> enemyImagesSet = new LinkedList<>();

	private ArrayList<ArrayList<Integer>> collisionIndexList = new ArrayList<>();

	private GameState gameState = GameState.STOPPED;
	private EnemySpawner hbGen;
	private IntegerProperty score;
	private GraphiqueIA graph;

	public final Consumer<Void> updater = (v) -> update();
	public final Consumer<Void> renderer = (v) -> render();

	public Game(short nbHumans, short nbAI, GraphiqueIA graph) {
		hbGen = new EnemySpawner();
		short trueNbHumans = filterNbHumans(nbHumans);

		for (int i = 0; i < trueNbHumans; i++) {
			createPlayer(GameObjectType.HUMAN);
		}
		for (int j = 0; j < nbAI; j++) {
			createPlayer(GameObjectType.AI);
		}
		score = new SimpleIntegerProperty();
		this.graph = graph;
	}

	public ArrayList<ArrayList<Integer>> getCollisionIndexList() {
		return collisionIndexList;
	}

	public List<ExtendedRectangle> getPlayerImagesSet() {
		return playerImagesSet;
	}

	public List<ExtendedRectangle> getEnemyImagesSet() {
		return enemyImagesSet;
	}

	public int getScore() {
		return score.get();
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public void setScore(int score) {
		this.score.set(score);
	}

	public void printGameState() {
		System.out.println(gameState);
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

	public void createPlayer(GameObjectType pType) {
		HitBox hb = new HitBox(Player.PLAYER_DIM, Player.PLAYER_DIM, 50, Controleur.MID_HEIGHT);
		Player p = new Player(pType, hb);

		playersSet.add(p);

	}

	public List<Player> getPlayersSet() {
		return playersSet;
	}

	public List<Enemy> getEnemiesSet() {
		return enemiesSet;
	}

	public void render() {
		if (isRunning()) {

			List<ExtendedRectangle> playerImagesBufferList = new LinkedList<>();
			List<ExtendedRectangle> enemyImagesBufferList = new LinkedList<>();

			List<Player> playerBufferList = new LinkedList<>();
			List<Enemy> enemyBufferList = new LinkedList<>();

			// On remplit les buffers avec tous les GameObject qui ont une image
			playerImagesSet.forEach(pI -> playerBufferList.add((Player) pI.getGameObject()));
			enemyImagesSet.forEach(eI -> enemyBufferList.add((Enemy) eI.getGameObject()));

			// On collecte toutes les images qui n'ont plus de GameObject dans
			// le jeu, puis on les retire de la liste
			playerImagesSet.forEach(pI -> {
				if (!playersSet.contains(pI.getGameObject())) {
					playerImagesBufferList.add(pI);
				}
			});
			enemyImagesSet.forEach(pI -> {
				if (!enemiesSet.contains(pI.getGameObject())) {
					enemyImagesBufferList.add(pI);
				}
			});

			playerImagesSet.removeAll(playerImagesBufferList);
			enemiesSet.removeAll(enemyImagesBufferList);

			// On ajoute une image à tous les GameObject qui n'ont pas
			// présentement d'image
			playersSet.forEach(p -> {
				if (!playerBufferList.contains(p)) {
					ExtendedRectangle eR = new ExtendedRectangle(p);
					playerImagesSet.add(eR);
				}
			});
			enemiesSet.forEach(p -> {
				if (!enemyBufferList.contains(p)) {
					ExtendedRectangle eR = new ExtendedRectangle(p);
					playerImagesSet.add(eR);
				}
			});

		}
	}

	public void update() {
		if (isRunning()) {

			List<Player> playerBufferList = new LinkedList<>();
			List<Enemy> enemyBufferList = new LinkedList<>();

			playersSet.forEach(p -> {
				p.checkObjectBeyondEdges();
				handleMovement(p.getHitBox());
			});
			enemiesSet.forEach(e -> {
				handleMovement(e.getHitBox());
				if (e.checkObjectBeyondEdges())
					enemyBufferList.add(e);
			});

			score.set(score.get() + 1);

			playersSet.forEach(p -> enemiesSet.forEach(e -> {
				if (p.getHitBox().checkCollision(e.getHitBox())) {
					System.out.println("collision");
					playerBufferList.add(p);
				}
			}));

			playersSet.forEach(p -> {
				if (p.getObjectType() == GameObjectType.AI) {
					ArrayList<Double> indexListBuffer = new ArrayList<>();

					p.getvGrid().getHitBoxes().forEach(hb -> enemiesSet.forEach(e -> {
						if (hb.checkCollision(e.getHitBox())) {
							indexListBuffer.add((double) p.getvGrid().getHitBoxes().indexOf(hb));
						}

//						List<Double> listeEntreesNumeriquesDeP = ((PlayerAI) p).getListeEntreesNumeriques();
//
//						for (int i = 0; i < listeEntreesNumeriquesDeP.size(); i++) {
//
//						}

					}));

					p.setListeIndexEntrees(indexListBuffer);
					p.appliquerIndex();
				}
			});

			playersSet.removeAll(playerBufferList);
			enemiesSet.removeAll(enemyBufferList);

			playerBufferList.clear();
			enemyBufferList.clear();

			if (playersSet.size() == 0)
				stop();
		}
	}

	private short filterNbHumans(short nbHumans) {
		return (short) (nbHumans == 0 ? 0 : 1);
	}

	enum GameState {
		RUNNING("En Cours"), PAUSED("En Pause"), STOPPED("Arrêtée");

		String state = "";

		GameState(String state) {
			this.state = state;
		}

		public String toString() {
			return state;
		}
	}

}