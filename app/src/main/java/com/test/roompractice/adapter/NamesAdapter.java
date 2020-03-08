package com.test.roompractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.test.roompractice.R;
import com.test.roompractice.model.TestModel;

import java.util.ArrayList;
import java.util.List;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.ViewHolder> {

    Context context;
    List<TestModel> entries;
    onCardActionsClick cardActionListener;
    public interface onCardActionsClick{
        void deleteRow(int position);
        void editRow(int position);
    }

    public void onCardActionsListener(onCardActionsClick onActions){
        this.cardActionListener=onActions;
    }
    public NamesAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.name_card,parent,false);
        return new ViewHolder(v,cardActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestModel model=entries.get(position);
        holder.getName.setText(model.getName());
        holder.getPhone.setText(model.getPhone());
        holder.getCity.setText(model.getCity());
        holder.getCompany.setText(model.getCompany());
    }

    public void setWords(List<TestModel> myList){
        entries=myList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(entries!=null)
            return  entries.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView getName,getPhone,getCity,getCompany;
        ImageView deleteEntry,editEntry;
        public ViewHolder(@NonNull View itemView,final  onCardActionsClick listener) {
            super(itemView);
            getName=itemView.findViewById(R.id.name);
            getPhone=itemView.findViewById(R.id.phone);
            getCity=itemView.findViewById(R.id.city);
            getCompany=itemView.findViewById(R.id.company);
            deleteEntry=itemView.findViewById(R.id.delete);
            editEntry=itemView.findViewById(R.id.edit);
            deleteEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        if(entries.size()>0){
                            listener.deleteRow(getAdapterPosition());
                        }
                    }
                }
            });
            editEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        if(entries.size()>0){
                            listener.editRow(getAdapterPosition());
                        }
                    }
                }
            });

        }
    }
}
