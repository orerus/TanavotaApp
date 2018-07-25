package com.tanavota.tanavota.view

import android.content.Context
import android.support.v4.app.Fragment
import com.github.kubode.rxeventbus.RxEventBus
import com.tanavota.tanavota.TanavotaApplication
import com.tanavota.tanavota.di.component.ActivityComponent
import com.tanavota.tanavota.model.domain.favorite.FavoriteOperationEvent
import com.tanavota.tanavota.view.common.HeaderContents
import rx.Subscription
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by murata_sho on 2018/03/26.
 */
abstract class BaseFragment : Fragment() {
    lateinit var wHeaderContents: WeakReference<HeaderContents>
    val mComponent: ActivityComponent
        get () = (this.activity?.application as TanavotaApplication).getApplicationComponent()
                .activityComponent()
    @Inject
    lateinit var bus: RxEventBus
    private lateinit var subscription: Subscription

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (activity is HeaderContents) {
            wHeaderContents = WeakReference(activity as HeaderContents)
        } else {
            throw RuntimeException(activity.toString() + " must implement HeaderContents")
        }

        mComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()

        subscription = bus.subscribe(FavoriteOperationEvent::class.java) {
            onFavoriteOperationEvent(it)
        }
    }

    override fun onStop() {
        super.onStop()

        subscription.unsubscribe()
    }

    open fun onFavoriteOperationEvent(event: FavoriteOperationEvent) {
        // 必要に応じてオーバーライド
    }

    abstract fun setTitle()
}