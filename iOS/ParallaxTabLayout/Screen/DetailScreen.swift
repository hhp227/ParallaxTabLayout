import SwiftUI

struct DetailScreen: View {
    @Environment(\.presentationMode) private var presentationMode

    var body: some View {
        VStack(spacing: 0) {
            AppToolbar(
                title: "Detail",
                navigationIcon: .back,
                onNavigationClick: { presentationMode.wrappedValue.dismiss() }
            )
            Text("Hello blank fragment")
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
        }
        .navigationBarBackButtonHidden(true)
        .navigationBarHiddenCompat()
    }
}
