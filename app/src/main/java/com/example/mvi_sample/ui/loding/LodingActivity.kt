package com.example.mvi_sample.ui.loding

import android.os.Bundle
import com.example.mvi_sample.MainActivity
import com.example.mvi_sample.R
import com.example.mvi_sample.base.BaseActivity
import com.example.mvi_sample.ui.loding.status.LodingIntent
import com.example.mvi_sample.ui.login.LoginActivity
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LodingActivity : BaseActivity<LodingIntent,LodingViewState>() {

    override val layoutId = R.layout.activity_loding
    private val startGetServerInfo =
        PublishSubject.create<LodingIntent.getServerInfo>()

    @Inject
    lateinit var mViewModel: LodingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind()
        startCheckServer()
    }

    private fun startCheckServer(){
        Observable.just( LodingIntent.getServerInfo )
            .autoDisposable(scopeProvider)
            .subscribe(startGetServerInfo)
    }

    private fun bind(){
        mViewModel.states()
            .autoDisposable(scopeProvider)
            .subscribe(this::render)

        mViewModel.processIntents(intents())
    }


    override fun intents(): Observable<LodingIntent> = Observable.mergeArray<LodingIntent>(
        startGetServerInfo
    ).startWith(LodingIntent.InitialIntent)

    override fun render(states: LodingViewState) {
        when(states.uiEvents){
            is LodingViewState.LodingUiEvents.JumpMain -> {
                runBlocking {
                    delay(3000)
                }
                LoginActivity.launch(this)
                finish()
                return
            }
        }
    }
}