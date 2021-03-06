package com.codeaffine.eclipse.swt.widget.scrollable;

import static com.codeaffine.eclipse.swt.test.util.ShellHelper.createShell;
import static com.codeaffine.eclipse.swt.widget.scrollable.SelectionEventHelper.createEvent;
import static com.codeaffine.eclipse.swt.widget.scrollable.TreeHelper.createTree;
import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.junit.Rule;
import org.junit.Test;

import com.codeaffine.eclipse.swt.test.util.DisplayHelper;

public class HorizontalSelectionListenerTest {

  private static final int SELECTION = 5;

  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();

  @Test
  public void selectionChanged() {
    Shell shell = createShell( displayHelper );
    Tree tree = createTree( shell, 6, 4 );
    Point location = tree.getLocation();
    HorizontalSelectionListener listener = new HorizontalSelectionListener( tree );

    listener.widgetSelected( createEvent( shell, SELECTION ) );

    assertThat( tree.getLocation().x ).isEqualTo( location.x - SELECTION );
  }
}