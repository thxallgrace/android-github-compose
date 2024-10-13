package nextstep.github.ui.repo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import nextstep.github.GithubApplication
import nextstep.github.data.GithubRepository
import nextstep.github.model.GithubRepositoryDto

class GithubRepoViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val _repositories = MutableStateFlow<List<GithubRepositoryDto>>(listOf())
    val repositories: StateFlow<List<GithubRepositoryDto>> = _repositories.asStateFlow()

    init {
        fetchRepositories()
    }

    fun fetchRepositories() {
        viewModelScope.launch {
            repository
                .getRepositories(ORGANIZATION)
                .onSuccess { result ->
                    Log.d("grace_log", "result: ${result.joinToString("\n")}")
                    _repositories.value = result
                }
                .onFailure {
                    Log.d("grace_log", "error: ${it.message}")
                }
        }
    }

    companion object {
        private const val ORGANIZATION = "next-step"

        // ViewModel에서 별도의 의존성을 참조하고 있는 경우,
        // ViewModel Factory를 활용하여 ViewModel에 필요한 의존성을 주입하고 관리할 수 있다.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val githubRepository =
                    (this[APPLICATION_KEY] as GithubApplication).appContainer.githubRepository
                GithubRepoViewModel(
                    repository = githubRepository
                )
            }
        }
    }
}
