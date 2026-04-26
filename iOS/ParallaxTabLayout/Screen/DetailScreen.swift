import SwiftUI

struct DetailScreen: View {
    @Environment(\.dismiss) private var dismiss

    var body: some View {
        VStack(spacing: 0) {
            AppToolbar(
                title: "Detail",
                navigationIcon: .back,
                onNavigationClick: { dismiss() }
            )
            Text("Hello blank fragment")
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
        }
        .navigationBarBackButtonHidden(true)
        .toolbar(.hidden, for: .navigationBar)
    }
}
