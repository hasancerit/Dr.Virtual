package com.yusufalicezik.drvirtual.HastaIslemleri;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yusufalicezik.drvirtual.Model.Doktor;
import com.yusufalicezik.drvirtual.Model.GenelKullanici;
import com.yusufalicezik.drvirtual.Model.Mesajlar;
import com.yusufalicezik.drvirtual.R;
import com.yusufalicezik.drvirtual.Utils.FragmentDialogPuanla;
import com.yusufalicezik.drvirtual.Utils.MessagesAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yusufalicezik.drvirtual.AcilisActivity.girisYapan;

public class MessagesPersonActivity extends AppCompatActivity {

    private TextView isimSoyisim;
    private EditText mesajText;

    private String mesajGonderilenTC; //bu sonradan uid olacak. bu tc ye göre id yi çekeceğiz.
    private String mesajGonderilenIsimSoyisim;
    private String mesajGonderilenID;



    ///
    private FirebaseDatabase database;
    private DatabaseReference mRefMesajGonderen,mRefMesajAlan,mRef;
    private FirebaseAuth mAuth;
    private RecyclerView liste;

    ///

    String currentIsim,currentuid;

    ////
    private final List<Mesajlar> messagesList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_person);

        database=FirebaseDatabase.getInstance();
        mRefMesajGonderen=database.getReference().child("Messages");
        mRefMesajAlan=database.getReference().child("Messages");
        mRef=database.getReference();
        mAuth=FirebaseAuth.getInstance();

        isimSoyisim=findViewById(R.id.personMessagesIsimSoyisimtxt);
        mesajText=findViewById(R.id.personMessagesMesajGonderEdittext);

        mesajGonderilenTC=getIntent().getStringExtra("tc"); //bu tc ye göre uid çekilecek
        mesajGonderilenIsimSoyisim=getIntent().getStringExtra("isim");
        isimSoyisim.setText(mesajGonderilenIsimSoyisim);


        tcyeGoreUidBul();





    }

    public void tcyeGoreUidBul(){
        if(!mesajGonderilenTC.isEmpty() && !mesajGonderilenTC.equals("") || mesajGonderilenTC.equals(" ")){
            if(girisYapan==1) { //current kullanıcı hastadır. doktor tablosuna bak---------------

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Doktorlar");
                query.whereEqualTo("doktor_tc", mesajGonderilenTC);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> scoreList, ParseException e) {
                        if (e == null) {
                            if(scoreList.size()>0) {
                                for(ParseObject object:scoreList){

                                    mesajGonderilenID=(object.getString("uid"));
                                    initBaslat();
                                }

                            }else {
                                Toast.makeText(MessagesPersonActivity.this, "mesaj sayfası hata2", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MessagesPersonActivity.this, "mesaj sayfası hata2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }else{//current kullanıcı doktordur. hasta tablosuna bak------------------------
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Hastalar");
                query.whereEqualTo("tc", mesajGonderilenTC);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> scoreList, ParseException e) {
                        if (e == null) {
                            if(scoreList.size()>0) {
                                for(ParseObject object:scoreList){

                                    mesajGonderilenID=(object.getString("uid"));
                                    initBaslat();
                                }

                            }else {
                                Toast.makeText(MessagesPersonActivity.this, "mesaj sayfası hata2.2", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MessagesPersonActivity.this, "mesaj sayfası hata2.2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    public void initBaslat(){
        adapter=new MessagesAdapter(messagesList);
        liste=findViewById(R.id.personMessagesListe);
        linearLayoutManager=new LinearLayoutManager(MessagesPersonActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        liste.setHasFixedSize(true);
        liste.setLayoutManager(linearLayoutManager);
        liste.setAdapter(adapter);


        Log.e("mesajlasilanBilgiler","mesajlasilan isim : "+mesajGonderilenIsimSoyisim);
        Log.e("mesajlasilanBilgiler","mesajlasilan tc : "+mesajGonderilenTC);
        Log.e("mesajlasilanBilgiler","mesajlasilan uid : "+mesajGonderilenID);
        Log.e("mesajlasilanBilgiler","gonderen uid : "+mAuth.getCurrentUser().getUid().toString());
        fetchMessage();
    }

    private void fetchMessage() {

        mRefMesajAlan.child(mAuth.getCurrentUser().getUid()).child(mesajGonderilenID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.exists())
                {
                    Mesajlar messages=dataSnapshot.getValue(Mesajlar.class);

                    messagesList.add(messages);
                    adapter.notifyDataSetChanged();
                    liste.smoothScrollToPosition(liste.getAdapter().getItemCount());

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void mesajGonderTic(View view)
    {

        String mesajIcerik=mesajText.getText().toString();

        if(!mesajIcerik.equals("") || !mesajIcerik.isEmpty())
        {
            String mesajID=mRefMesajGonderen.push().getKey();

/*
        mRefMesajGonderen.child(mAuth.getCurrentUser().getUid()).child(mesajGonderilenID).child(mesajID).child("tarih").setValue(ServerValue.TIMESTAMP);
        mRefMesajGonderen.child(mAuth.getCurrentUser().getUid()).child(mesajGonderilenID).child(mesajID).child("icerik").setValue(mesajIcerik);
        mRefMesajGonderen.child(mAuth.getCurrentUser().getUid()).child(mesajGonderilenID).child(mesajID).child("from").setValue(mAuth.getCurrentUser().getUid());
        mRefMesajAlan.child(mesajGonderilenID).child(mAuth.getCurrentUser().getUid()).child(mesajID).child("tarih").setValue(ServerValue.TIMESTAMP);
        mRefMesajAlan.child(mesajGonderilenID).child(mAuth.getCurrentUser().getUid()).child(mesajID).child("icerik").setValue(mesajIcerik);
        mRefMesajAlan.child(mesajGonderilenID).child(mAuth.getCurrentUser().getUid()).child(mesajID).child("from").setValue(mAuth.getCurrentUser().getUid());
*/


            //  String message=mChatMessageView.getText().toString();



            DatabaseReference user_message_push = mRefMesajAlan.child(mAuth.getCurrentUser().getUid()).child(mesajGonderilenID).push();
            String push_id = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("icerik", mesajIcerik);


            messageMap.put("tarih", ServerValue.TIMESTAMP);
            messageMap.put("from", mAuth.getCurrentUser().getUid());





            String current_user_ref=mAuth.getCurrentUser().getUid()+ "/" +mesajGonderilenID;
            String chat_user_Ref=mesajGonderilenID+"/"+mAuth.getCurrentUser().getUid();


            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref+ "/" + push_id, messageMap);
            messageUserMap.put(chat_user_Ref + "/" + push_id, messageMap);

            mesajText.setText("");

            mRefMesajAlan.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {

                        //  Log.d("CHAT LOG", databaseError.getMessage().toString());
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

        }





    }
    public void puanlaIsimTik(View view) { //eğer mesajlasilan doktorsa diye kontrol yap, yani girisyapan==1 ise

        if(girisYapan==1){

            Doktor guncellenecekDoktor = new Doktor();
            guncellenecekDoktor.setDoktorTc(mesajGonderilenTC);
            guncellenecekDoktor.setDoktorAdi(mesajGonderilenIsimSoyisim);

            FragmentDialogPuanla fragmentDoktorPuanla=new FragmentDialogPuanla(guncellenecekDoktor,getApplicationContext());
            fragmentDoktorPuanla.show(getSupportFragmentManager(),"dialogGuncelle");
        }


    }

}

