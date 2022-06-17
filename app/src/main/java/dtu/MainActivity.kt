package dtu

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import com.estimote.proximity_sdk.api.*
import dtu.core.CloudCredentials.APP_ID
import dtu.core.CloudCredentials.APP_TOKEN
import dtu.core.Constants
import dtu.core.ZoneNavn
import dtu.engtech.DB3_3.ui.theme.EstimoteProximity103Theme

private const val TAG = "PROXIMITY"
private const val SCANTAG = "SCANNING"

class MainActivity : ComponentActivity() {

    private lateinit var proximityObserver: ProximityObserver
    private var proximityObservationHandler: ProximityObserver.Handler? = null
    private val cloudCredentials = EstimoteCloudCredentials(
        APP_ID,
        APP_TOKEN
    )

    //val zoneEventViewModel by viewModels<ZoneEventViewModel>()
    private val agegroupViewModel by viewModels<AgegroupViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstimoteProximity103Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    NavDemo()
                    //AgegroupScreen()
                    //BeaconListView(zoneEventViewModel.zoneInfo)
                }
            }
        }

        Log.d(Constants.FIREBASETAG, "AGVM: $agegroupViewModel")

        // Requirements check
        RequirementsWizardFactory.createEstimoteRequirementsWizard().fulfillRequirements(
            this,
            onRequirementsFulfilled = { startProximityObservation() },
            onRequirementsMissing = displayToastAboutMissingRequirements,
            onError = displayToastAboutError
        )
        agegroupViewModel.agegroupRepository.addListener()
    }

    override fun onDestroy() {  //Skal dette være her?
        super.onDestroy()
        proximityObservationHandler?.stop()
    }

    private fun startProximityObservation() {
        Log.d(Constants.BEACONLOGTAG, "StartObserving")
        proximityObserver = ProximityObserverBuilder(applicationContext, cloudCredentials)
            .onError(displayToastAboutError)
            .withTelemetryReportingDisabled()
            .withAnalyticsReportingDisabled()
            .withEstimoteSecureMonitoringDisabled()
            .withBalancedPowerMode()
            .build()

        /*val proximityZones = ArrayList<ProximityZone>()
        proximityZones.add(zoneBuild("Medicin1"))
        proximityZones.add(zoneBuild("Medicin2"))
        proximityZones.add(zoneBuild("Medicin3"))
        proximityZones.add(zoneBuild("512"))*/
        val proximityZones = ArrayList<ProximityZone>()
        proximityZones.add(zoneBuild(ZoneNavn.TAG510))
        proximityZones.add(zoneBuild(ZoneNavn.TAG511))
        proximityZones.add(zoneBuild(ZoneNavn.TAG512))

        proximityObservationHandler = proximityObserver.startObserving(proximityZones)
    }

    private fun zoneBuild(tag: String): ProximityZone {
        return ProximityZoneBuilder()
            .forTag(tag)
            .inNearRange()
            .onEnter {
                Log.d(Constants.BEACONLOGTAG, "Enter: ${it.tag}")
                agegroupViewModel.getAgegroup(it.tag)

            //Log.d(TAG, "Enter: ${it}")
                //zoneEventViewModel.updateZoneContexts(setOf(it))
                //agegroupViewModel.agegroupRepository.getAgegroup()
                //hent data fra firebase med agegroup fra beacon tag

                //agegroupViewModel.agegroupRepository.
            }
            .onExit {
                Log.d(Constants.BEACONLOGTAG, "Exit: ${it.tag}")
            }
            .onContextChange {
                Log.d(Constants.BEACONLOGTAG, "Change: ${it}")
                //zoneEventViewModel.updateZoneContexts(it)
            }
            .build()
    }

    // Lambda functions for displaying errors when checking requirements
    private val displayToastAboutMissingRequirements: (List<Requirement>) -> Unit = {
        Toast.makeText(
            this,
            "Unable to start proximity observation. Requirements not fulfilled: ${it.size}",
            Toast.LENGTH_SHORT
        ).show()
    }
    private val displayToastAboutError: (Throwable) -> Unit = {
        Toast.makeText(
            this,
            "Error while trying to start proximity observation: ${it.message}",
            Toast.LENGTH_SHORT
        ).show()
        Log.d("ERROR", it.message?: "null message")
    }

}
/*private fun testFirebaseGetStaff(ageID: String){
    val docRef = FirebaseFirestore.getInstance().collection(Constants.AGEGROUP)
    docRef.whereEqualTo(Constants.ZONETAG, ageID)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(Constants.FIREBASETAG, "Number of documents => ${documents.size()}")
                Log.d(Constants.FIREBASETAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w(Constants.FIREBASETAG, "Error getting documents: ", exception)
        }
}

private fun testFirebaseGet(){
    val docRef = FirebaseFirestore.getInstance().collection(Constants.AGEGROUP)
    docRef.get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(Constants.FIREBASETAG, "Number of documents => ${documents.size()}")
                Log.d(Constants.FIREBASETAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w(Constants.FIREBASETAG, "Error getting documents: ", exception)
        }
}*/

@Composable
fun BeaconListView(zoneInfo: List<BeaconInfo>) {
    LazyColumn {
        items(zoneInfo) { beaconInfo ->
            BeaconCard(beaconInfo)
        }
    }
}

@Composable
fun BeaconCard(beaconInfo: BeaconInfo) {
    Column() {
        Text(text = DeviceList.deviceNames[beaconInfo.deviceID.uppercase()] ?: "No device name")
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = beaconInfo.tag)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = beaconInfo.attachments[AttachmentKeys.DESCRIPTION.key] ?: "No description")
        Spacer(modifier = Modifier.height(16.dp))
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EstimoteProximity103Theme {
        //AgegroupScreen()
        NavDemo()

    }
}
