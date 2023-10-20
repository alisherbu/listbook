package kaa.alisherbu.listbook.feature.signup.domain.usecase

import kaa.alisherbu.listbook.feature.signup.domain.model.SignupResult
import kaa.alisherbu.listbook.feature.signup.domain.repository.SignupRepository
import javax.inject.Inject

internal class SignUpUseCase @Inject constructor(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke(email: String, password: String): SignupResult {
        return try {
            val user = signupRepository.signUp(email, password)
            if (user != null) SignupResult.Success(user)
            else SignupResult.Error("Something went wrong")
        } catch (e: Exception) {
            SignupResult.Error(e.message.toString())
        }
    }
}