import SwiftUI

struct SecondTabScreen: View {
    let headerHeight: CGFloat
    let isActive: Bool
    @Binding var scrollOffset: CGFloat

    @StateObject private var viewModel = SecondTabViewModel()

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                CollapsingHeaderSpacer(
                    height: headerHeight,
                    isScrollTrackingEnabled: isActive
                )
                ForEach(viewModel.items, id: \.self) { item in
                    Text(item)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 18)
                        .background(Color(uiColor: .systemBackground))
                    Divider()
                }
            }
        }
        .coordinateSpace(name: collapsingScrollCoordinateSpace)
        .onPreferenceChange(ScrollOffsetPreferenceKey.self) {
            if isActive && !$0.isNaN {
                scrollOffset = $0
            }
        }
    }
}
