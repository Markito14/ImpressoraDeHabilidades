import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Principal {

    public static void main (String [] args) {

        HashMap mapeamentoDeHabilidades = new HashMap();

        // Inicialmente vamos mapear as habilidades

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("C:\\Users\\Alisson\\Desktop\\HABILIDADES.csv"), ';', '\"', 1);
        } catch (FileNotFoundException e) {
            System.out.print("Arquivo não encontrado.");
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
            System.out.print("Tentou ler nulo.");
        }
        try {
            reader.close();
        } catch (IOException e) {
            System.out.print("Erro ao fechar arquivo.");
        }

        List<String[]> arquivoFinal = new ArrayList<>();
        ArrayList<String> linhaFinal;

        /*
        Agora vamos ler, linha a linha, o arquivo do ENEM do estado, para que a linha seja copiada e editada, virando
        linhaFinal, que será adicionada a arquivoFinal para impressão ao término de tudo.
        */

        String[] estados = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA",
                "PB", "PE", "PI", "PR",  "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};
        String[] areasDoConhecimento = {"CN", "CH", "LC", "MT"};

        for (String estado : estados) { //roda o programa para todos os .csv's, de todos os estados
            try { // load inicial, apenas para arrumar o cabeçalho
                reader = new CSVReader(new FileReader("C:\\Users\\Alisson\\Desktop\\" + estado + ".csv"), ';');
            } catch (FileNotFoundException e) {
                System.out.print("Arquivo não encontrado.");
            }
            try { // arrumando o cabeçalho (tirando as questoes e colocando as habilidades)
                linhaFinal = (ArrayList<String>) Arrays.asList(reader.readNext());
                for (int i = 20; i < 200; i++) {
                    linhaFinal.remove(i);
                }
                for (String area : areasDoConhecimento) {
                    for (int i = 1; i <= 30; i++) {
                        linhaFinal.add("HAB_" + area + "_" + i);
                    }
                }
                linhaFinal.trimToSize();
                arquivoFinal.add((String[]) linhaFinal.toArray());
            } catch (IOException e) {
                System.out.print("Tentou ler nulo.");
            }

            try { // arrumando cada linha
                reader = new CSVReader(new FileReader("C:\\Users\\Alisson\\Desktop\\" + estado + ".csv"), ';', '\"', 1);
            } catch (FileNotFoundException e) {
                System.out.print("Arquivo não encontrado.");
            }
            try {
                while ((linha = reader.readNext()) != null) {










                }
            } catch (IOException e) {
                System.out.print("Tentou ler nulo.");
            }
        }

    }

}
