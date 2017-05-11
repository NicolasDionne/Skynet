package ai.utilitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SeparateurEnTableau {
	public static ArrayList<Integer> separateurInteger(String str) {
		StringTokenizer tok = new StringTokenizer(str);
		ArrayList<Integer> retour = new ArrayList<>();
		while (tok.hasMoreTokens()) {
			Integer d = Integer.valueOf(tok.nextToken());
			retour.add(d);
		}
		return retour;
	}

	public static double[] separateurArrayDouble(String inputStr) {
		String[] inputsArrStr = inputStr.split(" ");

		double[] ret = new double[inputsArrStr.length];
		for (int i = 0; i < inputsArrStr.length; i++) {
			ret[i] = Double.parseDouble(inputsArrStr[i]);
		}

		return ret;
	}

	public static double[] toDoubleArray(List<Double> liste) {
		double[] retour = new double[liste.size()];
		for (int i = 0; i < liste.size(); i++) {
			retour[i] = liste.get(i).doubleValue();
		}
		return retour;
	}

	public static ArrayList<Double> convertirEnTableau(double[] array) {
		ArrayList<Double> tableau = new ArrayList<>(array.length);

		for (double val : array) {
			tableau.add(val);
		}

		return tableau;
	}

}
