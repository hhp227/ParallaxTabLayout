import SwiftUI

enum NavigationIcon {
    case menu
    case back
}

struct AppToolbar: View {
    let title: String
    let navigationIcon: NavigationIcon
    let onNavigationClick: () -> Void
    var transparent = false

    var body: some View {
        HStack(spacing: 0) {
            Button(action: buttonAction) {
                Image(systemName: navigationIcon == .menu ? "line.3.horizontal" : "chevron.left")
                    .font(.system(size: 20, weight: .semibold))
                    .frame(width: 56, height: 56)
                    .foregroundStyle(Color.white)
            }

            Text(title)
                .font(.title3.weight(.medium))
                .lineLimit(1)
                .foregroundStyle(Color.white)

            Spacer()
        }
        .frame(height: 56)
        .background(transparent ? Color.clear : Color.accentColor)
    }

    private func buttonAction() {
        onNavigationClick()
    }
}
