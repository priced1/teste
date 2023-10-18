package fieb.aula.confeitaria.model;

public class ItensPedidoModel {
    private int idItensPedido;
    private String idPedido;
    private String idBolo;
    private String qtde;
    private String total;

    public int getIdItensPedido() {
        return idItensPedido;
    }

    public void setIdItensPedido(int idItensPedido) {
        this.idItensPedido = idItensPedido;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdBolo() {
        return idBolo;
    }

    public void setIdBolo(String idBolo) {
        this.idBolo = idBolo;
    }

    public String getQtde() {
        return qtde;
    }

    public void setQtde(String qtde) {
        this.qtde = qtde;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
