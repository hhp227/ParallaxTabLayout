import SwiftUI

struct ParallaxTabScreen: View {
    let title: String

    @State private var selectedTab = 0
    @State private var collapseOffset: CGFloat = 0
    @State private var firstTabScrollOffset: CGFloat = 0
    @State private var secondTabScrollOffset: CGFloat = 0

    private var isCollapsed: Bool {
        collapseOffset >= 163
    }

    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            CollapsingListScaffold(
                title: title,
                navigationIcon: .back,
                onNavigationClick: {},
                showTabs: true,
                selectedTab: $selectedTab,
                collapseOffset: $collapseOffset,
                onTabSelected: { selectedTab = $0 }
            ) { headerHeight, appBarState in
                ZStack {
                    FirstTabScreen(
                        headerHeight: headerHeight,
                        isRefreshEnabled: selectedTab == 0 && appBarState.isExpanded,
                        isActive: selectedTab == 0,
                        scrollOffset: $firstTabScrollOffset
                    )
                    .opacity(selectedTab == 0 ? 1 : 0)
                    .allowsHitTesting(selectedTab == 0)

                    SecondTabScreen(
                        headerHeight: headerHeight,
                        isActive: selectedTab == 1,
                        scrollOffset: $secondTabScrollOffset
                    )
                    .opacity(selectedTab == 1 ? 1 : 0)
                    .allowsHitTesting(selectedTab == 1)
                }
            }

            if selectedTab == 0 {
                FloatingActionButton()
                    .padding(16)
            }
        }
        .navigationTitle(title)
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarBackgroundColorCompat(isCollapsed ? UIColor(Color.accentColor) : .clear)
        .navigationBarTintColorCompat(isCollapsed ? .white : UIColor(Color.accentColor))
    }

}
