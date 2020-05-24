package com.black.multi.videosample.ui.base.baseadapter;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:03
 * Description:
 */
public abstract class BaseAdapter<Type extends HViewHolder, Data> extends RecyclerView.Adapter<Type> {
    protected IRecycleViewCallback<Data> callback;
    protected LifecycleOwner owner;
    private List<Data> datas;

    public BaseAdapter(LifecycleOwner owner, IRecycleViewCallback<Data> callback) {
        this.callback = callback;
        this.owner = owner;
        this.datas = new ArrayList();
    }

    public BaseAdapter(IRecycleViewCallback<Data> callback) {
        this((LifecycleOwner)null, callback);
    }

    public void setData(List<Data> data) {
        this.datas = data;
        this.notifyDataSetChanged();
    }

    public List<Data> getDatas() {
        return this.datas;
    }

    public void remove(Data data) {
        if (this.datas != null) {
            int indx = this.datas.indexOf(data);
            this.datas.remove(data);
            this.notifyItemRemoved(indx);
        }
    }

    public int getItemCount() {
        return this.datas == null ? 0 : this.datas.size();
    }
}

