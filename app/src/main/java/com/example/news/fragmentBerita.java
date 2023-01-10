package com.example.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.models.api.CustomAdaptor;
import com.example.news.models.api.NewsApiResponse;
import com.example.news.models.api.NewsHeadlines;
import com.example.news.models.api.OnFetchDataListener;
import com.example.news.models.api.RequestManager;
import com.example.news.models.api.SelectListener;
import com.example.news.models.api.WebActivity;

import java.util.List;

public class fragmentBerita extends Fragment implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdaptor adaptor;
    ProgressDialog dialog;
    Button btn_umum, btn_kesehatan, btn_sains, btn_hiburan, btn_olahraga, btn_teknologi, btn_bisnis;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_berita, container, false);
        if (view != null) {
            searchView = view.findViewById(R.id.search_view);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    dialog.setTitle("Memuat artikel berita dari : " + query);
                    dialog.show();
                    RequestManager manager = new RequestManager(view.getContext());
                    manager.getNewsHeadlines(listener, "general", query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Memuat artikel..");
            dialog.show();

            btn_umum = view.findViewById(R.id.btn_umum);
            btn_umum.setOnClickListener(this);
            btn_kesehatan = view.findViewById(R.id.btn_kesehatan);
            btn_kesehatan.setOnClickListener(this);
            btn_sains = view.findViewById(R.id.btn_Sains);
            btn_sains.setOnClickListener(this);
            btn_hiburan = view.findViewById(R.id.btn_hiburan);
            btn_hiburan.setOnClickListener(this);
            btn_olahraga = view.findViewById(R.id.btn_olahraga);
            btn_olahraga.setOnClickListener(this);
            btn_teknologi = view.findViewById(R.id.btn_teknologi);
            btn_teknologi.setOnClickListener(this);
            btn_bisnis = view.findViewById(R.id.btn_bisnis);
            btn_bisnis.setOnClickListener(this);

            RequestManager manager = new RequestManager(getActivity());
            manager.getNewsHeadlines(listener, "general", null);
        }
        return view;
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(getActivity(), "Tidak ada data yang ditemukan!!", Toast.LENGTH_SHORT).show();
            } else {
                showNews(list, getView());
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(getActivity(), "Terjadi Galat!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list, View view) {
        if (view == null){
            Toast.makeText(getActivity(),"Terjadi Galat!!",Toast.LENGTH_SHORT).show();
            return;
        }
        recyclerView = view.findViewById(R.id.recycle_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adaptor = new CustomAdaptor(getContext(), list, this);
        recyclerView.setAdapter(adaptor);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        Intent myIntent = new Intent(getActivity(), WebActivity.class);
        myIntent.putExtra("data", headlines);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();
        String kategori;
        switch (category) {
            case "Bisnis":
                kategori = "business";
                break;
            case "Hiburan":
                kategori = "entertainment";
                break;
            case "Umum":
                kategori = "general";
                break;
            case "Kesehatan":
                kategori = "health";
                break;
            case "Sains":
                kategori = "science";
                break;
            case "Olahraga":
                kategori = "sports";
                break;
            case "Teknologi":
                kategori = "technology";
                break;
            default :
                Toast.makeText(getActivity(),"Terjadi Galat!!",Toast.LENGTH_SHORT).show();
                return;
        }
        dialog.setTitle("Memuat artikel berita dari : " + category);
        dialog.show();
        RequestManager manager = new RequestManager(getActivity());
        manager.getNewsHeadlines(listener, kategori, null);
    }
}

