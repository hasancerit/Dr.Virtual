package com.yusufalicezik.drvirtual.HastaIslemleri;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.yusufalicezik.drvirtual.R;
import com.yusufalicezik.drvirtual.Utils.GecmisRandevuAdapter;

import java.util.ArrayList;

public class GecmisRanevu extends AppCompatActivity {
    RecyclerView gecmisRandevuList;
    ArrayList<String> gecmisRandevuArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecmis_ranevu);

        gecmisRandevuArray = new ArrayList<>();
        gecmisRandevuArray.add("Hasan CERİT");
        gecmisRandevuArray.add("Yusuf Ali Cezik");
        gecmisRandevuArray.add("Mehmet Burak Hammusoğlu");
        gecmisRandevuArray.add("Rıdvan Kabak");

        gecmisRandevuList = findViewById(R.id.gecmisRandevuList);

        GecmisRandevuAdapter gecmisRandevuAdapter = new GecmisRandevuAdapter(this,gecmisRandevuArray);
        gecmisRandevuList.setAdapter(gecmisRandevuAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        gecmisRandevuList.setLayoutManager(linearLayoutManager);
    }
}
