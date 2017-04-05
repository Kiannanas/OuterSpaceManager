package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnBuyListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.ShipAmount;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders.ShipViewHolder;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders.UserShipAmountViewHolder;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class UserShipsAmountArrayAdapter extends RecyclerView.Adapter<UserShipAmountViewHolder> {

    private final Context context;
    private final List<ShipAmount> shipsAmount;
    private final LayoutInflater inflater;

    public UserShipsAmountArrayAdapter(Context context, List<ShipAmount> shipsAmount) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.shipsAmount = shipsAmount;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserShipAmountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.row_user_ship_amount, parent, false);
        UserShipAmountViewHolder vh = new UserShipAmountViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(UserShipAmountViewHolder vh, int position) {

        ShipAmount current = shipsAmount.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        vh.ivImg.setImageResource(R.drawable.chasseur_leger);
        vh.tvName.setText(current.getName());
        vh.tvAmountValue.setText(""+current.getAmount());
        vh.tvMinAttackValue.setText(""+current.getMinAttack());
        vh.tvMaxAttackValue.setText(""+current.getMaxAttack());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return shipsAmount.size();

    }

}
