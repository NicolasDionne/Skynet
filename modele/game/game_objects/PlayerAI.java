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
		this.vGrid = new VisionGrid(hb, (short) 4, (short) 2);
		listeNeuronesEntrees = reseau.getNeuronesEntree();
		listeEntreesNumeriques=new ArrayList<>();
		for (int i = 0; i < listeNeuronesEntrees.size(); i++) {
			listeEntreesNumeriques.add(0.0);
		}
	}

	@Override
	public void changeDirection(int i) {
		modifierValEntrees();
		reseau.calculer();
		i = (int) (reseau.getSortie())[0];
		if (i >= 1)
			getHitBox().getCenterPoint().setVelocityY(-VELOCITY_PLAYER);
		else if (i <= -1)
			getHitBox().getCenterPoint().setVelocityY(VELOCITY_PLAYER);
		else
			getHitBox().getCenterPoint().setVelocityY(0);

		checkObjectBeyondEdges();
	}

	private void modifierValEntrees() {
		for (int i = 0; i < listeNeuronesEntrees.size(); i++) {
			double result = vGrid.getHitBoxes().get(i).isInCollision() ? 1 : 0;
			listeNeuronesEntrees.get(i).setTotalEntrees(result);
		}
	}

	public ArrayList<Double> getListeEntreesNumeriques() {
		return listeEntreesNumeriques;
	}

	public void setListeEntreesNumeriques(ArrayList<Double> listeEntreesNumeriques) {
		this.listeEntreesNumeriques = listeEntreesNumeriques;
	}

	@Override
	public void appliquerIndex() {

	}

}
