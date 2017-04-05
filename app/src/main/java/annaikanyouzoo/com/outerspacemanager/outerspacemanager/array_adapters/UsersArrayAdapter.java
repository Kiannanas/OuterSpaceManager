package annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnAttackListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders.UserViewHolder;

/**
 * Created by annaikanyouzoo on 28/03/2017.
 */

public class UsersArrayAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private final Context context;
    private final OnAttackListener attackListener;
    private final List<User> users;
    private final LayoutInflater inflater;

    public UsersArrayAdapter(Context context, List<User> users, OnAttackListener listener){
        inflater = LayoutInflater.from(context);
        attackListener = listener;
        this.context = context;
        this.users = users;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.row_user, parent, false);
        UserViewHolder vh = new UserViewHolder(view, attackListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(UserViewHolder vh, int position) {

        User current = users.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        vh.tvUsername.setText(current.getUsername());
        vh.tvPointsValue.setText(""+current.getPoints());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return users.size();

    }

}
