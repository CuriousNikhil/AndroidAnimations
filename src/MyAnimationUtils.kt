package me.nikhya.animationutils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.*

class MyAnimationUtils {

    //    public static final long DEFAULT_ANIM_DURATION = 300;

    companion object {

        val DEFAULT_ANIM_DURATION: Long = 300
        val DEFAULT_ANIM_DURATION_LONG: Long = 400

        fun zoomIn(view: View, pivotX: Float, pivotY: Float): Animation {
            return zoomIn(view, pivotX, pivotY, 0f, 1f)
        }

        fun zoomIn(view: View, pivotX: Float, pivotY: Float, startScale: Float, endScale: Float): Animation {
            val animation = ScaleAnimation(startScale, endScale, startScale, endScale, pivotX, pivotY)
            animation.duration = DEFAULT_ANIM_DURATION_LONG
            animation.interpolator = DecelerateInterpolator()
            view.startAnimation(animation)
            return animation
        }

        fun zoomOut(view: View, pivotX: Float, pivotY: Float): Animation {
            return zoomOut(view, pivotX, pivotY, 1f, 0f)
        }

        fun zoomOut(view: View, pivotX: Float, pivotY: Float, startScale: Float, endScale: Float): Animation {
            val animation = ScaleAnimation(1f, 0f, 1f, 0f, pivotX, pivotY)
            animation.duration = DEFAULT_ANIM_DURATION_LONG
            animation.interpolator = AccelerateInterpolator()
            view.startAnimation(animation)
            return animation
        }

        fun fadeIn(view: View): Animation {
            return fadeIn(view, DEFAULT_ANIM_DURATION)
        }

        fun fadeIn(view: View, duration: Long): Animation {
            val animation = AlphaAnimation(0f, 1f)
            animation.duration = duration
            animation.interpolator = DecelerateInterpolator()
            view.startAnimation(animation)
            return animation
        }

        fun fadeOut(view: View): Animation {
            return fadeOut(view, DEFAULT_ANIM_DURATION)
        }

        fun fadeOut(view: View, duration: Long): Animation {
            val animation = AlphaAnimation(1f, 0f)
            animation.duration = duration
            animation.interpolator = AccelerateInterpolator()
            view.startAnimation(animation)
            return animation
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun circularRevealIn(view: View, pivotX: Float, pivotY: Float, maxRadius: Int): Animator {
            val anim =
                ViewAnimationUtils.createCircularReveal(view, pivotX.toInt(), pivotY.toInt(), 0f, maxRadius.toFloat())
            anim.duration = DEFAULT_ANIM_DURATION_LONG
            anim.interpolator = AccelerateInterpolator()
            anim.start()
            return anim
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun circularRevealOut(view: View, pivotX: Float, pivotY: Float, maxRadius: Int): Animator {
            val anim =
                ViewAnimationUtils.createCircularReveal(view, pivotX.toInt(), pivotY.toInt(), maxRadius.toFloat(), 0f)
            anim.duration = DEFAULT_ANIM_DURATION_LONG
            anim.interpolator = DecelerateInterpolator()
            anim.start()
            return anim
        }

        fun zoomAndFadeOut(view: View, pivotX: Float, pivotY: Float) {
            val anim = AnimationSet(true)
            anim.addAnimation(AlphaAnimation(1f, 0f))
            anim.addAnimation(ScaleAnimation(1f, 0f, 1f, 0f, pivotX, pivotY))
            anim.duration = DEFAULT_ANIM_DURATION_LONG
            anim.interpolator = AccelerateInterpolator()
            view.startAnimation(anim)

        }

        fun waveViews(views: View, views1: Array<View>, goUp: Boolean) {
            val goDown = android.view.animation.AnimationUtils.loadAnimation(
                views.context,
                if (goUp) R.anim.slide_in_from_bottom else R.anim.slide_out_to_bottom
            )
            goDown.startOffset = (if (goUp) 0 else 70 * (views1.size + 1)).toLong()
            views.animation = goDown
            views.visibility = if (goUp) View.VISIBLE else View.INVISIBLE
            for (i in views1.indices) {
                val goDown1 = android.view.animation.AnimationUtils.loadAnimation(
                    views.context,
                    if (goUp) R.anim.slide_in_from_bottom else R.anim.slide_out_to_bottom
                )
                goDown1.startOffset = (70 * i).toLong()
                views1[if (goUp) i else views1.size - 1 - i].animation = goDown1
                views1[if (goUp) i else views1.size - 1 - i].visibility = if (goUp) View.VISIBLE else View.INVISIBLE
            }
        }

        fun bounce(ctx: Context, v: View) {
            val bounceAnim = android.view.animation.AnimationUtils.loadAnimation(ctx, R.anim.card_bounce)
            val animationDuration = 800.0
            bounceAnim.duration = animationDuration.toLong()
            bounceAnim.interpolator = BounceInterpolator()
            v.startAnimation(bounceAnim)
        }

        fun hugeBounce(ctx: Context, v: View, listener: Animation.AnimationListener) {
            val bounceAnim = android.view.animation.AnimationUtils.loadAnimation(ctx, R.anim.long_bounce)
            val animationDuration = 300.0
            bounceAnim.duration = animationDuration.toLong()
            val interpolator = BounceInterpolator()
            bounceAnim.interpolator = interpolator
            bounceAnim.setAnimationListener(listener)
            v.startAnimation(bounceAnim)
        }

        class BounceInterpolator : android.view.animation.Interpolator {

            internal var mAmplitude = 0.67   //default yo
            internal var mFrequency = 7.0

            fun setFreq(mFrequency: Double) {
                this.mFrequency = mFrequency
            }

            override fun getInterpolation(time: Float): Float {
                return (-1.0 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1).toFloat()
            }
        }

        fun scaleX(view: View, scaleFactor: Float) {
            val anim = ObjectAnimator.ofFloat(view, "scaleY", scaleFactor)
            anim.duration = 100
            anim.start()
        }

        fun scaleY(view: View, scaleFactor: Float) {
            val anim = ObjectAnimator.ofFloat(view, "scaleY", scaleFactor)
            anim.duration = 100
            anim.start()
        }

    }

}