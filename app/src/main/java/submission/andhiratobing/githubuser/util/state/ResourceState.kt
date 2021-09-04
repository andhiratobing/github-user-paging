package submission.andhiratobing.githubuser.util.state

class ResourceState private constructor(
    val status: Status,
    val message: String) {

    enum class Status{LOADING, SUCCESS, ERROR}

    companion object {
        val LOADING: ResourceState = ResourceState(Status.LOADING, "Loading")
        val LOADED: ResourceState = ResourceState(Status.SUCCESS,"Success")
        val FAILED: ResourceState = ResourceState(Status.ERROR,"Something went wrong")

    }
}