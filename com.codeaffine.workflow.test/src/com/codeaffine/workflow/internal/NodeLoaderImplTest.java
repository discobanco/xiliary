package com.codeaffine.workflow.internal;

import static com.codeaffine.test.util.lang.ThrowableCaptor.thrown;
import static com.codeaffine.workflow.WorkflowContext.VARIABLE_CONTEXT;
import static com.codeaffine.workflow.WorkflowContexts.lookup;
import static com.codeaffine.workflow.internal.NodeLoaderImpl.ERROR_CHECKED_EXCEPTION;
import static com.codeaffine.workflow.internal.NodeLoaderImpl.ERROR_DEFAULT_CONSTRUCTOR_MISSING;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import java.rmi.AccessException;

import org.junit.Before;
import org.junit.Test;

import com.codeaffine.test.util.lang.ThrowableCaptor.Actor;
import com.codeaffine.workflow.WorkflowContext;

public class NodeLoaderImplTest {

  private WorkflowContextImpl context;
  private NodeLoaderImpl loader;

  public static class Type {
    WorkflowContext context = lookup( VARIABLE_CONTEXT );
  }

  public static class TypeWithoutDefaultConstructor {
    public TypeWithoutDefaultConstructor( @SuppressWarnings("unused") Object argument ) {}
  }

  private static class TypeWithInvisibleConstructor {
    private TypeWithInvisibleConstructor() {}
  }

  public static class TypeThrowingIllegalStateOnCreation {
    public TypeThrowingIllegalStateOnCreation() {
      throw new IllegalStateException( getClass().getName() );
    }
  }

  public static class TypeThrowingAccessExceptionOnCreation {
    public TypeThrowingAccessExceptionOnCreation() throws AccessException {
      throw new AccessException( getClass().getName() );
    }
  }

  @Before
  public void setUp() {
    loader = new NodeLoaderImpl();
    context = new WorkflowContextImpl();
  }

  @Test
  public void load() {
    context.defineVariable( VARIABLE_CONTEXT, context );

    Type actual = loader.load( Type.class, context );

    assertThat( actual ).isNotNull();
    assertThat( actual.context ).isSameAs( context );
  }

  @Test
  public void loadWithUninitializedContextVariable() {
    Type actual = loader.load( Type.class, context );

    assertThat( actual ).isNotNull();
    assertThat( actual.context ).isNull();
  }

  @Test
  public void loadWithInvisibleConstructor() {
    TypeWithInvisibleConstructor actual = loader.load( TypeWithInvisibleConstructor.class, context );

    assertThat( actual ).isNotNull();
  }

  @Test
  public void loadWithMissingDefaultConstructor() {
    Throwable actual = thrown( new Actor() {
      @Override
      public void act() {
        loader.load( TypeWithoutDefaultConstructor.class, context );
      }
    } );

    assertThat( actual )
      .isInstanceOf( IllegalArgumentException.class )
      .hasMessage( format( ERROR_DEFAULT_CONSTRUCTOR_MISSING, TypeWithoutDefaultConstructor.class.getName() ) );
  }

  @Test
  public void loadWithConstructorThatThrowsRuntimeException() {
    Throwable actual = thrown( new Actor() {
      @Override
      public void act() {
        loader.load( TypeThrowingIllegalStateOnCreation.class, context );
      }
    } );

    assertThat( actual )
      .isInstanceOf( IllegalStateException.class )
      .hasMessage( TypeThrowingIllegalStateOnCreation.class.getName() );
  }

  @Test
  public void loadWithConstructorThatThrowsCheckedException() {
    Throwable actual = thrown( new Actor() {
      @Override
      public void act() {
        loader.load( TypeThrowingAccessExceptionOnCreation.class, context );
      }
    } );

    assertThat( actual )
      .isInstanceOf( IllegalArgumentException.class )
      .hasMessage( format( ERROR_CHECKED_EXCEPTION, TypeThrowingAccessExceptionOnCreation.class.getName() ) );
  }

  @Test
  public void lookupOutsideOfNodeLoadingCycle() {
    Throwable actual = thrown( new Actor() {
      @Override
      public void act() {
        NodeLoaderImpl.lookup( VARIABLE_CONTEXT );
      }
    } );

    assertThat( actual )
      .isInstanceOf( IllegalStateException.class )
      .hasMessage( NodeLoaderImpl.ERROR_OUTSIDE_LOAD );
  }
}