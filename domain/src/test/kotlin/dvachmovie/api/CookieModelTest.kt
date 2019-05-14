package dvachmovie.api

import org.junit.Assert
import org.junit.Test

class CookieModelTest {

    @Test
    fun `Verify cookie model` (){
        val cookie = Cookie("var", "val")
        Assert.assertEquals(cookie.toString(), "${cookie.header}=${cookie.value}")
    }
}
