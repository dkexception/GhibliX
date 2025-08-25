//
//  MovieDetailsScreen.swift
//  GhibliXiOSApp
//
//  Created by Dhanesh on 19/08/25.
//

import SwiftUI
import SharedKit

extension Film {
    var ratingOutOfTen: String {
        String(format: "%.1f", Double(self.rating) / 10.0)
    }
}

// MARK: - Screen
struct MovieDetailsScreen: View {
    
    enum Tab { case about, cast }
    
    let filmId: String
    
    @Environment(\.dismiss) private var dismiss
    @State private var tab: Tab = .about
    
    @StateObject var viewModelStoreOwner = IOSViewModelStoreOwner()
    
    @EnvironmentObject var appContainer: ObservableValueWrapper<AppContainer>
    
    var body: some View {
        
        let movieViewModel: MovieViewModel =
        viewModelStoreOwner.viewModel(
            key: "movie_\(filmId)",
            factory: appContainer.value.movieViewModelFactory,
            extras: creationExtras { extras in
                extras.set(
                    key: MovieViewModel.companion.FILM_ID_KEY,
                    t: filmId
                )
            }
        )
        
        Observing(movieViewModel.state) { movieState in
            
            if let film = movieState.film {
                
                ScrollView(.vertical, showsIndicators: false) {
                    VStack(spacing: 0) {
                        headerHero(film)
                        contentCard(film)
                    }
                }
                .background(Color(.systemBackground))
                .navigationTitle(movieState.film?.title ?? "")
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .topBarTrailing) {
                        Button {
                            movieViewModel.onEvent(movieEvent: MovieEvent.WatchlistToggle())
                        } label: {
                            Image(
                                systemName: movieState.isWatchlisted ? "bookmark.fill" : "bookmark"
                            )
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundStyle(.primary)
                        }
                    }
                }
                
            } else {
                ProgressView()
            }
        }
    }
    
    // MARK: - Header (Backdrop with gradient)
    @ViewBuilder
    private func headerHero(_ film: Film) -> some View {
        ZStack(alignment: .bottomLeading) {
            AsyncImage(url: URL(string: film.bannerUrl)) { phase in
                switch phase {
                case .success(let img): img
                        .resizable()
                        .scaledToFill()
                case .failure(_): Color.gray.opacity(0.2)
                case .empty: Color.gray.opacity(0.1)
                @unknown default: Color.gray.opacity(0.2)
                }
            }
            .frame(height: 260)
            .clipped()
            
            // Gradient for text readability + to match mock
            LinearGradient(
                colors: [.clear, .black.opacity(0.55)],
                startPoint: .center,
                endPoint: .bottom
            )
            .frame(height: 220)
            
            // Small poster + rating badge
            HStack(alignment: .bottom, spacing: 16) {
                Spacer()
                    .frame(width: 1)
                posterThumb(film)
                Spacer()
                ratingBadge(film)
                    .padding(.trailing, 16)
            }
            .padding(.leading, 16)
            .offset(y: 44)
        }
    }
    
    @ViewBuilder
    private func posterThumb(_ film: Film) -> some View {
        AsyncImage(url: URL(string: film.image)) { phase in
            switch phase {
            case .success(let img): img.resizable().scaledToFill()
            case .failure(_): Color.gray.opacity(0.2)
            case .empty: Color.gray.opacity(0.1)
            @unknown default: Color.gray.opacity(0.2)
            }
        }
        .frame(width: 100, height: 140)
        .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))
        .overlay(
            RoundedRectangle(cornerRadius: 12, style: .continuous)
                .stroke(Color.white.opacity(0.2), lineWidth: 0.5)
        )
        .shadow(radius: 8, y: 6)
    }
    
    @ViewBuilder
    private func ratingBadge(_ film: Film) -> some View {
        HStack(spacing: 6) {
            Image(systemName: "star.fill")
            Text(film.ratingOutOfTen)
                .font(.headline.bold())
        }
        .foregroundStyle(Color.orange)
        .padding(.vertical, 6)
        .padding(.horizontal, 10)
        .background(.ultraThinMaterial, in: Capsule())
    }
    
    // MARK: - Main content card
    @ViewBuilder
    private func contentCard(_ film: Film) -> some View {
        VStack(alignment: .leading, spacing: 16) {
            // Title
            Text(film.title)
                .font(.title.bold())
                .multilineTextAlignment(.leading)
            
            // Meta row
            HStack(spacing: 18) {
                Label(film.releaseYear, systemImage: "calendar")
                Label("\(film.runningTimeMinutes) Minutes", systemImage: "clock")
            }
            .font(.subheadline)
            .foregroundStyle(.secondary)
            
            // Tabs
            SegmentedTabs(selected: $tab)
            
            // Underline like mock
            Rectangle()
                .fill(Color.primary.opacity(0.08))
                .frame(height: 1)
            
            // Tab content
            Group {
                switch tab {
                case .about:
                    Text(film.description_)
                        .font(.body)
                        .foregroundStyle(.primary)
                        .lineSpacing(4)
                case .cast:
                    VStack(alignment: .leading, spacing: 10) {
                        infoRow(title: "Director", value: film.director)
                        infoRow(title: "Producer", value: film.producer)
                        infoRow(title: "People Links", value: "\(film.people.count)")
                        infoRow(title: "Species Links", value: "\(film.species.count)")
                        infoRow(title: "Vehicles Links", value: "\(film.vehicles.count)")
                    }
                }
            }
        }
        .padding(16)
        .padding(.horizontal)
        .padding(.top, 52) // to clear poster overlap
        .padding(.bottom, 32)
    }
    
    // MARK: - Helpers
    private var safeTopInset: CGFloat {
        UIApplication.shared.connectedScenes
            .compactMap { ($0 as? UIWindowScene)?.keyWindow?.safeAreaInsets.top }
            .first ?? 0
    }
    
    private func infoRow(title: String, value: String) -> some View {
        HStack(alignment: .firstTextBaseline) {
            Text(title)
                .font(.subheadline.weight(.semibold))
                .frame(width: 110, alignment: .leading)
                .foregroundStyle(.secondary)
            Text(value).font(.subheadline)
        }
    }
}

// MARK: - Segmented Tabs like "About Movie | Reviews | Cast"
private struct SegmentedTabs: View {
    
    @Binding var selected: MovieDetailsScreen.Tab
    
    var body: some View {
        HStack(spacing: 24) {
            tabButton(.about, title: "About Movie")
            tabButton(.cast, title: "Cast")
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    private func tabButton(_ tab: MovieDetailsScreen.Tab, title: String) -> some View {
        Button {
            withAnimation(.easeInOut(duration: 0.18)) { selected = tab }
        } label: {
            VStack(alignment: .leading, spacing: 4) {
                Text(title)
                    .font(.headline.weight(selected == tab ? .semibold : .regular))
                    .foregroundStyle(selected == tab ? Color.primary : .secondary)
                Rectangle()
                    .fill(selected == tab ? Color.primary : Color.clear)
                    .frame(width: 36, height: 3)
                    .clipShape(Capsule())
            }
        }
        .buttonStyle(.plain)
    }
}

// MARK: - Preview
#Preview {
    NavigationStack {
        MovieDetailsScreen(
            filmId: "58611129-2dbc-4a81-a72f-77ddfc1b1b49"
        )
        .environmentObject(
            ObservableValueWrapper<AppContainer>(
                value: AppContainer()
            )
        )
    }
}
