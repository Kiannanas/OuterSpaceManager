package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Ship;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class ShipsArrayAdapter extends ArrayAdapter<Ship> {

    private final Context context;
    private final List<Ship> ships;

    public ShipsArrayAdapter(Context context, List<Ship> ships) {
        super(context, -1, ships);
        this.context = context;
        this.ships = ships;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Récupération des composants
        View rowView = inflater.inflate(R.layout.ships_list_row, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvGasCost = (TextView) rowView.findViewById(R.id.tvGasCostValue);
        TextView tvMineralCost = (TextView) rowView.findViewById(R.id.tvMineralCostValue);

        Ship ship = ships.get(position);

        tvName.setText(ship.getName());
        tvGasCost.setText((""+ship.getGasCost()));
        tvMineralCost.setText((""+ship.getMineralCost()));

        return rowView;
    }


}
