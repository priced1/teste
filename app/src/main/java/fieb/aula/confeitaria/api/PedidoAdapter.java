package fieb.aula.confeitaria.api;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fieb.aula.confeitaria.R;
import fieb.aula.confeitaria.controller.PedidoController;
import fieb.aula.confeitaria.model.PedidoModel;
import fieb.aula.confeitaria.view.ItensDoPedido;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {
    @NonNull

    public List<PedidoModel> listPedido;
    public Context aContext;

    PedidoController pedidoController;

    public PedidoAdapter(List<PedidoModel> pedido, Context context) {
        listPedido = pedido;
        aContext = context;
    }

    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View linhaView = inflater.inflate(R.layout.linhas_bolos, parent, false);
        ViewHolder viewHolder = new ViewHolder(linhaView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.ViewHolder holder, int position) {
        PedidoModel objLinha = listPedido.get(position);

        TextView tvIdBolo = holder.rvIdBolo;
        tvIdBolo.setText(String.valueOf(objLinha.getId()));

//        ========Carrega as Imagens===========

        ImageView tvImagemBolo = holder.rvImagemBolo;

        Resources resources = aContext.getResources();
        int idR = resources.getIdentifier(objLinha.getFotoBolo(), "drawable", aContext.getPackageName());
        tvImagemBolo.setImageResource(idR);

//        =====================================

        TextView tvNomeBolo = holder.rvNomeBolo;
        tvNomeBolo.setText(objLinha.getNomeBolo());

        TextView tvDescricao = holder.rvDescricaoBolo;
        tvDescricao.setText(objLinha.getDescricaoBolo());


        TextView tvPreco = holder.rvPrecoBolo;
        tvPreco.setText(Auxiliares.formatarNumero(objLinha.getPrecoBolo()));

    }

    @Override
    public int getItemCount() {
        return listPedido.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView rvIdBolo;
        public ImageView rvImagemBolo;
        public TextView rvNomeBolo;
        public TextView rvDescricaoBolo;
        public TextView rvPrecoBolo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rvIdBolo = itemView.findViewById(R.id.tvIdBolo);
            rvImagemBolo = itemView.findViewById(R.id.ivBolo);
            rvNomeBolo = itemView.findViewById(R.id.tvNomeBolo);
            rvDescricaoBolo = itemView.findViewById(R.id.tvDescricaoBolo);
            rvPrecoBolo = itemView.findViewById(R.id.tvPrecoBolo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            pedidoController = new PedidoController();

            //Grava no Frag o valor 1 para consultar os itens do pedido
            pedidoController.alterarItemDoPedido(rvIdBolo.getText().toString(), aContext);

            Intent intent = new Intent(aContext, ItensDoPedido.class);

            //Passando o id do Bolo e o valor
            intent.putExtra("chave", rvIdBolo.getText().toString());
            intent.putExtra("chaveValorBolo", rvPrecoBolo.getText().toString());
            intent.putExtra("contextPedido", "aContext");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            aContext.startActivity(intent);
        }
    }
}

