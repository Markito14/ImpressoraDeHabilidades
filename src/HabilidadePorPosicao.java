import java.util.HashMap;

public class HabilidadePorPosicao {

    private int id;
    private HashMap tabela;

    public HabilidadePorPosicao(int id, HashMap tabela) {
        this.id = id;
        this.tabela = tabela;
    }

    public int getId() {
        return id;
    }

    public HashMap getTabela() {
        return tabela;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTabela(HashMap tabela) {
        this.tabela = tabela;
    }

}
