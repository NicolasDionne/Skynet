package modele.graphique;

import java.util.ArrayList;
import java.util.Random;

import ai.coeur.Reseau;
import ai.coeur.apprentissage.ApprentissageSupervise;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GraphiqueIA {
	private Pane zoneAffichage;
	private int largeur = 20;
	private int hauteur = 25;
	private static final Color couleurOn = Color.GREEN;
	private static final Color couleurOff = Color.ORANGE;
	private static final Color couleurNeutre = Color.GRAY;
	private static final Color couleurErreur = Color.RED;
	private static final Color couleurCollision = Color.BLACK;
	private ArrayList<Rectangle> listeEntrants;
	private ArrayList<Circle> listeNiveaux;
	private Rectangle sortie;
	private Reseau<?> reseau;
	private int nbNiveau = 6;
	private int nbNeuronesNiveau = 6;

	/**
	 * constructeur du GraphiqueIA, reçoit uniquement en paramètre sa zone
	 * d'affichage, requiers de lui assigner un réseau avant d'être complet.
	 *
	 * @param affichage
	 *            le Pane où le graphique se situeras.
	 */
	public GraphiqueIA(Pane affichage) {
		this.zoneAffichage = affichage;
		listeEntrants = new ArrayList<Rectangle>();
		genererAffichageNeuronesEntree();
		listeNiveaux = new ArrayList<Circle>();
		genererAffichageOutput();
		// TODO requiers de mettre un réseau pour marcher
		genererAffichageNiveaux();
		afficherLiens();

	}

	/**
	 * assigne un nouveau réseau à représenter au graphiqueIA
	 *
	 * @param reseau
	 *            le réseau à représenter
	 */
	public void setResau(Reseau<?> reseau) {
		this.reseau = reseau;
		nbNiveau = reseau.getNombreNiveaux();
		// TODO assigner la bone valeur à ceci.
		nbNeuronesNiveau = reseau.getNombreEntrees();
		afficherLiens();

	}

	/**
	 * genere les rectangles de la grille d'entree colonne par colonne de
	 * rectangles et les place dans la listeEntrants pour pouvoir y accéder par
	 * la suite
	 */
	private void genererAffichageNeuronesEntree() {
		Rectangle r;
		for (int col = 0; col < 10; col++) {
			for (int li = 0; li < 4; li++) {
				r = new Rectangle(col * 25, li * 30 + 60, largeur, hauteur);
				r.setFill(couleurNeutre);
				listeEntrants.add(r);
				zoneAffichage.getChildren().add(r);
			}
		}
	}

	/**
	 * genere l'affichage des niveaux niveau par niveau sous forme de colonne de
	 * cercles et les place dans le listeNiveaux
	 */
	private void genererAffichageNiveaux() {
		// TODO prendre les valeurs du cerveau pour afficher le bon nombre de
		// stock
		if (reseau != null) {
			Circle r;
			for (int col = 0; col < nbNiveau; col++) {
				Label l = new Label(new Integer(col).toString());
				l.setLayoutX(col * 100 + 107);
				;
				zoneAffichage.getChildren().add(l);
				for (int li = 0; li < nbNeuronesNiveau; li++) {
					r = new Circle(col * 100 + 100, li * 30 + 20, largeur);
					r.setFill(couleurNeutre);
					listeNiveaux.add(r);
					zoneAffichage.getChildren().add(r);
				}
			}
		} else {
			// TODO affiche pour le test
			Circle c;
			for (int col = 0; col < nbNiveau; col++) {
				Label l = new Label(new Integer(col).toString());
				l.setLayoutX(col * 100 + 347);
				l.setLayoutY(26);

				zoneAffichage.getChildren().add(l);
				for (int li = 0; li < nbNeuronesNiveau; li++) {
					c = new Circle(col * 100 + 350, li * 30 + 50, largeur / 2);
					c.setFill(couleurNeutre);
					listeNiveaux.add(c);
					zoneAffichage.getChildren().add(c);
				}
			}
		}

	}

	/**
	 * Génère l'affichage de la sortie
	 */
	private void genererAffichageOutput() {
		Rectangle r = new Rectangle(50, 45, largeur + 5, hauteur);
		r.setFill(couleurNeutre);
		r.setX(925);
		r.setY(75);
		sortie = r;
		zoneAffichage.getChildren().add(sortie);

	}

	/**
	 * Affiche les liens entre les différents morceaux du réseau à partir des
	 * paramètres de celui-ci, des entrées aux neurones puis entre elles et
	 * enfin à la sortie.
	 */
	private void afficherLiens() {
		Line l = new Line();
		Random ra = new Random();
		// Liens entre les neurones "cachees" et jusqu'a la sortie
		for (Circle r1 : listeNiveaux) {
			for (Shape r2 : getNextColumn(r1)) {
				if (r2.getClass().equals(Circle.class)) {
					l = new Line(r1.getCenterX(), r1.getCenterY(), ((Circle) r2).getCenterX(),
							((Circle) r2).getCenterY());
				} else if (r2.getClass().equals(Rectangle.class)) {
					l = new Line(r1.getCenterX(), r1.getCenterY(),
							((Rectangle) r2).getX() + ((Rectangle) r2).getWidth() / 2,
							((Rectangle) r2).getY() + ((Rectangle) r2).getHeight() / 2);
				}
				// TODO mettre la vraie importance
				l.setStroke(couleurCelonDouble(ra.nextDouble()));
				zoneAffichage.getChildren().add(l);
				l.toBack();
			}
		}
		// liens entre les entrees et la premiere ligne de neurones "cachees"
		Circle c;

		for (Rectangle r : listeEntrants) {
			for (int i = 0; i < nbNeuronesNiveau; i++) {
				c = listeNiveaux.get(i);
				l = new Line(r.getX() + r.getWidth() / 2, r.getY() + r.getHeight() / 2, c.getCenterX(), c.getCenterY());
				// TODO mettre la vraie importance
				l.setStroke(couleurCelonDouble(ra.nextDouble()));
				zoneAffichage.getChildren().add(l);
				l.toBack();
			}
		}
	}
/**
 * Rafraichit le graphique: ses liens, ses entrées et sa sortie
 * @param mouvement
 */
	public void refreshGraph(float mouvement, ArrayList<Integer> arrayList) {
		sortie.setFill(couleurParFloat(mouvement));
		for (Rectangle r : listeEntrants) {
			if(arrayList.indexOf(listeEntrants.indexOf(r))!=-1){
				r.setFill(couleurParFloat(booleanToInt(true)));
			}else{
				r.setFill(couleurParFloat(booleanToInt(false)));
			}
		}

	}

	/**
	 * retourne une couleur prédéfinie pour différents integers, utilisé dans
	 * refresh graph pour la couleur des entrées et de la sortie.
	 *
	 * @param i
	 *            la valeur numérique de la couleur
	 * @return la couleur assignée au chiffre en paramètre
	 */
	private Color couleurParFloat(float i) {
		Color c=couleurNeutre;
		if(i>0){
			c = couleurOff;
		}else if(i<0){
			c = couleurOn;
		}

		return c;

	}

	/**
	 * Retourne le prochain niveau de neurones d'une neurone choisie ou la
	 * sortie dans une liste de shapes si il n'y a pas de prochain niveau pour
	 * celle-ci
	 *
	 * @param c
	 *            la neurone
	 * @return la liste des shapes suivant la neurone
	 */
	private ArrayList<Shape> getNextColumn(Circle c) {
		ArrayList<Shape> retour = new ArrayList<Shape>();
		int position = listeNiveaux.indexOf(c);
		int ligneDeC = 0;
		boolean flag = true;

		while (flag) {
			if (position >= nbNeuronesNiveau) {
				position = position - nbNeuronesNiveau;
				ligneDeC++;
			} else {
				flag = false;
			}
		}
		for (int i = (ligneDeC + 1) * nbNeuronesNiveau; i < (((ligneDeC + 2) * nbNeuronesNiveau))
				&& i < listeNiveaux.size() + 1; i++) {
			if (ligneDeC >= nbNiveau - 1) {
				retour.add(sortie);
			} else {
				retour.add(listeNiveaux.get(i));
			}
		}

		return retour;
	}

	/**
	 * retourne la couleur du lien celon son importance
	 *
	 * @param d
	 *            la valeur de l'importance du lien
	 * @return la couleur a donner au lien
	 */
	private Color couleurCelonDouble(double d) {
		Color c;
		// TODO METTRE LE WEIGHT DE 0 A 1
		if (d >= 0.5) {
			c = new Color(0, 0, 1, d);
		} else {
			c = new Color(1, 0, 0, d);
		}
		return c;

	}

	/**
	 * retourne la valeur numerique d'un boolean 1 = vrai 0 = faux
	 *
	 * @param b
	 *            le boolean a evaluer
	 * @return la valeur numerique
	 */
	private int booleanToInt(boolean b) {
		int retour = 0;
		if (b) {
			retour = 1;
		}
		return retour;
	}
	/**
	 * remets a zero les liens liant les neurones
	 */
	public void resetLiens(){
		zoneAffichage.getChildren().clear();
		genererAffichageNeuronesEntree();
		genererAffichageNiveaux();
		genererAffichageOutput();

	}

}
