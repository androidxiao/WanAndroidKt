package com.black.xcommon.tips;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.black.xcommon.R;


/**
 * FileName: TipsUtils
 * description:
 * Author: 丁博洋
 * Date: 2016/8/13
 */
public class TipsUtils {

    public static View showLoadingFailedTips(View targetView, Throwable th,
                                             View.OnClickListener retryListener) {

        return showLoadingFailedTips(targetView, th.getMessage(), retryListener);
    }

    public static View showLoadingFailedTips(View targetView, CharSequence errorTips,
                                             View.OnClickListener retryListener) {
        View tipsView = TipsUtils.showTips(targetView, TipsType.LOADING_FAILED);
        View retryBtn = tipsView.findViewById(R.id.retry_btn);
        if (retryListener != null) {
            retryBtn.setVisibility(View.VISIBLE);
            retryBtn.setOnClickListener(retryListener);
        } else {
            retryBtn.setVisibility(View.GONE);
        }
        if (errorTips != null) {
            ((TextView) tipsView.findViewById(R.id.description)).setText(errorTips);
        }

        return tipsView;
    }

    /**
     * 显示视图
     * @return 返回提示视图
     */
    public static View showTips(View targetView, TipsType tipsType) {
        Tips tips = tipsType.createTips(targetView.getContext());
        return tips.applyTo(targetView, tipsType.ordinal());
    }

    /**
     * 隐藏视图
     */
    public static void hideTips(View targetView, TipsType... tipsTypes) {
        if (targetView == null || tipsTypes == null || tipsTypes.length == 0) {
            return;
        }
        for (TipsType tipsType : tipsTypes) {
            hideTips(targetView, tipsType.ordinal());
        }
    }

    private static void hideTips(View targetView, int tipsId) {
        ViewGroup tipsContainerView = (ViewGroup) targetView.getParent();
        if (!(tipsContainerView instanceof TipsContainer)) {
            return;
        }
        View tipsView = findChildViewById(tipsContainerView, tipsId);
        hideTipsInternal(targetView, tipsView);
    }

    private static void hideTipsInternal(View targetView, View tipsView) {
        ViewGroup tipsContainerView = (ViewGroup) targetView.getParent();
        if (!(tipsContainerView instanceof TipsContainer)) {
            return;
        }
        tipsContainerView.removeView(tipsView);
        boolean hideTarget = false;
        for (int i = 0; i < tipsContainerView.getChildCount(); ++i) {
            Tips tips = (Tips) tipsContainerView.getChildAt(i).getTag();
            if (tips == null) {
                continue;
            }
            hideTarget = tips.mHideTarget;
            if (hideTarget) {
                break;
            }
        }
        targetView.setVisibility(hideTarget ? View.INVISIBLE : View.VISIBLE);
        if (tipsContainerView.getChildCount() == 1) {
            removeContainerView(tipsContainerView, targetView);
        }
    }

    private static void removeContainerView(ViewGroup tipsContainerView, View targetView) {
        ViewGroup parent = (ViewGroup) tipsContainerView.getParent();
        ViewGroup.LayoutParams targetParams = tipsContainerView.getLayoutParams();
        int index = parent.indexOfChild(tipsContainerView);
        parent.removeViewAt(index);
        if (targetView.getParent() != null) {
            ((ViewGroup) targetView.getParent()).removeView(targetView);
        }
        parent.addView(targetView, index, targetParams);
    }

    static View findChildViewById(ViewGroup parent, int id) {
        final int count = parent.getChildCount();
        for (int i = 0; i < count; ++i) {
            View child = parent.getChildAt(i);
            if (child.getId() == id) {
                return child;
            }
        }
        return null;
    }
}
