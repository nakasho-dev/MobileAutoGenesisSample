import Foundation

struct Item: Identifiable, Equatable {
    let id: String
    let title: String
    let description: String
    var isFavorite: Bool = false
}

// TODO(copilot): docs/BASIC_DESIGN.md 5節のモックAPI実装。Task.sleep のみでスタブ応答
actor MockRepository {
    static let shared = MockRepository()
    private var items: [Item] = [
        Item(id: "1", title: "First Item", description: "Description of the first item."),
        Item(id: "2", title: "Second Item", description: "Description of the second item."),
        Item(id: "3", title: "Third Item", description: "Description of the third item.")
    ]

    func login(email: String, password: String) async -> Bool {
        try? await Task.sleep(nanoseconds: 500_000_000)
        return email == "demo@example.com" && password == "password"
    }

    func listItems() async -> [Item] {
        try? await Task.sleep(nanoseconds: 300_000_000); return items
    }

    func getItem(id: String) async -> Item? {
        try? await Task.sleep(nanoseconds: 200_000_000); return items.first(where: { $0.id == id })
    }

    func toggleFavorite(id: String) async {
        try? await Task.sleep(nanoseconds: 100_000_000)
        if let idx = items.firstIndex(where: { $0.id == id }) { items[idx].isFavorite.toggle() }
    }
}
