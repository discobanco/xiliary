package com.codeaffine.eclipse.swt.widget.scrollable;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scrollable;

import com.codeaffine.eclipse.swt.util.ActionScheduler;

class WatchDog implements Runnable, DisposeListener {

  static final int DELAY = 10;

  private final VerticalScrollBarUpdater verticalBarUpdater;
  private final Visibility vScrollVisibility;
  private final Visibility hScrollVisibility;
  private final LayoutTrigger layoutTrigger;
  private final ActionScheduler scheduler;
  private final TreeWidth treeWidth;

  private boolean disposed;

  WatchDog( Scrollable scrollable, LayoutContextFactory contextFactory, VerticalScrollBarUpdater verticalBarUpdater  ) {
    this( verticalBarUpdater,
          new Visibility( scrollable.getHorizontalBar(), contextFactory ),
          new Visibility( scrollable.getVerticalBar(), contextFactory ),
          null,
          new LayoutTrigger( scrollable.getParent() ),
          new TreeWidth( scrollable, contextFactory ) );
  }

  WatchDog( VerticalScrollBarUpdater verticalBarUpdater,
            Visibility hScrollVisibility,
            Visibility vScrollVisibility,
            ActionScheduler actionScheduler,
            LayoutTrigger layoutTrigger,
            TreeWidth treeWidth )
  {
    this.verticalBarUpdater = verticalBarUpdater;
    this.hScrollVisibility = hScrollVisibility;
    this.vScrollVisibility = vScrollVisibility;
    this.scheduler = ensureScheduler( actionScheduler );
    this.layoutTrigger = layoutTrigger;
    this.treeWidth = treeWidth;
    scheduler.schedule( DELAY );
  }

  @Override
  public void widgetDisposed( DisposeEvent e ) {
    disposed = true;
  }

  @Override
  public void run() {
    if( !disposed ) {
      doRun();
      scheduler.schedule( DELAY );
    }
  }

  private void doRun() {
    if( vScrollVisibility.hasChanged() || hScrollVisibility.hasChanged() || treeWidth.hasScrollEffectingChange() ) {
      layoutTrigger.pull();
    }
    treeWidth.update();
    vScrollVisibility.update();
    hScrollVisibility.update();
    if( vScrollVisibility.isVisible() ) {
      verticalBarUpdater.update();
    }
  }

  private ActionScheduler ensureScheduler( ActionScheduler actionScheduler ) {
    return actionScheduler == null ? new ActionScheduler( Display.getDefault(), this ) : actionScheduler;
  }
}