import java.util.HashMap;

public class HabilidadePorPosicao {

    public HashMap areaDoConhecimentoPorIdProva; // key = id da prova, value = codigo da área do conhecimento
    public HashMap mapaPorIdProva; // key = id da prova, value = mapa habilidadePorNumeroDaQuestao correspondente


    public HabilidadePorPosicao() {
        mapaPorIdProva = new HashMap();
        areaDoConhecimentoPorIdProva = new HashMap();

    }
}