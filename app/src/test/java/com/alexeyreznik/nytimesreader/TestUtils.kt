package com.alexeyreznik.nytimesreader

import org.mockito.Mockito

/**
 * Created by alexeyreznik on 15/01/2018.
 */
fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T