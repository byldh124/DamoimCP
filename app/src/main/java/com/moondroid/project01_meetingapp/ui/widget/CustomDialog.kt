package com.moondroid.project01_meetingapp.ui.widget

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.ui.theme.Blue01
import com.moondroid.project01_meetingapp.ui.theme.Gray04
import com.moondroid.project01_meetingapp.ui.theme.Typography

abstract class DialogButton(open val text: String, open val onClick: () -> Unit)
class PositiveButton(override val text: String = "확인", override val onClick: () -> Unit = {}) : DialogButton(text, onClick)
class NegativeButton(override val text: String = "취소", override val onClick: () -> Unit = {}) : DialogButton(text, onClick)

@Composable
fun ButtonDialog(
    positiveButton: PositiveButton,
    negativeButton: NegativeButton? = null,
    contents :@Composable ColumnScope.() -> Unit
) {
    val onDismissRequest: (() -> Unit) = negativeButton?.onClick ?: positiveButton.onClick

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 30.dp, horizontal = 10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    contents()
                }

                HorizontalDivider(color = Gray04, thickness = 1.dp)

                Row(
                    modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)
                ) {
                    if (negativeButton != null) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable(onClick = negativeButton.onClick),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                negativeButton.text,
                                fontSize = 20.sp,
                                color = Blue01,
                                style = Typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 20.dp)
                            )
                        }
                    }

                    if (negativeButton != null) {
                        VerticalDivider(color = Gray04, thickness = 1.dp)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = positiveButton.onClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            positiveButton.text,
                            fontSize = 20.sp,
                            color = Blue01,
                            style = Typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingDialog() {
    Dialog({}) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 25.dp, horizontal = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current
                val gifEnabledLoader = ImageLoader.Builder(context)
                    .components {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }.build()
                AsyncImage(
                    imageLoader = gifEnabledLoader,
                    model = R.drawable.loading,
                    contentDescription = "로딩중",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                )
                Spacer(Modifier.height(20.dp))
                Text("잠시만 기다려주세요.", fontSize = 16.sp, style = Typography.bodyLarge)
            }
        }
    }
}