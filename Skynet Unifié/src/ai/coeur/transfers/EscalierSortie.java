package ai.coeur.transfers;

import ai.utilitaire.Proprietes;

/**
 * Détermine si valeur de sortie de la neurone est soit -1, soit 0, soit 1.
 * <P>
 * C'est la même chose que <code>Escalier</code>, mais avec une possibilité de
 * plus (-1).
 * <P>
 * Cette classe hérite de <code>FonctionTransfers</code>.
 * 
 * @see Escalier
 * @see FonctionTransfers
 */
public class EscalierSortie extends FonctionTransfers {

	private static final long serialVersionUID = -5881013521618703650L;
	private double yHaut = 1;
	private double yBas = -1;

	public EscalierSortie() {
	}

	public EscalierSortie(Proprietes proprietes) {
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
	 * @return <code>double</code>, soit -1, soit 0, soit 1 selon cet
	 *         algorithme:
	 *         {@code (1 <= totalEntree) ? yHaut : (totalEntree <= -1) ? yBas : 0}.
	 */
	@Override
	public double getSortie(double totalEntree) {
		return (1 <= totalEntree) ? yHaut : (totalEntree <= -1) ? yBas : 0;
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
