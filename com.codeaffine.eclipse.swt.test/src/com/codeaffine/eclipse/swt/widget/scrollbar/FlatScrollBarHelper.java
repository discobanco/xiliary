package com.codeaffine.eclipse.swt.widget.scrollbar;

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.mockito.ArgumentCaptor;

public class FlatScrollBarHelper {

  static FlatScrollBar stubScrollBar( int selection, int increment ) {
    FlatScrollBar result = mock( FlatScrollBar.class );
    when( result.getIncrement() ).thenReturn( increment );
    when( result.getSelection() ).thenReturn( selection );
    return result;
  }

  static SelectionListener equipScrollBarWithListener( FlatScrollBar scrollBar ) {
    SelectionListener result = mock( SelectionListener.class );
    scrollBar.addSelectionListener( result );
    return result;
  }

  static SelectionEvent verifyNotification( SelectionListener listener ) {
    ArgumentCaptor<SelectionEvent> captor = forClass( SelectionEvent.class );
    verify( listener ).widgetSelected( captor.capture() );
    return captor.getValue();
  }
}