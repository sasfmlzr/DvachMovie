package dvachmovie.api

import org.junit.Assert
import org.junit.Test

class FourChanBoardsTest {
    @Test
    fun `Verify cookie model` (){
        val model = FourChanBoards

        Assert.assertEquals(true, model.isBoardExists("b"))
        Assert.assertEquals(false, model.isBoardExists("zzz"))
    }
}
