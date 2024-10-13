package nextstep.github.data

import nextstep.github.data.remote.GithubRemoteDataSource
import nextstep.github.model.GithubRepositoryDto

class GithubRepositoryImpl(
    private val githubRemoteDataSource: GithubRemoteDataSource,
) : GithubRepository {

    override suspend fun getRepositories(organization: String): Result<List<GithubRepositoryDto>> =
        githubRemoteDataSource.getRepositories(organization)
}
