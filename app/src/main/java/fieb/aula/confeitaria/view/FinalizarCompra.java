package fieb.aula.confeitaria.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.api.Auxiliares;
import fieb.aula.confeitaria.controller.ItensDoPedidoController;
import fieb.aula.confeitaria.model.CriarIdPedidoModel;
import fieb.aula.confeitaria.model.ItensPedidoModel;
import fieb.aula.confeitaria.model.PedidoModel;

public class FinalizarCompra extends AppCompatActivity {

    CustomAdapter customAdapter;
    ListView lvFinalizarPedido;

    CriarIdPedidoModel criarIdPedidoModel;
    ItensPedidoModel itensPedidoModel;

    //Recebe por Intent
    String totalAPagar;

    //Informação ao cliente
    TextView tvFinalizarCompraInfo;

    RadioButton rbCartaoCredito, rbPix, rbDebito;
    RadioGroup radioGroup;
    String entradaContexto;

    Button btnFinalizarCompra;
    ImageView ivVoltarFinalizarPedido;
    TextView tvTotalAPagar;

    ItensDoPedidoController itensDoPedidoController;

    List<PedidoModel> pedidos; //Linha dos dados

    Context context;//Receber a Tela anterior para fechar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_compra);

        //Recebe o valor Total para fechar o pedido
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            //Se houver id, então
            totalAPagar = extra.getString("chave");
            entradaContexto = extra.getString("contexto");
        }

        inicializarComponentes();

        tvTotalAPagar.setText("Total a Pagar: " + totalAPagar);

        lvFinalizarPedido = findViewById(R.id.lvHistorico);

        preencherItensPedido();


        ivVoltarFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarPedido();// Gerar o nr do Pedido
                gravarItensPedido(); //Gravar todos os itens do pedido
                finalizarCompra(); //Gravar o cliente e o total da compra
                apagarItensPedido(); //Excluir os itens
            }
        });


        rbCartaoCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auxiliares.alertCustom(getApplicationContext(), "Cartão de Crédito");
                btnFinalizarCompra.setEnabled(true);
            }
        });

        rbPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auxiliares.alertCustom(getApplicationContext(), "Pagar com PIX");
                btnFinalizarCompra.setEnabled(true);
            }
        });

        rbDebito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auxiliares.alertCustom(getApplicationContext(), "Pagar com Débito");
                btnFinalizarCompra.setEnabled(true);
            }
        });
    }

    private void apagarItensPedido() {

        //==============LIMPEZA DOS DADOS========
        // Limpar Shared
        Auxiliares.preferencesManagerIncluir(getApplicationContext(), "itensDaCompra", "R$ 0,00");
        // Limpar dados de compra
        ItensDoPedidoController itensDoPedidoController = new ItensDoPedidoController();
        itensDoPedidoController.deixarTabelasValoresPadrao(getApplicationContext());
        //=======================================

        //Desativar o botão
        btnFinalizarCompra.setEnabled(false);

        //Informar o cliente

        AlertDialog.Builder informarCliente = new AlertDialog.Builder(FinalizarCompra.this);
        informarCliente.setTitle("Pedido Fechado");
        informarCliente.setMessage("Parabéns Compra Realizada com Sucesso\n" +
                "Para ver suas compras Entre em Histórico");

        informarCliente.setCancelable(false);

        informarCliente.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Activity objContext = (Activity) context;
                objContext.finish(); //Finaliza Activity anterior
                finish(); //Finaliza esta Activity
            }
        });
        informarCliente.create().show();
    }


    private void inicializarComponentes() {
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra);
        ivVoltarFinalizarPedido = findViewById(R.id.ivVoltarFinalizarPedido);

        tvTotalAPagar = findViewById(R.id.tvTotalAPagar);

        tvFinalizarCompraInfo = findViewById(R.id.tvFinalizarCompraInfo);

        radioGroup = findViewById(R.id.radioGroup);

        rbCartaoCredito = findViewById(R.id.rbCartaoCredito);
        rbDebito = findViewById(R.id.rbDebito);
        rbPix = findViewById(R.id.rbPix);

        setTitle("Finalizar Compra");
    }


    private void gravarItensPedido() {

        //O getId() é o valor/campo retornado
        //Obter o ultimo id gerado do Pedido
        int idUltimoIdGerado = itensDoPedidoController.consultarIdPedido(getApplicationContext()).getId();

        itensDoPedidoController = new ItensDoPedidoController();
        //O res é a resposta na qual se for menor ou igual a 0 haverá erro
        Boolean res = itensDoPedidoController.gravarItensDoPedido(getApplicationContext(), idUltimoIdGerado);

        if (res == false) {
            Auxiliares.alertCustom(getApplicationContext(), "Erro ao criar o Pedido");
        } else {
            //Auxiliares.alertCustom(getApplicationContext(), "Item do pedido Gravado com Sucesso!!!");
        }
    }

    private void gerarPedido() {

        //Gravar nr do Pedido, data, hora, usuário e valor = R$ 0,00
        criarIdPedidoModel = new CriarIdPedidoModel(Auxiliares.getDataAtual(), Auxiliares.getHoraAtual(),"1","0");

        itensDoPedidoController = new ItensDoPedidoController();
        //O res é a resposta na qual se for menor ou igual a 0 haverá erro
        int res = itensDoPedidoController.gravarNrPedido(getApplicationContext(), criarIdPedidoModel);

        if (res <= 0) {
            Auxiliares.alert(getApplicationContext(), "Erro ao criar o Pedido");
        } else {
        }
    }


    private void finalizarCompra() {

        //Alterar o Pedido com o idUsuario e TotalGeral da compra
        int idUltimoIdGerado = itensDoPedidoController.consultarIdPedido(getApplicationContext()).getId();

        itensDoPedidoController = new ItensDoPedidoController();
        String totalGeral = tvTotalAPagar.getText().toString().replace("Total a pagar: ", "");
        Boolean res = itensDoPedidoController.completarPedido(getApplicationContext(),
                idUltimoIdGerado, 2, totalAPagar);

        if (res == false) {
            Auxiliares.alert(getApplicationContext(), "Erro ao criar o Pedido");
        } else {
            btnFinalizarCompra.setEnabled(false);
        }
    }

    public void voltarFinalizarPedido(View view) {
        finish();
    }

    private void preencherItensPedido() {

        itensDoPedidoController = new ItensDoPedidoController();

        pedidos = itensDoPedidoController.consultaItensDoPedidoBolo(getApplicationContext());

        customAdapter = new CustomAdapter();
        lvFinalizarPedido.setAdapter(customAdapter);
    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pedidos.size();
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
            View view = getLayoutInflater().inflate(R.layout.linhas_finalizar_compra, null);

            TextView tvFecharNomeBolo = view.findViewById(R.id.tvFecharNomeBolo);
            TextView tvFecharPreco = view.findViewById(R.id.tvFecharPreco);
            TextView tvFecharQtde = view.findViewById(R.id.tvFecharQtde);
            TextView tvFecharTotalBolo = view.findViewById(R.id.tvFecharTotalBolo);

            PedidoModel objLinha = pedidos.get(position);

            tvFecharNomeBolo.setText(objLinha.getNomeBolo());
            tvFecharPreco.setText(Auxiliares.formatarNumero(objLinha.getPrecoBolo()));
            tvFecharQtde.setText(objLinha.getQtde());

            Double totalDaLinha = Double.parseDouble(
                    objLinha.getPrecoBolo().replace("R$ ", "").replace(",", ".")) * Double.parseDouble(objLinha.getQtde());

            tvFecharTotalBolo.setText(Auxiliares.formatarNumero(totalDaLinha.toString()));
            return view;
        }


        public void voltarFinalizarPedido(View view) {
            finish();
        }
    }
}