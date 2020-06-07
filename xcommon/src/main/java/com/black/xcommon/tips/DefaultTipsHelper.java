package com.black.xcommon.tips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.black.xcommon.R;


/**
 * Created by dingboyang on 2016/11/10.
 */
public class DefaultTipsHelper implements TipsHelper {

    private final ProgressBar mLoadingView;
    private final View mView;
    private final Context mContext;

    public DefaultTipsHelper(Context context, View view) {
        this.mView = view;
        this.mContext = context;
        mLoadingView = new ProgressBar(context);
        mLoadingView.setPadding(0, (int) DensityUtils.dip2px(context, 10),
                0, (int) DensityUtils.dip2px(context, 10));
        mLoadingView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) DensityUtils.dip2px(context, 40)));
    }

    @Override
    public void showEmpty() {
        hideLoading();
        TipsUtils.showTips(mView, TipsType.EMPTY);
    }

    @Override
    public void hideEmpty() {
        TipsUtils.hideTips(mView, TipsType.EMPTY);
    }

    @Override
    public void showLoading(boolean firstPage) {
        hideEmpty();
        hideError();
        if (firstPage) {
            TipsUtils.showTips(mView, TipsType.LOADING);
        }
    }

    @Override
    public void hideLoading() {
        hideEmpty();
        hideError();
        TipsUtils.hideTips(mView, TipsType.LOADING);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void showError(boolean firstPage, String errorMessage, View.OnClickListener onClickListener) {
        hideLoading();
        if (firstPage) {
            View tipsView = TipsUtils.showTips(mView, TipsType.LOADING_FAILED);
            AppCompatButton retryBtn = tipsView.findViewById(R.id.retry_btn);
            retryBtn.setOnClickListener(onClickListener);
            retryBtn.setSupportBackgroundTintList(mContext.getResources().getColorStateList(R.color.c_FF9C26));
            retryBtn.setSupportBackgroundTintMode(PorterDuff.Mode.SRC_IN);
            if (!TextUtils.isEmpty(errorMessage)) {
                ((TextView) tipsView.findViewById(R.id.description)).setText(errorMessage);
            }
        }
    }

    @Override
    public void hideError() {
        TipsUtils.hideTips(mView, TipsType.LOADING_FAILED);
    }
}
