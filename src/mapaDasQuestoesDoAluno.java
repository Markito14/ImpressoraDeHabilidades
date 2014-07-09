import java.util.HashMap;

public class MapaDasQuestoesDoAluno {

    public int lingua; //0 para ingles, 1 para espanhol
    public HashMap<Integer, int[]> respostasPorId; //key = idProva, value = int[45]

    public MapaDasQuestoesDoAluno() {
        respostasPorId = new HashMap<>();
    }
}
