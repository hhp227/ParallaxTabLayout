import SwiftUI

enum AppRoute: Hashable {
    case detail
    case parallaxTab(String)
}

enum MainDestination {
    case first
    case second
}

struct ParallaxNavHost: View {
    @State private var path: [AppRoute] = []
    @State private var selectedDestination: MainDestination = .first

    var body: some View {
        NavigationStack(path: $path) {
            Group {
                switch selectedDestination {
                case .first:
                    FirstScreen(
                        selectedDestination: $selectedDestination,
                        onOpenDetail: { path.append(.detail) }
                    )
                case .second:
                    SecondScreen(
                        selectedDestination: $selectedDestination,
                        onOpenParallaxTab: { path.append(.parallaxTab($0)) }
                    )
                }
            }
            .navigationDestination(for: AppRoute.self) { route in
                switch route {
                case .detail:
                    DetailScreen()
                case let .parallaxTab(group):
                    ParallaxTabScreen(title: group.isEmpty ? "ParallaxTabFragment" : group)
                }
            }
        }
    }
}
