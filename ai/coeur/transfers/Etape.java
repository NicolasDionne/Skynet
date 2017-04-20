package ai.coeur.transfers;

import ai.utilitaire.Proprietes;

public class Etape extends FonctionTransfers {

	// TODO Javadoc
	private double yHaut = 1;
	private double yBas = 0;

	public Etape() {

	}

	public Etape(Proprietes proprietes) {
		try {
			this.yHaut = (Double) proprietes.getPropriete("fonctionTransfers.yHaut");
			this.yBas = (Double) proprietes.getPropriete("fonctionTransfers.yBas");
		} catch (NullPointerException e) {

		} catch (NumberFormatException e) {
			System.err
					.println("Propriété(s) de fonction de transfers invalide(s)! Utilisation des valeur par défault.");
		}
	}

	@Override
	public double getSortie(double totalEntree) {
		return (0 < totalEntree) ? yHaut : yBas;
	}

	public double getYHaut() {
		return yHaut;
	}

	public void setYHaut(double yHaut) {
		this.yHaut = yHaut;
	}

	public double getYBas() {
		return yBas;
	}

	public void setYBas(double yBas) {
		this.yBas = yBas;
	}

	public Proprietes getProprietes() {
		Proprietes proprietes = new Proprietes();

		proprietes.setPropriete("fonctionTransfers.yHaut", String.valueOf(yHaut));
		proprietes.setPropriete("fonctionTransfers.yBas", String.valueOf(yBas));

		return proprietes;
	}

}
