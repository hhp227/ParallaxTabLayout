import SwiftUI

struct FirstTabScreen: View {
    let headerHeight: CGFloat
    let isRefreshEnabled: Bool
    let isActive: Bool
    @Binding var scrollOffset: CGFloat

    @StateObject private var viewModel = FirstTabViewModel()

    var body: some View {
        RefreshableLazyColumn(
            items: viewModel.items,
            headerHeight: headerHeight,
            isRefreshEnabled: isRefreshEnabled,
            isScrollTrackingEnabled: isActive
        ) { item in
            Text(item)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal, 16)
                .padding(.vertical, 18)
                .background(Color(uiColor: .systemBackground))
            Divider()
        }
        .onPreferenceChange(ScrollOffsetPreferenceKey.self) {
            if isActive && !$0.isNaN {
                scrollOffset = $0
            }
        }
    }
}
