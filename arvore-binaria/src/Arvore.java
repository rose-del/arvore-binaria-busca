public class Arvore {
    Nodo raiz;

    public Arvore() {
        this.raiz = null;
    }

    public void adicionar(int valor) {
        Nodo novo = new Nodo(valor);

        if (raiz == null) {
            raiz = novo;
            return;
        }

        Nodo atual = raiz;

        while (true) {
            if (valor < atual.valor) {
                if (atual.esquerda == null) {
                    atual.esquerda = novo;
                    break;
                }
                atual = atual.esquerda;

            } else if (valor > atual.valor) {
                if (atual.direita == null) {
                    atual.direita = novo;
                    break;
                }
                atual = atual.direita;
            }else {
                break;
            }
        }
    }

    public boolean buscar(int valor) {
        Nodo atual = raiz;

        while (atual != null) {
            if (valor == atual.valor) {
                return true;
            } else if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }
        return false;
    }

    public void remover(int valor) {
        raiz = removerRec(raiz, valor);
    }

    // Precisamos reatribuir os ponteiros corretamente ao subir na árvore
    private Nodo removerRec(Nodo nodo, int valor) {
        if (nodo == null) {
            return null;
        }

        if (valor < nodo.valor) {
            nodo.esquerda = removerRec(nodo.esquerda, valor);
        } else if (valor > nodo.valor) {
            nodo.direita = removerRec(nodo.direita, valor);
        } else {
            if (nodo.esquerda == null && nodo.direita == null) {
                return null;
            } else if (nodo.esquerda == null) {
                return nodo.direita;
            } else if (nodo.direita == null) {
                return nodo.esquerda;
            } else {
                Nodo sucessor = menorNodo(nodo.direita);
                nodo.valor = sucessor.valor;
                nodo.direita = removerRec(nodo.direita, sucessor.valor);
            }
        }

        return nodo;
    }

    private Nodo menorNodo(Nodo nodo) {
        Nodo atual = nodo;

        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }

        return atual;
    }

    // EXIBIR
    public void emOrdem() {
        emOrdemRec(raiz);
        System.out.println();
    }

    private void emOrdemRec(Nodo nodo) {
        if (nodo != null) {
            emOrdemRec(nodo.esquerda);
            System.out.print(nodo.valor + " ");
            emOrdemRec(nodo.direita);
        }
    }

    public void preOrdem() {
        preOrdemRec(raiz);
        System.out.println();
    }

    private void preOrdemRec(Nodo nodo) {
        if (nodo != null) {
            System.out.println(nodo.valor + " ");
            preOrdemRec(nodo.esquerda);
            preOrdemRec(nodo.direita);
        }
    }

    public void posOrdem() {
        posOrdemRec(raiz);
        System.out.println();
    }

    private void posOrdemRec(Nodo nodo) {
        if (nodo != null) {

            posOrdemRec(nodo.esquerda);
            posOrdemRec(nodo.direita);

            System.out.print(nodo.valor + " ");
        }
    }
}
