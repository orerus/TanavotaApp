package com.tanavota.tanavota.view

import android.support.v4.app.Fragment
import com.tanavota.tanavota.TanavotaApplication
import com.tanavota.tanavota.di.component.ActivityComponent

/**
 * Created by murata_sho on 2018/03/26.
 */
abstract class BaseFragment : Fragment() {
    val mComponent: ActivityComponent
        get () = (this.activity?.application as TanavotaApplication).getApplicationComponent()
                .activityComponent()

}