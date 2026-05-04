public class Main {
    public static void main(String[] args) {
        Arvore arv = new Arvore();

        int[] valores = {50, 17, 72, 12, 23, 54, 76, 9, 14};

        for (int v : valores) {
            arv.adicionar(v);
        }

        arv.emOrdem();

        System.out.println(arv.buscar(23));
        System.out.println(arv.buscar(99));

        arv.remover(12);
        arv.remover(76);

        arv.emOrdem();
    }
}