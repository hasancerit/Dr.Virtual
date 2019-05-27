package com.yusufalicezik.drvirtual.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusufalicezik.drvirtual.R;

import java.util.ArrayList;

public class GecmisRandevuAdapter extends RecyclerView.Adapter<GecmisRandevuAdapter.MyHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<String> gecmisRandevuList;

    public GecmisRandevuAdapter(Context context, ArrayList<String> gecmisRandevuList){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.gecmisRandevuList = gecmisRandevuList;
    }

    @NonNull
    @Override
    public GecmisRandevuAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.gecmis_randevu_satir,viewGroup,false);
        MyHolder viewHolder = new MyHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GecmisRandevuAdapter.MyHolder myHolder, int i) {
        myHolder.setData(gecmisRandevuList.get(i));

    }

    @Override
    public int getItemCount() {
        return gecmisRandevuList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView doktorIsim,saat;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            doktorIsim = itemView.findViewById(R.id.doktorIsim);
            saat = itemView.findViewById(R.id.saat);
        }

        public void setData(String randevu){
            doktorIsim.setText(randevu);
        }
    }
}
