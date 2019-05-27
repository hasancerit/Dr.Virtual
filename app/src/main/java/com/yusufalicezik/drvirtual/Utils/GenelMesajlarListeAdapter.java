package com.yusufalicezik.drvirtual.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yusufalicezik.drvirtual.HastaIslemleri.MessagesPersonActivity;
import com.yusufalicezik.drvirtual.Model.Doktor;
import com.yusufalicezik.drvirtual.Model.GenelKullanici;
import com.yusufalicezik.drvirtual.R;

import java.util.List;

public class GenelMesajlarListeAdapter extends RecyclerView.Adapter<GenelMesajlarListeAdapter.GenelMesajlarListeViewHolder> {


    private List<GenelKullanici> genelMesajlasilanKullanicilar;
    Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;


    public GenelMesajlarListeAdapter(List<GenelKullanici> genelMesajlasilanKullanicilar,Context context)
    {
        this.genelMesajlasilanKullanicilar=genelMesajlasilanKullanicilar;
        this.mContext=context;
    }



    @NonNull
    @Override
    public GenelMesajlarListeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_genel_mesajlar_liste,parent,false);
        mAuth=FirebaseAuth.getInstance();
        return new GenelMesajlarListeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenelMesajlarListeViewHolder holder, final int position) {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
       holder.genelKullaniciIsimSoyisim.setText(genelMesajlasilanKullanicilar.get(position).getIsimsoyisim());
       holder.genelKullaniciRandevuSaat.setText(genelMesajlasilanKullanicilar.get(position).getRandevuSaat());


        holder.genelKullaniciIsimSoyisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MessagesPersonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("isim",genelMesajlasilanKullanicilar.get(position).getIsimsoyisim());
                intent.putExtra("tc",genelMesajlasilanKullanicilar.get(position).getTc());
                //diğer aktiviteden tc ye göre uid çekeceğim ve verileri kaydederken ona göre kaydedeceğim.(mesajları)//linear unutma

                mContext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return genelMesajlasilanKullanicilar.size();
    }



    public class GenelMesajlarListeViewHolder extends RecyclerView.ViewHolder {

        public TextView genelKullaniciIsimSoyisim;
        public TextView genelKullaniciRandevuSaat;
        //  public ConstraintLayout genelLayout;

        public GenelMesajlarListeViewHolder(View itemView){
            super(itemView);
            genelKullaniciIsimSoyisim=itemView.findViewById(R.id.customGenelMesajlarDoktorAdiLabel);
            genelKullaniciRandevuSaat=itemView.findViewById(R.id.customGenelMesajlarRandevuSaatiLabel);
            // genelLayout=itemView.findViewById(R.id.genelListeLayout);
        }
    }


}
