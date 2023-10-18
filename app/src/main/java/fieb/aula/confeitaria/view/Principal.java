package fieb.aula.confeitaria.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fieb.aula.confeitaria.R;

public class Principal extends AppCompatActivity {

    Button btnQuemSomos, btnVoltarPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        InicializarComponentes();

        btnQuemSomos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quemSomos = new AlertDialog.Builder(Principal.this);
                quemSomos.setTitle("Somos a MagicTI");
                quemSomos.setMessage("Temos uma equipe totalmente apropriada \npara o desenvolvimento do curso." +
                        "\ncomposta pelos  seguintes alunos: " +
                        "\nGessivan Junior \nKetlen Oliveira \nKaua Selles \nJhonatan Torquato \nMurilo Trijilo" +
                        "\nAgradecemos aos professores por ajudar a conclusão desse trabalho.<3");

                quemSomos.setCancelable(false);

                quemSomos.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                quemSomos.create().show();
            }
        });





    }


    private void InicializarComponentes() {
        btnQuemSomos = findViewById(R.id.btnQuemSomos);
        btnVoltarPrincipal = findViewById(R.id.btnVoltarPrincipal);
    }

    public void fecharPrincial(View view) {
        finish();
    }
}