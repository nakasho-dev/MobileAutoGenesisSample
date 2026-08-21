import SwiftUI

enum LoginState: Equatable {
    case idle
    case loading
    case success
    case error
}

@MainActor
final class LoginViewModel: ObservableObject {
    @Published private(set) var state: LoginState = .idle

    func login(email: String, password: String) {
        guard state != .loading else { return }

        state = .loading
        Task {
            let succeeded = await MockRepository.shared.login(email: email, password: password)
            state = succeeded ? .success : .error
        }
    }
}