package engine.gameloop;

public class NoTick implements Tick {
    @Override
    public void tick(double deltaTime) {
        // Do nothing
    }
}
