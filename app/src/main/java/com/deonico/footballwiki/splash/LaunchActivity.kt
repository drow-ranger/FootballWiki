package com.deonico.footballwiki.splash

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import com.deonico.footballwiki.R
import com.deonico.footballwiki.MainActivity
import org.jetbrains.anko.*

class LaunchActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var splashDelay: Long = 5000

    private fun hideSystemUI(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            window.statusBarColor = Color.TRANSPARENT

            supportActionBar?.hide()
        }
    }

    internal val mRunnable: Runnable = Runnable {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI()
        LaunchUI().setContentView(this)

        mDelayHandler= Handler()

        mDelayHandler!!.postDelayed(mRunnable, splashDelay)
    }

    class LaunchUI: AnkoComponent<LaunchActivity>{
        override fun createView(ui: AnkoContext<LaunchActivity>) = with(ui){
            relativeLayout {
                lparams(matchParent, matchParent)
                fitsSystemWindows = true
                backgroundDrawable = context.getDrawable(R.drawable.homescreen)

                imageView {
                    setImageResource(R.drawable.ic_app)
                    animation = AnimationUtils.loadAnimation(context, R.anim.move_down)
                    startAnimation(animation)
                }.lparams {
                    width = dip(80)
                    height = dip(80)
                    alignParentTop()
                    centerHorizontally()
                    topMargin = dip(10)
                }

                textView {
                    textSize = sp(12).toFloat()
                    text = context.getString(R.string.app_name)
                    textColor = Color.parseColor("#ffdf50")
                    gravity = Gravity.CENTER_HORIZONTAL
                    animation = AnimationUtils.loadAnimation(context, R.anim.move_marquee)
                    startAnimation(animation)
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    alignParentTop()
                    centerHorizontally()
                    topMargin = dip(80)
                }

                verticalLayout {

                    textView {
                        textSize = sp(6).toFloat()
                        text = context.getString(R.string.txt_splash_screen)
                        textColor = Color.parseColor("#ffffff")
                        gravity = Gravity.CENTER_HORIZONTAL
                        animation = AnimationUtils.loadAnimation(context, R.anim.move_top)
                        startAnimation(animation)
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(20)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    alignParentBottom()
                    bottomMargin = dip(50)
                }

            }
        }
    }
}
