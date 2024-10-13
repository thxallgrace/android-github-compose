package nextstep.github.ui.repo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nextstep.github.model.GithubRepositoryDto
import nextstep.github.ui.repo.component.RepoListItem
import nextstep.github.ui.repo.component.RepoListTopBar

@Composable
fun GithubRepoListScreen(
    modifier: Modifier = Modifier,
    viewModel: GithubRepoViewModel = viewModel(factory = GithubRepoViewModel.Factory),
) {
    val repositories by viewModel.repositories.collectAsStateWithLifecycle()

    GithubRepoListScreen(
        modifier = modifier,
        items = repositories,
    )

}

@Composable
fun GithubRepoListScreen(
    modifier: Modifier = Modifier,
    items : List<GithubRepositoryDto> = listOf(),
) {
    Scaffold(topBar = { RepoListTopBar() }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
            ) {
                items(items) { item ->
                    RepoListItem(
                        item = item,
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GithubRepoListScreenPreview() {
    GithubRepoListScreen(
        items = listOf(
            GithubRepositoryDto(fullName = "name1", description = "description1"),
            GithubRepositoryDto(fullName = "name2", description = "description2"),
        )
    )
}
