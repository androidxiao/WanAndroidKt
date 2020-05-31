package com.black.multi.videosample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.ItemNavTitleBinding

/**
 * Created by wei.
 * Date: 2020/5/26 10:30
 * Desc:
 */
class NavTitleAdapter :
    RecyclerView.Adapter<NavTitleAdapter.NavTitleHolder>() {

    private var mDatas: List<String>?=null
    private var selectPosition = 0

    open fun setDatas(data: List<String>) {
        mDatas = data;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavTitleHolder {
        val binding = DataBindingUtil.inflate<ItemNavTitleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_nav_title,
            parent,
            false
        )
        return NavTitleHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (mDatas == null) {
            0
        } else mDatas!!.size
    }

    override fun onBindViewHolder(holder: NavTitleHolder, position: Int) {
        holder.mBind.bean = mDatas!![position]
        holder.mBind.root.setOnClickListener {
            onItemClickListener(position)
        }
        setTitleBg(position, holder)
    }

    override fun onBindViewHolder(
        holder: NavTitleHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder,position)
        }else{
            setTitleBg(position, holder)
        }
    }

    private fun setTitleBg(
        position: Int,
        holder: NavTitleHolder
    ) {
        if (position == selectPosition) {
            holder.mBind.tvTitle.isSelected= true
            holder.mBind.root.setBackgroundColor(
                ContextCompat.getColor(
                    holder.mBind.root.context,
                    R.color.colorPrimary
                )
            )
        } else {
            holder.mBind.tvTitle.isSelected= false
            holder.mBind.root.setBackgroundColor(
                ContextCompat.getColor(
                    holder.mBind.root.context,
                    R.color.c_transparent
                )
            )
        }
    }

    class NavTitleHolder(binding: ItemNavTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        val mBind = binding
    }

    open fun setChoose(position: Int) {
        selectPosition = position
//        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    lateinit var onItemClickListener: (Int) -> Unit
}