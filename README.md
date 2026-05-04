# Árvore Binária de Pesquisa

Este projeto implementa uma **Árvore Binária de Busca (Binary Search Tree - BST)** em Java, contendo as principais operações fundamentais da estrutura.

## Sobre o projeto

A **Árvore Binária de Busca** é uma estrutura de dados onde:

* Cada nodo possui no máximo dois filhos
* Valores menores ficam à **esquerda**
* Valores maiores ficam à **direita**

Essa organização permite buscas eficientes.

## Regras

Para qualquer nodo:

```
Esquerda < Raiz < Direita
```

## Inserção

* Começa pela raiz
* Compara o valor:

  * Menor → esquerda
  * Maior → direita
* Insere quando encontra uma posição `null`

## Busca

* Segue a mesma lógica da inserção
* Retorna:

  * `true` → se encontrou
  * `false` → se não existe

## Remoção

A operação mais importante e complexa. Possui 3 casos:

### 1. Nodo folha

Remove diretamente.

### 2. Nodo com 1 filho

Substitui pelo filho.

### 3. Nodo com 2 filhos

* Encontra o **sucessor** (menor valor da subárvore direita)
* Copia o valor
* Remove o sucessor


### Classes

* **Nodo** → representa cada elemento da árvore
* **Arvore** → contém a lógica da estrutura
* **Main** → execução e testes

## Complexidade

| Operação | Complexidade Média | Pior Caso |
| -------- | ------------------ | --------- |
| Inserir  | O(log n)           | O(n)      |
| Buscar   | O(log n)           | O(n)      |
| Remover  | O(log n)           | O(n)      |

O pior caso ocorre quando a árvore fica desbalanceada (parecendo uma lista).
