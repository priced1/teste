package fieb.aula.confeitaria.controller;

import android.content.Context;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fieb.aula.confeitaria.api.ConexaoSqlSever;
import fieb.aula.confeitaria.model.CriarIdPedidoModel;
import fieb.aula.confeitaria.model.HistoricoModel;
import fieb.aula.confeitaria.model.PedidoHistoricoModel;
import fieb.aula.confeitaria.model.PedidoModel;

public class ItensDoPedidoController {


    public ArrayList<HistoricoModel> apresentarHistorico(Context context, String nrPedido) {
        ArrayList<HistoricoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlSever.conectar(context).createStatement();

            //Histórico pelo nr do pedido, retornando:
            //NomeBolo, preço do bolo, qtde, total e totalCompra
            ResultSet rs = stm.executeQuery(
                    "SELECT tbBolo.nomeBolo,tbBolo.precoBolo,tbItensPedido.qtde, tbItensPedido.total, tbPedido.totalCompra" +
                            " FROM tbPedido" +
                            " INNER JOIN tbItensPedido" +
                            " ON tbPedido.idPedido= tbItensPedido.idPedido" +
                            " INNER JOIN tbBolo" +
                            " ON tbBolo.id=tbItensPedido.idBolo" +
                            " WHERE tbPedido.idPedido="+nrPedido);
            while (rs.next()) {
                HistoricoModel historicoModel = new HistoricoModel();

                historicoModel.setNomeBolo(rs.getString(1));
                historicoModel.setPrecoBolo(rs.getString(2));
                historicoModel.setQtde(rs.getString(3));
                historicoModel.setTotal(rs.getString(4));

                list.add(historicoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }


    public ArrayList<PedidoHistoricoModel> apresentarListaPedido(Context context) {
        ArrayList<PedidoHistoricoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlSever.conectar(context).createStatement();

            //Histórico de todos os pedidos sendo o IdPedido da ultima para primeira:
            ResultSet rs = stm.executeQuery(
                    "SELECT idPedido,dataCompra,horaCompra,totalCompra FROM tbPedido ORDER BY idPedido DESC");

            while (rs.next()) {
                PedidoHistoricoModel pedidoHistoricoModel = new PedidoHistoricoModel();

                pedidoHistoricoModel.setIdPedido(rs.getInt(1));
                pedidoHistoricoModel.setDataCompra(rs.getString(2));
                pedidoHistoricoModel.setHoraCompra(rs.getString(3));
                pedidoHistoricoModel.setTotalCompra(rs.getString(4));

                list.add(pedidoHistoricoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }

    public boolean completarPedido(Context context,int nrPedido,int idUsuario, String valorTotalCompra) {
        Boolean sucesso = false;
        try {
            Statement stm = ConexaoSqlSever.conectar(context).createStatement();
            String sql = "";
            sql = "UPDATE tbPedido SET ";
            sql += "idUsuario="+idUsuario + ",totalCompra='"+ valorTotalCompra +"'";
            sql += " WHERE idPedido=" + nrPedido;
            stm.executeUpdate(sql);
            sucesso = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return sucesso;
    }



    public Boolean gravarItensDoPedido(Context context, int idUltimoIdGerado) {

        Boolean sucesso = false;
        try {
            String sql = "";
            Statement stm = ConexaoSqlSever.conectar(context).createStatement();

            sql = "Insert Into tbItensPedido(idPedido,idBolo,qtde,total) ";
            sql += "SELECT "+ idUltimoIdGerado +", id, qtde, (qtde*precoBolo)";
            sql += " FROM tbBolo";
            sql += " WHERE frag = 1";
//Insert Into tbItensPedido(idPedido,idBolo,qtde,total) SELECT 3, id, qtde, (qtde*precoBolo) FROM tbBolo WHERE frag = 1
            stm.executeUpdate(sql);
            sucesso=true;
        } catch (Exception e) {
            e.getMessage();
        }

        return sucesso;
    }


    public PedidoModel consultarIdPedido(Context context) {
        PedidoModel idPedido = null;
        try {
            Statement stm = ConexaoSqlSever.conectar(context).createStatement();
            ResultSet rs = stm.executeQuery("SELECT MAX (idPedido) FROM tbPedido");
            while (rs.next()) {
                PedidoModel pedidoModel = new PedidoModel();

                pedidoModel.setId(rs.getInt(1)); // Envia o Id para tabelaTemp
                idPedido = pedidoModel;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return idPedido;
    }

    public int gravarNrPedido(Context context, CriarIdPedidoModel criarIdPedidoModel) {

        int resposta = 0;
        try {
            PreparedStatement pst =ConexaoSqlSever.conectar(context).prepareStatement(
                    "Insert Into tbPedido(dataCompra,horaCompra,idUsuario,totalCompra) " + "values (?,?,?,?)"
            );
            pst.setString(1, criarIdPedidoModel.getDataCompra());
            pst.setString(2, criarIdPedidoModel.getHoraCompra());
            pst.setString(3, criarIdPedidoModel.getIdUsuario());
            pst.setString(4, criarIdPedidoModel.getTotalCompra());
            resposta = pst.executeUpdate();

        } catch (Exception e) {
            e.getMessage();
        }

        return resposta;
    }



    public boolean excluirItemDaTelaPedido(String id, Context context) {
        Boolean sucesso = false;
        try {
            Statement stm = ConexaoSqlSever.conectar(context).createStatement();
            String sql = "";
            sql = "UPDATE tbBolo ";
            sql += "SET frag=0 ";
            sql += "WHERE id=" + id;

            stm.executeUpdate(sql);
            sucesso = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return sucesso;
    }



    public boolean IncluirQtdeItemDaTelaBolo(String id, Context context, String qtde) {
        Boolean sucesso = false;
        try {
            Statement stm =ConexaoSqlSever.conectar(context).createStatement();
            String sql = "";
            sql = "UPDATE tbBolo ";
            sql += "SET qtde= " + qtde;
            sql += "WHERE id=" + id;

            stm.executeUpdate(sql);
            sucesso = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return sucesso;
    }


    public void deixarTabelasValoresPadrao(Context context) {
        try {
            Statement stm = ConexaoSqlSever.conectar(context).createStatement();
            String sql = "";
            sql = "UPDATE tbBolo SET qtde=1,frag=0";
            stm.executeUpdate(sql);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public ArrayList<PedidoModel> consultaItensDoPedidoBolo(Context context) {
        ArrayList<PedidoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlSever.conectar(context).createStatement();

            ResultSet rs = stm.executeQuery("Select * from tbBolo where frag=1");

            while (rs.next()) {
                PedidoModel pedidoModel = new PedidoModel();

                pedidoModel.setId(rs.getInt(1));
                pedidoModel.setFotoBolo(rs.getString(2));
                pedidoModel.setNomeBolo(rs.getString(3));
                pedidoModel.setDescricaoBolo(rs.getString(4));
                pedidoModel.setPrecoBolo(rs.getString(5));
                pedidoModel.setQtde(rs.getString(6));
                pedidoModel.setFrag(rs.getString(7));

                list.add(pedidoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }




}
