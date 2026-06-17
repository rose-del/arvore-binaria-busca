public class Arvore {
    private Nodo raiz;
    private final int ORDEM = 4;           // ordem da árvore
    private final int MAX_CHAVES = ORDEM - 1; // 3 chaves por nodo
    private final int MIN_CHAVES = (int) Math.ceil(ORDEM / 2.0) - 1; // 1 chave mínima

    public Arvore() {
        this.raiz = new Nodo(ORDEM);
    }

    public boolean buscar(int chave) {
        return buscarRec(raiz, chave);
    }

    private boolean buscarRec(Nodo nodo, int chave) {
        int i = 0;

        while (i < nodo.numChaves && chave > nodo.chaves[i]) {
            i++;
        }

        if (i < nodo.numChaves && chave == nodo.chaves[i]) {
            return true;
        }

        if (nodo.folha) {
            return false;
        }

        return buscarRec(nodo.filhos[i], chave);
    }

    // ─── INSERIR ───────────────────────────────────────────────
    public void inserir(int chave) {
        Nodo r = raiz;

        if (r.numChaves == MAX_CHAVES) {
            Nodo novaRaiz = new Nodo(ORDEM);
            novaRaiz.folha = false;
            novaRaiz.filhos[0] = r;
            dividirFilho(novaRaiz, 0, r);
            raiz = novaRaiz;
            inserirNaoCheio(novaRaiz, chave);
        } else {
            inserirNaoCheio(r, chave);
        }
    }

    private void inserirNaoCheio(Nodo nodo, int chave) {
        int i = nodo.numChaves - 1;

        if (nodo.folha) {
            while (i >= 0 && chave < nodo.chaves[i]) {
                nodo.chaves[i + 1] = nodo.chaves[i];
                i--;
            }
            nodo.chaves[i + 1] = chave;
            nodo.numChaves++;
        } else {
            while (i >= 0 && chave < nodo.chaves[i]) {
                i--;
            }
            i++;
            if (nodo.filhos[i].numChaves == MAX_CHAVES) {
                dividirFilho(nodo, i, nodo.filhos[i]);
                if (chave > nodo.chaves[i]) {
                    i++;
                }
            }
            inserirNaoCheio(nodo.filhos[i], chave);
        }
    }

    private void dividirFilho(Nodo pai, int indiceFilho, Nodo filhoCheio) {
        int meio = ORDEM / 2 - 1; // índice da chave do meio (índice 1 para ordem 4)

        Nodo filhoDireito = new Nodo(ORDEM);
        filhoDireito.folha = filhoCheio.folha;
        filhoDireito.numChaves = MAX_CHAVES - meio - 1;

        for (int j = 0; j < filhoDireito.numChaves; j++) {
            filhoDireito.chaves[j] = filhoCheio.chaves[j + meio + 1];
        }

        if (!filhoCheio.folha) {
            for (int j = 0; j <= filhoDireito.numChaves; j++) {
                filhoDireito.filhos[j] = filhoCheio.filhos[j + meio + 1];
            }
        }

        filhoCheio.numChaves = meio;

        for (int j = pai.numChaves; j >= indiceFilho + 1; j--) {
            pai.filhos[j + 1] = pai.filhos[j];
        }
        pai.filhos[indiceFilho + 1] = filhoDireito;

        for (int j = pai.numChaves - 1; j >= indiceFilho; j--) {
            pai.chaves[j + 1] = pai.chaves[j];
        }
        pai.chaves[indiceFilho] = filhoCheio.chaves[meio];
        pai.numChaves++;
    }

    public void remover(int chave) {
        if (raiz == null || raiz.numChaves == 0) {
            System.out.println("Árvore vazia.");
            return;
        }
        removerRec(raiz, chave);

        if (raiz.numChaves == 0 && !raiz.folha) {
            raiz = raiz.filhos[0];
        }
    }

    private void removerRec(Nodo nodo, int chave) {
        int i = encontrarIndice(nodo, chave);

        if (i < nodo.numChaves && nodo.chaves[i] == chave) {
            if (nodo.folha) {
                removerDaFolha(nodo, i);
            } else {
                removerDeInterior(nodo, i);
            }
        } else {
            if (nodo.folha) {
                System.out.println("Chave " + chave + " não encontrada.");
                return;
            }

            boolean ultimoFilho = (i == nodo.numChaves);

            if (nodo.filhos[i].numChaves < MIN_CHAVES + 1) {
                completarFilho(nodo, i);
            }

            if (ultimoFilho && i > nodo.numChaves) {
                removerRec(nodo.filhos[i - 1], chave);
            } else {
                removerRec(nodo.filhos[i], chave);
            }
        }
    }

    private void removerDaFolha(Nodo nodo, int i) {
        for (int j = i + 1; j < nodo.numChaves; j++) {
            nodo.chaves[j - 1] = nodo.chaves[j];
        }
        nodo.numChaves--;
    }

    private void removerDeInterior(Nodo nodo, int i) {
        int chave = nodo.chaves[i];

        if (nodo.filhos[i].numChaves >= MIN_CHAVES + 1) {
            int predecessor = getPredecessor(nodo, i);
            nodo.chaves[i] = predecessor;
            removerRec(nodo.filhos[i], predecessor);

        } else if (nodo.filhos[i + 1].numChaves >= MIN_CHAVES + 1) {
            int sucessor = getSucessor(nodo, i);
            nodo.chaves[i] = sucessor;
            removerRec(nodo.filhos[i + 1], sucessor);

        } else {
            merge(nodo, i);
            removerRec(nodo.filhos[i], chave);
        }
    }

    private int getPredecessor(Nodo nodo, int i) {
        Nodo atual = nodo.filhos[i];
        while (!atual.folha) {
            atual = atual.filhos[atual.numChaves];
        }
        return atual.chaves[atual.numChaves - 1];
    }

    private int getSucessor(Nodo nodo, int i) {
        Nodo atual = nodo.filhos[i + 1];
        while (!atual.folha) {
            atual = atual.filhos[0];
        }
        return atual.chaves[0];
    }

    private void completarFilho(Nodo nodo, int i) {
        if (i > 0 && nodo.filhos[i - 1].numChaves >= MIN_CHAVES + 1) {
            emprestarDoEsquerdo(nodo, i);

        } else if (i < nodo.numChaves && nodo.filhos[i + 1].numChaves >= MIN_CHAVES + 1) {
            emprestarDoDireito(nodo, i);

        } else {
            if (i < nodo.numChaves) {
                merge(nodo, i);
            } else {
                merge(nodo, i - 1);
            }
        }
    }

    private void emprestarDoEsquerdo(Nodo pai, int i) {
        Nodo filho    = pai.filhos[i];
        Nodo irmao    = pai.filhos[i - 1];

        for (int j = filho.numChaves - 1; j >= 0; j--) {
            filho.chaves[j + 1] = filho.chaves[j];
        }

        if (!filho.folha) {
            for (int j = filho.numChaves; j >= 0; j--) {
                filho.filhos[j + 1] = filho.filhos[j];
            }
            filho.filhos[0] = irmao.filhos[irmao.numChaves];
        }

        filho.chaves[0] = pai.chaves[i - 1];
        filho.numChaves++;

        pai.chaves[i - 1] = irmao.chaves[irmao.numChaves - 1];
        irmao.numChaves--;
    }

    private void emprestarDoDireito(Nodo pai, int i) {
        Nodo filho = pai.filhos[i];
        Nodo irmao = pai.filhos[i + 1];

        filho.chaves[filho.numChaves] = pai.chaves[i];
        filho.numChaves++;

        if (!filho.folha) {
            filho.filhos[filho.numChaves] = irmao.filhos[0];
        }

        pai.chaves[i] = irmao.chaves[0];

        for (int j = 1; j < irmao.numChaves; j++) {
            irmao.chaves[j - 1] = irmao.chaves[j];
        }
        if (!irmao.folha) {
            for (int j = 1; j <= irmao.numChaves; j++) {
                irmao.filhos[j - 1] = irmao.filhos[j];
            }
        }
        irmao.numChaves--;
    }

    private void merge(Nodo pai, int i) {
        Nodo esquerdo = pai.filhos[i];
        Nodo direito  = pai.filhos[i + 1];

        esquerdo.chaves[esquerdo.numChaves] = pai.chaves[i];

        for (int j = 0; j < direito.numChaves; j++) {
            esquerdo.chaves[esquerdo.numChaves + 1 + j] = direito.chaves[j];
        }

        if (!esquerdo.folha) {
            for (int j = 0; j <= direito.numChaves; j++) {
                esquerdo.filhos[esquerdo.numChaves + 1 + j] = direito.filhos[j];
            }
        }

        esquerdo.numChaves += direito.numChaves + 1;

        for (int j = i + 1; j < pai.numChaves; j++) {
            pai.chaves[j - 1] = pai.chaves[j];
        }
        for (int j = i + 2; j <= pai.numChaves; j++) {
            pai.filhos[j - 1] = pai.filhos[j];
        }
        pai.numChaves--;
    }

    private int encontrarIndice(Nodo nodo, int chave) {
        int i = 0;
        while (i < nodo.numChaves && chave > nodo.chaves[i]) {
            i++;
        }
        return i;
    }

    public void exibir() {
        exibirRec(raiz, 0);
    }

    private void exibirRec(Nodo nodo, int nivel) {
        if (nodo == null) return;

        System.out.print("Nível " + nivel + " | Chaves: [");
        for (int i = 0; i < nodo.numChaves; i++) {
            System.out.print(nodo.chaves[i]);
            if (i < nodo.numChaves - 1) System.out.print(", ");
        }
        System.out.println("]");

        for (int i = 0; i <= nodo.numChaves; i++) {
            if (!nodo.folha) {
                exibirRec(nodo.filhos[i], nivel + 1);
            }
        }
    }

    public void emOrdem() {
        emOrdemRec(raiz);
        System.out.println();
    }

    private void emOrdemRec(Nodo nodo) {
        if (nodo == null) return;
        for (int i = 0; i < nodo.numChaves; i++) {
            if (!nodo.folha) {
                emOrdemRec(nodo.filhos[i]);
            }
            System.out.print(nodo.chaves[i] + " ");
        }
        if (!nodo.folha) {
            emOrdemRec(nodo.filhos[nodo.numChaves]);
        }
    }
}