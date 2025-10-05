package com.example.knowyourplants.ui.account

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.knowyourplants.DimenUtils
import com.example.knowyourplants.R


class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val solidCircle: CardView = view.findViewById(R.id.circle)
        val dimensions = DimenUtils.getScreenDimensions(activity)
        val statusBarHeight = DimenUtils.getStatusBarHeight(activity)
        val dotView: DotView = view.findViewById(R.id.dotView)
        val squareView: SquareView = view.findViewById(R.id.squareView)
        setCentreCircle(dotView, dimensions, statusBarHeight)
        createTranslationEdgeAnimation(solidCircle, dimensions, statusBarHeight)

        createCircularMotionAnimation(squareView, dimensions, statusBarHeight)
    }

    private fun setCentreCircle(
        dotView: DotView,
        dimensions: Array<Int>,
        statusBarHeight: Int
    ) {
        val screenHeight = dimensions[0].toFloat()
        val screenWidth = dimensions[1].toFloat()
        // center of the fragment screen
        val centerX = screenWidth / 2f
        val centerY = (screenHeight - DimenUtils.dpToPx(72f, requireContext()) - statusBarHeight) / 2f

        dotView.setDotPosition(centerX, centerY, 20f)
    }

    private fun createTranslationEdgeAnimation(view: View, dimensions: Array<Int>, statusBarHeight: Int){
        val bottomAnim = ObjectAnimator.ofFloat(view, "translationX", dimensions[1].toFloat()-200).apply {
            duration = 2000
        }
        val endAnim = ObjectAnimator.ofFloat(view, "translationY", -(dimensions[0].toFloat()-200 - DimenUtils.dpToPx(72F + 8F, requireContext()) - statusBarHeight)).apply {
            duration = 3500
        } // 72 dp for main padding bottom, 8dp for margin
        val topAnim = ObjectAnimator.ofFloat(view, "translationX", (dimensions[1].toFloat()-200),  0F).apply {
            duration = 2000
        }
        val startAnim = ObjectAnimator.ofFloat(view, "translationY", -(dimensions[0].toFloat()-200 - DimenUtils.dpToPx(72F + 8F, requireContext()) - statusBarHeight), 0F).apply {
            duration = 3500
        }
        val set = AnimatorSet().apply {
            play(endAnim).after(bottomAnim)
            play(topAnim).after(endAnim)
            play(startAnim).after(topAnim)
            start()
        }
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                set.start() // loop again
            }
        })
    }

    private fun createCircularMotionAnimation(
        view: View,
        dimensions: Array<Int>,
        statusBarHeight: Int
    ){
        val screenHeight = dimensions[0].toFloat()
        val screenWidth = dimensions[1].toFloat()

        // the center of the circle's orbit
        val centerX = screenWidth / 2f
        val centerY = (screenHeight - DimenUtils.dpToPx(72f, requireContext()) - statusBarHeight) / 2f

        // define orbit radius (margin so it stays visible)
        val radius = Math.min(screenWidth, screenHeight) / 2f - 200f

        // circular path (ANTI-CLOCKWISE)
        val path = Path().apply {
            addCircle(centerX-75F, centerY-75F, radius, Path.Direction.CCW)
        }

        // create animator following the circular path
        ObjectAnimator.ofFloat(view, View.X, View.Y, path).apply {
            duration = 10000L          // full circle duration
            interpolator = LinearInterpolator()  // constant speed
            repeatCount = ValueAnimator.INFINITE // loop forever
            start()
        }
    }
}