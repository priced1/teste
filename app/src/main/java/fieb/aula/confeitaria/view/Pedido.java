package fieb.aula.confeitaria.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.api.Auxiliares;
import fieb.aula.confeitaria.api.PedidoAdapter;
import fieb.aula.confeitaria.controller.ItensDoPedidoController;
import fieb.aula.confeitaria.controller.PedidoController;
import fieb.aula.confeitaria.model.PedidoModel;

public class Pedido extends AppCompatActivity {

    PedidoController pedidoController;

    List<PedidoModel> pedidos; //Linha dos dados
    RecyclerView recyclerView; //Objetos receberá os dados montados

    PedidoAdapter pedidoAdapter;//RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        recyclerView = findViewById(R.id.rvPedidos);

        //==============LIMPEZA DOS DADOS========
        // Limpar Shared
        Auxiliares.preferencesManagerIncluir(getApplicationContext(), "itensDaCompra", "R$ 0,00");
        // Limpar dados de compra
        ItensDoPedidoController itensDoPedidoController = new ItensDoPedidoController();
        itensDoPedidoController.deixarTabelasValoresPadrao(getApplicationContext());
        //=======================================
        carregaLista();
    }


    private void carregaLista() {
        pedidoController = new PedidoController();

        //Obtem a lista de todos os bolos
        pedidos = pedidoController.consultaBolo(getApplicationContext());

        //Adaptador RecyclerView passando a lista de bolos
        pedidoAdapter = new PedidoAdapter(pedidos,Pedido.this);

        //Add a Linha onde está no LinearLayout
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pedidoAdapter);
    }


    public void voltarTelaPedido(View view) {
        finish();
    }
}