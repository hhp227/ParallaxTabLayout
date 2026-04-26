import SwiftUI

struct DrawerScaffold<Content: View>: View {
    @Binding var selectedDestination: MainDestination
    let content: (_ openDrawer: @escaping () -> Void) -> Content

    @State private var isDrawerOpen = false

    var body: some View {
        ZStack(alignment: .leading) {
            content { withAnimation(.easeOut) { isDrawerOpen = true } }
                .disabled(isDrawerOpen)

            if isDrawerOpen {
                Color.black.opacity(0.32)
                    .ignoresSafeArea()
                    .onTapGesture { withAnimation(.easeOut) { isDrawerOpen = false } }

                VStack(alignment: .leading, spacing: 0) {
                    DrawerHeader()
                    DrawerItem(
                        title: "FirstFragment",
                        isSelected: selectedDestination == .first,
                        onClick: {
                            selectedDestination = .first
                            withAnimation(.easeOut) { isDrawerOpen = false }
                        }
                    )
                    DrawerItem(
                        title: "SecondFragment",
                        isSelected: selectedDestination == .second,
                        onClick: {
                            selectedDestination = .second
                            withAnimation(.easeOut) { isDrawerOpen = false }
                        }
                    )
                    Spacer()
                }
                .frame(width: 304)
                .background(Color(uiColor: .systemBackground))
                .transition(.move(edge: .leading))
            }
        }
    }
}

private struct DrawerHeader: View {
    var body: some View {
        ZStack {
            Color.gray
            Image(systemName: "person.crop.circle.fill")
                .resizable()
                .frame(width: 90, height: 90)
                .foregroundColorCompat(Color.white)

            VStack(alignment: .leading, spacing: 3) {
                Spacer()
                Text("Name")
                    .font(.title2)
                Text("E-mail")
                    .font(.caption)
                    .padding(.bottom, 7)
            }
            .foregroundColorCompat(Color.white)
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.horizontal, 16)
        }
        .frame(height: 230)
    }
}

private struct DrawerItem: View {
    let title: String
    let isSelected: Bool
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            HStack {
                Image(systemName: "app.fill")
                Text(title)
                Spacer()
            }
            .padding(.horizontal, 16)
            .frame(height: 48)
            .background(isSelected ? Color.accentColor.opacity(0.14) : Color.clear)
        }
        .buttonStyle(.plain)
    }
}
