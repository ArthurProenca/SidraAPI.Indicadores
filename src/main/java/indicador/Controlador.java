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

public interface Controlador {
    static void buscaCidade(int mod) throws Exception {
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

    static String search(String typed_url) throws Exception {
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

    static void createSearch(String json) {
        String aux = json.replace("[", "");
        aux = aux.replace("]", "");
        aux = aux.replace(" ", "");

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(aux);
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

    static void consumeSidra(String url, String parameter) {
        String aux = url.replace("[", "");
        aux = aux.replace("]", "");
        aux = aux.replace(" ", "");

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(aux);
            String temp = node.get(parameter).asText();
            if (temp.equals("")) {
                temp = "0";
            }
            System.out.println(temp);


        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    static void searchBy(int mod) {
        Scanner in = new Scanner(System.in);
        switch (mod) {
            case 1:
                System.out.println("1 - Masculino" +
                        "\n2 - Feminino" +
                        "\n3 - Total" + "\n");
                int opt = in.nextInt();
                String url;
                try {
                    switch (opt) {
                        case 1:
                            url = "https://apisidra.ibge.gov.br/values/t/6682/n1/all/c2/4/f/c/h/n";
                            consumeSidra(search(url),"V");
                            break;
                        case 2:
                            url = "https://apisidra.ibge.gov.br/values/t/6682/n1/all/c2/5/f/c/h/n";
                            consumeSidra(search(url),"V");
                            break;
                        case 3:
                            url = "https://apisidra.ibge.gov.br/values/t/6682/n1/all/c2/all/f/c/h/n";
                            consumeSidra(search(url),"V");
                            break;
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                break;

        }


    }
}
