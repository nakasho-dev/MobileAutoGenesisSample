import SwiftUI

struct SplashView: View {
    var body: some View {
        VStack(spacing: 40) {
            Text(NSLocalizedString("splash_logo", comment: ""))
                .frame(width: 92, height: 92)
                .overlay {
                    Circle()
                        .stroke(Color.accentColor, lineWidth: 3)
                }
                .accessibilityIdentifier(TestIds.splash_Logo)

            Text(NSLocalizedString("splash_transition_hint", comment: ""))
                .foregroundStyle(.secondary)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .navigationTitle(NSLocalizedString("splash_title", comment: ""))
        .accessibilityIdentifier(TestIds.screenRoot_Splash)
    }
}