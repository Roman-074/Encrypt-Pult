package com.pult.ui.fragment.list

import java.util.*

/**
 * A dummy repository with ice creams.
 */
class IceCreamRepository : BaseRepository<IceCream>() {

    override fun generateNewItem(): IceCream {
        val iceCream = IceCream(0, "", "", "")
        addItem(iceCream)
        return iceCream
    }

    companion object {
        private var instance: IceCreamRepository? = null
        fun getInstance(): IceCreamRepository {
            if (instance == null)
                instance = IceCreamRepository()

            return instance as IceCreamRepository
        }
    }

}