import SwiftUI

enum ItemListState: Equatable {
    case loading
    case empty
    case success([Item])
    case error
}

@MainActor
final class ItemListViewModel: ObservableObject {
    @Published private(set) var state: ItemListState = .loading

    func refresh() async {
        state = .loading
        let items = await MockRepository.shared.listItems()
        state = items.isEmpty ? .empty : .success(items)
    }
}