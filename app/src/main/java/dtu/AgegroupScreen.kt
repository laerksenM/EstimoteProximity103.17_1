package dtu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AgegroupScreen(
    modifier: Modifier = Modifier,
    agegroupViewModel: AgegroupViewModel = viewModel()
) {
    AgegroupList(
        list = agegroupViewModel.agegroup
    )
}