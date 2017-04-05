package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnAttackListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Report;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders.AttackViewHolder;

/**
 * Created by annaikanyouzoo on 21/03/2017.
 */

public class AttacksArrayAdapter extends RecyclerView.Adapter<AttackViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<Report> attacks;

    public AttacksArrayAdapter(Context context, List<Report> attacks){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.attacks = attacks;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AttackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.row_attack, parent, false);
        AttackViewHolder vh = new AttackViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AttackViewHolder vh, int position) {

        Report current = attacks.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        vh.tvAttacker.setText(current.getFrom());
        vh.tvDefender.setText(current.getTo());
        vh.tvDate.setText(""+current.getDate());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return attacks.size();

    }

}
