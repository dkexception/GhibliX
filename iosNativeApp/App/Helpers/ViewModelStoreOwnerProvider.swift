//
//  ViewModelStoreOwnerProvider.swift
//  GhibliXApp
//
//  Created by Dhanesh on 19/08/25.
//

import SwiftUI
import SharedKit

struct ViewModelStoreOwnerProvider<Content: View>: View {
    
    @StateObject private var viewModelStoreOwner = IOSViewModelStoreOwner()
    
    private let content: Content
    
    /// Initializes the provider with its content, creating a new `IOSViewModelStoreOwner`.
    init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }
    
    var body: some View {
        content
            .environmentObject(viewModelStoreOwner)
            .onDisappear {
                viewModelStoreOwner.clear()
            }
    }
}
