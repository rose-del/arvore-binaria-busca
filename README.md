# Árvore B — Ordem 4

Implementação de uma Árvore B de ordem 4 em Java, com suporte completo a inserção, busca, remoção e exibição da estrutura.

## Conceito: O que é uma Árvore B de Ordem 4?

Uma Árvore B é uma estrutura de dados de busca balanceada, generalização da árvore binária que permite múltiplas chaves e filhos por nodo. A **ordem** define os limites de capacidade de cada nodo.

Para ordem **4**, as regras são:

| Propriedade | Valor |
|---|---|
| Máximo de chaves por nodo | 3 (`ordem - 1`) |
| Mínimo de chaves (não-raiz) | 1 (`⌈ordem/2⌉ - 1`) |
| Máximo de filhos por nodo | 4 (`ordem`) |
| Mínimo de filhos (nodo interno) | 2 |

A árvore sempre cresce para cima: quando um nodo estoura (fica com 4 chaves), a chave do meio é **promovida** ao pai e o nodo é **dividido** em dois.

## Métodos Públicos

### `inserir(int chave)`
Insere uma chave mantendo a árvore balanceada.

Estratégia de **split preventivo**: ao descer pela árvore, se um filho está cheio (3 chaves), ele é dividido antes mesmo de entrar nele. Isso evita ter que subir novamente para rebalancear.

Caso especial — raiz cheia: uma nova raiz vazia é criada, a raiz antiga vira filho dela, e então a divisão acontece normalmente.

```
Inserindo: 50, 17, 72, 12

Após 3 inserções (nodo folha com [17, 50, 72]):
[17, 50, 72]

Ao inserir 12, o nodo está cheio → split:
    [50]
   /    \
[17]   [72]
↓ insere 12:
    [50]
   /    \
[12,17] [72]
```

---

### `buscar(int chave) → boolean`
Percorre a árvore de cima para baixo. Em cada nodo, varre as chaves da esquerda para a direita:
- se encontrar a chave, retorna `true`;
- se chegar em uma chave maior ou no fim do nodo, desce para o filho correspondente;
- se for folha e não encontrou, retorna `false`.

Complexidade: **O(log n)**.

---

### `remover(int chave)`
A remoção cobre três casos principais:

**Caso 1 — Chave em folha:** remove diretamente, deslocando as chaves restantes.

**Caso 2 — Chave em nodo interior:**
- Se o filho **esquerdo** tem chaves suficientes (> mínimo): substitui pela chave **predecessora** (maior da subárvore esquerda) e remove o predecessor recursivamente.
- Se o filho **direito** tem chaves suficientes: substitui pelo **sucessor** (menor da subárvore direita) e remove o sucessor recursivamente.
- Se ambos estão no mínimo: faz **merge** dos dois filhos com a chave do pai, e remove da fusão resultante.

**Caso 3 — Chave não está no nodo atual:** antes de descer para o filho, garante que ele tem chaves suficientes via `completarFilho()`, que pode:
- Emprestar do irmão esquerdo (`emprestarDoEsquerdo`)
- Emprestar do irmão direito (`emprestarDoDireito`)
- Fazer merge com um dos irmãos (`merge`)

---

### `exibir()`
Imprime a árvore nível por nível, mostrando as chaves de cada nodo:

```
Nível 0 | Chaves: [54]
Nível 1 | Chaves: [14, 23]
Nível 2 | Chaves: [9, 12]
Nível 2 | Chaves: [17, 19]
Nível 2 | Chaves: [50]
Nível 1 | Chaves: [76, 88]
Nível 2 | Chaves: [67, 72]
Nível 2 | Chaves: [76]  ← após remoções pode variar
...
```
## Comparação com Árvore Binária de Busca

| Característica | BST | Árvore B (ordem 4) |
|---|---|---|
| Filhos por nodo | 2 | até 4 |
| Chaves por nodo | 1 | 1 a 3 |
| Balanceamento | não garantido | sempre balanceada |
| Altura | O(n) no pior caso | O(log n) garantido |
| Ideal para | memória principal | disco / grandes volumes |