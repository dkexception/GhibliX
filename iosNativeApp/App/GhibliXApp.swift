//
//  GhibliXApp.swift
//  GhibliXApp
//
//  Created by Dhanesh on 19/08/25.
//

import SwiftUI
import SharedKit

@main
struct GhibliXApp: App {
    
    @AppStorage("appTheme") private var appTheme: AppTheme = .system
    
    /// The application's dependency container, wrapped for SwiftUI observation.
    let appContainer: ObservableValueWrapper<AppContainer>
    
    init() {
        InitKoinKt.doInitKoin()
        self.appContainer = ObservableValueWrapper<AppContainer>(
            value: AppContainer()
        )
    }
    
    var body: some Scene {
        WindowGroup {
            /// Provides the root `ViewModelStoreOwner` to the environment, making it accessible to all child views.
            /// Nested `ViewModelStoreOwnerProvider` instances can create additional, scoped ViewModel stores.
            ViewModelStoreOwnerProvider {
                RootBottomTabs()
            }
            .environmentObject(appContainer)
            .preferredColorScheme(colorSchemeFromTheme(appTheme))
        }
    }
    
    private func colorSchemeFromTheme(_ theme: AppTheme) -> ColorScheme? {
        switch theme {
        case .system: return nil
        case .light:  return .light
        case .dark:   return .dark
        }
    }
}
