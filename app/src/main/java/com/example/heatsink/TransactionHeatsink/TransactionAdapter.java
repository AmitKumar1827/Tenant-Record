package com.example.heatsink.TransactionHeatsink;

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
import com.example.heatsink.TenantsHeatsink.Tenants;
import com.example.heatsink.TenantsHeatsink.tenantAdapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionVH> implements Filterable {

    List<Transactions> data;
    List<Transactions> dataFull;
    private OnclickTransaction onclickTransaction;

    public TransactionAdapter(List<Transactions> data , OnclickTransaction onclickTransaction) {
        this.data = data;
        dataFull = new ArrayList<>(data);
        this.onclickTransaction = onclickTransaction;
    }


    @NonNull
    @Override
    public TransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listtransaction, parent, false);

        return new TransactionVH(view , onclickTransaction);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionVH holder, int position) {
        if(getItemCount()==0){
            return;
        }
        Transactions transactions = data.get(position);

        String title = transactions.getTenantName();
        String prefix = transactions.getTenantName().substring(0,1).toUpperCase();
        holder.nameicon.setText(title+" " + transactions.getDate()+" - " + transactions.getTime());
        holder.imageicon.setText(prefix);
        holder.gogo.setText(" ₹ " + transactions.getAmount()+"+");
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
            List<Transactions> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataFull);
            } else {
                CharSequence filterPattern = constraint.toString().toLowerCase().trim();
                for (Transactions item : dataFull) {
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
    public interface OnclickTransaction{
        void onListTransaction(int position);
    }

    public class TransactionVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView gogo;
        public TextView imageicon;
        public TextView nameicon;
        OnclickTransaction onclickTransaction;

        public TransactionVH(@NonNull View itemView , OnclickTransaction onclickTransaction) {
            super(itemView);
            imageicon = itemView.findViewById(R.id.imageicon);
            nameicon = itemView.findViewById(R.id.nameicon);
            gogo = itemView.findViewById(R.id.gogo);
            this.onclickTransaction = onclickTransaction;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onclickTransaction.onListTransaction(getAdapterPosition());
        }
    }
}
