package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;

/**
 * Created by annaikanyouzoo on 07/03/2017.
 */

public class EnemiesArrayAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final List<User> enemies;

    public EnemiesArrayAdapter(Context context, List<User> ships) {
        super(context, -1, ships);
        this.context = context;
        this.enemies = ships;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Récupération des composants
        View rowView = inflater.inflate(R.layout.row_user, parent, false);
        TextView tvUsername = (TextView) rowView.findViewById(R.id.tvUsername);
        TextView tvPointsValue = (TextView) rowView.findViewById(R.id.tvPointsValue);
        //Button btnAttack = (Button) rowView.findViewById(R.id.btnAttack);

        User enemy = enemies.get(position);

        tvUsername.setText(enemy.getUsername());
        tvPointsValue.setText(enemy.getPoints()+" points");

        return rowView;
    }

}
