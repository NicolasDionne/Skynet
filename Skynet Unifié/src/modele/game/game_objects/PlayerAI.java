package modele.game.game_objects;

import java.util.ArrayList;

import ai.apprentissage.nonsupervise.CompetitionInterReseaux;
import ai.coeur.Neurone;
import ai.coeur.Reseau;
import modele.elements.ai_related.VisionGrid;
import modele.elements.hitbox.HitBox;

public class PlayerAI extends Player {

	private Reseau<CompetitionInterReseaux> reseau;
	private ArrayList<Neurone> listeNeuronesEntrees;
	private ArrayList<Double> listeEntreesNumeriques;

	public PlayerAI(HitBox hb, Reseau<CompetitionInterReseaux> reseau) {
		super(GameObjectType.AI, hb);
		this.reseau = reseau;
		listeNeuronesEntrees = reseau.getNeuronesEntree();
		listeEntreesNumeriques = new ArrayList<>();
		for (int i = 0; i < listeNeuronesEntrees.size(); i++) {
			listeEntreesNumeriques.add(0.0);
		}
	}

	@Override
	public void changeDirection(int i) {
		if (i >= 1)
			getHitBox().getCenterPoint().setVelocityY(-VELOCITY_PLAYER);
		else if (i <= -1)
			getHitBox().getCenterPoint().setVelocityY(VELOCITY_PLAYER);
		else
			getHitBox().getCenterPoint().setVelocityY(0);

		checkObjectBeyondEdges();
	}

	private void modifierValEntrees() {
		reseau.setValEntree(listeEntreesNumeriques);
	}

	public ArrayList<Double> getListeEntreesNumeriques() {
		return listeEntreesNumeriques;
	}

	public void setListeEntreesNumeriques(ArrayList<Double> listeEntreesNumeriques) {
		this.listeEntreesNumeriques = listeEntreesNumeriques;
	}

	@Override
	public void appliquerIndex() {
		for (int i = 0; i < listeEntreesNumeriques.size(); i++) {
			if (listeIndexEntrees.contains(i)) {
				listeEntreesNumeriques.set(i, 1.0);
			} else {
				listeEntreesNumeriques.set(i, 0.0);
			}
		}
		moveBitch();
	}

	private void moveBitch() {
		modifierValEntrees();
		reseau.calculer();
		int i = (int) (reseau.getSortie())[0];
		changeDirection(i);
	}

	public Reseau<CompetitionInterReseaux> getReseau() {
		return reseau;
	}

}
