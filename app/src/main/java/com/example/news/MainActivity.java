package com.example.news;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FrameLayout konten;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        konten = findViewById(R.id.konten);
        bottomNavigationView = findViewById(R.id.menu_navigasi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_berita) {
                    buka_fragment(new fragmentBerita());
                    return true;
                }
                if (item.getItemId() == R.id.item_MataUang){
                    buka_fragment(new fragmentCurrency());
                    return true;
                }
                if (item.getItemId() == R.id.item_about){
                    buka_fragment(new AboutUs());
                    return true;
                }
                return false;
            }
        });
      buka_fragment(new fragmentBerita());
    }
    private Boolean buka_fragment (Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.konten, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}