package fieb.aula.confeitaria.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.api.Auxiliares;
import fieb.aula.confeitaria.api.ItensPedidoAdapter;
import fieb.aula.confeitaria.controller.ItensDoPedidoController;
import fieb.aula.confeitaria.model.PedidoModel;

public class ItensDoPedido extends AppCompatActivity {

    ItensDoPedidoController itensDoPedidoController;

    List<PedidoModel> pedidos; //Linha dos dados
    RecyclerView recyclerView; //Objetos receberá os dados montados

    ItensPedidoAdapter itensPedidoAdapter;

    //Id do Bolo informado pela seleção em Pedido
    String id = "", valorBolo;

    Button btnFecharPedido;
    TextView tvTotal;
    double totalItensCompra;

    String contextPedido;//Receber a Tela do pedido para fechar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_do_pedido);

        btnFecharPedido = findViewById(R.id.btnFecharPedido);
        tvTotal = findViewById(R.id.tvTotal);
        setTitle("Selecione a Quantidade");



        btnFecharPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder solicitaFecharPedido = new AlertDialog.Builder(ItensDoPedido.this);
                solicitaFecharPedido.setTitle("Fechar Pedido!");
                solicitaFecharPedido.setMessage("Quer Finalizar o Pedido?");

                solicitaFecharPedido.setCancelable(false);

                solicitaFecharPedido.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ItensDoPedido.this, FinalizarCompra.class);
                        //Envia o valor para finalizar pedido
                        intent.putExtra("chave", tvTotal.getText().toString());
                        intent.putExtra("contextoPedido" , "contextPedido");
                        startActivity(intent);
                        finish();
                    }
                });

                solicitaFecharPedido.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                solicitaFecharPedido.create().show();

            }
        });

        //Verifica os valores informados
        //Obtem o ID passado
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            //Se houver id, então
            id = extra.getString("chave");
            valorBolo = extra.getString("chaveValorBolo");
            contextPedido = extra.getString("contextPedido");
            recyclerView = findViewById(R.id.rvItemDoPedido);
            carregaLista();
        }

    }

    private void carregaLista() {
        //Obtem o valor atual
        String valorAtual = Auxiliares.preferencesManagerObter(getApplicationContext(),"itensDaCompra","R$ 0,00");//  tvTotal.getText().toString().replace("R$ ","").replace(",",".");

        //Somar o valor atual + valor enviado  0.00
        valorAtual=valorAtual.replace("R$ ","").replace(",",".");
        valorBolo=valorBolo.replace("R$ ","").replace(",",".");

        totalItensCompra = (Double.parseDouble(valorAtual) + Double.parseDouble(valorBolo));

        Auxiliares.preferencesManagerIncluir(getApplicationContext(),"itensDaCompra",String.valueOf(totalItensCompra));

        tvTotal.setText(Auxiliares.formatarNumero(String.valueOf(totalItensCompra))+"");

        itensDoPedidoController = new ItensDoPedidoController();

        //A consultaItensDoPedidoBolo carrega os Bolos com frag=1
        pedidos = itensDoPedidoController.consultaItensDoPedidoBolo(getApplicationContext());


        //===> Muito cuidado aqui
        //===> Por que temos que passar o this e não o getApplicationContext()
        itensPedidoAdapter = new ItensPedidoAdapter(pedidos, ItensDoPedido.this, btnFecharPedido, tvTotal);

        //Add a Linha onde está no LinearLayout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(itensPedidoAdapter);
    }



}
