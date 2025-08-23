//
//  IOSViewModelStoreOwner.swift
//  GhibliXApp
//
//  Created by Dhanesh on 19/08/25.
//

import SwiftUI
import SharedKit

/// A ViewModelStoreOwner specifically for iOS to be an ObservableObject.
class IOSViewModelStoreOwner: ObservableObject, Lifecycle_viewmodelViewModelStoreOwner {
    
    var viewModelStore = Lifecycle_viewmodelViewModelStore()
    
    /// This function allows retrieving the androidx ViewModel from the store.
    func viewModel<T: Lifecycle_viewmodelViewModel>(
        key: String? = nil,
        factory: Lifecycle_viewmodelViewModelProviderFactory,
        extras: Lifecycle_viewmodelCreationExtras? = nil
    ) -> T {
        do {
            return try viewModel(
                modelClass: T.self,
                factory: factory,
                key: key,
                extras: extras
            ) as! T
        } catch {
            fatalError("Failed to create ViewModel of type \(T.self)")
        }
    }
    
    /// This can be called from outside when using the `ViewModelStoreOwnerProvider`
    func clear() {
        viewModelStore.clear()
    }
    
    /// This is called when this class is used as a `@StateObject`
    deinit {
        viewModelStore.clear()
    }
}
