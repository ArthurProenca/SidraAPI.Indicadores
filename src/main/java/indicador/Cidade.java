package indicador;

import java.util.Scanner;

public class Cidade {
    private String nome;
    private int ibge;
    private String indicador;

    public Cidade(String nome, int ibge, String indicador) {
        this.nome = nome;
        this.ibge = ibge;
        this.indicador = indicador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIbge() {
        return ibge;
    }

    public void setIbge(int ibge) {
        this.ibge = ibge;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    static boolean verifyCep(String CEP) {
        if (CEP.length() != 9) {
            return false;
        } else {
            if (CEP.charAt(5) != '-') {
                return false;
            }
            return !CEP.matches("[a-z?]");
        }
    }

    static void searchCity(String name) throws Exception {
        String State, Address;

        Scanner in = new Scanner(System.in);
        System.out.println("Type the UF (State): ");
        State = in.nextLine();

        String url = "https://viacep.com.br/ws/" + State + "/" + name + "/json/";

        if ((Controlador.search(url)).equals("[]")) {
            System.out.println("Type the street's name: ");
            Address = in.nextLine();
            url = "https://viacep.com.br/ws/" + State + "/" + name + "/" + Address + "/json/";

            Controlador.createSearch(Controlador.search(url));

        } else {
            Controlador.createSearch(Controlador.search(url));
        }
    }

     static void searchCEP(String CEP) throws Exception {
        if (verifyCep(CEP)) {
            String url = "https://viacep.com.br/ws/" + CEP + "/json/";
            Controlador.createSearch(Controlador.search(url));
        } else {
            System.out.println("Invalid CEP, type a valid CEP." +
                    "\nA valid CEP are composed by IIIII-III, where 'I' are a number.");
        }
    }
}
