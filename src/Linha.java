import java.util.HashMap;

public class Linha {

    private boolean tpLingua;
    private HashMap respostasPorId; //key = ID_PROVA, value = int[45]

    public Linha(boolean tpLingua, HashMap respostasPorId) {
        this.tpLingua = tpLingua;
        this.respostasPorId = respostasPorId;
    }

    public boolean isTpLingua() {
        return tpLingua;
    }

    public HashMap getRespostasPorId() {
        return respostasPorId;
    }

    public void setTpLingua(boolean tpLingua) {
        this.tpLingua = tpLingua;
    }

    public void setRespostasPorId(HashMap respostasPorId) {
        this.respostasPorId = respostasPorId;
    }

}
