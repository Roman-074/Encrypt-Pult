package com.pult.ui.fragment.list

class RecordsRepo: BaseRepository<Record>() {

    companion object {
        private var instance: RecordsRepo? = null

        fun getInstance(): RecordsRepo {
            if (instance == null)
                instance = RecordsRepo()

            return instance as RecordsRepo
        }
    }


    override fun generateNewItem(): Record {
        val record = Record(0, "aa", "b")
        addItem(record)
        return  record
    }

}