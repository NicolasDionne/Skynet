package modele.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import ai.coeur.Reseau;
import controleur.Controleur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import modele.elements.hitbox.HitBox;
import modele.elements.visuals.ExtendedImageView;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.GameObjectType;
import modele.game.game_objects.Player;
import modele.game.game_objects.PlayerAI;
import modele.graphique.GraphiqueIA;
import modele.reseau.GenerateurReseauCIL;

public class Game implements Bias, Update, Render, Spawn {

	public static final short MAX_NB_PLAYERS = 20;
	public static final float SPAWN_ENEMY_BIAS = 0.1f;

	private List<Player> playersSet = new LinkedList<>();
	private List<Enemy> enemiesSet = new LinkedList<>();
	private List<ExtendedImageView> playerImagesSet = new LinkedList<>();
	private List<ExtendedImageView> enemyImagesSet = new LinkedList<>();

	private ArrayList<ArrayList<Integer>> collisionIndexList = new ArrayList<>();

	private GameState gameState = GameState.STOPPED;
	private EnemySpawner hbGen;
	private IntegerProperty score;
	private GraphiqueIA graph;
	private ArrayList<Reseau> listeReseauxCIR;

	private float timeBetweenEnemies = 0;
	private float timerScaleFactor = 1;
	private float difficultyIncrement = 0.05f;

	public final Consumer<Void> updater = (v) -> update();
	public final Consumer<Pane> renderer = (p) -> render(p);
	public final Consumer<Void> spawner = (v) -> spawn();

	public Game(short nbHumans, short nbAI, GraphiqueIA graph, ArrayList<Reseau> listeReseauxCIR, Controleur c) {

		score = new SimpleIntegerProperty();

		if (listeReseauxCIR == null && nbAI != 0) {
			System.out.println("asd");

			GenerateurReseauCIL g = new GenerateurReseauCIL();
			g.genererReseauCIL(nbAI, 40, 1, 7, 20, -100, 100);
			listeReseauxCIR = g.getReseauxCIR();
			this.listeReseauxCIR = listeReseauxCIR;
			c.setListeReseaux(this.listeReseauxCIR);
		}

		this.listeReseauxCIR = listeReseauxCIR;
		hbGen = new EnemySpawner();
		short trueNbHumans = filterNbHumans(nbHumans);

		for (int i = 0; i < trueNbHumans; i++) {
			createPlayer(GameObjectType.HUMAN, null);
		}
		for (int j = 0; j < nbAI; j++) {
			createPlayer(GameObjectType.AI, this.listeReseauxCIR.get(j));
		}

		this.graph = graph;
	}

	public ArrayList<ArrayList<Integer>> getCollisionIndexList() {
		return collisionIndexList;
	}

	public List<ExtendedImageView> getPlayerImagesSet() {
		return playerImagesSet;
	}

	public List<ExtendedImageView> getEnemyImagesSet() {
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

	public float getTimeBetweenEnemies() {
		return timeBetweenEnemies;
	}

	public void setTimeBetweenEnemies(float timeBetweenEnemies) {
		this.timeBetweenEnemies = timeBetweenEnemies;
	}

	public float getTimerScaleFactor() {
		return timerScaleFactor;
	}

	public void setTimerScaleFactor(float timerScaleFactor) {
		this.timerScaleFactor = timerScaleFactor;
	}

	public float getDifficultyIncrement() {
		return difficultyIncrement;
	}

	public void setDifficultyIncrement(float difficultyIncrement) {
		this.difficultyIncrement = difficultyIncrement;
	}

	public Enemy spawnEnemy() {
		HitBox hb = hbGen.spawn(Enemy.ENEMY_DIM);

		Enemy toSpawn = new Enemy(hb);
		enemiesSet.add(toSpawn);

		return toSpawn;
	}

	private void handleMovement(HitBox hb) {

		if (hb.getCenterPoint().getRotationParameters().getAngularVelocity() != 0
				&& hb.getCenterPoint().getRotationParameters().getAngularAcceleration() != 0)
			hb.getCenterPoint().rotate(hb.getOrigin());

		hb.getCenterPoint().move();
		hb.moveOrigin();
		hb.rotateSelf();

	}

	public void createPlayer(GameObjectType pType, Reseau reseau) {
		HitBox hb = new HitBox(Player.PLAYER_DIM, Player.PLAYER_DIM, 50, Controleur.MID_HEIGHT);
		Player p;

		if (pType.equals(GameObjectType.AI)) {
			p = createPlayerAI(hb, reseau);
		} else {
			p = new Player(pType, hb);
		}

		ExtendedImageView eI = new ExtendedImageView(p, "joueur");
		playerImagesSet.add(eI);

		p.scoreProperty().bind(this.scoreProperty());
		playersSet.add(p);

	}

	private PlayerAI createPlayerAI(HitBox hb, Reseau reseau) {
		PlayerAI p = new PlayerAI(hb, reseau);

		return p;
	}

	public List<Player> getPlayersSet() {
		return playersSet;
	}

	public List<Enemy> getEnemiesSet() {
		return enemiesSet;
	}

	@Override
	public void spawn() {
		timeBetweenEnemies = 0;
		timerScaleFactor += difficultyIncrement;

		Enemy enemy = spawnEnemy();
		ExtendedImageView r = new ExtendedImageView(enemy, "obstacle");

		enemyImagesSet.add(r);
	}

	@Override
	public void render(Pane pane) {

		List<ExtendedImageView> playerImagesBufferList = new LinkedList<>();
		List<ExtendedImageView> enemyImagesBufferList = new LinkedList<>();

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
		enemyImagesSet.removeAll(enemyImagesBufferList);

		// On ajoute une image à tous les GameObject qui n'ont pas
		// présentement d'image
		playersSet.forEach(p -> {
			if (!playerBufferList.contains(p)) {
				ExtendedImageView eIV = new ExtendedImageView(p, "joueur");
				playerImagesSet.add(eIV);
			}
		});
		enemiesSet.forEach(p -> {
			if (!enemyBufferList.contains(p)) {
				ExtendedImageView eIV = new ExtendedImageView(p, "obstacle");
				enemyImagesSet.add(eIV);
			}
		});

		pane.getChildren().clear();
		pane.getChildren().addAll(playerImagesSet);
		pane.getChildren().addAll(enemyImagesSet);

	}

	public void update() {

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

		collisionIndexList.clear();

		playersSet.forEach(p -> {
			ArrayList<Integer> indexListBuffer = new ArrayList<>();

			p.getvGrid().getHitBoxes().forEach(hb -> enemiesSet.forEach(e -> {
				if (hb.checkCollision(e.getHitBox())) {
					indexListBuffer.add(p.getvGrid().getHitBoxes().indexOf(hb));
				}
			}));

			collisionIndexList.add(indexListBuffer);
			if (p.getClass() == PlayerAI.class) {
				p.setListeIndexEntrees(indexListBuffer);
				p.appliquerIndex();
			}

		});

		graph.refreshGraph(playersSet.get(0).getHitBox().getCenterPoint().velocityY(), collisionIndexList.get(0));

		playersSet.removeAll(playerBufferList);
		enemiesSet.removeAll(enemyBufferList);

		if (playersSet.size() == 0) {
			stop();
			enemiesSet.clear();
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
