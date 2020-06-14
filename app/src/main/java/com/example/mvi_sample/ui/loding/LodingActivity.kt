package com.example.mvi_sample.ui.loding

import android.os.Bundle
import com.example.mvi_sample.MainActivity
import com.example.mvi_sample.R
import com.example.mvi_sample.base.BaseActivity
import com.example.mvi_sample.ui.loding.state.LodingIntent
import com.jakewharton.rxbinding3.view.clicks
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_loding.*
import javax.inject.Inject

class LodingActivity : BaseActivity<LodingIntent,LodingViewState>() {

    override val layoutId = R.layout.activity_loding

    private val startClickIntentPublisher =
        PublishSubject.create<LodingIntent.Start>()

    @Inject
    lateinit var mViewModel: LodingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind()
    }
    private fun bind(){
        bt.clicks()
            .map { LodingIntent.Start }
            .autoDisposable(scopeProvider)
            .subscribe(startClickIntentPublisher)

        mViewModel.states()
            .autoDisposable(scopeProvider)
            .subscribe(this::render)

        mViewModel.processIntents(intents())
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