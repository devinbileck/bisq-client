package bisq.client

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}