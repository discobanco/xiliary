package com.codeaffine.eclipse.swt.widget.scrollable;

import org.eclipse.swt.widgets.Scrollable;

import com.codeaffine.eclipse.swt.widget.scrollable.Platform.PlatformType;

class OffsetComputer {

  static final int DEFAULT_OFFSET = 0;
  static final int GTK_OFFSET = 4;

  private final Scrollable scrollable;
  private final Platform platform;

  OffsetComputer( Scrollable scrollable ) {
    this( scrollable, new Platform() );
  }

  OffsetComputer( Scrollable scrollable, Platform platform ) {
    this.scrollable = scrollable;
    this.platform = platform;
  }

  int compute() {
    if( matchesGtk() && !isOverlay() ) {
      return GTK_OFFSET;
    }
    return DEFAULT_OFFSET;
  }

  private boolean matchesGtk() {
    return platform.matches( PlatformType.GTK );
  }

  private boolean isOverlay() {
    return scrollable.getVerticalBar().getSize().x == 0 && scrollable.getHorizontalBar().getSize().y == 0;
  }
}