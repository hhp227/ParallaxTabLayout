import SwiftUI

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

    private let imageHeight: CGFloat = 256
    private let toolbarHeight: CGFloat = 56
    private let tabHeight: CGFloat = 48

    var body: some View {
        GeometryReader { proxy in
            let topInset = proxy.safeAreaInsets.top
            let expandedHeight = topInset + imageHeight
            let collapsedHeight = topInset + toolbarHeight + (showTabs ? tabHeight : 0)
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
                content(headerHeight, appBarState)
                    .coordinateSpace(name: "collapsing-scroll")
                    .onPreferenceChange(ScrollOffsetPreferenceKey.self) { offset in
                        collapseOffset = min(max(-offset, 0), maxCollapse)
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
                AppToolbar(
                    title: title,
                    navigationIcon: navigationIcon,
                    onNavigationClick: onNavigationClick,
                    transparent: true
                )
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
    static var defaultValue: CGFloat = 0

    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value = nextValue()
    }
}
