package com.yxr.language.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxr.language.LanguageManager;
import com.yxr.language.R;
import com.yxr.language.entity.CustomLanguage;
import com.yxr.language.entity.LanguageConfig;
import com.yxr.language.entity.LanguageLocale;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageSelectorDialog extends Dialog implements View.OnClickListener {
    private final List<CustomLanguage> customLanguageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<LanguageViewHolder> adapter;
    private int selectedPosition = 0;

    public LanguageSelectorDialog(@NonNull Context context) {
        super(context, R.style.LanguageCommentDialogStyle);
        setContentView(R.layout.language_dialog_language_selector);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getWindow() != null) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    private void initListener() {
        findViewById(R.id.ivClose).setOnClickListener(this);
        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.tvConfirm).setOnClickListener(this);
    }

    private void initData() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        int bottomMargin = (int) (getContext().getResources().getDisplayMetrics().density * 96);

        LanguageLocale currLanguage = LanguageManager.getInstance().getCurrLanguage();
        LanguageConfig languageConfig = LanguageManager.getInstance().getLanguageConfig();

        customLanguageList.clear();
        customLanguageList.addAll(LanguageManager.getInstance().getLanguageList(getContext()));

        if (currLanguage.isFollowSystem() && languageConfig != null && languageConfig.isNeedDefaultSystem()) {
            selectedPosition = 0;
        } else {
            Locale currLocale = currLanguage.getLocale();
            for (int i = 1; i < customLanguageList.size(); i++) {
                Locale locale = customLanguageList.get(i).getLocale();
                if (!TextUtils.isEmpty(locale.getCountry()) && !TextUtils.isEmpty(currLocale.getCountry())) {
                    // 如果两个地区都不为空，则用地区判断
                    if (TextUtils.equals(locale.getCountry(), currLocale.getCountry())) {
                        selectedPosition = i;
                        break;
                    }
                } else if (TextUtils.equals(locale.getLanguage(), currLocale.getLanguage())) {
                    // 否则用国家判断
                    selectedPosition = i;
                    break;
                }
            }
        }

        adapter = new RecyclerView.Adapter<LanguageViewHolder>() {

            @NonNull
            @Override
            public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
                return new LanguageViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.language_item_language_selector, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull LanguageViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
                viewHolder.itemView.setSelected(selectedPosition == position);
                viewHolder.ivIcon.setSelected(selectedPosition == position);
                viewHolder.tvTitle.setText(customLanguageList.get(position).getTitle());

                ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
                if (params instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) params).bottomMargin = position == getItemCount() - 1 ? bottomMargin : 0;
                    viewHolder.itemView.setLayoutParams(params);
                }

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return customLanguageList.size();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (R.id.tvConfirm == v.getId()) {
            dismiss();
            CustomLanguage customLanguage = getSelectedData();
            if (customLanguage != null) {
                LanguageManager.getInstance().updateCurrLanguage(customLanguage.isSystem(), customLanguage.getLocale());
            }
        } else if (R.id.tvCancel == v.getId() || R.id.ivClose == v.getId()) {
            dismiss();
        }
    }

    private CustomLanguage getSelectedData() {
        if (selectedPosition >= 0 && selectedPosition < adapter.getItemCount()) {
            return customLanguageList.get(selectedPosition);
        }
        return null;
    }


    public static class LanguageViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final ImageView ivIcon;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }
}
