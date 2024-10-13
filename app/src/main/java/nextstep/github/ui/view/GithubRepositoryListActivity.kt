package nextstep.github.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import nextstep.github.GithubApplication
import nextstep.github.ui.theme.GithubTheme

class GithubRepositoryListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                }
            }
        }
        val appContainer = (application as GithubApplication).appContainer
        val repository = appContainer.githubRepository
        lifecycleScope.launch {
            val result = repository.getRepositories("next-step")
            result.fold(
                onSuccess = { Log.d("grace_log", "result: ${it.joinToString("\n")}") },
                onFailure = { Log.d("grace_log", "error: ${it.message}") }
            )
        }
    }
}
