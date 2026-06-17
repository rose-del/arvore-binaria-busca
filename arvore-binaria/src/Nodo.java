public class Nodo {
    int[] chaves;
    Nodo[] filhos;
    int numChaves;
    boolean folha;

    public Nodo(int ordem) {
        this.chaves    = new int[ordem - 1];
        this.filhos    = new Nodo[ordem];
        this.numChaves = 0;
        this.folha     = true;
    }
}