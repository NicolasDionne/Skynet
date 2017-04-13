package modele.game;

import java.util.function.Consumer;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

	private final Consumer<Float> updater;
    private final Runnable renderer;
    private float maximumStep = Float.MAX_VALUE;

    public GameLoop(Consumer<Float> updater, Runnable renderer)
    {
        this.updater = updater;
        this.renderer = renderer;
    }
    
    private long previousTime = 0;

    public float getMaximumStep()
    {
        return maximumStep;
    }

    public void setMaximumStep(float maximumStep)
    {
        this.maximumStep = maximumStep;
    }
    @Override
    public void handle(long currentTime)
    {
        if (previousTime == 0) {
            previousTime = currentTime;
            return;
        }

        float secondsElapsed = (currentTime - previousTime) / 1e9f; /* nanoseconds to seconds */
        float secondsElapsedCapped = Math.min(secondsElapsed, getMaximumStep());
        previousTime = currentTime;

        updater.accept(secondsElapsedCapped);
        renderer.run();

    }
    
    @Override
    public void stop()
    {
        previousTime = 0;
        super.stop();
    }
    
	
	
	
}
