package modele.math;

public interface VectorI<V> {

    V planarRotationRads(int[] plane, double rads);

    V planarRotationDegs(int[] plane, double degs);

    V scaledVector(float factor);

    V unitVector();

    V invertVector();

    V add(V u);

    V subtract(V u);

    float dotProduct(V u);

    V crossProduct(V u);

    int dimension();

    float norm();
}
