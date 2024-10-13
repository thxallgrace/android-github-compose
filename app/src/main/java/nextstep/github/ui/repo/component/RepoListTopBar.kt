package nextstep.github.ui.repo.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import nextstep.github.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.github_repo_top_bar_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun RepoListTopBarPreview() {
    RepoListTopBar()
}

