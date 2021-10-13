package bisq.client

import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test

class DesktopGreetingTest {

    private val os = System.getProperty("os.name")?.lowercase()

    @Test
    fun testWindows() {
        assumeTrue(os?.contains("win") == true)
        assertTrue("Check Windows is mentioned", Greeting().greeting().contains("Windows"))
    }

    @Test
    fun testLinux() {
        assumeTrue(os?.contains("nix") == true || os?.contains("nux") == true || os?.contains("aix") == true)
        assertTrue("Check Linux is mentioned", Greeting().greeting().contains("Linux"))
    }

    @Test
    fun testMacOs() {
        assumeTrue(os?.contains("mac") == true)
        assertTrue("Check macOS is mentioned", Greeting().greeting().contains("macOS"))
    }
}