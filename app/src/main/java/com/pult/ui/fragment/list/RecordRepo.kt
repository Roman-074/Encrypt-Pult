package com.pult.ui.fragment.list

/**
 * A dummy repository with ice creams.
 */
class RecordRepo : BaseRepository<Record>() {

    override fun generateNewItem(): Record {
        val iceCream = Record(0, "", "", "")
        addItem(iceCream)
        return iceCream
    }

    companion object {
        private var instance: RecordRepo? = null
        fun getInstance(): RecordRepo {
            if (instance == null)
                instance = RecordRepo()

            return instance as RecordRepo
        }
    }

}