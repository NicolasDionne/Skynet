package ai.utilitaire;

import java.util.HashMap;

@SuppressWarnings("all")
public class Proprietes extends HashMap {
	// TODO Javadoc
	private static final long serialVersionUID = 6351772504551099195L;

	protected void creerClees(String... clees) {
		for (int i = 0; i < clees.length; i++) {
			this.put(clees[i], "");
		}
	}

	public void setPropriete(String clee, Object valeur) {
		this.put(clee, valeur);
	}

	public Object getPropriete(String clee) {
		return this.get(clee);
	}

	public boolean aPropriete(String clee) {
		return this.containsKey(clee);
	}

}
