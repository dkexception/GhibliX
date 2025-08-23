//
//  RootBottomTabs.swift
//  GhibliXApp
//
//  Created by Dhanesh on 21/08/25.
//

import SwiftUI

struct RootBottomTabs: View {
    
    var body: some View {
        TabView {
            NavigationStack { HomeScreen() }
                .tabItem {
                    Label("Home", systemImage: "house.fill")
                }
            
            NavigationStack { WatchlistScreen() }
                .tabItem {
                    Label("Watchlist", systemImage: "bookmark.fill")
                }
            
            NavigationStack { SettingsScreen() }
                .tabItem {
                    Label("Settings", systemImage: "gearshape.fill")
                }
        }
        .tint(.blue)
    }
}

#Preview {
    RootBottomTabs()
}
