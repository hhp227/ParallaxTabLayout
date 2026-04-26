import SwiftUI

struct RefreshableLazyColumn<Row: View>: View {
    let items: [String]
    let headerHeight: CGFloat
    let isRefreshEnabled: Bool
    let row: (String) -> Row

    @State private var isRefreshing = false

    var body: some View {
        content
            .refreshable {
                guard isRefreshEnabled else { return }
                isRefreshing = true
                try? await Task.sleep(nanoseconds: 1_000_000_000)
                isRefreshing = false
            }
    }

    private var content: some View {
        ScrollView {
            ScrollOffsetReader()
            LazyVStack(spacing: 0) {
                ForEach(items, id: \.self) { item in
                    row(item)
                }
            }
            .padding(.top, headerHeight)
        }
    }

    init(
        items: [String],
        headerHeight: CGFloat,
        isRefreshEnabled: Bool,
        @ViewBuilder row: @escaping (String) -> Row
    ) {
        self.items = items
        self.headerHeight = headerHeight
        self.isRefreshEnabled = isRefreshEnabled
        self.row = row
    }
}

struct ScrollOffsetReader: View {
    var body: some View {
        GeometryReader { proxy in
            Color.clear.preference(
                key: ScrollOffsetPreferenceKey.self,
                value: proxy.frame(in: .named("collapsing-scroll")).minY
            )
        }
        .frame(height: 0)
    }
}
