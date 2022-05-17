package practica1;

public interface Algorithm<T extends Table, U, Z> {
    void train(T sample);
    U estimate(Z sample);

}
