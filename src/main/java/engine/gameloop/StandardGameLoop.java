package engine.gameloop;

public class StandardGameLoop implements GameLoop {
    public static final double UNITS_PER_SECOND = 1000000000.0;
    private long lastTickTime, lastFixedTickTime;
    private SetupTick setupTick;
    private Tick tick;
    private FixedTick fixedTick;
    private final long nanosPerFixedTick;
    public StandardGameLoop(int fixedTickRate) {
        tick = new NoTick();
        fixedTick = new NoFixedTick();
        setupTick = new NoSetupTick();
        nanosPerFixedTick = Math.round(UNITS_PER_SECOND / fixedTickRate);
    }

    @Override
    public void init() {
        lastTickTime = System.nanoTime();
        lastFixedTickTime = System.nanoTime();
        setupTick.tick();
    }

    @Override
    public void step() {
        long elapsedTime = System.nanoTime() - lastTickTime;
        lastTickTime += elapsedTime;
        double deltaTime = elapsedTime / UNITS_PER_SECOND;
        tick.tick(deltaTime);
        long now = System.nanoTime();
        while (now - lastFixedTickTime >= nanosPerFixedTick) {
            lastFixedTickTime += nanosPerFixedTick;
            fixedTick.tick();
        }
    }

    @Override
    public void setSetupTick(SetupTick setupTick) {
        this.setupTick = setupTick;
    }

    @Override
    public void setTick(Tick tick) {
        this.tick = tick;
    }

    @Override
    public void setFixedTick(FixedTick fixedTick) {
        this.fixedTick = fixedTick;
    }
}
