package nextstep.github.ui.repo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import nextstep.github.ui.theme.GithubTheme

class GithubRepoListActivity : ComponentActivity() {
    private val viewModel: GithubRepoViewModel by viewModels { GithubRepoViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    GithubRepoListScreen(
                        modifier = Modifier,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}
