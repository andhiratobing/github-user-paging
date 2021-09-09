package submission.andhiratobing.githubuser.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.data.repositories.local.FavoriteRepository
import submission.andhiratobing.githubuser.widget.FavoriteWidget.Companion.EXTRA_ITEM


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
//            val extras = bundleOf(FavoriteWidget.EXTRA_ITEM to position)

            val bundle = Bundle()
            bundle.putParcelable(EXTRA_ITEM, data)
            val fillIntent = Intent(position.toString())
            fillIntent.putExtra(EXTRA_ITEM, data)
            fillIntent.putExtras(bundle)
            remoteViews.setOnClickFillInIntent(R.id.ivItemFavoriteWidget, fillIntent)
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