public class Arvore {
    Nodo raiz;

    public Arvore() {
        this.raiz = null;
    }

    public int altura(Nodo nodo) {
        if (nodo == null) return -1;
        return 1 + Math.max(altura(nodo.esquerda), altura(nodo.direita));
    }

    public int fb(Nodo nodo) {
        if (nodo == null) return 0;
        return altura(nodo.esquerda) - altura(nodo.direita);
    }

    public Nodo rotDir(Nodo pai) {
        Nodo filho = pai.esquerda;
        Nodo neto  = filho.direita;

        filho.direita = pai;
        pai.esquerda  = neto;

        filho.pai = pai.pai;
        pai.pai   = filho;
        if (neto != null) neto.pai = pai;

        if (filho.pai == null) {
            raiz = filho;
        } else if (filho.pai.esquerda == pai) {
            filho.pai.esquerda = filho;
        } else {
            filho.pai.direita = filho;
        }

        return filho;
    }

    public Nodo rotEsq(Nodo pai) {
        Nodo filho = pai.direita;
        Nodo neto  = filho.esquerda;

        filho.esquerda = pai;
        pai.direita    = neto;

        filho.pai = pai.pai;
        pai.pai   = filho;
        if (neto != null) neto.pai = pai;

        if (filho.pai == null) {
            raiz = filho;
        } else if (filho.pai.esquerda == pai) {
            filho.pai.esquerda = filho;
        } else {
            filho.pai.direita = filho;
        }

        return filho;
    }

    public void balanceamento(Nodo nodo) {
        Nodo atual = nodo;

        while (atual != null) {
            int fator = fb(atual);

            if (fator > 1) {
                if (fb(atual.esquerda) < 0) {
                    rotEsq(atual.esquerda);
                }
                rotDir(atual);
                atual = atual.pai;

            } else if (fator < -1) {
                if (fb(atual.direita) > 0) {
                    rotDir(atual.direita);
                }
                rotEsq(atual);
                atual = atual.pai;

            } else {
                atual = atual.pai;
            }
        }
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
                    novo.pai = atual; 
                    break;
                }
                atual = atual.esquerda;
            } else if (valor > atual.valor) {
                if (atual.direita == null) {
                    atual.direita = novo;
                    novo.pai = atual;
                    break;
                }
                atual = atual.direita;
            } else {
                break;
            }
        }
        balanceamento(novo);
    }

    public boolean buscar(int valor) {
        Nodo atual = raiz;
        while (atual != null) {
            if (valor == atual.valor)      return true;
            else if (valor < atual.valor)  atual = atual.esquerda;
            else                           atual = atual.direita;
        }
        return false;
    }

    public void remover(int valor) {
        Nodo alvo = raiz;

        while (alvo != null && alvo.valor != valor) {
            alvo = (valor < alvo.valor) ? alvo.esquerda : alvo.direita;
        }
        if (alvo == null) return;

        Nodo paiParaBalancear = removerNodo(alvo);
        balanceamento(paiParaBalancear); 
    }

    private Nodo removerNodo(Nodo nodo) {
        if (nodo.esquerda == null && nodo.direita == null) {
            Nodo pai = nodo.pai;
            substituir(nodo, null);
            return pai;
        }
        if (nodo.esquerda == null) {
            Nodo pai = nodo.pai;
            nodo.direita.pai = pai;
            substituir(nodo, nodo.direita);
            return pai;
        }
        if (nodo.direita == null) {
            Nodo pai = nodo.pai;
            nodo.esquerda.pai = pai;
            substituir(nodo, nodo.esquerda);
            return pai;
        }
        Nodo sucessor = menorNodo(nodo.direita);
        Nodo paiParaBalancear = (sucessor.pai == nodo) ? sucessor : sucessor.pai;

        if (sucessor.pai != nodo) {
            substituir(sucessor, sucessor.direita);
            if (sucessor.direita != null) sucessor.direita.pai = sucessor.pai;
            sucessor.direita = nodo.direita;
            sucessor.direita.pai = sucessor;
        }
        substituir(nodo, sucessor);
        sucessor.esquerda = nodo.esquerda;
        sucessor.esquerda.pai = sucessor;
        sucessor.pai = nodo.pai;

        return paiParaBalancear;
    }

    private void substituir(Nodo alvo, Nodo novo) {
        if (alvo.pai == null) {
            raiz = novo;
        } else if (alvo.pai.esquerda == alvo) {
            alvo.pai.esquerda = novo;
        } else {
            alvo.pai.direita = novo;
        }
        if (novo != null) novo.pai = alvo.pai;
    }

    private Nodo menorNodo(Nodo nodo) {
        while (nodo.esquerda != null) nodo = nodo.esquerda;
        return nodo;
    }

    
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
            System.out.print(nodo.valor + " ");
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