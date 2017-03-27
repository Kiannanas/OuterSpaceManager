package annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Attack;

/**
 * Created by annaikanyouzoo on 21/03/2017.
 */

public class AttackViewHolder extends RecyclerView.ViewHolder{

    private Attack attack;
    TextView tvAttacker;
    TextView tvDefender;
    TextView tvDate;

    public AttackViewHolder(View itemView){

        super(itemView);

        // On récupère les composants
        this.tvAttacker = (TextView) itemView.findViewById(R.id.tvAttacker);
        this.tvDefender = (TextView) itemView.findViewById(R.id.tvDefender);
        this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);


    }
}
