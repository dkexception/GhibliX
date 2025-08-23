//
//  SettingsScreen.swift
//  GhibliXApp
//
//  Created by Dhanesh on 21/08/25.
//

import SwiftUI

struct SettingsScreen : View {
    
    // Persists across app restarts automatically
    @AppStorage("appTheme") private var appTheme: AppTheme = .system
    
    var body: some View {
        
        Form {
            Picker("Appearance", selection: $appTheme) {
                ForEach(AppTheme.allCases) { theme in
                    Text(theme.displayName).tag(theme)
                }
            }
            .pickerStyle(.menu)
        }
        .navigationTitle("Settings")
    }
}

enum AppTheme: String, CaseIterable, Identifiable {
    
    case system
    case light
    case dark
    
    var id: String { rawValue }
    
    var displayName: String {
        switch self {
        case .system: return "System"
        case .light:  return "Light"
        case .dark:   return "Dark"
        }
    }
}

#Preview {
    NavigationStack {
        SettingsScreen()
    }
}
