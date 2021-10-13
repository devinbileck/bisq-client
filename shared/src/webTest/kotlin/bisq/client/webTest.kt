package bisq.client

import org.junit.Assert.assertTrue
import org.junit.Test

class WebGreetingTest {

    @Test
    fun testExample() {
        assertTrue("Check Web is mentioned", Greeting().greeting().contains("Web"))
    }
}
