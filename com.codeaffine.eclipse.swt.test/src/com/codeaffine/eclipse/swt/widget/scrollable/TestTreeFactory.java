package com.codeaffine.eclipse.swt.widget.scrollable;

import static com.codeaffine.eclipse.swt.widget.scrollable.TreeHelper.createTree;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

class TestTreeFactory implements ScrollableFactory<Tree> {

  private Tree tree;

  @Override
  public Tree create( Composite parent ) {
    tree = createTree( parent, 2, 6 );
    return tree;
  }

  public Tree getTree() {
    return tree;
  }
}