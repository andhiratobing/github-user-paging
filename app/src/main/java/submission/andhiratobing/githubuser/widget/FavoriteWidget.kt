package submission.andhiratobing.githubuser.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import submission.andhiratobing.githubuser.BuildConfig
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity


class FavoriteWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.action.let { intentAction ->
            if (intentAction == EXTRA_FAVORITE_WIDGET_UPDATE) {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val widgetIds = appWidgetManager.getAppWidgetIds(
                    ComponentName(
                        context,
                        FavoriteWidget::class.java
                    )
                )
                appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.listFavoriteWidget)
            }

            if (intentAction == TOAST_ACTION) {
//                val viewIndex = intent?.getIntExtra(EXTRA_ITEM, 0)
//                Toast.makeText(context, "Touched $viewIndex", Toast.LENGTH_SHORT).show()

                val intentToDetail = intent?.extras?.getParcelable<FavoriteEntity>(EXTRA_ITEM)
                Toast.makeText(context, "Touched ${intentToDetail?.username}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled

    }

    companion object {
        private const val TOAST_ACTION = "TOAST_ACTION"
        const val EXTRA_ITEM = "EXTRA_ITEM"
        const val EXTRA_FAVORITE_WIDGET_UPDATE = "${BuildConfig.APPLICATION_ID}.FAVORITE_WIDGET_UPDATE"

        @SuppressLint("UnspecifiedImmutableFlag")
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            // Construct the RemoteViews object
            val intent = Intent(context, FavoriteWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_widget)
            views.setRemoteAdapter(R.id.listFavoriteWidget, intent)
            views.setEmptyView(R.id.listFavoriteWidget, R.id.tvEmptyView)

            val toasIntent = Intent(context, FavoriteWidget::class.java)
            toasIntent.action = TOAST_ACTION
            toasIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toasIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.listFavoriteWidget, toastPendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

    }
}
