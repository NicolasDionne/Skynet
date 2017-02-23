package base;

import java.util.ArrayList;

public interface CalculResultat {
	public ArrayList<NeuroneAvecImportance> listeEntreesDansNeurones = new ArrayList<>();
	
	abstract double valeurResultanteCalculee();
	abstract void addEntree();
}
