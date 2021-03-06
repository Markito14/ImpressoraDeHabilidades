import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
            mapaGeralDasHabilidades.mapaPorIdProva.put(idProvaAnterior,habilidadePorNumeroDaQuestao); //salva a ultima prova
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
                reader = new CSVReader(new FileReader("C:\\Users\\Alisson\\Desktop\\Estados_por_questao\\" + estado + ".csv"), ';');
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
                linhaInicial.clear();
            } catch (IOException e) {
                System.out.print("Tentou ler nulo.");
            }

            try { // arrumando cada uma das outras linhas
                while ((linha = reader.readNext()) != null) { //para cada aluno, que correponde a uma linha

                    //iniciamos com o preenchimento do mapaDasQuestoes

                    MapaDasQuestoesDoAluno mapaDasQuestoes = new MapaDasQuestoesDoAluno();
                    MapaDasHabilidadesDoAluno mapaDasHabilidades = new MapaDasHabilidadesDoAluno();
                    int[] questoesTemp;
                    int[] habilidadesTemp = new int[30];
                    mapaDasQuestoes.lingua = Integer.parseInt(linha[13]); //especificando a lingua escolhida pelo aluno
                    for (int j = 1; j < 5; j++) {
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

                    //vamos agora ao preenchimento do mapaDasHabilidades

                    for (int key : mapaDasQuestoes.respostasPorId.keySet()) {
                        questoesTemp = mapaDasQuestoes.respostasPorId.get(key); //respostas do aluno
                        areaDoConhecimento = mapaGeralDasHabilidades.areaDoConhecimentoPorIdProva.get(key); //area do conhecimento
                        habilidadePorNumeroDaQuestao = mapaGeralDasHabilidades.mapaPorIdProva.get(key); //relacao quest/hab
                        if (!areaDoConhecimento.equals("LC")) { //para CN, CH e MT
                            for (int i = 0; i < 45; i++) { //para cada questao
                                /*
                                i+1 ocorre porque o vetor de habilidades vai de 0 a 44 enquanto as questoes estao numeradas de
                                1 a 45 pelo INEP.

                                o -1 de "habilidadePorNumeroDaQuestao.get(i + 1) - 1" ocorre porque o vetor de habilidades
                                vai de 0 a 29 enquanto as habilidades sao nomeadas de 1 a 30 pelo INEP.
                                 */
                                habilidadesTemp[habilidadePorNumeroDaQuestao.get(i + 1) - 1] += questoesTemp[i];
                            }
                            mapaDasHabilidades.mapaPorAreaDoConhecimento.put(areaDoConhecimento, habilidadesTemp.clone());
                        }
                        else { // para Linguagens e Códigos temos de aplicar uma lógica diferente
                            //para as 5 primeiras questoes eh necessario saber a lingua escolhida pelo aluno
                            if (mapaDasQuestoes.lingua == 0) { //caso a lingua seja ingles
                                for (int i = 0; i < 5; i++) { //para cada questao
                                    habilidadesTemp[habilidadePorNumeroDaQuestao.get(i + 101) - 1] += questoesTemp[i];
                                }
                            }
                            else { //caso a lingua seja espanhol
                                for (int i = 0; i < 5; i++) { //para cada questao
                                    habilidadesTemp[habilidadePorNumeroDaQuestao.get(i + 201) - 1] += questoesTemp[i];
                                }
                            }
                            //para as demais questoes, funciona normalmente
                            for (int i = 5; i < 45; i++) { //para cada questao
                                habilidadesTemp[habilidadePorNumeroDaQuestao.get(i + 1) - 1] += questoesTemp[i];
                            }
                            mapaDasHabilidades.mapaPorAreaDoConhecimento.put(areaDoConhecimento, habilidadesTemp.clone());
                        }
                        habilidadesTemp = new int[30]; //resetando vetor habilidadesTemp para ser usado novamente no prox. loop
                    }

                    // por fim, vamos modificar a linha e adicionar a nova linha ao nosso arquivoFinal

                    Collections.addAll(linhaInicial, linha);
                    for (int i = 199; i >= 20; i--) {
                        linhaInicial.remove(i);
                    }
                    for (String area : areasDoConhecimento) {
                        for (int i = 0; i < 30; i++) {
                            linhaInicial.add(Integer.toString(mapaDasHabilidades.mapaPorAreaDoConhecimento.get(area)[i]));
                        }
                    }
                    linhaFinal = linhaInicial.toArray(new String[linhaInicial.size()]);
                    arquivoFinal.add(linhaFinal);
                    linhaInicial.clear();
                }
            } catch (IOException e) {
                System.out.print("Tentou ler nulo.");
            }
            try {
                reader.close();
            } catch (IOException e) {
                System.out.print("Erro ao fechar arquivo.");
            }

            // agora vamos imprimir o arquivo final

            CSVWriter writer = null;
            try {
                writer = new CSVWriter(new FileWriter("C:\\Users\\Alisson\\Desktop\\Estados_por_habilidade\\" + estado + "_POR_HABILIDADE.csv"), ';');
            } catch (IOException e) {
                System.out.print("Erro ao criar arquivo.");
            }
            writer.writeAll(arquivoFinal);
            try {
                writer.close();
            } catch (IOException e) {
                System.out.print("Erro ao fechar arquivo.");
            }
            arquivoFinal.clear(); // limpando o arquivo final
        }

    }

}
