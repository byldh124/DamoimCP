package com.moondroid.project01_meetingapp.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.moondroid.project01_meetingapp.ui.theme.Gray03
import com.moondroid.project01_meetingapp.ui.theme.Red01
import com.moondroid.project01_meetingapp.ui.theme.Red03
import com.moondroid.project01_meetingapp.ui.theme.Typography

@Composable
fun CustomTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    label: String? = null,
    maxLines: Int = 1,
    maxLength: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var fieldValue by remember { mutableStateOf(value) }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
    ) {
        OutlinedTextField(
            value = fieldValue,
            textStyle = Typography.bodyMedium,
            label = { if (label != null) Text(label) },
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                if (it.length <= maxLength) {
                    fieldValue = it
                    //text = value.filter { it.isDigit() }
                    onTextChanged(it)
                }
            },
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            shape = RoundedCornerShape(size = 12.dp),
            colors = OutlinedTextFieldDefaults.colors().copy(
                cursorColor = Red01,
                focusedIndicatorColor = Red01,
                focusedLabelColor = Red01,
                textSelectionColors = TextSelectionColors(
                    handleColor = Red01,
                    backgroundColor = Red03
                ),
                unfocusedIndicatorColor = Gray03,
                unfocusedLabelColor = Gray03,
            )
        )

    }
}