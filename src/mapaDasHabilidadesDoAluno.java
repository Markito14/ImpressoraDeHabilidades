import java.util.HashMap;

public class mapaDasHabilidadesDoAluno {
    /*
        0 = CN
        1 = CH
        2 = LC
        3 = MT
    */
    public HashMap mapaPorAreaDoConhecimento; //key = area do conhecimento, value = mapa habilidadesPorId correspondente
    public HashMap habilidadesPorId; //key = id da habilidade, value = int[30]

    public mapaDasHabilidadesDoAluno() {
        habilidadesPorId = new HashMap();
        mapaPorAreaDoConhecimento = new HashMap();
    }

}
