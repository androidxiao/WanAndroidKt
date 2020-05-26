package com.black.multi.videosample.base.baseadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.LifecycleOwner;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:03
 * Description:
 */
public abstract class SimpleBaseAdapter<V extends XViewHolder, Data> extends BaseAdapter<V, Data> {

    public SimpleBaseAdapter(LifecycleOwner owner, IRecycleViewCallback<Data> callback) {
        super(owner, callback);
    }

    public SimpleBaseAdapter(IRecycleViewCallback<Data> callback) {
        super(callback);
    }

    private Type getDataType(Type typeOfT) {
        ParameterizedType theType = (ParameterizedType)typeOfT;
        Type[] actualTypeArguments = theType.getActualTypeArguments();
        return actualTypeArguments != null && actualTypeArguments.length != 0 ? actualTypeArguments[0] : null;
    }

    public V onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        try {
            Type typeOfT = this.getClass().getGenericSuperclass();
            ParameterizedType theType = (ParameterizedType)typeOfT;
            Type[] actualTypeArguments = theType.getActualTypeArguments();
            Class viewholderclazz = (Class)actualTypeArguments[0];
            Type viewHolderOfT = viewholderclazz.getGenericSuperclass();
            ParameterizedType theTypeviewHolderOfT = (ParameterizedType)viewHolderOfT;
            Type[] actualTypeArgumentsV = theTypeviewHolderOfT.getActualTypeArguments();
            Class dataclazz = (Class)actualTypeArgumentsV[0];
            return (V) XViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, owner, this, viewholderclazz, dataclazz, callback);
        } catch (Exception var11) {
            return null;
        }
    }

    public void onBindViewHolder(V holder, int position) {
        holder.onBind(this.getDatas().get(position));
    }
}
