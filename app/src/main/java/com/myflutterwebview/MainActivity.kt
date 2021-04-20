package com.myflutterwebview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.myflutterwebview.databinding.ActivityMainBinding
import io.flutter.embedding.android.FlutterFragment
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : AppCompatActivity() {

    companion object {
        // Define a tag String to represent the FlutterFragment within this
        // Activity's FragmentManager. This value can be whatever you'd like.
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }

    // Declare a local variable to reference the FlutterFragment so that you
    // can forward calls to it later.
    private var flutterFragment: FlutterFragment? = null

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        FlutterEngineActivity.init(this, "my_flutter_engine")

        val fragmentManager: FragmentManager = supportFragmentManager

        flutterFragment = fragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        binder.loadOnFragment.setOnClickListener {
            loadOnFragment(fragmentManager)
        }

        binder.loadOnActivity.setOnClickListener {
            startActivity(
                FlutterEngineActivity.createCachedEngineBuilder("my_flutter_engine")
                    .build(this)
            )
        }

        fragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                // Force the registration of the engine and the GeneratedPluginRegistrant
                override fun onFragmentAttached(
                    fm: FragmentManager,
                    f: Fragment,
                    context: Context
                ) {
                    super.onFragmentAttached(fm, f, context)

                    (f as FlutterFragment?)?.let {
                        GeneratedPluginRegistrant.registerWith(it.flutterEngine!!)
                    }
                }
            }, true
        )
    }

    private fun loadOnFragment(fragmentManager: FragmentManager) {
        if (flutterFragment == null) {
            flutterFragment = FlutterFragment.withCachedEngine("my_flutter_engine").build()
        }

        fragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container,
                flutterFragment!!,
                TAG_FLUTTER_FRAGMENT
            ).addToBackStack(TAG_FLUTTER_FRAGMENT)
            .commit()
    }

    override fun onPostResume() {
        super.onPostResume()
        flutterFragment?.onPostResume()
    }

    override fun onNewIntent(@NonNull intent: Intent) {
        super.onNewIntent(intent)
        flutterFragment?.onNewIntent(intent)
    }

    override fun onBackPressed() {
        if (flutterFragment != null) {
            flutterFragment?.onBackPressed()
        } else
            super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        flutterFragment?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onUserLeaveHint() {
        flutterFragment?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment?.onTrimMemory(level)
    }

    override fun onDestroy() {
        flutterFragment?.onDestroy()
        super.onDestroy()
    }
}