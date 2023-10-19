package kaa.alisherbu.listbook.common.sign_in.domain.usecase

import kaa.alisherbu.listbook.common.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class SignInUseCase constructor(
    private val signInRepository: SignInRepository
) {
    operator fun invoke(email: String, password: String) {

    }
}