package com.example.heatsink.TenantsHeatsink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heatsink.PropertyHeatsink.Property;
import com.example.heatsink.PropertyHeatsink.propertyAdapter;
import com.example.heatsink.R;

import java.util.ArrayList;
import java.util.List;

public class tenantAdapter extends RecyclerView.Adapter<tenantAdapter.TenantVH> implements Filterable {

    List<Tenants> data;
    List<Tenants> dataFull;
    private OnclickTenant onclickTenant;

    public tenantAdapter(List<Tenants> data, OnclickTenant onclickTenant) {
        this.data = data;
        dataFull = new ArrayList<>(data);
        this.onclickTenant = onclickTenant;
    }

    @NonNull
    @Override
    public TenantVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listofview, parent, false);

        return new TenantVH(view, onclickTenant);
    }

    @Override
    public void onBindViewHolder(@NonNull TenantVH holder, int position) {
        if(getItemCount()==0){
            return;
        }
        Tenants tenants = data.get(position);

        String title = tenants.getTenantName();
        String prefix = tenants.getTenantName().substring(0,1).toUpperCase();
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
            List<Tenants> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataFull);
            } else {
                CharSequence filterPattern = constraint.toString().toLowerCase().trim();
                for (Tenants item : dataFull) {
                    if (item.getTenantName().toLowerCase().contains(filterPattern)) {
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

    public interface OnclickTenant{
         void onListTenant(int position);
    }

    public class TenantVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView gogo;
        public TextView imageicon;
        public TextView nameicon;
        OnclickTenant onclickTenant;
        public TenantVH(@NonNull View itemView , OnclickTenant onclickTenant) {
            super(itemView);
            imageicon = itemView.findViewById(R.id.imageicon);
            nameicon = itemView.findViewById(R.id.nameicon);
            gogo = itemView.findViewById(R.id.gogo);
            this.onclickTenant = onclickTenant;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onclickTenant.onListTenant(getAdapterPosition());
        }
    }
}
