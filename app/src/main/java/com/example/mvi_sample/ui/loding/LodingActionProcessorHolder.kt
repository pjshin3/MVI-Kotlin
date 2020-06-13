package com.example.mvi_sample.ui.loding

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class LodingActionProcessorHolder (
    private val repository : LodingDataSourceRepository
) {

//    private val initialUiActionTransformer =
//        ObservableTransformer<LodingAction.InitialUiAction, LodingResult> {action ->
//            action.flatMap {
//
//            }
//        }
//
//    internal val actionProcessor =
//        ObservableTransformer<LodingAction,LodingResult> {action ->
//            action.publish { shared ->
//                Observable.merge{
//                    shared.ofType(LodingAction.InitialUiAction::class.java).compose()
//                }
//            }
//        }
}