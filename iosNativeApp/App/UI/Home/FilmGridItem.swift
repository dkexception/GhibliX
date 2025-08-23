//
//  FilmGridItem.swift
//  GhibliXApp
//
//  Created by Dhanesh on 21/08/25.
//

import SwiftUI
import SharedKit

struct FilmGridItem: View {
    
    let film: Film
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            AsyncImage(url: URL(string: film.image)) { phase in
                switch phase {
                case .success(let img):
                    img
                        .resizable()
                        .scaledToFill()
                case .failure(_):
                    // fallback
                    Image(systemName: "film.fill")
                        .resizable()
                        .scaledToFit()
                        .padding(24)
                        .opacity(0.4)
                case .empty:
                    // skeleton shimmer-ish
                    Rectangle().opacity(0.1)
                @unknown default:
                    EmptyView()
                }
            }
            .frame(height: 180)                    // keep that 2:3-ish vibe
            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 16, style: .continuous)
                    .strokeBorder(.white.opacity(0.06))
            )
        }
    }
}

#Preview {
    FilmGridItem(
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
            locations: [
                "https://ghibliapi.vercel.app/locations/"
            ],
            people: [
                "https://ghibliapi.vercel.app/people/986faac6-67e3-4fb8-a9ee-bad077c2e7fe"
            ],
            species: [
                "https://ghibliapi.vercel.app/species/af3910a6-429f-4c74-9ad5-dfe1c4aa04f2"
            ],
            vehicles: [
                "https://ghibliapi.vercel.app/vehicles/"
            ]
        )
    )
}
