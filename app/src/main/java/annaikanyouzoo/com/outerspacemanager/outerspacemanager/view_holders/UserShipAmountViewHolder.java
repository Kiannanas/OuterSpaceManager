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
 * Created by annaikanyouzoo on 02/04/2017.
 */

public class UserShipAmountViewHolder extends RecyclerView.ViewHolder {

    private Ship ship;
    public ImageView ivImg;
    public TextView tvName;
    public TextView tvAmountValue;
    public TextView tvMinAttackValue;
    public TextView tvMaxAttackValue;

    public UserShipAmountViewHolder(View itemView){

        super(itemView);

        // On récupère les composants
        this.ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
        this.tvName = (TextView) itemView.findViewById(R.id.tvName);
        this.tvAmountValue = (TextView) itemView.findViewById(R.id.tvAmountValue);
        this.tvMinAttackValue = (TextView) itemView.findViewById(R.id.tvMinAttackValue);
        this.tvMaxAttackValue = (TextView) itemView.findViewById(R.id.tvMaxAttackValue);

    }


}
