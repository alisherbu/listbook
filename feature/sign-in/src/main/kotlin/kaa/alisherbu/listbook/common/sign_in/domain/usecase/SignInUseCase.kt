package kaa.alisherbu.listbook.common.sign_in.domain.usecase

import kaa.alisherbu.listbook.common.sign_in.domain.model.SignInResult
import kaa.alisherbu.listbook.common.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    suspend operator fun invoke(email: String, password: String): SignInResult {
        return try {
            val user = signInRepository.signIn(email, password)
            if (user != null) SignInResult.Success(user)
            else SignInResult.Error("Something went wrong")
        } catch (e: Exception) {
            SignInResult.Error(e.message.toString())
        }
    }
}