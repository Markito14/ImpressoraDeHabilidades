import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Principal {

    public static void main (String [] args) {

        HashMap mapeamentoDeHabilidades = new HashMap();

        // Inicialmente vamos mapear as habilidades

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("C:\\Users\\Alisson\\Desktop\\HABILIDADES.csv"), ';', '\"', 1);
        } catch (FileNotFoundException e) {
            System.out.print("Arquivo não encontrado");
        }

        String [] linha;
        int idProva;
        int idPosicao;
        int idHabilidade;
        int idProvaAnterior = 0;

        try {
            while ((linha = reader.readNext()) != null) {
                idProva = Integer.parseInt(linha[6]);
                if (linha[0].contains("e") || linha[0].contains("i")) {
                    linha[0] = linha[0].substring(0,1);
                }
                idPosicao = Integer.parseInt(linha[0]);
                idHabilidade = Integer.parseInt(linha[4]);
                if (idProva == idProvaAnterior) {
                    ((HabilidadePorPosicao) mapeamentoDeHabilidades.get(idProva)).tabela.put(idPosicao,idHabilidade);
                }
                else {
                    HabilidadePorPosicao temp = new HabilidadePorPosicao();
                    temp.tabela.put(idPosicao,idHabilidade);
                    mapeamentoDeHabilidades.put(idProva,temp);
                }
                idProvaAnterior = idProva;
            }
        } catch (IOException e) {
            System.out.print("Tentou ler nulo");
        }

        // Agora vamos ler, linha a linha, o arquivo do ENEM do estado

        String[] estados = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA",
                "PB", "PE", "PI", "PR",  "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};
        for (String estado : estados) {
            try {
                reader = new CSVReader(new FileReader("C:\\Users\\Alisson\\Desktop\\" + estado + ".csv"), ';', '\"', 1);
            } catch (FileNotFoundException e) {
                System.out.print("Arquivo não encontrado");
            }
            try {
                while ((linha = reader.readNext()) != null) {
                    System.out.print(linha[0] + " ");
                }
            } catch (IOException e) {
                System.out.print("Tentou ler nulo");
            }
        }

    }

}
