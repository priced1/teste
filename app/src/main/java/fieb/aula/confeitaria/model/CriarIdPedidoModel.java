package fieb.aula.confeitaria.model;

public class CriarIdPedidoModel {
    private int idPedido;
    private String dataCompra;
    private String horaCompra;
    private String idUsuario;
    private String totalCompra;


    public CriarIdPedidoModel(String dataCompra, String horaCompra, String idUsuario, String totalCompra) {
        this.dataCompra = dataCompra;
        this.horaCompra = horaCompra;
        this.idUsuario = idUsuario;
        this.totalCompra = totalCompra;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(String horaCompra) {
        this.horaCompra = horaCompra;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(String totalCompra) {
        this.totalCompra = totalCompra;
    }
}
