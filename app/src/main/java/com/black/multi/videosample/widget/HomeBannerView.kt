package com.black.multi.videosample.widget

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import cn.bingoogolapple.bgabanner.BGABanner
import com.black.multi.videosample.R
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.databinding.HomeBannerBinding
import com.black.multi.videosample.model.Banner
import com.black.multi.videosample.utils.AppConfig
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url
import com.black.multi.videosample.viewmodel.HomeVm
import com.black.xcommon.imageloader.ImageLoader
import com.black.xcommon.imageloader.glide.ImageConfigImpl
import com.black.xcommon.utils.EzLog
import java.util.*

/**
 * Created by wei.
 * Date: 2020/5/26 8:46
 * Desc:
 */
class HomeBannerView : CardView, BGABanner.Adapter<ImageView, String>,BGABanner.Delegate<ImageView,String>{

    private lateinit var mBind: HomeBannerBinding
    private lateinit var bannerList: List<Banner>

    constructor(context: Context):super(context)
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)
    constructor(context: Context,attributeSet: AttributeSet,defStyle:Int):super(context,attributeSet,defStyle)

    fun initView(owner: LifecycleOwner){
        mBind = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.home_banner,this,true)

        HomeVm.instance.getBanner().observe(owner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    showBanner(it)
                }
                Status.ERROR -> {
                    EzLog.d("HomeBannerView--->"+it.msg)
                }
            }
        })
    }

    private fun showBanner(it:Resource<List<Banner>>){
        bannerList = it.data!!
        val mList: MutableList<String> = ArrayList()
        for (banner in bannerList) {
            mList.add(banner.imagePath)
        }

        mBind.bannerView.setAutoPlayAble(mList.size > 1)
        mBind.bannerView.setAdapter(this)
        mBind.bannerView.setData(mList, null)
        mBind.bannerView.setDelegate(this)
    }



    fun startAnimation(){
        mBind.bannerView.startAutoPlay()
    }

    fun stopAnimation(){
        mBind.bannerView.stopAutoPlay()
    }

    override fun fillBannerItem(banner: BGABanner, itemView: ImageView?, model: String?, position: Int) {
        ImageLoader.getInstance().loadImage<ImageConfigImpl>(banner.context,
                ImageConfigImpl.builder().url(model)
                        .cacheStrategy(1).isCenterCrop(true)
                        .isCircle(false).imageView(itemView).build())
    }

    override fun onBannerItemClick(
        banner: BGABanner?,
        itemView: ImageView?,
        model: String?,
        position: Int
    ) {
        val destination = AppConfig.getDestConfig()!![HOME_DETAIL_PAGE]
        val bundle = Bundle()
        bundle.putString(HomeDetailFragment_Url,bannerList[position].url)
        bundle.putString(HomeDetailFragment_Title,bannerList[position].title)
        Navigation.findNavController(context as FragmentActivity,R.id.bannerView).navigate(destination!!.id,bundle)
    }

}