package com.example.heatsink.BankHeatsink;

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

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankVH> implements Filterable{

    private List<Banks> data;
    private List<Banks> dataFull;
    private OnclickBank monclickBank;

    public BankAdapter(List<Banks> data , OnclickBank onclickBank) {
        this.data = data;
        dataFull = new ArrayList<>(data);
        this.monclickBank = onclickBank;
    }

    @NonNull
    @Override
    public BankVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listofview, parent, false);

        return new BankAdapter.BankVH(view , monclickBank);
    }

    @Override
    public void onBindViewHolder(@NonNull BankVH holder, int position) {

        if(getItemCount()==0){
            return;
        }
        Banks banks = data.get(position);


        String prefix = banks.getBankName().substring(0,1).toUpperCase();
        holder.nameicon.setText(banks.getAccountHolder() +" : " +banks.getBankName());
        holder.imageicon.setText(prefix);
        holder.gogo.setImageResource(R.drawable.arrow);
    }
    public interface OnclickBank{
        void onListBank(int position);
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
            List<Banks> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataFull);
            } else {
                CharSequence filterPattern = constraint.toString().toLowerCase().trim();
                for (Banks item : dataFull) {
                    if (item.getBankName().toLowerCase().contains(filterPattern)) {
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



    public class BankVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView gogo;
        public TextView imageicon;
        public TextView nameicon;
        OnclickBank onclickBank;
        public BankVH(@NonNull View itemView , OnclickBank onclickBank) {
            super(itemView);
            imageicon = itemView.findViewById(R.id.imageicon);
            nameicon = itemView.findViewById(R.id.nameicon);
            gogo = itemView.findViewById(R.id.gogo);
            this.onclickBank = onclickBank;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onclickBank.onListBank(getAdapterPosition());
        }
    }
}