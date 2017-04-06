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

public class GraphiqueIA {
	private Pane zoneEntrants;
	private Pane zoneNiveaux;
	private Pane zoneSortants;
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

	public GraphiqueIA(Pane entrants, Pane niveaux, Pane sortants) {
		this.zoneEntrants = entrants;
		this.zoneNiveaux = niveaux;
		this.zoneSortants = sortants;
		listeEntrants = new ArrayList<Rectangle>();
		genererAffichageNeuronesEntree();
		listeNiveaux = new ArrayList<Circle>();
		genererAffichageNiveaux();
		genererAffichageOutput();
		afficherLiens();

	}

	public void setResau(Reseau reseau) {

	}

	/**
	 * genere les rectangles de la grille d'entree colonne par colonne
	 */
	private void genererAffichageNeuronesEntree() {
		Rectangle r;
		for (int col = 0; col < 10; col++) {
			for (int li = 0; li < 4; li++) {
				r = new Rectangle(col * 25, li * 30 + 20, largeur, hauteur);
				r.setFill(couleurNeutre);
				listeEntrants.add(r);
				zoneEntrants.getChildren().add(r);
			}
		}
	}

	/**
	 * genere l'affichage des niveaux niveau par niveau
	 */
	private void genererAffichageNiveaux() {
		// TODO prendre les valeurs du cerveau pour afficher le bon nombre de
		// stock
		if (reseau != null) {
			int nbNiv = reseau.getNombreNiveaux();
			int neuronesParNiv;
			Circle r;
			for (int col = 0; col < nbNiv; col++) {
				Label l = new Label(new Integer(col).toString());
				l.setLayoutX(col * 100 + 107);
				;
				zoneNiveaux.getChildren().add(l);
				neuronesParNiv = reseau.getNiveauA(0).getNombreDeNeurones();
				for (int li = 0; li < neuronesParNiv; li++) {
					r = new Circle(col * 100 + 100, li * 30 + 20, largeur);
					r.setFill(couleurNeutre);
					listeNiveaux.add(r);
					zoneNiveaux.getChildren().add(r);
				}
			}
		} else {
			// TODO affiche pour le test
			int nbNiv = 4;
			int neuronesParNiv = 4;
			Circle r;
			for (int col = 0; col < nbNiv; col++) {
				Label l = new Label(new Integer(col).toString());
				l.setLayoutX(col * 100 + 97);
				l.setTranslateY(-5);

				zoneNiveaux.getChildren().add(l);
				for (int li = 0; li < neuronesParNiv; li++) {
					r = new Circle(col * 100 + 100, li * 30 + 20, largeur/2);
					r.setFill(couleurNeutre);
					listeNiveaux.add(r);
					zoneNiveaux.getChildren().add(r);
				}
			}
		}

	}

	private void genererAffichageOutput() {
		Rectangle r = new Rectangle(50, 45, largeur + 5, hauteur);
		r.setFill(couleurNeutre);
		sortie = r;
		zoneSortants.getChildren().add(sortie);

	}

	// TODO afjouter le raiseau au traitement des choses
	private void afficherLiens() {
		Line l;
		for (Circle r1 : listeNiveaux) {
			for (Circle r2 : listeNiveaux) {
				l = new Line(r1.getCenterX(), r1.getCenterY(), r2.getCenterX(), r2.getCenterY());
				l.setFill(Color.BLACK);
				zoneNiveaux.getChildren().add(l);
				l.toBack();
			}
		}
	}

	public void refreshGraph(int mouvement) {
		Random ran = new Random();
		int i = 0;
		sortie.setFill(couleurParInt(mouvement));
		// TODO code a partir d'ici inutile mais est la base pour traiter celon
		// un reseau fonctionnel
		Color c = couleurParInt(ran.nextInt(4) - 2);
		for (Rectangle r : listeEntrants) {
			if (i % 2 == 0) {
				r.setFill(c);
			}

			for (Circle r2 : listeNiveaux) {
				if (i % 2 == 0) {
					r2.setFill(c);
				}
			}
			i++;
		}

	}

	private Color couleurParInt(int i) {
		Color c;
		switch (i) {
		case 1: {
			c = couleurOff;
			break;
		}
		case 0: {
			c = couleurNeutre;
			break;
		}
		case -1: {
			c = couleurOn;
			break;
		}
		default: {
			c = couleurErreur;
			break;
		}

		}

		return c;

	}
}
