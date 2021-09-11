package submission.andhiratobing.githubuser.view.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.data.repositories.local.FavoriteRepository

@ExperimentalCoroutinesApi
class FavoriteRemoteViewFactory constructor(
    private val context: Context,
    private val favoriteRepository: FavoriteRepository
) : RemoteViewsService.RemoteViewsFactory {

    private var listWidgetItems = ArrayList<FavoriteEntity>()

    private fun fetchDataFavoriteUser() {
        favoriteRepository.getAllDataToFavoriteWidget().forEach {
            listWidgetItems.add(it)
        }
    }

    override fun onCreate() {}

    override fun onDataSetChanged() {
        val identity = Binder.clearCallingIdentity()
        listWidgetItems.clear()
        fetchDataFavoriteUser()
        Binder.restoreCallingIdentity(identity)
    }

    override fun onDestroy() {}


    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.layout_favorite_widget_item)
        try {
            val data = listWidgetItems[position]
            val bitmap = Glide.with(context).asBitmap()
                .load(data.avatar)
                .transform(CircleCrop())
                .submit().get()


            remoteViews.setImageViewBitmap(R.id.ivItemFavoriteWidget, bitmap)
            remoteViews.setTextViewText(R.id.tvUsernameWidget, data.username)
            val extras = bundleOf(FavoriteWidget.EXTRA_ITEM to data.username)
            val fillInIntent = Intent()
            fillInIntent.putExtras(extras)

            remoteViews.setOnClickFillInIntent(R.id.ivItemFavoriteWidget, fillInIntent)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return remoteViews
    }

    override fun getCount(): Int = listWidgetItems.size

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}