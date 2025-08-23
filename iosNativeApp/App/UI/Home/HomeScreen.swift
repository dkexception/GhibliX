//
//  HomeScreen.swift
//  GhibliXiOSApp
//
//  Created by Dhanesh on 19/08/25.
//

import SwiftUI
import Foundation
import SharedKit

struct HomeScreen: View {
    
    /// Injects the `IOSViewModelStoreOwner` from the environment, which manages the lifecycle of `ViewModel` instances.
    @EnvironmentObject var viewModelStoreOwner: IOSViewModelStoreOwner
    
    /// Injects the `AppContainer` from the environment, providing access to application-wide dependencies.
    @EnvironmentObject var appContainer: ObservableValueWrapper<AppContainer>
    
    private let columns = [GridItem(.adaptive(minimum: 120), spacing: 12)]
    
    var body: some View {
        
        let homeViewModel: HomeViewModel = viewModelStoreOwner.viewModel(
            factory: appContainer.value.homeViewModelFactory
        )
        
        Observing(homeViewModel.state) { homeUIState in
            
            ScrollView {
                
                VStack(alignment: .leading, spacing: 16) {
                    
                    Observing(homeViewModel.searchQuery) { query in
                        TextField(
                            "Search movies",
                            text: Binding<String>(
                                get: { query },
                                set: { newValue in
                                    homeViewModel.onEvent(homeEvent: HomeEvent.OnSearchQueryChange(query: newValue))
                                }
                            )
                        )
                        .textFieldStyle(.roundedBorder)
                        .padding(.horizontal, 16)
                    }
                    
                    LazyVGrid(columns: columns, spacing: 12) {
                        ForEach(homeUIState.films, id: \.self.id) { film in
                            NavigationLink {
                                MovieDetailsScreen(film: film)
                            } label: {
                                FilmGridItem(film: film)
                            }
                        }
                    }
                    .padding(.horizontal, 16)
                    .padding(.bottom, 24)
                }
            }
        }
        .navigationTitle("What do you want to watch?")
        .navigationBarTitleDisplayMode(.inline)
        .background(Color(.systemGray6).opacity(0.15))
        .toolbarBackground(Color(.systemGray6).opacity(0.15), for: .navigationBar)
    }
}
