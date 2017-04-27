package proxiad.com.commercesratp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import proxiad.com.commercesratp.model.Commerces;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Commerces> dataSet;

    public CustomAdapter(ArrayList<Commerces> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_commerces, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.textLabel.setText(dataSet.get(listPosition).getLabel().toUpperCase());
        holder.textCity.setText(dataSet.get(listPosition).getCity().toUpperCase());
        holder.txtCodePostal.setText(dataSet.get(listPosition).getCodePostal());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textLabel;
        public TextView textCity;
        public TextView txtCodePostal;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textLabel = (TextView) itemView.findViewById(R.id.textLabel);
            this.textCity = (TextView) itemView.findViewById(R.id.textCity);
            this.txtCodePostal = (TextView) itemView.findViewById(R.id.textPoint);

        }
    }
}