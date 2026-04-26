import Foundation

final class FirstViewModel: ObservableObject {
    @Published var items: [String] = (1...20).map { "item\($0)" }
}
