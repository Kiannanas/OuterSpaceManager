package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnBuyListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders.BuildingViewHolder;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class BuildingsArrayAdapter extends RecyclerView.Adapter<BuildingViewHolder>{


    private final Context context;
    private final OnBuyListener buyListener;
    private final List<Building> buildings;
    private final LayoutInflater inflater;

    public BuildingsArrayAdapter(Context context, List<Building> buildings, OnBuyListener listener) {
        inflater = LayoutInflater.from(context);
        buyListener = listener;
        this.context = context;
        this.buildings = buildings;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BuildingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.row_shop_building_ship, parent, false);
        BuildingViewHolder vh = new BuildingViewHolder(view, buyListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BuildingViewHolder vh, int position) {

        Building current = buildings.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        vh.tvName.setText(current.getName());
        vh.tvGasCostAmount.setText(""+current.getGasCost());
        vh.tvMineralCostAmount.setText(""+current.getMineralCost());

        Glide
                .with(context)
                .load(current.getImageUrl())
                .crossFade()
                .into(vh.ivImg);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return buildings.size();

    }

}
