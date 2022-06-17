package dtu

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class AgegroupViewModel : ViewModel() {
    //var staffRepository = StaffRepositoryMock()
    var agegroupRepository = AgegroupRepositoryFirestore()

    private var _agegroup = agegroupRepository.agegroup.toMutableStateList() //for at få fra firebase cloud
    val agegroup: List<Agegroup>
        //get() = _agegroup //for at få fra firebase cloud
        get() = AgegroupData.ageSample //for at få dummydata

    private fun getAge() = AgegroupData.ageSample

    //init {
    //  agegroupRepository.getAgegroup()
    // }

    fun getAgegroup(ageID: String) {
        agegroupRepository.getAgegroup(ageID)
        //agegroupRepository.agegroup.add(Agegroup("30"))
    }

}
