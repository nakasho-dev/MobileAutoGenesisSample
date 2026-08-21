import SwiftUI

enum ItemDetailState: Equatable {
    case loading
    case success(Item)
    case error
}

@MainActor
final class ItemDetailViewModel: ObservableObject {
    @Published private(set) var state: ItemDetailState = .loading

    private var itemId: String?

    func load(id: String) async {
        itemId = id
        state = .loading
        if let item = await MockRepository.shared.getItem(id: id) {
            state = .success(item)
        } else {
            state = .error
        }
    }

    func toggleFavorite() async {
        guard let itemId else { return }
        await MockRepository.shared.toggleFavorite(id: itemId)
        if let item = await MockRepository.shared.getItem(id: itemId) {
            state = .success(item)
        } else {
            state = .error
        }
    }
}