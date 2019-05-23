package dvachmovie.usecase.real

import dvachmovie.api.Cookie
import dvachmovie.api.CookieManager
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetCookieUseCase @Inject constructor(
        private val cookieManager: CookieManager) : UseCase<Unit, Cookie>() {

    override suspend fun execute(input: Unit): Cookie {
        return cookieManager.getCookie()
    }
}
