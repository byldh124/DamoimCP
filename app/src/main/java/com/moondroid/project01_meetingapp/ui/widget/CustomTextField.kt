package com.moondroid.project01_meetingapp.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
    ) {
        OutlinedTextField(
            value = value,
            textStyle = Typography.bodyMedium,
            label = { if (label != null) Text(label) },
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                onTextChanged(it)
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

@Composable
fun CustomText(value: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Gray03,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 18.dp),
            text = value,
            style = Typography.bodyMedium,
        )
    }
}