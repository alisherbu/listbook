package kaa.alisherbu.listbook.common.signup.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.signup.domain.usecase.SignUpUseCase

internal class SignupStoreProvider(
    private val storeFactory: StoreFactory,
    private val executor: SignupExecutor = SignupExecutor(SignUpUseCase())
) {

    fun provide(): SignupStore =
        object :
            SignupStore,
            Store<Intent, SignupState, Label> by storeFactory.create(
                name = "SignupStore",
                executorFactory = { executor },
                reducer = SignupReducer(),
                initialState = SignupState()
            ) {}
}
