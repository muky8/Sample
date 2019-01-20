package com.example.mukhtaradepoju.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>  {
    private LayoutInflater inflater;


    private List<AllTasksQuery.AllTask> dataarray;

    public Adapter(Context ctx,List<AllTasksQuery.AllTask>dataarray) {
      this.dataarray=dataarray;
        inflater = LayoutInflater.from(ctx);

    }
    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_view, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder myViewHolder, int i) {

        final AllTasksQuery.AllTask tasks = dataarray.get(i);

        myViewHolder.txtview.setText(tasks.name);
    }

    @Override
    public int getItemCount() {
        return (null!=dataarray?dataarray.size():0);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtview;
        public final View mView;


        public MyViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

           txtview = (TextView) itemView.findViewById(R.id.todotxt);

        }
    }
}
