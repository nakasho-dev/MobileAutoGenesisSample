import SwiftUI

// TODO(copilot): NavigationStack で Splash -> Login -> ItemList -> ItemDetail / Settings を組む
@main
struct SampleAppApp: App {
    var body: some Scene {
        WindowGroup {
            Text("SampleApp").accessibilityIdentifier(TestIds.screenRoot_Splash)
        }
    }
}
