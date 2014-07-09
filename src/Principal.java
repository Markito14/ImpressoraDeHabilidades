import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Principal {

    public static void main (String [] args) {
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
        String areaDoConhecimento;
        HabilidadePorPosicao mapaGeralDasHabilidades = new HabilidadePorPosicao();
        HashMap<Integer, Integer> habilidadePorNumeroDaQuestao = new HashMap<>();  // key = numero da questao, value = id da habilidade

        try {
            while ((linha = reader.readNext()) != null) {
                idProva = Integer.parseInt(linha[6]);
                idHabilidade = Integer.parseInt(linha[4]);
                areaDoConhecimento = linha[1];
                // tratando os casos de ingles e espanhol
                if (linha[0].contains("i")) { //ingles
                    linha[0] = "10" + linha[0].substring(0,1); //ingles viram 10_ e espanhol viram 20_
                }
                if (linha[0].contains("e")) { //espanhol
                    linha[0] = "20" + linha[0].substring(0, 1);
                }
                idPosicao = Integer.parseInt(linha[0]);

                if (idProva != idProvaAnterior) { //prova nova
                    mapaGeralDasHabilidades.areaDoConhecimentoPorIdProva.put(idProva, areaDoConhecimento); //salva a area do conhecimento
                    mapaGeralDasHabilidades.mapaPorIdProva.put(idProvaAnterior,habilidadePorNumeroDaQuestao); //salva a prova anterior
                    habilidadePorNumeroDaQuestao = new HashMap<>(); //reseta o mapa habilidadePorNumeroDaQuestao
                }
                habilidadePorNumeroDaQuestao.put(idPosicao,idHabilidade); //populando habilidadePorNumeroDaQuestao
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
        String[] linhaFinal;
        List<String> linhaInicial = new ArrayList<>();

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
                linhaFinal = reader.readNext();
                Collections.addAll(linhaInicial, linhaFinal);
                for (int i = 199; i >= 20; i--) {
                    linhaInicial.remove(i);
                }
                for (String area : areasDoConhecimento) {
                    for (int i = 1; i <= 30; i++) {
                        linhaInicial.add("HAB_" + area + "_" + i);
                    }
                }
                linhaFinal = linhaInicial.toArray(new String[linhaInicial.size()]);
                arquivoFinal.add(linhaFinal);
            } catch (IOException e) {
                System.out.print("Tentou ler nulo.");
            }

            try { // arrumando cada uma das outras linhas
                mapaDasQuestoesDoAluno mapaDasQuestoes = new mapaDasQuestoesDoAluno();
                mapaDasHabilidadesDoAluno mapaDasHabilidades = new mapaDasHabilidadesDoAluno();
                while ((linha = reader.readNext()) != null) { //para cada aluno, que correponde a uma linha
                    mapaDasQuestoes.respostasPorId.clear();
                    for (int j = 1; j < 5; j++) { //preenchendo o mapaDasQuestoes
                        // j = 1 => CN
                        // j = 2 => CH
                        // j = 3 => LC
                        // j = 4 => MT
                        idProva = Integer.parseInt(linha[8 + j]);
                        int[] respostasDoAluno = new int[45];
                        for (int i = 0; i < 45; i++) {
                            respostasDoAluno[i] = Integer.parseInt(linha[i + 20 + ((j - 1) * 45)]);
                        }
                        mapaDasQuestoes.respostasPorId.put(idProva, respostasDoAluno);
                    }
                    for (int i = 0; i < 4; i++) { //preenchendo o mapaDasHabilidades
                        // quero pegar cada uma das 4 provas e ir buscando a habilidade de cada questao pra
                        // preencher no mapa de habilidades
                        for (int key : mapaDasQuestoes.respostasPorId.keySet()) {

                        }
                    }
                }
            } catch (IOException e) {
                System.out.print("Tentou ler nulo.");
            }
        }

    }

}
