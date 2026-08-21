import SwiftUI

@main
struct SampleAppApp: App {
    @State private var destination = Destination.splash
    @State private var selectedItem: Item?
    @State private var isDarkTheme = false

    var body: some Scene {
        WindowGroup {
            NavigationStack {
                switch destination {
                case .splash:
                    SplashView()
                        .task {
                            try? await Task.sleep(nanoseconds: 2_000_000_000)
                            destination = .login
                        }
                case .login:
                    LoginView(onLoginSuccess: { destination = .itemList })
                case .itemList:
                    ItemListView(
                        onItemTap: { item in
                            selectedItem = item
                            destination = .itemDetail
                        },
                        onSettingsTap: { destination = .settings }
                    )
                case .itemDetail:
                    if let selectedItem {
                        ItemDetailView(
                            itemId: selectedItem.id,
                            onBack: { destination = .itemList }
                        )
                    } else {
                        Color.clear.task { destination = .itemList }
                    }
                case .settings:
                    SettingsView(
                        isDarkTheme: $isDarkTheme,
                        onSignOut: {
                            selectedItem = nil
                            destination = .login
                        }
                    )
                }
            }
            .preferredColorScheme(isDarkTheme ? .dark : .light)
        }
    }
}

private enum Destination {
    case splash
    case login
    case itemList
    case itemDetail
    case settings
}
