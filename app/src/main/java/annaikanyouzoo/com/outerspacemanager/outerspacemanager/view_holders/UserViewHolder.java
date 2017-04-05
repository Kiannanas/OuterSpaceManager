package annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnAttackListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Report;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;

/**
 * Created by annaikanyouzoo on 28/03/2017.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

    private User user;
    public TextView tvUsername;
    public TextView tvPointsValue;
    public Button btnAttack;

    public UserViewHolder(View itemView, final OnAttackListener attackListener){

        super(itemView);

        // On récupère les composants
        this.tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        this.tvPointsValue = (TextView) itemView.findViewById(R.id.tvPointsValue);
        this.btnAttack = (Button) itemView.findViewById(R.id.btnAttack);

        this.btnAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attackListener.onAttack(v, getAdapterPosition());
            }
        });

    }
}
