package valetinho;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Estacionamento {
    private String[] placas;
    private int totalVagas;
    private ArrayList<Integer> vagasLivres;

    public Estacionamento(int n) {
        totalVagas = n;
        placas = new String[n];
        vagasLivres = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            placas[i] = "livre";
            vagasLivres.add(i + 1);
        }
    }

    public void entrar(String placa, int vaga, String date) throws Exception {
        if (vaga < 1 || vaga > totalVagas) {
            throw new Exception("Vaga inválida.");
        }

        if (!placas[vaga - 1].equals("livre")) {
            throw new Exception("Vaga já ocupada.");
        }

        placas[vaga - 1] = placa;
        vagasLivres.remove(Integer.valueOf(vaga));
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        String dataHora = agora.format(formato);
        gravarDados(placa, vaga,dataHora);
        System.out.println("Veículo com placa " + placa + " entrou na vaga " + vaga + " em " + dataHora);
    }

    public void sair(int vaga, String date) throws Exception {
        if (vaga < 1 || vaga > totalVagas) {
            throw new Exception("Vaga inválida.");
        }

        if (placas[vaga - 1].equals("livre")) {
            throw new Exception("Vaga já está desocupada.");
        }

        String placa = placas[vaga - 1];
        placas[vaga - 1] = "livre";
        vagasLivres.add(vaga);
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        String dataHora = agora.format(formato);
        gravarDados(placa, vaga, dataHora);
        System.out.println("Veículo com placa " + placa + " saiu da vaga " + vaga + " em " + dataHora);
    }

   

    public void transferir(int vaga1, int vaga2) throws Exception {
        if (vaga1 < 1 || vaga1 > totalVagas || vaga2 < 1 || vaga2 > totalVagas) {
            throw new Exception("Vaga inválida.");
        }

        if (placas[vaga1 - 1].equals("livre") || !placas[vaga2 - 1].equals("livre")) {
            throw new Exception("Transferência não permitida.");
        }

        String placa = placas[vaga1 - 1];
        placas[vaga1 - 1] = "livre";
        placas[vaga2 - 1] = placa;
        vagasLivres.remove(Integer.valueOf(vaga2));
        vagasLivres.add(vaga1);
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        String dataHora = agora.format(formato);
        gravarDados(placa, vaga1, dataHora);
        System.out.println("Veículo transferido da vaga " + vaga1 + " para a vaga " + vaga2 + " em " + dataHora);
    }


    public int consultarPlaca(String placa) {
        for (int i = 0; i < totalVagas; i++) {
            if (placas[i].equals(placa)) {
                return i + 1;
            }
        }
        return -1;
    }


    public String[] listarGeral() {
        return placas;
    }

    public ArrayList<Integer> listarLivres() {
        return vagasLivres;
    }

    public void gravarDados(String placa, int vaga, String dataHora) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("historico.csv", true));
            writer.write(placa + "," + vaga + "," + dataHora + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no arquivo historico.csv: " + e.getMessage());
        }
    }
    



    public void lerDados() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("historico.csv"));
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    String placa = dados[0];
                    int vaga = Integer.parseInt(dados[1]);
                    long timestamp = Long.parseLong(dados[2]);
                    placas[vaga - 1] = placa;
                    vagasLivres.remove(Integer.valueOf(vaga));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler dados do arquivo historico.csv: " + e.getMessage());
        }
    }

    public void imprimirEstacionamento() {
        System.out.println("Estado atual do estacionamento:");
        for (int i = 0; i < totalVagas; i++) {
            System.out.println("Vaga " + (i + 1) + ": " + placas[i]);
        }
    }

}