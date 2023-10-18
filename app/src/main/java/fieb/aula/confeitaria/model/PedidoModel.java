package fieb.aula.confeitaria.model;

public class PedidoModel {

    private int id;
    private String fotoBolo;
    public String nomeBolo;
    private String descricaoBolo;
    private String precoBolo;
    private String qtde;
    private String frag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFotoBolo() {
        return fotoBolo;
    }

    public void setFotoBolo(String fotoBolo) {
        this.fotoBolo = fotoBolo;
    }

    public String getNomeBolo() {
        return nomeBolo;
    }

    public void setNomeBolo(String nomeBolo) {
        this.nomeBolo = nomeBolo;
    }

    public String getDescricaoBolo() {
        return descricaoBolo;
    }

    public void setDescricaoBolo(String descricaoBolo) {
        this.descricaoBolo = descricaoBolo;
    }

    public String getPrecoBolo() {
        return precoBolo;
    }

    public void setPrecoBolo(String precoBolo) {
        this.precoBolo = precoBolo;
    }

    public String getQtde() {
        return qtde;
    }

    public void setQtde(String qtde) {
        this.qtde = qtde;
    }

    public String getFrag() {
        return frag;
    }

    public void setFrag(String frag) {
        this.frag = frag;
    }
}
