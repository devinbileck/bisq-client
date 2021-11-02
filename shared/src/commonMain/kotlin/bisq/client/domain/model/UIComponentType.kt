package bisq.client.domain.model

sealed class UIComponentType {
    object Dialog: UIComponentType()
    object None: UIComponentType()
}
