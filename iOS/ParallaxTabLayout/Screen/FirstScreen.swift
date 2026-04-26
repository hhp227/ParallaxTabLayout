import SwiftUI

struct FirstScreen: View {
    @Binding var selectedDestination: MainDestination
    let onOpenDetail: () -> Void

    @StateObject private var viewModel = FirstViewModel()
    @State private var collapseOffset: CGFloat = 0

    var body: some View {
        DrawerScaffold(selectedDestination: $selectedDestination) { openDrawer in
            ZStack(alignment: .bottomTrailing) {
                CollapsingListScaffold(
                    title: "FirstFragment",
                    navigationIcon: .menu,
                    onNavigationClick: openDrawer,
                    showTabs: false,
                    selectedTab: .constant(0),
                    collapseOffset: $collapseOffset,
                    onTabSelected: { _ in }
                ) { headerHeight, appBarState in
                    RefreshableLazyColumn(
                        items: viewModel.items,
                        headerHeight: headerHeight,
                        isRefreshEnabled: appBarState.isExpanded
                    ) { item in
                        Text(item)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.horizontal, 16)
                            .padding(.vertical, 18)
                            .background(Color(uiColor: .systemBackground))
                            .contentShape(Rectangle())
                            .onTapGesture { onOpenDetail() }
                        Divider()
                    }
                }

                FloatingActionButton()
                    .padding(16)
            }
        }
        .navigationBarBackButtonHidden(true)
        .toolbar(.hidden, for: .navigationBar)
    }
}
