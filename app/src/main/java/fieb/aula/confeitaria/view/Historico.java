package fieb.aula.confeitaria.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.api.Auxiliares;
import fieb.aula.confeitaria.controller.ItensDoPedidoController;
import fieb.aula.confeitaria.model.HistoricoModel;
import fieb.aula.confeitaria.model.PedidoHistoricoModel;

public class Historico extends AppCompatActivity {

    ListView lvHistoricoCompra, lvListaPedidos;

    TextView tvDetalhesIdPedido;

    List<HistoricoModel> historicoModel;
    List<PedidoHistoricoModel> pedidoHistoricoModel;

    ItensDoPedidoController itensDoPedidoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        setTitle("Histórico");

        lvHistoricoCompra = findViewById(R.id.lvHistoricoCompra);
        lvListaPedidos = findViewById(R.id.lvListaPedidos);

        tvDetalhesIdPedido=findViewById(R.id.tvDetalhesIdPedido);

        preencherListaPedido();

        lvListaPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PedidoHistoricoModel obterPedido = pedidoHistoricoModel.get(position);
                preencherHistorico(String.valueOf(obterPedido.getIdPedido()));
            }
        });
    }

    private void preencherListaPedido() {
        itensDoPedidoController = new ItensDoPedidoController();

        pedidoHistoricoModel = itensDoPedidoController.apresentarListaPedido(getApplicationContext());

        CustomAdapterListaPedido customAdapterListaPedido = new CustomAdapterListaPedido();
        lvListaPedidos.setAdapter(customAdapterListaPedido);
    }

    public void btnHistoricoVoltar(View view) {finish();
    }

    public class CustomAdapterListaPedido extends BaseAdapter {
        @Override
        public int getCount() {
            return pedidoHistoricoModel.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.linhas_pedido, null);

            TextView tvPedidoIdPedido = view.findViewById(R.id.tvPedidoIdPedido);
            TextView tvPedidoDataCompra = view.findViewById(R.id.tvPedidoDataCompra);
            TextView tvPedidoHoraCompra = view.findViewById(R.id.tvPedidoHoraCompra);
            TextView tvPedidoTotalCompra = view.findViewById(R.id.tvPedidoTotalCompra);

            PedidoHistoricoModel objLinha = pedidoHistoricoModel.get(position);

            tvPedidoIdPedido.setText(String.valueOf("Pedido: "+objLinha.getIdPedido()));
            tvPedidoDataCompra.setText(objLinha.getDataCompra());
            tvPedidoHoraCompra.setText(objLinha.getHoraCompra());
            tvPedidoTotalCompra.setText(objLinha.getTotalCompra());

            return view;
        }
    }

    private void preencherHistorico(String nrPedido) {
        itensDoPedidoController = new ItensDoPedidoController();

        historicoModel = itensDoPedidoController.apresentarHistorico(getApplicationContext(),nrPedido);

        tvDetalhesIdPedido.setText("Pedido: "+nrPedido);

        CustomAdapter customAdapter = new CustomAdapter();
        lvHistoricoCompra.setAdapter(customAdapter);

    }


    public class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return historicoModel.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.linhas_historico, null);

            TextView tvHistoricoNomeBolo = view.findViewById(R.id.tvHistoricoNomeBolo);
            TextView tvHistoricoPrecoBolo = view.findViewById(R.id.tvHistoricoPrecoBolo);
            TextView tvHistoricoQtde = view.findViewById(R.id.tvHistoricoQtde);
            TextView tvHistoricoTotal = view.findViewById(R.id.tvHistoricoTotal);

            HistoricoModel objLinha = historicoModel.get(position);

            tvHistoricoNomeBolo.setText(objLinha.getNomeBolo());
            tvHistoricoPrecoBolo.setText(Auxiliares.formatarNumero(objLinha.getPrecoBolo()));
            tvHistoricoQtde.setText(objLinha.getQtde());
            tvHistoricoTotal.setText(Auxiliares.formatarNumero(objLinha.getTotal()));

            return view;
        }
    }
}