package ai.coeur.transfers;

import ai.utilitaire.Proprietes;

/**
 * Détermine la valeur de sortie de la neurone comme étant soit 0, soit 1.
 * <P>
 * Cette classe hérite de <code>FonctionTransfers</code>.
 * 
 * @see FonctionTransfers
 */
public class Escalier extends FonctionTransfers {

	private static final long serialVersionUID = -4103641407126396051L;
	private double yHaut = 1;
	private double yBas = 0;

	public Escalier() {
	}

	public Escalier(Proprietes proprietes) {
		try {
			this.yHaut = (Double) proprietes.getPropriete("fonctionTransfers.yHaut");
			this.yBas = (Double) proprietes.getPropriete("fonctionTransfers.yBas");
		} catch (NullPointerException e) {

		} catch (NumberFormatException e) {
			System.err
					.println("Propriété(s) de fonction de transfers invalide(s)! Utilisation des valeur par défault.");
		}
	}

	/**
	 * Détermine la valeur de sortie de la neurone comme étant soit 0, soit 1.
	 * 
	 * @return <code>double</code>, 1 si la valeur d'entrée est plus grande que
	 *         0, sinon 0.
	 * @see FonctionTransfers#getSortie(double)
	 */
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
