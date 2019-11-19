package dvachmovie.api

import org.junit.Assert
import org.junit.Test

class NeoChanBoardsTest {
    @Test
    fun `Verify cookie model` (){
        val model = NeoChanBoards

        Assert.assertEquals(true, model.isBoardExists("b"))
        Assert.assertEquals(false, model.isBoardExists("zzz"))
    }
}
