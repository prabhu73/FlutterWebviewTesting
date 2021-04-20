package com.myflutterwebview

import android.content.Context
import android.content.Intent
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterSurfaceView
import io.flutter.embedding.android.FlutterTextureView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugins.GeneratedPluginRegistrant

class FlutterEngineActivity: FlutterActivity() {

    private class NewEngineBuilder :
        FlutterActivity.NewEngineIntentBuilder(FlutterEngineActivity::class.java)

    private class CachedIntentBuilder(engineId: String):
        FlutterActivity.CachedEngineIntentBuilder(FlutterEngineActivity::class.java, engineId)

    companion object {
        private const val TAG = "#####Engine"

        private var ackoFlutterEngine: FlutterEngine? = null
        private var initRoute: String? = null

        fun init(context: Context, engineId: String? = null, initRoute: String? = null) {
            this.initRoute = initRoute
            if (engineId != null) {
                ackoFlutterEngine = FlutterEngineCache.getInstance().get(engineId)
                if (ackoFlutterEngine == null) {
                    // Instantiate a cachedFlutterEngine.
                    ackoFlutterEngine = FlutterEngine(context.applicationContext)
                    ackoFlutterEngine?.dartExecutor?.executeDartEntrypoint(
                        DartExecutor.DartEntrypoint.createDefault()
                    )
                    FlutterEngineCache
                        .getInstance()
                        .put(engineId, ackoFlutterEngine)
                }
            } else {
                ackoFlutterEngine = FlutterEngine(context.applicationContext)
                ackoFlutterEngine?.dartExecutor?.executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
                )
            }
        }

        fun createNewBuilder(): FlutterActivity.NewEngineIntentBuilder {
            return NewEngineBuilder()
        }

        fun createCachedEngineBuilder(engineId: String): CachedEngineIntentBuilder {
            return CachedIntentBuilder(engineId)
        }
    }

    override fun getFlutterEngine(): FlutterEngine? {
        return ackoFlutterEngine
    }

    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        Log.d(TAG, "provideFlutterEngine")
        return super.provideFlutterEngine(context)
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        super.configureFlutterEngine(flutterEngine)
        if (initRoute != null && initRoute?.isNotEmpty()!!) {
            flutterEngine.navigationChannel.setInitialRoute(initRoute!!)
        }
        Log.d(TAG, "configureFlutterEngine")
    }

    override fun cleanUpFlutterEngine(flutterEngine: FlutterEngine) {
        super.cleanUpFlutterEngine(flutterEngine)
        Log.d(TAG, "cleanUpFlutterEngine")
    }

    override fun detachFromFlutterEngine() {
        super.detachFromFlutterEngine()
        Log.d(TAG, "detachFromFlutterEngine")
    }

    override fun onFlutterSurfaceViewCreated(flutterSurfaceView: FlutterSurfaceView) {
        super.onFlutterSurfaceViewCreated(flutterSurfaceView)
        Log.d(TAG, "onFlutterSurfaceViewCreated")
    }

    override fun onFlutterTextureViewCreated(flutterTextureView: FlutterTextureView) {
        super.onFlutterTextureViewCreated(flutterTextureView)
        Log.d(TAG, "onFlutterTextureViewCreated")
    }

    override fun onFlutterUiDisplayed() {
        super.onFlutterUiDisplayed()
        Log.d(TAG, "onFlutterUiDisplayed")
    }

    override fun onFlutterUiNoLongerDisplayed() {
        super.onFlutterUiNoLongerDisplayed()
        Log.d(TAG, "onFlutterUiNoLongerDisplayed")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        ackoFlutterEngine?.platformViewsController?.destroyOverlaySurfaces()
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(TAG, "onTrimMemory")
    }
}