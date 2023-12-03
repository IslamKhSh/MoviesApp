package com.yassir.movies.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yassir.movies.R
import com.yassir.movies.data.models.ErrorType

@Composable
fun ErrorView(errorType: ErrorType, msg: String?) {
    when (errorType) {
        ErrorType.InternetConnection, ErrorType.TimeOut -> ErrorPopup(errorMsgRes = R.string.internet_connection_error)
        else -> ErrorPopup(errorMsg = msg ?: stringResource(id = R.string.something_wrong))
    }
}

@Composable
fun ErrorPopup(@StringRes errorMsgRes: Int, onDismiss: () -> Unit = {}) {
    ErrorPopup(errorMsg = stringResource(id = errorMsgRes), onDismiss)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorPopup(errorMsg: String, onDismiss: () -> Unit = {}) {
    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(onDismissRequest = {
            onDismiss()
            openDialog = false
        }) {
            Surface(
                modifier =
                Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = errorMsg,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center,
                        modifier =
                        Modifier
                            .padding(horizontal = 24.dp)
                            .padding(top = 16.dp)
                    )
                    Divider(
                        color = Color.LightGray,
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 8.dp)
                    )

                    TextButton(
                        onClick = { openDialog = false },
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok),
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                    }
                }
            }
        }
    }
}
