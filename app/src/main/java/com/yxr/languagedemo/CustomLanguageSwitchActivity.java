package com.yxr.languagedemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yxr.language.LanguageManager;
import com.yxr.language.entity.CustomLanguage;

import java.util.List;

public class CustomLanguageSwitchActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 获取配置的多语言列表
        List<CustomLanguage> languageList = LanguageManager.getInstance().getLanguageList(this, true);
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom, parent, false)) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                CustomLanguage customLanguage = languageList.get(position);
                TextView tvLanguage = holder.itemView.findViewById(R.id.tvLanguage);
                ImageView ivIcon = holder.itemView.findViewById(R.id.ivIcon);

                tvLanguage.setText(customLanguage.getTitle());
                tvLanguage.setTextColor(customLanguage.isSelected() ? 0xff000000 : 0xff999999);
                ivIcon.setSelected(customLanguage.isSelected());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        for (CustomLanguage language : languageList) {
                            language.setSelected(false);
                        }
                        customLanguage.setSelected(true);
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return languageList.size();
            }
        };
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomLanguage customLanguage = null;
                for (CustomLanguage language : languageList) {
                    if (language.isSelected()) {
                        customLanguage = language;
                        break;
                    }
                }

                if (customLanguage != null) {
                    // 切换当前语言
                    LanguageManager.getInstance().updateCurrLanguage(customLanguage.isSystem(), customLanguage.getLocale());
                }
            }
        });
    }
}
