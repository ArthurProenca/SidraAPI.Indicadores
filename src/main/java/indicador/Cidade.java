package indicador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Cidade {
    private String nome;
    private int ibge;
    private String indicador;

    public Cidade() {
    }

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

    public static void buscaCidade(int mod) throws Exception {
        Scanner in = new Scanner(System.in);
        switch (mod) {
            case 1 -> {
                String name;
                System.out.println("Type the city name: ");
                name = in.nextLine();
                Cidade.searchCity(name);
            }
            case 0 -> {
                String cep;
                System.out.println("Type the cep's city: ");
                cep = in.nextLine();
                Cidade.searchCEP(cep);
            }
        }

    }

    public static String search(String typed_url) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(typed_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

     public static void createInstance(String json) {
        String aux = json.replace("[", "");
        aux = aux.replace("]", "");
        aux = aux.replace(" ", "");

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(aux);

            String cep = node.get("cep").asText();
            String IBGE = node.get("ibge").asText();
            if (IBGE.equals("")) {
                IBGE = "0";
            }

            

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

        if ((search(url)).equals("[]")) {
            System.out.println("Type the street's name: ");
            Address = in.nextLine();
            url = "https://viacep.com.br/ws/" + State + "/" + name + "/" + Address + "/json/";

            createInstance(search(url));

        } else {
            createInstance(search(url));
        }
    }

     static void searchCEP(String CEP) throws Exception {
        if (verifyCep(CEP)) {
            String url = "https://viacep.com.br/ws/" + CEP + "/json/";
            createInstance(search(url));
        } else {
            System.out.println("Invalid CEP, type a valid CEP." +
                    "\nA valid CEP are composed by IIIII-III, where 'I' are a number.");
        }
    }
}
