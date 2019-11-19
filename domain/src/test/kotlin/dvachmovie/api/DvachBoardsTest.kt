package dvachmovie.api

import org.junit.Assert
import org.junit.Test

class DvachBoardsTest {
    @Test
    fun `Verify cookie model` (){
        val model = DvachBoards

        Assert.assertEquals(true, model.isBoardExists("b"))
        Assert.assertEquals(false, model.isBoardExists("zzz"))
    }
}
