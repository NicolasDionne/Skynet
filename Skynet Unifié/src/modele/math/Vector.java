package modele.math;

import utilitaires.AddressString;
import utilitaires.MathUtilitaires;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author Bénange Breton
 *
 *         Classe vecteur faite récréativement (avant le début du projet).
 *         Implémente beaucoup de comportements de vecteurs potentiellement
 *         utiles.
 *
 */
public class Vector implements Comparable<Vector>, VectorI<Vector>, AddressString {

	private List<Float> vector = null;
	private final static int MAX_DIM = 100;
	private boolean DIMENSION_LOCKED = false;

	public Vector(float[] u) {
		if (u != null && u.length > 0) {
			vector = new ArrayList<>();
			IntStream.range(0, u.length).forEach(x -> vector.add(u[x]));
		}
	}

	public Vector(Float[] u) {
		if (u != null && u.length > 0) {
			vector = new ArrayList<>();
			IntStream.range(0, u.length).forEach(x -> vector.add(u[x]));
		}
	}

	public Vector(List<Float> u) {
		if (u != null && u.size() > 0) {
			vector = new ArrayList<>();
			IntStream.range(0, u.size()).forEach(x -> vector.add(u.get(x)));
		}
	}

	public Vector(Vector u) {
		if (u != null && u.dimension() > 0) {
			vector = new ArrayList<>();
			IntStream.range(0, u.dimension()).forEach(x -> vector.add(u.get(x)));
		}
	}

	public Vector(int dimension) {
		vector = new ArrayList<>();
		IntStream.range(0, dimension).forEach(x -> vector.add(0f));
	}

	public Vector() {
		vector = new ArrayList<>();
	}

	@Override
	public Vector planarRotationRads(int[] plane, double rads) {

		// À faire pour divertir : une rotation qui peux prendre n'importe quel
		// couple de vecteurs linéairement indépendants en paramètre.

		if (plane.length != 2 || plane[0] < 0 || plane[1] < 0 || plane[0] > this.dimension()
				|| plane[1] > this.dimension() || plane[0] == plane[1])
			return null;

		Vector v = new Vector(this);
		v.truncate();

		float x1 = v.get(plane[0]);
		float y1 = v.get(plane[1]);

		double x2 = x1 * Math.cos(rads) - y1 * Math.sin(rads);
		double y2 = y1 * Math.cos(rads) + x1 * Math.sin(rads);

		v.set(plane[0], (float) MathUtilitaires.round(x2));
		v.set(plane[1], (float) MathUtilitaires.round(y2));

		return v;
	}

	@Override
	public Vector planarRotationDegs(int[] plane, double degs) {
		return planarRotationRads(plane, Math.toRadians(degs));
	}

	@Override
	public float norm() {
		double norm = IntStream.range(0, dimension()).parallel().mapToDouble(x -> Math.pow(this.get(x), 2)).reduce(0,
				Double::sum);
		return (float) MathUtilitaires.round(Math.sqrt(norm));
	}

	public void scale(float factor) {
		IntStream.range(0, this.dimension()).forEach(x -> this.set(x, this.get(x) * factor));
	}

	@Override
	public Vector scaledVector(float factor) {
		Vector u = new Vector(this);
		u.scale(factor);
		return u;
	}

	@Override
	public Vector unitVector() {
		Vector u = new Vector(this);
		IntStream.range(0, u.dimension()).forEach(x -> u.set(x, u.get(x) / this.norm()));
		return u;
	}

	public void invert() {
		IntStream.range(0, this.dimension()).forEach(x -> this.set(x, -this.get(x)));
	}

	@Override
	public Vector invertVector() {
		Vector u = new Vector(this);
		u.invert();
		return u;
	}

	@Override
	public Vector add(Vector u) {

		Vector v = new Vector(this.dimension() >= u.dimension() ? this : u);
		Vector w = new Vector(this.dimension() >= u.dimension() ? u : this);

		int minDim = Math.min(this.dimension(), u.dimension());
		IntStream.range(0, minDim).forEach(x -> v.set(x, w.get(x) + v.get(x)));

		return v;
	}

	@Override
	public Vector subtract(Vector u) {
		Vector v = new Vector(this);
		Vector w = new Vector(u);

		return v.add(w.invertVector());
	}

	@Override
	public float dotProduct(Vector u) {
		if (u.dimension() == this.dimension()) {
			double product = IntStream.range(0, u.dimension()).parallel().mapToDouble(x -> u.get(x) * this.get(x))
					.reduce(0, Double::sum);
			return (float) product;
		}
		return 0;
	}

	@Override
	public Vector crossProduct(Vector u) {

		// À faire pour divertir: un crossproduct généralisé

		Vector v = new Vector(this);
		Vector w = new Vector(u);
		v.truncate();
		w.truncate();

		if ((w.dimension() < 1 && w.dimension() > 3) || (v.dimension() < 1 && v.dimension() > 3))
			return null;

		float[] n = new float[3];
		n[0] = v.get(1) * w.get(2) - v.get(2) * w.get(1);
		n[1] = v.get(2) * w.get(0) - v.get(0) * w.get(2);
		n[2] = v.get(0) * w.get(1) - v.get(1) * w.get(0);

		return new Vector(n);
	}

	public ArrayList<Float> asList() {
		ArrayList<Float> al = new ArrayList<>();
		vector.forEach(al::add);
		return al;
	}

	public float[] toArray() {
		float[] ar = new float[this.dimension()];
		IntStream.range(0, this.dimension()).forEach(x -> ar[x] = vector.get(x));
		return ar;
	}

	@Override
	public int dimension() {
		return vector.size();
	}

	public float get(int index) {
		if (index > MAX_DIM)
			throw new IndexOutOfBoundsException();
		try {
			vector.get(index);
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
		return vector.get(index);
	}

	public void truncate() {
		int index = this.dimension() - 1;
		while (index >= 0 && MathUtilitaires.round(vector.get(index)) == 0.0) {
			index--;
		}
		vector = (vector.subList(0, index + 1));
	}

	public void set(int index, Float value) {
		if (validerIndex(index))
			vector.set(index, value);
		else if (index >= this.dimension() && DIMENSION_LOCKED == false)
			this.add(value);

	}

	public void set(int index, float value) {
		if (validerIndex(index))
			vector.set(index, value);
		else if (index >= this.dimension() && DIMENSION_LOCKED == false)
			this.add(value);

	}

	public void set(int index, int value) {
		if (validerIndex(index))
			vector.set(index, (float) value);
		else if (index >= this.dimension() && DIMENSION_LOCKED == false)
			this.add(value);

	}

	private boolean validerIndex(int index) {
		return index >= 0 && index < this.dimension();
	}

	public void add(Float f) {
		if (DIMENSION_LOCKED == false)
			vector.add(f);
	}

	public void add(float f) {
		if (DIMENSION_LOCKED == false)
			vector.add(f);
	}

	public void add(int i) {
		if (DIMENSION_LOCKED == false)
			vector.add((float) i);
	}

	public void lockDimensions() {
		DIMENSION_LOCKED = true;
	}

	public void unlockDimensions() {
		DIMENSION_LOCKED = false;
	}

	public boolean isLocked() {
		return DIMENSION_LOCKED;
	}

	public boolean equals(Object u) {
		if (!(u instanceof Vector))
			return false;
		if (u == this)
			return true;

		Vector v = new Vector(this);
		Vector w = new Vector((Vector) u);

		v.truncate();
		w.truncate();

		int maxDim = Math.max(v.dimension(), w.dimension());
		int countEqual = IntStream.range(0, maxDim).map(x -> v.get(x) == w.get(x) ? 1 : 0).reduce(0, Integer::sum);

		return countEqual == v.dimension();
	}

	@Override
	public int compareTo(Vector u) {
		final int LESS = -1;
		final int EQUAL = 0;
		final int MORE = 1;

		if (u == this)
			return EQUAL;

		if (u.norm() == this.norm())
			return EQUAL;

		return this.norm() > u.norm() ? MORE : LESS;
	}

	@Override
	public int hashCode() {
		return (int) Math.pow(this.norm(), vector.get(0));
	}

	@Override
	public String toString() {
		return address() + " : " + vector.toString();
	}
}
