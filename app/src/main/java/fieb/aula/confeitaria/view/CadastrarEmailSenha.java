package fieb.aula.confeitaria.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.api.Auxiliares;
import fieb.aula.confeitaria.controller.LoginController;
import fieb.aula.confeitaria.model.LoginModel;

public class CadastrarEmailSenha extends AppCompatActivity {

    Button btnCadastrarUsuario;
    EditText edtEmail, edtConfirmaSenha, edtSenha;

    //Instancia o usuário para obter os dados por get/set
    LoginModel loginModel;

    //Instancia o Controller para acesso aos dados
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_email_senha);

        inicializaComponentes();

        btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                    edtEmail.setError("Obrigatório *");
                    edtEmail.requestFocus();
                } else if (TextUtils.isEmpty(edtSenha.getText().toString())) {
                    edtSenha.setError("Obrigatório *");
                    edtSenha.requestFocus();
                } else if (TextUtils.isEmpty(edtConfirmaSenha.getText().toString())) {
                    edtConfirmaSenha.setError("Obrigatório *");
                    edtConfirmaSenha.requestFocus();

                } else if (!(edtSenha.getText().toString().equals(edtConfirmaSenha.getText().toString()))) {
                    Auxiliares.alertCustom(getApplicationContext(), "As senhas devem ser Iguais");
                    edtConfirmaSenha.requestFocus();
                } else if (!Auxiliares.validEmail(edtEmail.getText().toString().trim())) {
                    Auxiliares.alertCustom(getApplicationContext(), "Formato do e-mail incorreto");
                    edtEmail.requestFocus();
                }else {
                    loginModel = new LoginModel();
                    loginModel.setEmail(edtEmail.getText().toString());
                    loginModel.setSenha(edtSenha.getText().toString());

                    loginController = new LoginController();

                    //Se validar retorna valor 0 ou menor, então não cadastrou o e-mail/senha
                    int validar = loginController.cadastrarLogin(loginModel, getApplicationContext());
                    if (validar > 0) {
                        Auxiliares.alert(getApplicationContext(), "Cadastro realizado com Sucesso!");
                        finish();
                    } else {
                        Auxiliares.alert(getApplicationContext(), "Error no Cadastro!");
                    }
                }
            }
        });
    }

    private void inicializaComponentes() {

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);

        btnCadastrarUsuario = findViewById(R.id.btnCadastrarUsuario);

        setTitle("Novo Cadastro");
        edtEmail.requestFocus();
}

    public void fecharCadastro(View view) {
        finish();
    }
}