package nextstep.github.ui.repo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nextstep.github.R
import nextstep.github.model.GithubRepositoryDto
import nextstep.github.ui.repo.component.RepoListItem
import nextstep.github.ui.repo.component.RepoListTopBar

@Composable
fun GithubRepoListScreen(
    modifier: Modifier = Modifier,
    viewModel: GithubRepoViewModel = viewModel(factory = GithubRepoViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GithubRepoListScreen(
        uiState = uiState,
        onClickRetry = { viewModel.fetchRepositories() },
        modifier = modifier,
    )

}

@Composable
fun GithubRepoListScreen(
    uiState : GithubRepoUiState,
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState) {
        if (uiState is GithubRepoUiState.Error) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.github_repo_list_error_message),
                actionLabel = context.getString(R.string.github_repo_list_error_retry),
            )

            if (result == SnackbarResult.ActionPerformed) {
                onClickRetry()
            }
        }
    }

    Scaffold(topBar = { RepoListTopBar() }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when(uiState) {
                GithubRepoUiState.Loading,
                GithubRepoUiState.Error -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                GithubRepoUiState.Empty -> {
                    GithubRepoListEmptyContent()
                }
                is GithubRepoUiState.Success -> {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.surface),
                    ) {
                        items(uiState.repositories) { item ->
                            RepoListItem(item = item)
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GithubRepoListEmptyContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.github_repo_list_empty),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

class GithubRepoListScreenPreviewParameter : PreviewParameterProvider<GithubRepoUiState> {
    override val values: Sequence<GithubRepoUiState> = sequenceOf(
        GithubRepoUiState.Loading,
        GithubRepoUiState.Error,
        GithubRepoUiState.Empty,
        GithubRepoUiState.Success(
            listOf(
                GithubRepositoryDto(fullName = "name1", description = "description1"),
                GithubRepositoryDto(fullName = "name2", description = "description2"),
            )
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun GithubRepoListScreenPreview(
    @PreviewParameter(GithubRepoListScreenPreviewParameter::class) parameter : GithubRepoUiState
) {
    GithubRepoListScreen(
        uiState = parameter,
        onClickRetry = {}
    )
}
