package com.black.multi.videosample.base.baseadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:04
 * Description:
 */
public abstract class XViewHolder<B extends ViewDataBinding, T> extends HViewHolder<B, T> {
    protected LifecycleOwner owner;

    public XViewHolder(LifecycleOwner owner, BaseAdapter adapter, B binding, IRecycleViewCallback<T> callback) {
        super(binding.getRoot(), adapter, binding);
        this.owner = owner;
        binding.getRoot().setOnClickListener(view -> callback.onModelClicked(getCurrentT(), binding.getRoot()));
        binding.getRoot().setOnLongClickListener((view) -> callback.onModelLongClicked(getCurrentT(), view));
    }

    public abstract void onBind(T bean);

    public static <B extends ViewDataBinding, T> XViewHolder create(LayoutInflater inflater, ViewGroup viewGroup, LifecycleOwner owner, BaseAdapter adapter, Class xViewHolderClazz, Class databindingClazz, IRecycleViewCallback<T> callback) {
        try {
            Method target = databindingClazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE);
            B binding = (B) target.invoke((Object) null, inflater, viewGroup, false);
            Constructor constructor = xViewHolderClazz.getConstructor(LifecycleOwner.class, BaseAdapter.class, databindingClazz, IRecycleViewCallback.class);
            return (XViewHolder) constructor.newInstance(owner, adapter, binding, callback);
        } catch (Exception var10) {
            throw new RuntimeException("反射有错误!");
        }
    }

    protected abstract T getCurrentT();
}
