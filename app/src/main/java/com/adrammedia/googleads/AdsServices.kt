package com.adrammedia.googleads

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.util.Calendar

class AdsServices( ) {
    companion object {

        //Banner Add
        fun loadBanner(container:LinearLayout, context: Context){
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
            container.addView(adView)

            // Create an ad request.
            val adRequest = AdRequest.Builder().build()

            // Start loading the ad in the background.
            adView.loadAd(adRequest)
        }

        //Interstitial Add

        private var mInterstitialAd: InterstitialAd? = null
        fun loadInterstitialAd(context: Context) {
            val adRequest = AdRequest.Builder().build()

            InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    loadInterstitialAd(context)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
        }

        fun showInterstitialAd(context: Context){
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(context as Activity)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }


        //Rewarded ads

        private var rewardedAd: RewardedAd? = null
        private val adRequest = AdRequest.Builder().build()
        fun getReward(context: Context){
            RewardedAd.load(context,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let {
                        Log.d("TAG", " failLoad $it") }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("TAG", "Ad was loaded.")
                    rewardedAd = ad
                }
            })
        }

        @SuppressLint("SetTextI18n")
        private var userPoints = 0
        fun showRewardedAdd(context: Context, texView:TextView){

            rewardedAd?.let { ad ->
                ad.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    val rewardAmount = 5
                   val rewardType = " Points"
                    userPoints += rewardAmount
                    texView.text = "$userPoints $rewardType"
                    Log.d("TAG", "User earned the reward.")
                })
            } ?: run {
                Log.d("TAG", "The rewarded ad wasn't ready yet.")
            }
        }

        /**
         * Function for giving 5 points every 24 hours


        // SharedPreferences file name
        private const val PREFS_FILENAME = "MyPrefs"
        // Key for storing the last time points were awarded
        private const val LAST_AWARDED_TIME_KEY = "last_awarded_time"
        // Key for storing the user's total points
        private const val USER_POINTS_KEY = "user_points"

        fun showRewardedAdd(context: Context, textView: TextView) {
            val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            val lastAwardedTime = prefs.getLong(LAST_AWARDED_TIME_KEY, 0)
            val currentTime = Calendar.getInstance().timeInMillis

            // Calculate the time difference in milliseconds
            val timeDifference = currentTime - lastAwardedTime

            if (timeDifference >= 24 * 60 * 60 * 1000) { // If 24 hours have passed since last award
                rewardedAd?.let { ad ->
                    ad.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                        // Handle the reward.
                        val rewardAmount = 5
                        val rewardType = "Points"

                        // Update user points
                        val userPoints = prefs.getInt(USER_POINTS_KEY, 0) + rewardAmount
                        prefs.edit().putInt(USER_POINTS_KEY, userPoints).apply()

                        // Update last awarded time
                        prefs.edit().putLong(LAST_AWARDED_TIME_KEY, currentTime).apply()

                        textView.text = "$userPoints $rewardType"
                        Log.d("TAG", "User earned the reward.")
                    })
                } ?: run {
                    Log.d("TAG", "The rewarded ad wasn't ready yet.")
                }
            } else {
                // Notify the user that they have to wait until 24 hours have passed
                Log.d("TAG", "You have to wait until 24 hours have passed since the last award.")
            }
        }
         */
    }


}