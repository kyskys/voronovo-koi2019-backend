package voronovo.koi2019.api;

public interface IGenerator<T> {
    void generate();
    void evaluate();
    String toStringFormat();
}
