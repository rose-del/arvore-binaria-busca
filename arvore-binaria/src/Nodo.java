public class Nodo {
    int valor;
    Nodo esquerda;
    Nodo direita;
    Nodo pai; 

    public Nodo(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
        this.pai = null; 
    }
}