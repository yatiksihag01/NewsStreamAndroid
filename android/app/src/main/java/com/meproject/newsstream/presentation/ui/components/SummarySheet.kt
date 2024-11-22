package com.meproject.newsstream.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.meproject.newsstream.R
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.presentation.ui.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummarySheet(
    modifier: Modifier = Modifier,
    onDismissRequest: (sheetState: SheetState) -> Unit,
    title: String,
    summaryResourceFlow: Flow<Resource<String>>,
) {
    val modalSheetState = rememberModalBottomSheetState()
    val summaryResource = summaryResourceFlow.collectAsState(initial = Resource.Loading()).value
    val scope = rememberCoroutineScope()
    SummaryBottomSheet(
        modifier = modifier,
        title = title,
        summaryResource = summaryResource,
        sheetState = modalSheetState,
        onDismissRequest = {
            scope.launch {
                if (modalSheetState.isVisible) {
                    modalSheetState.hide()
                }
            }
            onDismissRequest(modalSheetState)
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    summaryResource: Resource<String>,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small
                )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.small)
                    .clip(shape = MaterialTheme.shapes.small),
                thickness = MaterialTheme.spacing.small
            )
            when (summaryResource) {
                is Resource.Loading -> {
                    SummaryShimmer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.small)
                    )
                }

                is Resource.Error -> {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.sorry_something_went_wrong)
                                + " " + summaryResource.message,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = summaryResource.data
                            ?: stringResource(R.string.no_summary_available),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}