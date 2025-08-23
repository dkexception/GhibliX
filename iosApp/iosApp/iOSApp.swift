import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        KoinInitializerKt.startKoinForIOS()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea()
        }
    }
}

#Preview {
    ContentView()
}
