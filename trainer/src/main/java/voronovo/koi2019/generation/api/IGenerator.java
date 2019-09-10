package voronovo.koi2019.generation.api;

public interface IGenerator<T> {
    void generate();
    void evaluate();
    String toStringFormat();
}
