package dvachmovie.usecase.real

import dvachmovie.api.Cookie
import dvachmovie.api.CookieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetCookieUseCase @Inject constructor(
        private val cookieStorage: CookieStorage) : UseCase<Unit, Cookie>() {

    override fun execute(input: Unit): Cookie = cookieStorage.cookie
}
