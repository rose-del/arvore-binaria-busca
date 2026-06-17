public class Main {
    public static void main(String[] args) {
        Arvore arv = new Arvore();

        int[] valores = {50, 17, 72, 12, 23, 54, 76, 9, 14, 19, 67, 3, 1, 100, 88};
        System.out.println("=== Inserindo valores ===");
        for (int v : valores) {
            arv.inserir(v);
            System.out.println("Inseriu: " + v);
        }

        System.out.println("\n=== Estrutura da Árvore ===");
        arv.exibir();

        System.out.println("\n=== Em-Ordem ===");
        arv.emOrdem();

        System.out.println("\n=== Buscas ===");
        System.out.println("Busca 23: " + arv.buscar(23));
        System.out.println("Busca 99: " + arv.buscar(99));
        System.out.println("Busca  1: " + arv.buscar(1));

        System.out.println("\n=== Removendo 17, 72 e 50 ===");
        arv.remover(17);
        arv.remover(72);
        arv.remover(50);

        System.out.println("\n=== Estrutura após remoções ===");
        arv.exibir();

        System.out.println("\n=== Em-Ordem após remoções ===");
        arv.emOrdem();
    }
}