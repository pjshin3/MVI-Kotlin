package com.example.mvi_sample.ui.loding

import android.os.Bundle
import com.example.mvi_sample.MainActivity
import com.example.mvi_sample.R
import com.example.mvi_sample.base.BaseActivity
import com.jakewharton.rxbinding3.view.clicks
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_loding.*

class LodingActivity : BaseActivity<LodingIntent,LodingViewState>() {

    override val layoutId = R.layout.activity_loding

    private val startClickIntentPublisher =
        PublishSubject.create<LodingIntent.LodingClicksIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind()
    }
    private fun bind(){
        bt.clicks()
            .map {
                LodingIntent.LodingClicksIntent(
                    "hello",
                    "world"
                )
            }
            .autoDisposable(scopeProvider)
            .subscribe(startClickIntentPublisher)
    }


    override fun intents(): Observable<LodingIntent> = Observable.mergeArray<LodingIntent>(
        startClickIntentPublisher
    ).startWith(LodingIntent.InitialIntent)

    override fun render(states: LodingViewState) {
        when(states.uiEvents){
            is LodingViewState.LodingUiEvents.JumpMain -> {
                MainActivity.launch(this)

                return
            }
        }
    }
}