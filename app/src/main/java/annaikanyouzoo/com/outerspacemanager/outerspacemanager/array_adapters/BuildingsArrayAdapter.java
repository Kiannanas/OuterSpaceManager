package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class BuildingsArrayAdapter extends ArrayAdapter<Building> {

    private final Context context;
    private final List<Building> buildings;

    public BuildingsArrayAdapter(Context context, List<Building> buildings) {
        super(context, -1, buildings);
        this.context = context;
        this.buildings = buildings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Récupération des composants
        View rowView = inflater.inflate(R.layout.buildings_list_row, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvGasCost = (TextView) rowView.findViewById(R.id.tvGasCostAmount);
        TextView tvMineralCost = (TextView) rowView.findViewById(R.id.tvMineralCostAmount);
        ImageView ivImg = (ImageView) rowView.findViewById(R.id.ivImg);

        Building building = buildings.get(position);

        tvName.setText(building.getName());
        tvGasCost.setText((""+building.getGasCostByLevel()));
        tvMineralCost.setText((""+building.getMineralCostByLevel()));

        Glide
                .with(context)
                .load(building.getImageUrl())
                .crossFade()
                .into(ivImg);

        return rowView;
    }


}
