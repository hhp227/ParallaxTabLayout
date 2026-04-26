import SwiftUI

struct SecondScreen: View {
    @Binding var selectedDestination: MainDestination
    let onOpenParallaxTab: (String) -> Void

    @StateObject private var viewModel = SecondViewModel()

    private let columns = [
        GridItem(.flexible(), spacing: 0),
        GridItem(.flexible(), spacing: 0)
    ]

    var body: some View {
        DrawerScaffold(selectedDestination: $selectedDestination) { openDrawer in
            VStack(spacing: 0) {
                AppToolbar(
                    title: "SecondFragment",
                    navigationIcon: .menu,
                    onNavigationClick: openDrawer
                )
                BottomGroupNavigation()
                ScrollView {
                    LazyVGrid(columns: columns, spacing: 0) {
                        ForEach(viewModel.items, id: \.self) { item in
                            GroupGridItem(item: item) {
                                onOpenParallaxTab(item)
                            }
                        }
                    }
                    .padding(.leading, 14)
                    .padding(.trailing, 12)
                    .padding(.top, 8)
                    .padding(.bottom, 16)
                }
            }
        }
        .navigationBarBackButtonHidden(true)
        .navigationBarHiddenCompat()
    }
}

private struct BottomGroupNavigation: View {
    var body: some View {
        HStack(spacing: 0) {
            ForEach(["그룹찾기", "가입신청중 그룹", "그룹만들기"], id: \.self) { title in
                VStack(spacing: 4) {
                    Image(systemName: "app.fill")
                        .font(.system(size: 32))
                    Text(title)
                        .font(.caption)
                        .lineLimit(1)
                }
                .frame(maxWidth: .infinity, minHeight: 80)
            }
        }
        .background(Color(uiColor: .systemBackground))
    }
}

private struct GroupGridItem: View {
    let item: String
    let onClick: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            RoundedRectangle(cornerRadius: 8)
                .fill(
                    LinearGradient(
                        colors: [Color.accentColor.opacity(0.35), Color.gray.opacity(0.25)],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
                .aspectRatio(16 / 9, contentMode: .fit)
                .shadow(radius: 3)

            Text(item)
                .font(.system(size: 15))
                .foregroundColorCompat(Color(uiColor: .darkGray))
                .padding(.top, 10)
                .padding(.horizontal, 10)
                .frame(height: 65, alignment: .topLeading)
        }
        .padding(.horizontal, 6)
        .padding(.bottom, 8)
        .contentShape(Rectangle())
        .onTapGesture(perform: onClick)
    }
}
