import SwiftUI

let collapsingScrollCoordinateSpace = "collapsing-scroll"

struct CollapsingAppBarState {
    let isExpanded: Bool
    let setExpanded: (Bool) -> Void
}

struct CollapsingListScaffold<Content: View>: View {
    let title: String
    let navigationIcon: NavigationIcon
    let onNavigationClick: () -> Void
    let showTabs: Bool
    @Binding var selectedTab: Int
    @Binding var collapseOffset: CGFloat
    let onTabSelected: (Int) -> Void
    let content: (_ headerHeight: CGFloat, _ appBarState: CollapsingAppBarState) -> Content

    @State private var ignoresNextExpansion = false
    @State private var isDragging = false

    private let imageHeight: CGFloat = 256
    private let toolbarHeight: CGFloat = 56
    private let tabHeight: CGFloat = 48

    var body: some View {
        GeometryReader { proxy in
            let topInset = proxy.safeAreaInsets.top
            let collapsedToolbarHeight = navigationIcon == .back ? 0 : toolbarHeight
            let expandedHeight = topInset + imageHeight
            let collapsedHeight = topInset + collapsedToolbarHeight + (showTabs ? tabHeight : 0)
            let maxCollapse = max(0, expandedHeight - collapsedHeight)
            let currentOffset = min(max(collapseOffset, 0), maxCollapse)
            let headerHeight = expandedHeight - currentOffset
            let fraction = maxCollapse == 0 ? 1 : currentOffset / maxCollapse
            let appBarState = CollapsingAppBarState(
                isExpanded: currentOffset <= 0.5,
                setExpanded: { expanded in
                    collapseOffset = expanded ? 0 : maxCollapse
                }
            )

            ZStack(alignment: .top) {
                content(expandedHeight, appBarState)
                    .onPreferenceChange(ScrollOffsetPreferenceKey.self) { contentOffset in
                        guard !contentOffset.isNaN else { return }

                        let scrolledOffset = max(-contentOffset, 0)
                        let nextCollapseOffset = min(scrolledOffset, maxCollapse)
                        if ignoresNextExpansion && nextCollapseOffset < collapseOffset {
                            ignoresNextExpansion = false
                            return
                        }
                        if nextCollapseOffset < collapseOffset && !isDragging {
                            return
                        }

                        ignoresNextExpansion = false
                        collapseOffset = nextCollapseOffset
                    }

                HeaderView(
                    title: title,
                    navigationIcon: navigationIcon,
                    onNavigationClick: onNavigationClick,
                    showTabs: showTabs,
                    selectedTab: $selectedTab,
                    onTabSelected: onTabSelected,
                    headerHeight: headerHeight,
                    imageOpacity: 1 - fraction,
                    topInset: topInset
                )
            }
            .ignoresSafeArea(edges: .top)
            .onChange(of: selectedTab) { _ in
                ignoresNextExpansion = true
            }
            .simultaneousGesture(
                DragGesture(minimumDistance: 0)
                    .onChanged { _ in isDragging = true }
                    .onEnded { _ in isDragging = false }
            )
        }
    }
}

private struct HeaderView: View {
    let title: String
    let navigationIcon: NavigationIcon
    let onNavigationClick: () -> Void
    let showTabs: Bool
    @Binding var selectedTab: Int
    let onTabSelected: (Int) -> Void
    let headerHeight: CGFloat
    let imageOpacity: CGFloat
    let topInset: CGFloat

    var body: some View {
        ZStack(alignment: .top) {
            Color.accentColor
            HeaderBackground()
                .opacity(imageOpacity)

            VStack(spacing: 0) {
                Color.clear.frame(height: topInset)
                if navigationIcon == .menu {
                    AppToolbar(
                        title: title,
                        navigationIcon: navigationIcon,
                        onNavigationClick: onNavigationClick,
                        transparent: true
                    )
                }
                Spacer(minLength: 0)
                if showTabs {
                    HStack(spacing: 0) {
                        TabButton(title: "First", isSelected: selectedTab == 0) {
                            selectedTab = 0
                            onTabSelected(0)
                        }
                        TabButton(title: "Second", isSelected: selectedTab == 1) {
                            selectedTab = 1
                            onTabSelected(1)
                        }
                    }
                    .frame(height: 48)
                }
            }
        }
        .frame(height: headerHeight)
        .clipped()
    }
}

private struct HeaderBackground: View {
    var body: some View {
        LinearGradient(
            colors: [Color.blue.opacity(0.65), Color.green.opacity(0.35), Color.orange.opacity(0.45)],
            startPoint: .topLeading,
            endPoint: .bottomTrailing
        )
    }
}

private struct TabButton: View {
    let title: String
    let isSelected: Bool
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            VStack(spacing: 0) {
                Spacer()
                Text(title)
                    .font(.subheadline.weight(.medium))
                    .foregroundColorCompat(isSelected ? Color.white : Color.white.opacity(0.82))
                Spacer()
                Rectangle()
                    .fill(isSelected ? Color.black.opacity(0.7) : Color.clear)
                    .frame(height: 2)
            }
        }
        .buttonStyle(.plain)
        .frame(maxWidth: .infinity)
    }
}

struct ScrollOffsetPreferenceKey: PreferenceKey {
    static var defaultValue: CGFloat = .nan

    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        let next = nextValue()
        if !next.isNaN {
            value = next
        }
    }
}

struct CollapsingHeaderSpacer: View {
    var isScrollTrackingEnabled = true

    var body: some View {
        Group {
            if isScrollTrackingEnabled {
                Color.clear
                    .frame(height: 0)
                    .background(
                        GeometryReader { proxy in
                            Color.clear.preference(
                                key: ScrollOffsetPreferenceKey.self,
                                value: proxy.frame(in: .named(collapsingScrollCoordinateSpace)).minY
                            )
                        }
                    )
            } else {
                Color.clear
                    .frame(height: 0)
            }
        }
    }
}
