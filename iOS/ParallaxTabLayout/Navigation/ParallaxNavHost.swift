import SwiftUI

enum MainDestination {
    case first
    case second
}

struct ParallaxNavHost: View {
    @State private var selectedDestination: MainDestination = .first
    @State private var isDetailActive = false
    @State private var parallaxTabTitle: String?

    var body: some View {
        NavigationView {
            ZStack {
                FirstScreen(
                    selectedDestination: $selectedDestination,
                    onOpenDetail: { isDetailActive = true }
                )
                .opacity(selectedDestination == .first ? 1 : 0)
                .allowsHitTesting(selectedDestination == .first)

                SecondScreen(
                    selectedDestination: $selectedDestination,
                    onOpenParallaxTab: { parallaxTabTitle = $0 }
                )
                .opacity(selectedDestination == .second ? 1 : 0)
                .allowsHitTesting(selectedDestination == .second)

                NavigationLink(
                    destination: DetailScreen(),
                    isActive: $isDetailActive
                ) {
                    EmptyView()
                }
                .hidden()

                NavigationLink(
                    destination: ParallaxTabScreen(title: parallaxTabTitle?.isEmpty == false ? parallaxTabTitle! : "ParallaxTabFragment"),
                    isActive: Binding(
                        get: { parallaxTabTitle != nil },
                        set: { isActive in
                            if !isActive {
                                parallaxTabTitle = nil
                            }
                        }
                    )
                ) {
                    EmptyView()
                }
                .hidden()
            }
            .navigationBarHiddenCompat()
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}
