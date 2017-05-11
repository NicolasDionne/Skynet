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
import modele.elements.visuals.VisualFactory;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.GameObjectType;
import modele.game.game_objects.Player;
import modele.game.game_objects.PlayerAI;
import modele.graphique.GraphiqueIA;
import modele.reseau.GenerateurReseauCIL;

/**
 *
 * @author Bénange Breton, Vincent Girard
 *
 *         Classe principale du jeu.
 */
public class Game implements Bias, Update, Render, Spawn {

	public static final short MAX_NB_PLAYERS = 20;
	public static final float SPAWN_ENEMY_BIAS = 0.1f;
	private Controleur c;
	private List<Player> playersSet = new LinkedList<>();
	private List<Enemy> enemiesSet = new LinkedList<>();
	private List<ExtendedImageView> playerImagesSet = new LinkedList<>();
	private List<ExtendedImageView> enemyImagesSet = new LinkedList<>();

	private ArrayList<ArrayList<Integer>> collisionIndexList = new ArrayList<>();

	private GameState gameState = GameState.STOPPED;
	private EnemySpawner hbGen;
	private IntegerProperty score;
	private GraphiqueIA graph;
	private ArrayList<Reseau> listeReseauxCI;
	private ArrayList<Reseau> listeReseauLive;
	private VisualFactory vF;

	private float timeBetweenEnemies = 0;
	private float timerScaleFactor = 1;
	private Difficulty difficulty = Difficulty.VERY_EASY;

	/**
	 * Constructeur principal du jeu, établit tous les paramètres nécessaires au
	 * fonctionnement du programme
	 *
	 * @param nbHumans
	 *            le nombre de joueurs humains
	 * @param nbAI
	 *            le nombre de jouers AI
	 * @param graph
	 *            le graphe d'un réseau
	 * @param listeReseauxCI
	 *            la liste des réseaux en compétition
	 * @param c
	 *            le contrôleur de la scène, avec lequel on utilise les
	 *            Paramètres de réseau
	 * @param difficulty
	 *            la difficulté du jeu
	 */
	public Game(short nbHumans, short nbAI, GraphiqueIA graph, ArrayList<Reseau> listeReseauxCI, Controleur c,
			Difficulty difficulty) {

		vF = new VisualFactory();
		this.difficulty = difficulty;
		this.listeReseauLive = new ArrayList<>();
		this.c = c;
		score = new SimpleIntegerProperty();

		if (listeReseauxCI == null && nbAI != 0) {

			GenerateurReseauCIL g = new GenerateurReseauCIL();
			g.genererReseauCIL(nbAI, (this.c.parametres.getValNbColonnes() * this.c.parametres.getValNbLignes()), 1,
					this.c.parametres.getValNbNiveaux(), this.c.parametres.getValNbNeuronesParNiveau(), -100, 100);
			listeReseauxCI = g.getReseauxCIR();
			this.listeReseauxCI = listeReseauxCI;
			this.c.setListeReseaux(this.listeReseauxCI);
		}

		this.listeReseauxCI = listeReseauxCI;
		if (graph != null) {
			if (this.listeReseauxCI != null) {
				for (Reseau reseau : this.listeReseauxCI) {
					listeReseauLive.add(reseau);
				}
				if (!this.listeReseauLive.isEmpty()) {
					graph.setReseau(listeReseauLive.get(0));
				}
			}
		}
		hbGen = new EnemySpawner(6, this.difficulty);
		short trueNbHumans = filterNbHumans(nbHumans);

		for (int j = 0; j < nbAI; j++) {
			createPlayer(GameObjectType.AI, this.listeReseauxCI.get(j));
		}

		for (int i = 0; i < trueNbHumans; i++) {
			createPlayer(GameObjectType.HUMAN, null);
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

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty diff) {
		this.difficulty = diff;
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

	/**
	 * Crée un ennemi
	 *
	 * @return un nouvel ennemi
	 */
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

	/**
	 * Crée un joueur d'un certain type
	 *
	 * @param pType
	 *            le type de joueur voulu
	 * @param reseau
	 *            le réseau qu'on attache au joueur si il est gouverné par un AI
	 */
	public void createPlayer(GameObjectType pType, Reseau reseau) {
		HitBox hb = new HitBox(Player.PLAYER_DIM, Player.PLAYER_DIM, 50, Controleur.MID_HEIGHT);
		Player p;

		if (pType.equals(GameObjectType.AI)) {
			p = createPlayerAI(hb, reseau);
		} else {
			p = new Player(pType, hb, this.c.parametres);
		}

		ExtendedImageView eI = vF.getInstance(p, p.getObjectType().getStyle());
		playerImagesSet.add(eI);

		p.scoreProperty().bind(this.scoreProperty());
		playersSet.add(p);

	}

	private PlayerAI createPlayerAI(HitBox hb, Reseau reseau) {
		PlayerAI p = new PlayerAI(hb, reseau, this.c.parametres);

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
		timerScaleFactor += difficulty.getTimeBetweenEnemiesIncrement();

		Enemy enemy = spawnEnemy();
		ExtendedImageView r = vF.getInstance(enemy, enemy.getObjectType().getStyle());

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
				ExtendedImageView eIV = vF.getInstance(p, p.getObjectType().getStyle());
				playerImagesSet.add(eIV);
			}
		});
		enemiesSet.forEach(e -> {
			if (!enemyBufferList.contains(e)) {
				ExtendedImageView eIV = vF.getInstance(e, e.getObjectType().getStyle());
				enemyImagesSet.add(eIV);
			}
		});

		pane.getChildren().clear();
		pane.getChildren().addAll(playerImagesSet);
		pane.getChildren().addAll(enemyImagesSet);

	}

	@Override
	public void update() {

		List<Player> playerBufferList = new LinkedList<>();
		List<Enemy> enemyBufferList = new LinkedList<>();

		if (!playersSet.isEmpty()) {
			playersSet.forEach(p -> {
				p.checkObjectBeyondEdges();
				handleMovement(p.getHitBox());
			});
		}

		enemiesSet.forEach(e -> {
			handleMovement(e.getHitBox());
			if (e.checkObjectBeyondEdges())
				enemyBufferList.add(e);
		});

		score.set(score.get() + 1);

		if (!playersSet.isEmpty()) {
			playersSet.forEach(p -> enemiesSet.forEach(e -> {
				if (p.getHitBox().checkCollision(e.getHitBox())) {
					playerBufferList.add(p);
					if (p.getClass() == PlayerAI.class) {
						((PlayerAI) p).getReseau().setScore(score.get());
						listeReseauLive.remove(((PlayerAI) p).getReseau());
						if (listeReseauLive.size() != 0) {
							graph.setReseau(listeReseauLive.get(0));
						}
					}
				}
			}));
		}

		collisionIndexList.clear();

		if (!playersSet.isEmpty()) {
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
					((PlayerAI) p).appliquerIndex();
				}

			});
		}

		if (!playersSet.isEmpty()) {
			graph.refreshGraph(playersSet.get(0).getHitBox().getCenterPoint().velocityY(), collisionIndexList.get(0));
		}

		playersSet.removeAll(playerBufferList);
		enemiesSet.removeAll(enemyBufferList);

		if (playersSet.size() == 0) {
			stop();
			enemiesSet.clear();
		} else {
			if (!listeReseauLive.isEmpty()) {
				graph.refreshGraphMiddle(listeReseauLive.get(0));
			}
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