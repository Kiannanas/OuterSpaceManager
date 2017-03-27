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
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.ShipAmount;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class ShipsAmountArrayAdapter extends ArrayAdapter<ShipAmount> {

    private final Context context;
    private final List<ShipAmount> shipsAmount;

    public ShipsAmountArrayAdapter(Context context, List<ShipAmount> shipsAmount) {
        super(context, -1, shipsAmount);
        this.context = context;
        this.shipsAmount = shipsAmount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Récupération des composants
        View rowView = inflater.inflate(R.layout.shipsamount_list_row, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvAmountValue = (TextView) rowView.findViewById(R.id.tvAmountValue);
        TextView tvMinAttackValue = (TextView) rowView.findViewById(R.id.tvMinAttackValue);
        TextView tvMaxAttackValue = (TextView) rowView.findViewById(R.id.tvMaxAttackValue);

        ShipAmount shipAmount = shipsAmount.get(position);
        Ship ship = shipAmount.getShip();

        tvName.setText(ship.getName());
        tvAmountValue.setText((""+shipAmount.getAmount()));
        tvMinAttackValue.setText((""+ship.getMinAttack()));
        tvMaxAttackValue.setText((""+ship.getMaxAttack()));

        return rowView;
    }


}
