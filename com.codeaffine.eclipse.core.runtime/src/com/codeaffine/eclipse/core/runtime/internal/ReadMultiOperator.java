package com.codeaffine.eclipse.core.runtime.internal;

import static com.codeaffine.eclipse.core.runtime.Predicates.alwaysTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;

import com.codeaffine.eclipse.core.runtime.Extension;
import com.codeaffine.eclipse.core.runtime.Predicate;
import com.codeaffine.eclipse.core.runtime.internal.ContributionElementLoop.ConfigurationElementHandler;
import com.codeaffine.eclipse.core.runtime.internal.Operator.ReadExtensionsOperator;

class ReadMultiOperator implements ReadExtensionsOperator<Extension> {

  private final ContributionElementLoop loop;
  private Predicate predicate;
  private String extensionPointId;

  ReadMultiOperator( IExtensionRegistry registry ) {
    this.loop = new ContributionElementLoop( registry );
    this.predicate = alwaysTrue();
  }

  @Override
  public void setExtensionPointId( String extensionPointId ) {
    this.extensionPointId = extensionPointId;
  }

  @Override
  public void setPredicate( Predicate predicate ) {
    this.predicate = predicate;
  }

  @Override
  public Collection<Extension> create() {
    final Collection<Extension> result = new ArrayList<Extension>();
    loop.forEach( extensionPointId, predicate, new ConfigurationElementHandler() {
      @Override
      public void handle( IConfigurationElement element ) {
        result.add( new Extension( element ) );
      }
    } );
    return result;
  }
}