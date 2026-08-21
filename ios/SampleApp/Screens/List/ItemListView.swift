import SwiftUI

struct ItemListView: View {
    let onItemTap: (Item) -> Void
    let onSettingsTap: () -> Void

    @StateObject private var viewModel = ItemListViewModel()

    var body: some View {
        VStack(alignment: .leading, spacing: 14) {
            Button {
                Task { await viewModel.refresh() }
            } label: {
                Text(NSLocalizedString("item_list_refresh", comment: ""))
            }
            .disabled(viewModel.state == .loading)
            .accessibilityIdentifier(TestIds.itemList_PullToRefresh)

            switch viewModel.state {
            case .loading:
                ProgressView()
                    .frame(maxWidth: .infinity)
            case .empty:
                Text(NSLocalizedString("item_list_empty", comment: ""))
            case .error:
                Text(NSLocalizedString("item_list_error", comment: ""))
                    .foregroundStyle(.red)
            case .success(let items):
                ForEach(Array(items.enumerated()), id: \.element.id) { index, item in
                    Button {
                        onItemTap(item)
                    } label: {
                        HStack {
                            Text(item.title)
                            Spacer()
                            Image(systemName: "chevron.right")
                        }
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background {
                            RoundedRectangle(cornerRadius: 8)
                                .stroke(Color.secondary.opacity(0.5))
                        }
                    }
                    .buttonStyle(.plain)
                    .accessibilityIdentifier(TestIds.itemList_Row(index))
                }
            }

            Button {
                onSettingsTap()
            } label: {
                Label(
                    NSLocalizedString("item_list_settings", comment: ""),
                    systemImage: "gearshape"
                )
                .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
            .accessibilityIdentifier(TestIds.itemList_SettingsButton)

            Spacer()
        }
        .padding(.horizontal, 28)
        .padding(.top, 24)
        .navigationTitle(NSLocalizedString("item_list_title", comment: ""))
        .accessibilityIdentifier(TestIds.screenRoot_ItemList)
        .task { await viewModel.refresh() }
    }
}