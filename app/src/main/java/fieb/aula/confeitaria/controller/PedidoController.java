package fieb.aula.confeitaria.controller;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fieb.aula.confeitaria.api.ConexaoSqlSever;
import fieb.aula.confeitaria.model.PedidoModel;

public class PedidoController {

    public boolean alterarItemDoPedido(String id, Context context){
        Boolean sucesso=false;
        try {
            Statement stm = ConexaoSqlSever.conectar(context).createStatement();
            String sql = "";
            sql = "UPDATE tbBolo ";
            sql += "SET frag=1 ";
            sql += "WHERE id=" + id ;
            stm.executeUpdate(sql);
            sucesso=true;
        }
        catch (Exception e){
            e.getMessage();
        }
        return sucesso;
    }


    public ArrayList<PedidoModel> consultaBolo(Context context) {
        ArrayList<PedidoModel> list = new ArrayList<>();
        try {

            Statement stm = ConexaoSqlSever.conectar(context).createStatement();

            ResultSet rs = stm.executeQuery("Select * from tbBolo WHERE frag=0");

            while (rs.next()) {
                PedidoModel pedidoModel = new PedidoModel();

                pedidoModel.setId(rs.getInt(1));
                pedidoModel.setFotoBolo(rs.getString(2));
                pedidoModel.setNomeBolo(rs.getString(3));
                pedidoModel.setDescricaoBolo(rs.getString(4));
                pedidoModel.setPrecoBolo(rs.getString(5));

                list.add(pedidoModel);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return list;
    }


}
