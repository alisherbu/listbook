package kaa.alisherbu.listbook.chapter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.core.shared.R
import kaa.alisherbu.listbook.core.shared.model.Chapter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterItem(
    chapter: Chapter,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = chapter.name)
            if (chapter.isDownloaded) {
                Box(modifier = Modifier.weight(1f)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_download_done_24),
                        contentDescription = "Downloaded",
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
