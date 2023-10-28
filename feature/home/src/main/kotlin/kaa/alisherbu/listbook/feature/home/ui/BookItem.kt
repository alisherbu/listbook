package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.core.shared.R

@Composable
internal fun BookItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_play_arrow_24),
            contentDescription = "",
            modifier = Modifier
                .height(140.dp)
                .width(100.dp)
        )
    }
}

@Composable
@Preview
private fun BookItemPreview() {
    BookItem()
}