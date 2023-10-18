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

    Button btnPedidos, btnPromocoes, btnHistorio, btnMeusDados, btnQuemSomos, btnVoltarPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        InicializarComponentes();

        btnQuemSomos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quemSomos = new AlertDialog.Builder(Principal.this);
                quemSomos.setTitle("Somos da Confeitaria Que Delícia");
                quemSomos.setMessage("Tudo feito a mão por renomados confeiteiros\\verdadeiros artistas" +
                        "\ncom fornos de ultima geração por isso uma Delícia" +
                        "\nAproveite nossas promoções!!!\nAguardo seus pedidos");

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


        btnHistorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Historico.class);
                startActivity(intent);
            }
        });

        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pedido.class);
                startActivity(intent);
            }
        });


    }


    private void InicializarComponentes() {
        btnPedidos = findViewById(R.id.btnPedidos);
        btnPromocoes = findViewById(R.id.btnPromocoes);
        btnHistorio = findViewById(R.id.btnHistorico);
        btnMeusDados = findViewById(R.id.btnMeusDados);
        btnQuemSomos = findViewById(R.id.btnQuemSomos);
        btnVoltarPrincipal = findViewById(R.id.btnVoltarPrincipal);
    }

    public void fecharPrincial(View view) {
        finish();
    }
}