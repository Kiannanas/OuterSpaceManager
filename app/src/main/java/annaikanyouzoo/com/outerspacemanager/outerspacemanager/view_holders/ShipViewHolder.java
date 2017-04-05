package annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnBuyListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Ship;


/**
 * Created by annaikanyouzoo on 01/04/2017.
 */

public class ShipViewHolder extends RecyclerView.ViewHolder {

    private Ship ship;
    public ImageView ivImg;
    public TextView tvName;
    public TextView tvGasCostAmount;
    public TextView tvMineralCostAmount;
    public Button btnBuy;

    public ShipViewHolder(View itemView, final OnBuyListener buyListener){

        super(itemView);

        // On récupère les composants
        this.ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
        this.tvName = (TextView) itemView.findViewById(R.id.tvName);
        this.tvGasCostAmount = (TextView) itemView.findViewById(R.id.tvGasCostAmount);
        this.tvMineralCostAmount = (TextView) itemView.findViewById(R.id.tvMineralCostAmount);
        this.btnBuy = (Button) itemView.findViewById(R.id.btnBuy);

        this.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyListener.onBuy(v, getAdapterPosition());
            }
        });

    }
}
