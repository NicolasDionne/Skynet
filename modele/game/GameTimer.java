package modele.game;

import java.util.function.Consumer;

import javafx.animation.AnimationTimer;

public class GameTimer extends AnimationTimer {

    private final Consumer<Void> updater;
    private final Consumer<Void> renderer;
    private float maximumStep = Float.MAX_VALUE;

    public GameTimer(Game game) {
        this.updater = game.updater;
        this.renderer = game.renderer;
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
        renderer.accept(null);
        //if(previousTime )

        float secondsElapsed = (currentTime - previousTime) / 1e9f;
        float secondsElapsedCapped = Math.min(secondsElapsed, getMaximumStep());
        previousTime = currentTime;


    }

    @Override
    public void stop() {
        previousTime = 0;
        super.stop();
    }


}
