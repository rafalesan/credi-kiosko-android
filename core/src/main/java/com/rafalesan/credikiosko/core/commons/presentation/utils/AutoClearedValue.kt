package com.rafalesan.credikiosko.core.commons.presentation.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClearedValue<T : Any> : ReadWriteProperty<Fragment, T>, DefaultLifecycleObserver {
    private var _value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        _value ?: throw IllegalStateException("Trying to call an auto-cleared value outside of the view lifecycle.")

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
        _value = value
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        _value = null
    }

}