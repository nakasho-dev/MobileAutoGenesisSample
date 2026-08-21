import SwiftUI

struct SettingsView: View {
    @Binding var isDarkTheme: Bool
    let onSignOut: () -> Void

    var body: some View {
        VStack(spacing: 32) {
            Toggle(
                NSLocalizedString("settings_theme", comment: ""),
                isOn: $isDarkTheme
            )
            .accessibilityIdentifier(TestIds.settings_ThemeToggle)

            Button {
                onSignOut()
            } label: {
                Text(NSLocalizedString("settings_sign_out", comment: ""))
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
            .accessibilityIdentifier(TestIds.settings_SignOutButton)

            Spacer()
        }
        .padding(.horizontal, 28)
        .padding(.top, 24)
        .navigationTitle(NSLocalizedString("settings_title", comment: ""))
        .accessibilityIdentifier(TestIds.screenRoot_Settings)
    }
}