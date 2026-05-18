# O que é “percorrer uma árvore”?

Percorrer significa **visitar todos os nodos** da árvore em uma determinada ordem.

Os 3 tipos principais são:

1. Pré-ordem
2. Em-ordem
3. Pós-ordem

A diferença entre eles é:

> QUANDO a raiz é visitada.

Os slides mostram isso: 

* Pré-ordem → raiz antes
* Em-ordem → raiz no meio
* Pós-ordem → raiz depois

# MINHA ÁRVORE

Com os valores:

```java
int[] valores = {50, 17, 72, 12, 23, 54, 76, 9, 14};
```

Sua árvore fica assim:

```text
          50
        /    \
      17      72
     /  \    /  \
   12   23 54   76
  /  \
 9   14
```

# Resultado esperado

```text
Pré-ordem:
50 17 12 9 14 23 72 54 76

Em-ordem:
9 12 14 17 23 50 54 72 76

Pós-ordem:
9 14 12 23 17 54 76 72 50
```

# Para memorizar

## Pré

```text
RAIZ primeiro
```

## Em

```text
RAIZ no meio
```

## Pós

```text
RAIZ por último
```

Essa é a essência dos 3 percursos.
