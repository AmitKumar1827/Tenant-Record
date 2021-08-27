package com.example.heatsink.OwnersHeatsink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

public class programingAdapter extends RecyclerView.Adapter<programingAdapter.PVH> implements Filterable {

    private List<Owners> data;
    private List<Owners> dataFull;
    private OnclickOwners monclickOwners;


//    private SelectedUser selectedUser;


    public programingAdapter(List<Owners> data, OnclickOwners onclickOwners) {

        this.data = data;
        dataFull = new ArrayList<>(data);
        this.monclickOwners = onclickOwners;
//        this.selectedUser = selectedUser;
    }


    @NonNull
    @Override
    public PVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listofview, parent, false);

        return new PVH(view, monclickOwners);
    }

    @Override
    public void onBindViewHolder(@NonNull PVH holder, int position) {

        if(getItemCount()==0){
            return;
        }
        Owners owners = data.get(position);

        String title = owners.getName();
        String prefix = owners.getName().substring(0,1).toUpperCase();
        holder.nameicon.setText(title);
        holder.imageicon.setText(prefix);
        holder.gogo.setImageResource(R.drawable.arrow);

    }

    @Override
    public int getItemCount() {
        if(data!=null){
            return data.size();
        } else{
            return 0;
        }
    }

    public class PVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView gogo;
         public TextView imageicon;
        public TextView nameicon;
        OnclickOwners onclickOwners;


        public PVH(final View itemView, OnclickOwners onclickOwners) {
            super(itemView);
            imageicon = itemView.findViewById(R.id.imageicon);
            nameicon = itemView.findViewById(R.id.nameicon);
            gogo = itemView.findViewById(R.id.gogo);
            this.onclickOwners = onclickOwners;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onclickOwners.onListOwners((int)getAdapterPosition());
        }
    }

    public interface OnclickOwners{
        void onListOwners(int position);
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Owners> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataFull);
            } else {
                CharSequence filterPattern = constraint.toString().toLowerCase().trim();
                for (Owners item : dataFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            data.clear();
            data.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }

    };

}


//    public interface SelectedUser{
//        void selectedUser(userModel userModel);
//    }




