package com.black.multi.videosample.base.baseadapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:04
 * Description:
 */
public abstract class HViewHolder<B extends ViewDataBinding, T> extends RecyclerView.ViewHolder {
    protected B binding;
    protected BaseAdapter adapter;

    public HViewHolder(@NonNull View itemView, BaseAdapter adapter, B binding) {
        super(itemView);
        this.adapter = null;
        this.adapter = adapter;
        this.binding = binding;
        this.setIsRecyclable(false);
    }

    public HViewHolder(@NonNull View itemView, B binding) {
        this(itemView, (BaseAdapter)null, binding);
    }

    public abstract void onBind(T var1);
}
