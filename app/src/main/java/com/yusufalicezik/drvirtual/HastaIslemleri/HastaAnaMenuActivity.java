package com.yusufalicezik.drvirtual.HastaIslemleri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yusufalicezik.drvirtual.AcilisActivity;
import com.yusufalicezik.drvirtual.R;

import java.util.List;

public class HastaAnaMenuActivity extends AppCompatActivity {
    private CardView randevuGecmisi;
    private  TextView txtHastaAnaMenuHG;
    private FirebaseAuth mAuth;
    private String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasta_ana_menu);

        txtHastaAnaMenuHG=findViewById(R.id.txt_hastaAnaMenuHG);

        mAuth = FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid().toString();

        verileriGetir();
    }

    private void verileriGetir() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Hastalar");
        query.whereEqualTo("uid", currentUserID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                   txtHastaAnaMenuHG.setText("Hoşgeldiniz, "+scoreList.get(0).getString("isim") + " "+ scoreList.get(0).getString("soyisim"));
                } else {

                }
            }
        });

    }

    public void randevuAlMenuTik(View view){
        Intent intent=new Intent(HastaAnaMenuActivity.this,RandevuAlActivity.class);
        startActivity(intent);
    }

    public void onMuayeneMenuTik(View view){
        Intent intent=new Intent(HastaAnaMenuActivity.this,OnMuayeneMesajActivity.class);
        startActivity(intent);
        //Intent intent=new Intent(HastaAnaMenuActivity.this,OnMuayene.class);
        //startActivity(intent);

    }

    public void randevuGecmisMenuTik(View view){
        Intent intent=new Intent(HastaAnaMenuActivity.this,GecmisRanevu.class);
        startActivity(intent);
    }

    public void profilBilgileriGuncelleTik(View view){
        Intent intent=new Intent(HastaAnaMenuActivity.this,ProfilGuncelle.class);
        startActivity(intent);
    }

    public void cikisYapButonTik(View view){
        mAuth.signOut();
        Intent intent=new Intent(HastaAnaMenuActivity.this, AcilisActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
    }
}
