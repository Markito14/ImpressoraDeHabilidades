import java.util.HashMap;

public class HabilidadePorPosicao {

    public HashMap areaDoConhecimentoPorIdProva;
    public HashMap mapaPorIdProva;
    public HashMap habilidadePorNumeroDaQuestao; // key = numero da questao, value = id da habilidade

    public HabilidadePorPosicao() {
        mapaPorIdProva = new HashMap();
        habilidadePorNumeroDaQuestao = new HashMap();
        areaDoConhecimentoPorIdProva = new HashMap();

    }
}