package com.codeaffine.eclipse.swt.widget.scrollable;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.codeaffine.eclipse.swt.test.util.DisplayHelper;
import com.codeaffine.eclipse.swt.test.util.ShellHelper;

public class ScrollableLayoutFactoryTest {

  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();

  private ScrollableLayoutFactorySpy factorySpy;
  private Scrollable scrollable;
  private Layout layout;
  private Shell shell;

  @Before
  public void setUp() {
    shell = ShellHelper.createShell( displayHelper );
    scrollable = new Text( shell, SWT.MULTI );
    factorySpy = new ScrollableLayoutFactorySpy();
    layout = factorySpy.create( shell, scrollable );
  }

  @Test
  public void horizontalScrollBarInitialization() {
    factorySpy.getHorizontal().notifyListeners( SWT.NONE );

    assertThat( factorySpy.getSelectionEvent().widget )
      .isNotNull()
      .isSameAs( factorySpy.getHorizontal() );
  }

  @Test
  public void verticalScrollBarInitialization() {
    factorySpy.getVertical().notifyListeners( SWT.NONE );

    assertThat( factorySpy.getSelectionEvent().widget )
      .isNotNull()
      .isSameAs( factorySpy.getVertical() );
  }

  @Test
  public void watchDogInitialization() {
    shell.dispose();

    assertThat( factorySpy.getDisposeEvent().widget )
      .isNotNull()
      .isSameAs( shell );
  }

  @Test
  public void createCompositeT() {
    assertThat( layout ).isSameAs( factorySpy.getLayout() );
  }

  @Test
  public void setIncrementButtonLength() {
    int expected = 20;

    factorySpy.setIncrementButtonLength( expected );

    assertThat( factorySpy.getHorizontal().getIncrementButtonLength() ).isEqualTo( expected );
    assertThat( factorySpy.getVertical().getIncrementButtonLength() ).isEqualTo( expected );
    assertThat( factorySpy.getIncrementButtonLength() ).isEqualTo( expected );
  }

  @Test
  public void setIncrementColor() {
    Color expected = displayHelper.getDisplay().getSystemColor( SWT.COLOR_BLACK );

    factorySpy.setIncrementColor( expected );

    assertThat( factorySpy.getHorizontal().getIncrementColor() ).isEqualTo( expected );
    assertThat( factorySpy.getVertical().getIncrementColor() ).isEqualTo( expected );
    assertThat( factorySpy.getIncrementColor() ).isEqualTo( expected );
  }

  @Test
  public void setPageIncrementColor() {
    Color expected = displayHelper.getDisplay().getSystemColor( SWT.COLOR_BLACK );

    factorySpy.setPageIncrementColor( expected );

    assertThat( factorySpy.getHorizontal().getPageIncrementColor() ).isEqualTo( expected );
    assertThat( factorySpy.getVertical().getPageIncrementColor() ).isEqualTo( expected );
    assertThat( factorySpy.getPageIncrementColor() ).isEqualTo( expected );
  }

  @Test
  public void setThumbColor() {
    Color expected = displayHelper.getDisplay().getSystemColor( SWT.COLOR_BLACK );

    factorySpy.setThumbColor( expected );

    assertThat( factorySpy.getHorizontal().getThumbColor() ).isEqualTo( expected );
    assertThat( factorySpy.getVertical().getThumbColor() ).isEqualTo( expected );
    assertThat( factorySpy.getThumbColor() ).isEqualTo( expected );
  }
}