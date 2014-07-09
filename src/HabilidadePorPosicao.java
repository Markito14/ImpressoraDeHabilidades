import java.util.HashMap;

public class HabilidadePorPosicao {

    public HashMap <Integer, String> areaDoConhecimentoPorIdProva; // key = id da prova, value = codigo da área do conhecimento
    public HashMap<Integer, HashMap<Integer,Integer>> mapaPorIdProva; // key = id da prova, value = mapa habilidadePorNumeroDaQuestao correspondente


    public HabilidadePorPosicao() {
        mapaPorIdProva = new HashMap<>();
        areaDoConhecimentoPorIdProva = new HashMap<>();

    }
}