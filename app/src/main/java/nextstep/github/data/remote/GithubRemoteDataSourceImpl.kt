package nextstep.github.data.remote

import nextstep.github.data.remote.api.GithubApiService
import nextstep.github.model.GithubRepositoryDto

class GithubRemoteDataSourceImpl(
    private val githubApiService: GithubApiService,
) : GithubRemoteDataSource {

    override suspend fun getRepositories(organization: String): Result<List<GithubRepositoryDto>> =
        runCatching { githubApiService.getRepositories(organization) }
}
