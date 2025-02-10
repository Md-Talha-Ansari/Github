package com.tf.app.github.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle


@Composable
fun buildAnnotatedStringForLink(link: String, label: String, linkColor:Color): AnnotatedString{
    return buildAnnotatedString {
        withStyle(style = SpanStyle(fontStyle = MaterialTheme.typography.titleSmall.fontStyle)){
            append(label)
            append("  ")
        }
        withLink(
            LinkAnnotation.Url(
                link,
                TextLinkStyles(style = SpanStyle(color = linkColor))
            )
        ) {
            append(link)
        }
    }
}