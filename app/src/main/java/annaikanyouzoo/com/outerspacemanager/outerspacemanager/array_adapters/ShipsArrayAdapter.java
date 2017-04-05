package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnBuyListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Ship;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders.ShipViewHolder;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class ShipsArrayAdapter extends RecyclerView.Adapter<ShipViewHolder> {

    private final Context context;
    private final OnBuyListener buyListener;
    private final List<Ship> ships;
    private final LayoutInflater inflater;

    public ShipsArrayAdapter(Context context, List<Ship> ships, OnBuyListener listener) {
        inflater = LayoutInflater.from(context);
        buyListener = listener;
        this.context = context;
        this.ships = ships;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.row_shop_building_ship, parent, false);
        ShipViewHolder vh = new ShipViewHolder(view, buyListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ShipViewHolder vh, int position) {

        Ship current = ships.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        vh.ivImg.setImageResource(R.drawable.chasseur_leger);
        vh.tvName.setText(current.getName());
        vh.tvGasCostAmount.setText(""+current.getGasCost());
        vh.tvMineralCostAmount.setText(""+current.getMineralCost());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return ships.size();

    }

}
