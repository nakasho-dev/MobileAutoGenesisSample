import SwiftUI

struct ItemDetailView: View {
    let itemId: String
    let onBack: () -> Void

    @StateObject private var viewModel = ItemDetailViewModel()

    var body: some View {
        VStack(alignment: .leading, spacing: 24) {
            switch viewModel.state {
            case .loading:
                ProgressView()
                    .frame(maxWidth: .infinity)
            case .error:
                Text(NSLocalizedString("item_detail_error", comment: ""))
                    .foregroundStyle(.red)
            case .success(let item):
                Text(item.title)
                    .font(.headline)
                    .accessibilityIdentifier(TestIds.itemDetail_Title)

                Text(item.description)
                    .accessibilityIdentifier(TestIds.itemDetail_Description)

                Toggle(
                    NSLocalizedString("item_detail_favorite", comment: ""),
                    isOn: Binding(
                        get: { item.isFavorite },
                        set: { _ in Task { await viewModel.toggleFavorite() } }
                    )
                )
                .accessibilityIdentifier(TestIds.itemDetail_FavoriteToggle)
            }

            Button {
                onBack()
            } label: {
                Label(
                    NSLocalizedString("item_detail_back", comment: ""),
                    systemImage: "arrow.left"
                )
                .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
            .accessibilityIdentifier(TestIds.itemDetail_BackButton)

            Spacer()
        }
        .padding(.horizontal, 28)
        .padding(.top, 24)
        .navigationTitle(NSLocalizedString("item_detail_title", comment: ""))
        .accessibilityIdentifier(TestIds.screenRoot_ItemDetail)
        .task { await viewModel.load(id: itemId) }
    }
}