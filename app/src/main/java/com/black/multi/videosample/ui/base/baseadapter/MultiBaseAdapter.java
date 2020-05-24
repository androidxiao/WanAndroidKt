package com.black.multi.videosample.ui.base.baseadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:03
 * Description:
 */
public abstract class MultiBaseAdapter<Data> extends BaseAdapter<XViewHolder, Data> {
    HashMap<Integer, Class<? extends XViewHolder>> viewholders = new HashMap();
    HashMap<Integer, Class<? extends ViewDataBinding>> viewholdersdatabindings = new HashMap();

    public MultiBaseAdapter(LifecycleOwner owner, IRecycleViewCallback<Data> callback) {
        super(owner, callback);
        this.initViewHolderClazz();
    }

    public MultiBaseAdapter(IRecycleViewCallback<Data> callback) {
        super(callback);
        this.initViewHolderClazz();
    }

    protected void addViewHolders(int viewtype, Class<? extends XViewHolder> viewholderclazz, Class<? extends ViewDataBinding> dbdclazz) {
        this.viewholders.put(viewtype, viewholderclazz);
        this.viewholdersdatabindings.put(viewtype, dbdclazz);
    }

    protected abstract void initViewHolderClazz();

    public XViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Class<? extends XViewHolder> clazz = (Class)this.viewholders.get(viewType);
        Class<? extends ViewDataBinding> dbclazz = (Class)this.viewholdersdatabindings.get(viewType);
        return clazz == null ? null : XViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, this.owner, this, clazz, dbclazz, this.callback);
    }

    public void onBindViewHolder(XViewHolder holder, int position) {
        Iterator var3 = this.viewholders.values().iterator();

        while(var3.hasNext()) {
            Class<? extends XViewHolder> clazz = (Class)var3.next();
            if (holder.getClass() == clazz) {
                holder.onBind(this.getDatas().get(position));
            }
        }

    }
}
