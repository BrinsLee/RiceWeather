package com.brins.riceweather.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.brins.riceweather.R
import com.brins.riceweather.RiceWeatherApplication
import com.brins.riceweather.data.model.weather.HeWeather
import android.content.Intent
import android.content.ComponentName


class WeatherWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
/*        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)*/
        val view = RemoteViews(context.packageName, R.layout.view_app_widget)
//        view.setOnClickPendingIntent(R.id.container, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetIds, view)

    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent == null) {
            return
        }
        val action = intent.action
        if (action == "action_weather") {
            val view = RemoteViews(context.packageName, R.layout.view_app_widget)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, WeatherWidget::class.java)
            appWidgetManager.updateAppWidget(componentName, view)

        }
    }

    companion object {

        fun updateRemoteViews(
            context: Context = RiceWeatherApplication.context,
            viewModel: HeWeather
        ): RemoteViews {
            val view = RemoteViews(context.packageName, R.layout.view_app_widget)
            viewModel.weather?.let {
                view.setTextViewText(R.id.temp, it[0].temp())
                view.setTextViewText(R.id.weather, it[0].weatherDesc)
                view.setTextViewText(R.id.location, viewModel.city)
                view.setImageViewResource(R.id.iv_weather, it[0].weatherImages())
            }
            return view
        }
    }
}