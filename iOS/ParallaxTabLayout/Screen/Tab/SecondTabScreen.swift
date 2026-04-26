import SwiftUI

struct SecondTabScreen: View {
    let headerHeight: CGFloat
    let isActive: Bool
    @Binding var scrollOffset: CGFloat

    @StateObject private var viewModel = SecondTabViewModel()

    var body: some View {
        ScrollView {
            ScrollOffsetReader(isEnabled: isActive)
            LazyVStack(spacing: 0) {
                Color.clear
                    .frame(height: headerHeight)
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
            if isActive {
                scrollOffset = $0
            }
        }
    }
}
