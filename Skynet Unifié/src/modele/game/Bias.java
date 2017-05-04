package modele.game;

import java.util.function.Supplier;


public interface Bias {

    Supplier<Double> rand = Math::random;

    /**
    *
    *   Une simple méthode par défaut qui permet de donner un vrai/faux selon une probabilité.
    *
    *   @param bias un float entre 0 et 1
    */
    default boolean testBias(float bias) {
        return (rand.get() < bias);
    }
}
