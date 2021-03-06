package com.black.multi.videosample.base.baseadapter;

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
        this(null, callback);
    }

    public void setData(List<Data> data) {
        this.datas = data;
        notifyDataSetChanged();
    }

    public void addData(List<Data> data) {
        datas.addAll(data);
        notifyDataSetChanged();
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void remove(Data data) {
        if (this.datas != null) {
            int index = this.datas.indexOf(data);
            this.datas.remove(data);
            this.notifyItemRemoved(index);
        }
    }

    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
}

