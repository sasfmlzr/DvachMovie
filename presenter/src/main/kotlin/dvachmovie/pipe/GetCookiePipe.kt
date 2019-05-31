package dvachmovie.pipe

import dvachmovie.api.Cookie
import dvachmovie.usecase.real.GetCookieUseCase
import javax.inject.Inject

class GetCookiePipe @Inject constructor(
        private val useCase: GetCookieUseCase) : PipeSync<Unit, Cookie>() {

    override fun execute(input: Unit): Cookie = useCase.execute(Unit)

}
