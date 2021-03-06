package com.codeaffine.eclipse.core.runtime.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;

import com.codeaffine.eclipse.core.runtime.Predicate;
import com.codeaffine.eclipse.core.runtime.Extension;

class ContributionElementLoop {

  private final IExtensionRegistry registry;

  public static interface ConfigurationElementHandler {
    void handle( IConfigurationElement element );
  }

  ContributionElementLoop( IExtensionRegistry registry ) {
    this.registry = registry;
  }

  void forEach(
    String extensionPointId, Predicate predicate, ConfigurationElementHandler handler )
  {
    for( IConfigurationElement elem : registry.getConfigurationElementsFor( extensionPointId ) ) {
      if( predicate.apply( new Extension( elem ) ) ) {
        handler.handle( elem );
      }
    }
  }
}