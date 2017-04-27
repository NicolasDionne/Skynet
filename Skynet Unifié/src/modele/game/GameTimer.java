package modele.game;

import java.util.function.Consumer;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class GameTimer extends AnimationTimer {

    private final Consumer<Void> updater;
    private final Consumer<Pane> renderer;
    private float maximumStep = Float.MAX_VALUE;
    private Pane pane;

    public GameTimer(Game game, Pane pane) {
        this.updater = game.updater;
        this.renderer = game.renderer;
        this.pane = pane;
    }

    private long previousTime = 0;

    public float getMaximumStep() {
        return maximumStep;
    }

    public void setMaximumStep(float maximumStep) {
        this.maximumStep = maximumStep;
    }

    @Override
    public void handle(long currentTime) {

        if (previousTime == 0) {
            previousTime = currentTime;
            return;
        }

        updater.accept(null);
        renderer.accept(pane);

        float secondsElapsed = (currentTime - previousTime) / 1e9f;

        previousTime = currentTime;


    }

    @Override
    public void stop() {
        previousTime = 0;
        super.stop();
    }


}
