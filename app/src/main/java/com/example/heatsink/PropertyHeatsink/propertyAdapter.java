package com.example.heatsink.PropertyHeatsink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heatsink.OwnersHeatsink.Owners;
import com.example.heatsink.OwnersHeatsink.programingAdapter;
import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

public class propertyAdapter extends RecyclerView.Adapter<propertyAdapter.PropertyVH> implements Filterable {

    List<Property> data;
    private List<Property> dataFull;
    private  OnclickProperty onclickProperty;

    public propertyAdapter(List<Property> data , OnclickProperty onclickProperty) {
        this.data = data;
        dataFull = new ArrayList<>(data);
        this.onclickProperty = onclickProperty;
    }

    @NonNull
    @Override
    public PropertyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listofview, parent, false);

        return new PropertyVH(view, onclickProperty);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyVH holder, int position) {
        if(getItemCount()==0){
            return;
        }
        Property property = data.get(position);

        String title = property.getPropertyName();
        String prefix = property.getPropertyName().substring(0,1).toUpperCase();
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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Property> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataFull);
            } else {
                CharSequence filterPattern = constraint.toString().toLowerCase().trim();
                for (Property item : dataFull) {
                    if (item.getPropertyOwnerName().toLowerCase().contains(filterPattern)) {
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

    public interface OnclickProperty{
        void onListProperty(int position);
    }





    public class PropertyVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView gogo;
        public TextView imageicon;
        public TextView nameicon;
        OnclickProperty onclickProperty;

        public PropertyVH(@NonNull View itemView , OnclickProperty onclickProperty) {
            super(itemView);
            imageicon = itemView.findViewById(R.id.imageicon);
            nameicon = itemView.findViewById(R.id.nameicon);
            gogo = itemView.findViewById(R.id.gogo);
            this.onclickProperty = onclickProperty;
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            onclickProperty.onListProperty(getAdapterPosition());
        }
    }
}
