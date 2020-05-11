package com.example.mymarket.app

import android.app.Application
import android.content.Context
import com.example.mymarket.api.Repository
import com.example.mymarket.api.Url
import com.example.mymarket.database.AppDatabase
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), KodeinAware {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initViewPump()
    }

    private fun initViewPump() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                            .build()
                    )
                )
                .build()
        )
    }

    override val kodein by Kodein.lazy {
        bind<Context>() with singleton { this@App }
        bind<Repository>() with singleton { Repository(instance(), instance()) }
        bind<Retrofit>() with singleton { provideRetrofit() }
        bind<AppDatabase>() with singleton { AppDatabase.getInstance(this@App) }
    }

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}