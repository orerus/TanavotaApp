package com.tanavota.tanavota.view

import android.content.Context
import android.support.v4.app.Fragment
import com.tanavota.tanavota.TanavotaApplication
import com.tanavota.tanavota.di.component.ActivityComponent
import com.tanavota.tanavota.view.common.HeaderContents
import java.lang.ref.WeakReference

/**
 * Created by murata_sho on 2018/03/26.
 */
abstract class BaseFragment : Fragment() {
    lateinit var wHeaderContents: WeakReference<HeaderContents>
    val mComponent: ActivityComponent
        get () = (this.activity?.application as TanavotaApplication).getApplicationComponent()
                .activityComponent()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (activity is HeaderContents) {
            wHeaderContents = WeakReference(activity as HeaderContents)
        } else {
            throw RuntimeException(activity.toString() + " must implement HeaderContents")
        }
    }

    abstract fun setTitle()
}