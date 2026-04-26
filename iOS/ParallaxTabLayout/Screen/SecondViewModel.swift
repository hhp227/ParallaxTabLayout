import Foundation

final class SecondViewModel: ObservableObject {
    @Published var items: [String] = (1...20).map { "item\($0)" }
}
