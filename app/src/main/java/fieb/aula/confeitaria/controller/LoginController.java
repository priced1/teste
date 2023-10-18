package fieb.aula.confeitaria.controller;

import android.content.Context;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import fieb.aula.confeitaria.api.ConexaoSqlSever;
import fieb.aula.confeitaria.model.LoginModel;

public class LoginController {

    public static LoginModel validarLogin(Context context, String email, String senha) {

        try {

            PreparedStatement pst = ConexaoSqlSever.conectar(context).prepareStatement(
                    "SELECT eMail, senha FROM tbUsuario WHERE eMail=? AND senha=?");

            //Os números abaixo são os indices da ordem dos campos da tabela
            //Deve seguir a ordem
            pst.setString(1,email);
            pst.setString(2,senha);

            ResultSet res = pst.executeQuery();

            while (res.next()) {

                LoginModel loginModel = new LoginModel();

                loginModel.setEmail(res.getString(1));
                loginModel.setSenha(res.getString(2));
                return loginModel;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public int cadastrarLogin(LoginModel loginModel, Context context) {

        //Se a resposta for 0 ou menor de 0 então o e-mail não foi encontrado
        int resposta = 0;
        try {
            PreparedStatement pst = ConexaoSqlSever.conectar(context).prepareStatement(
                    "INSERT INTO tbUsuario(eMail,senha) values (?,?)"
            );
            pst.setString(1, loginModel.getEmail());
            pst.setString(2, loginModel.getSenha());
            resposta = pst.executeUpdate();

        } catch (Exception e) {
            e.getMessage();
        }

        return resposta;
    }
}
