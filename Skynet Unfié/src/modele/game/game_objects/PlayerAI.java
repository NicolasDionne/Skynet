package modele.game.game_objects;

import java.util.ArrayList;

import ai.coeur.Neurone;
import ai.coeur.Reseau;
import ai.coeur.apprentissage.RegleApprentissage;
import modele.elements.HitBox;
import modele.elements.VisionGrid;

public class PlayerAI extends Player {

	private Reseau<RegleApprentissage> reseau;
	private ArrayList<Neurone> listeNeuronesEntrees;

	public PlayerAI(HitBox hb, Reseau<RegleApprentissage> reseau) {
		super(PlayerType.AI, hb);
		this.reseau = reseau;
		this.vGrid = new VisionGrid(hb, (short) 4, (short) 2);
		listeNeuronesEntrees = reseau.getNeuronesEntree();
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

}
