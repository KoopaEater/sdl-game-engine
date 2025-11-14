package engine.gameloop;

public interface GameLoop {
    void init();
    void step();
    void setSetupTick(SetupTick setupTick);
    void setTick(Tick tick);
    void setFixedTick(FixedTick fixedTick);
}
