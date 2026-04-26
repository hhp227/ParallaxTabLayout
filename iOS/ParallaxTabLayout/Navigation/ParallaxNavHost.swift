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
                switch selectedDestination {
                case .first:
                    FirstScreen(
                        selectedDestination: $selectedDestination,
                        onOpenDetail: { isDetailActive = true }
                    )
                case .second:
                    SecondScreen(
                        selectedDestination: $selectedDestination,
                        onOpenParallaxTab: { parallaxTabTitle = $0 }
                    )
                }

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
