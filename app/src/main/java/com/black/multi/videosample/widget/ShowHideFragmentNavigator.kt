package com.black.multi.videosample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.black.xcommon.utils.EzLog
import java.util.*

/**
 * Created by wei.
 * Date: 2020/5/25 10:36
 * Desc:
 */
@Navigator.Name("ShowHideFragmentNavigator")
class ShowHideFragmentNavigator(context: Context, manager: FragmentManager, containerId: Int) : FragmentNavigator(context, manager, containerId) {
    private val TAG = "ShowHideFragmentNavigator"
    private var mContext: Context = context
    private var mManager: FragmentManager = manager
    private var mContainerId: Int = containerId
    private var mBackStack=ArrayDeque<Int>()

    @SuppressLint("LongLogTag")
    override fun navigate(destination: Destination, args: Bundle?,
                          navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        if (mManager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already"
                    + " saved its state")
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }
//        val frag = instantiateFragment(mContext, mManager,
//                className, args)
//        frag.arguments = args

        val ft = mManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
        var showFg = mManager.primaryNavigationFragment
        if (showFg != null) {
            ft.hide(showFg)
        }

        val tag = destination.id.toString()
        var frag = mManager.findFragmentByTag(tag)
        if (frag != null) {
            ft.show(frag)
        } else {
            frag = instantiateFragment(mContext, mManager, className, args)
            frag.arguments = args
            ft.add(mContainerId, frag,tag)
        }
//        ft.replace(mContainerId, frag)
        ft.setPrimaryNavigationFragment(frag)
        @IdRes val destId = destination.id

        val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
        field.isAccessible = true
        mBackStack = field[this] as ArrayDeque<Int>
        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId)
        val isAdded: Boolean
        isAdded = when {
            initialNavigation -> {
                true
            }
            isSingleTopReplacement -> {
                // Single Top means we only want one instance on the back stack
                if (mBackStack.size > 1) {
                    // If the Fragment to be replaced is on the FragmentManager's
                    // back stack, a simple replace() isn't enough so we
                    // remove it from the back stack and put our replacement
                    // on the back stack in its place
                    mManager.popBackStack(
                            generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
                }
                false
            }
            else -> {
                ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
                true
            }
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        EzLog.d("mBackStack--->${mBackStack.size}")
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }

}