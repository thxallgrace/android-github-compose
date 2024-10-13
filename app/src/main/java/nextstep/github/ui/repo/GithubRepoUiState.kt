package nextstep.github.ui.repo

import nextstep.github.model.GithubRepositoryDto

sealed interface GithubRepoUiState {

    data object Loading : GithubRepoUiState
    data object Empty : GithubRepoUiState
    data class Success(val repositories: List<GithubRepositoryDto>) : GithubRepoUiState
    data object Error : GithubRepoUiState
}

