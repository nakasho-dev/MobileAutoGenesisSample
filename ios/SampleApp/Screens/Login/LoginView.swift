import SwiftUI

struct LoginView: View {
    let onLoginSuccess: () -> Void

    @StateObject private var viewModel = LoginViewModel()
    @State private var email = ""
    @State private var password = ""

    private var isLoading: Bool { viewModel.state == .loading }

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(NSLocalizedString("login_welcome", comment: ""))

            TextField(NSLocalizedString("login_email_hint", comment: ""), text: $email)
                .textFieldStyle(.roundedBorder)
                .textInputAutocapitalization(.never)
                .keyboardType(.emailAddress)
                .disabled(isLoading)
                .accessibilityIdentifier(TestIds.login_EmailField)

            SecureField(NSLocalizedString("login_password_hint", comment: ""), text: $password)
                .textFieldStyle(.roundedBorder)
                .disabled(isLoading)
                .accessibilityIdentifier(TestIds.login_PasswordField)

            Button {
                viewModel.login(email: email, password: password)
            } label: {
                Group {
                    if isLoading {
                        ProgressView()
                    } else {
                        Text(NSLocalizedString("login_submit", comment: ""))
                    }
                }
                .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
            .disabled(isLoading)
            .accessibilityIdentifier(TestIds.login_SubmitButton)

            if viewModel.state == .error {
                Text(NSLocalizedString("login_error", comment: ""))
                    .foregroundStyle(.red)
                    .accessibilityIdentifier(TestIds.login_ErrorText)
            }

            Spacer()
        }
        .padding(.horizontal, 28)
        .padding(.top, 24)
        .navigationTitle(NSLocalizedString("login_title", comment: ""))
        .accessibilityIdentifier(TestIds.screenRoot_Login)
        .onChange(of: viewModel.state) { state in
            if state == .success { onLoginSuccess() }
        }
    }
}