package dtu

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dtu.core.Constants

@Composable
fun AgegroupList(
    list: List<Agegroup>,
    modifier: Modifier = Modifier
) {
    Log.d(Constants.AGEGROUP, "AgegroupList size: ${list.size}")
    LazyColumn(modifier = modifier
    ) {
        items(
            items = list
        ) { agegroup ->
            AgegroupCard(
                agegroup = agegroup
            )
        }
    }
}


