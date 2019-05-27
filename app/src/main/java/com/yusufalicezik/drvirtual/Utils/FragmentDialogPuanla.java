package com.yusufalicezik.drvirtual.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.yusufalicezik.drvirtual.HastaIslemleri.HastaAnaMenuActivity;
import com.yusufalicezik.drvirtual.HastaIslemleri.OnMuayeneMesajActivity;
import com.yusufalicezik.drvirtual.HastaIslemleri.RandevuAlActivity;
import com.yusufalicezik.drvirtual.Model.Doktor;
import com.yusufalicezik.drvirtual.R;

import java.util.List;


@SuppressLint("ValidFragment")
public class FragmentDialogPuanla extends DialogFragment {

    private double puanOrtalamasi=-1;
    private double puanlayanSayisi=-1;
    private Context mContext;

    Doktor guncellenecekDoktor;

        @SuppressLint("ValidFragment")
        public FragmentDialogPuanla(Doktor guncellenecekDoktor,Context mContext)
        {

            this.guncellenecekDoktor=guncellenecekDoktor;
            this.mContext=mContext;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.custom_popup,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            verileriCek();
            Button puanlaButon=view.findViewById(R.id.puanlaButon);
            final Spinner puanlaSpinner = view.findViewById(R.id.puanSpinner);
            TextView doktorAdiTxt=view.findViewById(R.id.doktorAdiTxt);

            doktorAdiTxt.setText(guncellenecekDoktor.getDoktorAdi());

            puanlaButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(puanlayanSayisi!=-1 && puanOrtalamasi!=-1) {
                        double secilenPuan = Double.valueOf(puanlaSpinner.getSelectedItem().toString());
                        double toplamPuan = puanOrtalamasi * puanlayanSayisi;
                        toplamPuan += Double.valueOf(secilenPuan);
                        puanlayanSayisi++;
                        double yeniPuan = toplamPuan / Double.valueOf(puanlayanSayisi);
                        verileriYaz(yeniPuan,puanlayanSayisi);
                    }else{
                        Toast.makeText(mContext, "İnternet bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    private void verileriYaz(final double yeniPuan, final double puanlayanSayisi) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doktorlar");
        query.whereEqualTo("doktor_tc", guncellenecekDoktor.getDoktorTc());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject gameScore, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    gameScore.put("doktor_puan_s", String.valueOf(yeniPuan));
                    gameScore.put("doktor_puan_sayi_s", String.valueOf(puanlayanSayisi));
                    gameScore.saveInBackground();
                    Toast.makeText(mContext, "Puanlama Başarılı, yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void verileriCek() {
            //gelen tc ye göre burdan verileri çekeceğim
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Doktorlar");
        query.whereEqualTo("doktor_tc", guncellenecekDoktor.getDoktorTc());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    if(scoreList.size()>0) {
                        for(ParseObject object:scoreList){


                                   puanOrtalamasi = Double.valueOf(object.getString("doktor_puan_s"));
                                   puanlayanSayisi = Double.valueOf(object.getString("doktor_puan_sayi_s"));


                        }
                        Toast.makeText(mContext, puanlayanSayisi+" "+puanOrtalamasi, Toast.LENGTH_SHORT).show();


                    }
                }else {
                    Toast.makeText(mContext, "Onmuayane sayfası hata3", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

