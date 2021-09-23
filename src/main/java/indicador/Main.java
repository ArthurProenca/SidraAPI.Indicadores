package indicador;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        int cont = 1, opt;
        Scanner in = new Scanner(System.in);

        while (cont != 0) {
            System.out.println("""
                    Welcome.
                    1 - Search by city name
                    2 - Search by CEP
                    3 - Quit""");
            opt = in.nextInt();

            try{
                switch (opt) {
                    case 1 -> Controlador.buscaCidade(1);
                    case 2 -> Controlador.buscaCidade(0);
                    case 3 -> cont = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         */
        Controlador.searchBy(1);

    }
}
