package com.eccentricyan.pohe.common.command

import android.util.Log

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ReplyCommand<T> {
    private var execute0: Action? = null
    private var execute1: Consumer<T>? = null

    private var canExecute0: Predicate<Boolean>? = null

    constructor(execute: Action) {
        this.execute0 = execute
    }


    constructor(execute: Consumer<T>) {
        this.execute1 = execute
    }

    /**
     *
     * @param execute callback for event
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    constructor(execute: Action, canExecute0: Predicate<Boolean>) {
        this.execute0 = execute
        this.canExecute0 = canExecute0
    }

    /**
     *
     * @param execute callback for event,this callback need a params
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    constructor(execute: Consumer<T>, canExecute0: Predicate<Boolean>) {
        this.execute1 = execute
        this.canExecute0 = canExecute0
    }


    fun execute() {
        if (execute0 != null && canExecute0()) {
            try {
                execute0!!.run()
            } catch (e: Exception) {
                Log.e("replyCommand excute", e.message)
            }

        }
    }

    private fun canExecute0(): Boolean {
        val rst: Boolean?
        try {
            rst = canExecute0!!.test(true)
        } catch (e: Exception) {
            return true
        }

        return rst
    }


    fun execute(parameter: T) {
        if (execute1 != null && canExecute0()) {
            try {
                execute1!!.accept(parameter)
            } catch (e: Exception) {
                Log.e("replyCommand excute T", e.message)
            }

        }
    }

}
