package submission.andhiratobing.githubuser.services.widget

import android.content.Intent
import android.widget.RemoteViewsService
import dagger.hilt.android.AndroidEntryPoint
import submission.andhiratobing.githubuser.data.repositories.local.FavoriteRepository
import submission.andhiratobing.githubuser.view.widget.FavoriteRemoteViewFactory
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteWidgetService : RemoteViewsService() {

    @Inject
    lateinit var favoriteRepository: FavoriteRepository

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        FavoriteRemoteViewFactory(this.applicationContext, favoriteRepository)

}