import SwiftUI

extension View {
    @ViewBuilder
    func navigationBarHiddenCompat() -> some View {
        if #available(iOS 16.0, *) {
            toolbar(.hidden, for: .navigationBar)
        } else {
            navigationBarHidden(true)
        }
    }

    func foregroundColorCompat(_ color: Color) -> some View {
        foregroundColor(color)
    }
}
