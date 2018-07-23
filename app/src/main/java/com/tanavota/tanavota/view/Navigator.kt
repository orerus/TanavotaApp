package com.tanavota.tanavota.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.tanavota.tanavota.R

/**
 * Created by murata_sho on 2018/03/26.
 */

interface Navigator {
    fun navigateToFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
        val transaction = fragmentManager.beginTransaction()
        // 新しく追加を行うのでaddを使用します
        // 他にも、よく使う操作で、replace removeといったメソッドがあります
        // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        // 最後にcommitを使用することで変更を反映します
        transaction.commit()
    }
}