package fieb.aula.confeitaria.api;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.controller.ItensDoPedidoController;
import fieb.aula.confeitaria.model.PedidoModel;

public class ItensPedidoAdapter extends RecyclerView.Adapter<ItensPedidoAdapter.ViewHolder> {
    @NonNull

    public List<PedidoModel> listPedido;
    public Context aContext;

    Button aBtnFecharPedido; //Botão da Activity Principal ItensDoPedido
    TextView aTvTotal; //Mesmo do acima - ItensDoPedido

    //int qtdeCompra = 0;
    float preco = 0.0f;

    ItensDoPedidoController itensDoPedidoController;

    //TODO Ver se é necessário o btnFecharPedido passado
    public ItensPedidoAdapter(List<PedidoModel> pedido, Context context, Button btnFecharPedido, TextView tvTotal) {
        listPedido = pedido;
        aContext = context;
//        aBtnFecharPedido = btnFecharPedido;
        aTvTotal = tvTotal;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View linhaView = inflater.inflate(R.layout.linhas_itens_do_pedido, parent, false);
        ViewHolder viewHolder = new ViewHolder(linhaView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PedidoModel objLinha = listPedido.get(position);

        TextView tvIdBolo = holder.rvItensPedidoIdBolo;
        tvIdBolo.setText(String.valueOf(objLinha.getId()));

//        ===================

        ImageView tvImagemBolo = holder.rvItensPedidoImagemBolo;

        Resources resources = aContext.getResources();
        int idR = resources.getIdentifier(objLinha.getFotoBolo(), "drawable", aContext.getPackageName());
        tvImagemBolo.setImageResource(idR);

//        =============

        TextView tvNomeBolo = holder.rvItensPedidoNomeBolo;
        tvNomeBolo.setText(objLinha.getNomeBolo());

        TextView tvDescricao = holder.rvItensPedidoDescricaoBolo;
        tvDescricao.setText(objLinha.getDescricaoBolo());

        TextView tvPreco = holder.rvItensPedidoPrecoBolo;
        tvPreco.setText(Auxiliares.formatarNumero(objLinha.getPrecoBolo()));

        TextView tvQtde = holder.tvQtdeCompra;
        tvQtde.setText(objLinha.getQtde());
    }

    @Override
    public int getItemCount() {

        return listPedido.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rvItensPedidoIdBolo;
        public ImageView rvItensPedidoImagemBolo;
        public TextView rvItensPedidoNomeBolo;
        public TextView rvItensPedidoDescricaoBolo;
        public TextView rvItensPedidoPrecoBolo;

        public TextView tvQtdeCompra;

        public ImageView btnAdicionarItem;
        public ImageView btnRemoverItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rvItensPedidoIdBolo = itemView.findViewById(R.id.tvIdBoloLinhaItensPedido);
            rvItensPedidoImagemBolo = itemView.findViewById(R.id.ivBoloLinhaItensPedido);
            rvItensPedidoNomeBolo = itemView.findViewById(R.id.tvNomeBoloLinhaItensPedido);
            rvItensPedidoDescricaoBolo = itemView.findViewById(R.id.tvDescricaoBoloLinhaItensPedido);
            rvItensPedidoPrecoBolo = itemView.findViewById(R.id.tvPrecoBoloLinhaItensPedido);

            tvQtdeCompra = itemView.findViewById(R.id.tvQtdeCompraItensPedido);

            btnAdicionarItem = itemView.findViewById(R.id.ivBtnAdicionarItensPedido);
            btnRemoverItem = itemView.findViewById(R.id.ivBtnRemoverItensPedido);

            btnRemoverItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //tvQtdeCompra = qtde de itens
                    tvQtdeCompra.setText(subtrairItem(tvQtdeCompra) + "");
                    excluirItemPedido();
                    //Atualiza a qtde no SQL
                    itensDoPedidoController = new ItensDoPedidoController();
                    itensDoPedidoController.IncluirQtdeItemDaTelaBolo(
                            rvItensPedidoIdBolo.getText().toString(),aContext,tvQtdeCompra.getText().toString());
                }
            });

            btnAdicionarItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //tvQtdeCompra = a qtde de compra
                    tvQtdeCompra.setText(somarItem(tvQtdeCompra) + "");
//                    aBtnFecharPedido.setEnabled(true);

                    //Gravar a Qtde
                    //Passa Id, contexto, valorCompra
                    itensDoPedidoController = new ItensDoPedidoController();
                    itensDoPedidoController.IncluirQtdeItemDaTelaBolo(
                            rvItensPedidoIdBolo.getText().toString(),aContext,tvQtdeCompra.getText().toString());
                }
            });
        }

        private int somarItem(TextView tvQtdeCompra) {
            //tvQtdeCompra Valor informado
            int aux = Integer.parseInt(tvQtdeCompra.getText().toString());
            if (aux >= 5) {
                Auxiliares.alert(aContext, "Somente conseguimos fazer 5 bolos");
            } else {
                //Preco obtem o valor do Bolo
                //Retirar o R$ e troca , por .
                preco=Float.parseFloat(aTvTotal.getText().toString().replace("R$ ","").replace(",","."));
                //Retira o R$ do total
                //Soma com preco do Bolo
                preco += Float.parseFloat(rvItensPedidoPrecoBolo.getText().toString()
                        .replace("R$ ", "")
                        .replace(",", "."));

                //aTvTotal = Total geral da compra
                //Preco ficou atualizado
                //Formata com R$
                aTvTotal.setText(Auxiliares.formatarNumero(String.valueOf(preco)));

                Auxiliares.preferencesManagerIncluir(aContext,
                        "itensDaCompra", aTvTotal.getText().toString());
                //aux contem a qtde de bolos selecionados para compra
                //Soma 1 e retorna
                aux++;
            }
            return aux;
        }

        private int subtrairItem(TextView tvQtdeCompra) {
            int qtdeItens = Integer.parseInt(tvQtdeCompra.getText().toString());
            //Caso menor que 1 excluir da lista
            if (qtdeItens <= 1) {
                excluirItemPedido();//AlertDialog para retirar item do pedido
            } else {
                preco=Float.parseFloat(aTvTotal.getText().toString().replace("R$ ","").replace(",","."));
                preco -= Double.parseDouble(rvItensPedidoPrecoBolo.getText().toString().replace("R$ ", "").replace(",", "."));
                aTvTotal.setText(Auxiliares.formatarNumero(String.valueOf(preco)));

                Auxiliares.preferencesManagerIncluir(aContext,
                        "itensDaCompra", aTvTotal.getText().toString());
                //Retirar item
                qtdeItens--;
            }
            return qtdeItens;
        }

        private void excluirItemPedido() {
            AlertDialog.Builder excluir = new AlertDialog.Builder(aContext);
            excluir.setTitle("EXCLUSÃO");
            excluir.setMessage("Tem certeza da Exclusão do Bolo\n"
                    + rvItensPedidoNomeBolo.getText().toString().toUpperCase());

            excluir.setCancelable(false);

            //=========Caso Não=============
            excluir.setPositiveButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            //=========Caso Sim=============
            //Excluir o bolo dos itens - botão menos
            excluir.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    itensDoPedidoController = new ItensDoPedidoController();
                    //Retira no Frag o valor 1 para excluir o item do pedido
                    //Informado rvItensPedidoIdBolo=Id bolo
                    itensDoPedidoController.excluirItemDaTelaPedido(rvItensPedidoIdBolo.getText().toString(), aContext);
                    int position = getAdapterPosition(); //Obtem a posição
                    listPedido.remove(position); //Remove a posição da linha
                    notifyItemRemoved(position); //Atualiza a lista

                    //===========Atualizar o valor total=============
                    //Atualizar o valor Total (Mesmo codígo do else em SubtrairItem)
                    preco=Float.parseFloat(aTvTotal.getText().toString().replace("R$ ","").replace(",","."));
                    preco -= Double.parseDouble(rvItensPedidoPrecoBolo.getText().toString().replace("R$ ", "").replace(",", "."));
                    aTvTotal.setText(Auxiliares.formatarNumero(String.valueOf(preco)));

                    Auxiliares.preferencesManagerIncluir(aContext,
                            "itensDaCompra", aTvTotal.getText().toString());

                    if(listPedido.size()==0){
                        try {
                            //Se não houver mais nenhum item de compra
                            // Limpar todos os valores no Shared
                            Auxiliares.preferencesManagerIncluir(aContext,"itensDaCompra","0.00");
                            //Fechar Activity
                            ((Activity) aContext).finish();

                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            });


            excluir.create().show();
        }

    }
}

