//
//  MovieDetailsScreen.swift
//  GhibliXiOSApp
//
//  Created by Dhanesh on 19/08/25.
//

import SwiftUI
import SharedKit

// MARK: - Screen
struct MovieDetailsScreen: View {
    
    enum Tab { case about, cast }
    
    let film: Film
    
    @Environment(\.dismiss) private var dismiss
    @State private var tab: Tab = .about
    
    private var ratingOutOfTen: String {
        String(format: "%.1f", Double(film.rating) / 10.0)
    }
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(spacing: 0) {
                headerHero
                contentCard
            }
        }
        .background(Color(.systemBackground))
        .navigationTitle(film.title)
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button {
                    // hook up to favorites later
                } label: {
                    Image(systemName: "bookmark")
                        .font(.system(size: 17, weight: .semibold))
                        .foregroundStyle(.primary)
                }
            }
        }
    }
    
    // MARK: - Header (Backdrop with gradient)
    private var headerHero: some View {
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
                posterThumb
                Spacer()
                ratingBadge
                    .padding(.trailing, 16)
            }
            .padding(.leading, 16)
            .offset(y: 44)
        }
    }
    
    private var posterThumb: some View {
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
    
    private var ratingBadge: some View {
        HStack(spacing: 6) {
            Image(systemName: "star.fill")
            Text(ratingOutOfTen)
                .font(.headline.bold())
        }
        .foregroundStyle(Color.orange)
        .padding(.vertical, 6)
        .padding(.horizontal, 10)
        .background(.ultraThinMaterial, in: Capsule())
    }
    
    // MARK: - Main content card
    private var contentCard: some View {
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
            film: Film(
                id: "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
                title: "My Neighbor Totoro",
                originalTitle: "となりのトトロ",
                originalTitleRomanised: "Tonari no Totoro",
                description: "Two sisters move to the country with their father and discover magical forest spirits called Totoros...",
                director: "Hayao Miyazaki",
                producer: "Hayao Miyazaki",
                releaseYear: "1988",
                rating: 93,
                runningTimeMinutes: 86,
                image: "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
                bannerUrl: "https://image.tmdb.org/t/p/original/etqr6fOOCXQOgwrQXaKwenTSuzx.jpg",
                url: "https://ghibliapi.vercel.app/films/58611129-2dbc-4a81-a72f-77ddfc1b1b49",
                locations: ["https://ghibliapi.vercel.app/locations/"],
                people: ["https://ghibliapi.vercel.app/people/986faac6-67e3-4fb8-a9ee-bad077c2e7fe"],
                species: ["https://ghibliapi.vercel.app/species/af3910a6-429f-4c74-9ad5-dfe1c4aa04f2"],
                vehicles: ["https://ghibliapi.vercel.app/vehicles/"]
            )
        )
    }
}
