package com.yusufalicezik.drvirtual.HastaIslemleri;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yusufalicezik.drvirtual.AcilisActivity;
import com.yusufalicezik.drvirtual.Model.Doktor;
import com.yusufalicezik.drvirtual.Model.GenelKullanici;
import com.yusufalicezik.drvirtual.R;
import com.yusufalicezik.drvirtual.Utils.GenelMesajlarListeAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.yusufalicezik.drvirtual.AcilisActivity.girisYapan;

public class OnMuayeneMesajActivity extends AppCompatActivity {
    private ArrayList<GenelKullanici>mesajlasilanGenelKullaniciler=new ArrayList<>();

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;

    private RecyclerView liste;
    private LinearLayoutManager linearLayoutManager;
    private GenelMesajlarListeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_muayene_mesaj);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();

        Log.e("girisYapanDeneme", String.valueOf(girisYapan));
        kimlerleMesajlastigimiGetir(girisYapan); //giriş yapan 1 ise şuan current user hasta, 2 ise doktor. giriş yapana göre arama yapacağım o yüzden gerkeli.

    }

    public void kimlerleMesajlastigimiGetir(int girisYapan){
        if(girisYapan==1){ //hastaya göre arama yap.

            String currentid=mAuth.getCurrentUser().getUid().toString();

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Hastalar");
            query.whereEqualTo("uid", currentid); //boş gelme ihtimali yok. kayıt olduğumda zaten kullanıcı bu listeye düşer.
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    if (e == null) {


                        if(scoreList.size()>0) {
                            String currentHastaTC=scoreList.get(0).getString("tc"); //current userım hasta olduğu için(girisYapan==1 den anladık) current uid sine göre o anki kullanıcının tc sini bulduk.
                            //bulduğumuz tc ye göre randevu_genel_mesajlar_liste tablosunda arama yapacağız kimlerle mesajlasmıs die
                            mesajlasilanDoktorlarinListesiniGetir(currentHastaTC);
                        }else {
                            Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayene sayfası hata1", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayane sayfası hata1", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else if(girisYapan==2){ //doktora göre arama yap.
            String currentid=mAuth.getCurrentUser().getUid().toString();

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Doktorlar");
            query.whereEqualTo("uid", currentid); //boş gelme ihtimali yok. kayıt olduğumda zaten kullanıcı bu listeye düşer.
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> scoreList, ParseException e) {
                    if (e == null) {


                        if(scoreList.size()>0) {
                            String currentDoktorTC=scoreList.get(0).getString("doktor_tc");
                            mesajlasilanHastalarinListesiniGetir(currentDoktorTC);
                        }else {
                            Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayene sayfası hata1", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayane sayfası hata1", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(this, "Hata, giriş yapan bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }

    private void mesajlasilanDoktorlarinListesiniGetir(String currentHastaTC) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("randevu_genel_mesajlar_liste");
        query.whereEqualTo("hastaTC", currentHastaTC); //gelen hasta tc ye göre arama yaptık.
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    if(scoreList.size()>0) {
                        for(ParseObject object:scoreList){
                            //current hasta tc me göre hastam hangi doktorlarla konuşmuş ise onların hepsinin tc sini sırayla listeye atarım.

                            GenelKullanici genelKullanici=new GenelKullanici();
                            genelKullanici.setTc(object.getString("doktorTC")); //doktor tc lerimi ekledim.
                            genelKullanici.setRandevuSaat(object.getString("randevuSaat"));
                            mesajlasilanGenelKullaniciler.add(genelKullanici);
                            genelKullanicilariBul();
                        }

                    }else {
                        Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayene sayfası hata2", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayane sayfası hata2", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mesajlasilanHastalarinListesiniGetir(String currentDoktorTC) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("randevu_genel_mesajlar_liste");
        query.whereEqualTo("doktorTC", currentDoktorTC); //gelen hasta tc ye göre arama yaptık.
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    if(scoreList.size()>0) {
                        for(ParseObject object:scoreList){

                            GenelKullanici genelKullanici=new GenelKullanici();
                            genelKullanici.setTc(object.getString("hastaTC")); //doktor tc lerimi ekledim.
                            genelKullanici.setRandevuSaat(object.getString("randevuSaat"));
                            mesajlasilanGenelKullaniciler.add(genelKullanici);
                            genelKullanicilariBul();
                        }

                    }else {
                        Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayene sayfası hata2", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayane sayfası hata2", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void genelKullanicilariBul(){
        if(girisYapan==1){ //hasta ise doktor listesi kullanılacak. bu hastanın mesajlaşmış olduğu doktorlar listesi
            //burdan elimizdeki doktor tc sine göre doktorun tum bilgilerini çekeceğiz.
            //genel kullanıcı listesindeki tc lere göre arama yapıp listeyi set edeceğiz.
            for(int i=0;i<mesajlasilanGenelKullaniciler.size();i++){


                ParseQuery<ParseObject> query = ParseQuery.getQuery("Doktorlar");
                query.whereEqualTo("doktor_tc", mesajlasilanGenelKullaniciler.get(i).getTc());
                final int finalI = i;
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> scoreList, ParseException e) {
                        if (e == null) {
                            if(scoreList.size()>0) {
                                for(ParseObject object:scoreList){

                                    mesajlasilanGenelKullaniciler.get(finalI).setIsimsoyisim(
                                            object.getString("doktor_adi_soyadi") //bu şekilde mesajlasılan tüm doktorların
                                            // adı soyadı tc si ve randevu saatleri alınmış oldu. artık tanımlamalarıızı yapalım.
                                    );

                                }
                                //for bittikten sonra tanımlamalarımızı yapalım.
                                gerekliTanimlamalariYap(mesajlasilanGenelKullaniciler);

                            }else {
                                Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayene sayfası hata3", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayane sayfası hata3", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }else if(girisYapan==2){
            //yukardaki parsequery de doktorlar tablosu yerine hastalar tablosuna bak.
            for(int i=0;i<mesajlasilanGenelKullaniciler.size();i++){


                ParseQuery<ParseObject> query = ParseQuery.getQuery("Hastalar");
                query.whereEqualTo("tc", mesajlasilanGenelKullaniciler.get(i).getTc());
                final int finalI = i;
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> scoreList, ParseException e) {
                        if (e == null) {
                            if(scoreList.size()>0) {
                                for(ParseObject object:scoreList){

                                    mesajlasilanGenelKullaniciler.get(finalI).setIsimsoyisim(
                                            object.getString("isim") + " " +object.getString("soyisim") //bu şekilde mesajlasılan tüm doktorların
                                            // adı soyadı tc si ve randevu saatleri alınmış oldu. artık tanımlamalarıızı yapalım.
                                    );

                                }
                                //for bittikten sonra tanımlamalarımızı yapalım.
                                gerekliTanimlamalariYap(mesajlasilanGenelKullaniciler);

                            }else {
                                Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayene sayfası hata3", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(OnMuayeneMesajActivity.this, "Onmuayane sayfası hata3", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }

    private void gerekliTanimlamalariYap(ArrayList<GenelKullanici>mesajlasilanGenelKullanicilar){
        adapter=new GenelMesajlarListeAdapter(mesajlasilanGenelKullanicilar,OnMuayeneMesajActivity.this);
        liste=findViewById(R.id.recyclerView_allmessagesListe);
        linearLayoutManager=new LinearLayoutManager(OnMuayeneMesajActivity.this);
        linearLayoutManager.setStackFromEnd(false);
        liste.setHasFixedSize(true);
        liste.setLayoutManager(linearLayoutManager);
        liste.setAdapter(adapter);
    }

    public void cikisYapButonTik(View view){
        mAuth.signOut();
        Intent intent=new Intent(OnMuayeneMesajActivity.this, AcilisActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        if (girisYapan != 1) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }else{
            super.onBackPressed();
        }

    }
}
