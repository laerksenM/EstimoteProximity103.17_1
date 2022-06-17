package dtu

import dtu.Agegroup

interface AgegroupRepository {
    abstract val agegroup: List<Agegroup>
    fun getAgegroup(ageID: String)
    fun getAge()
    fun addListener()
    //add Listner

}