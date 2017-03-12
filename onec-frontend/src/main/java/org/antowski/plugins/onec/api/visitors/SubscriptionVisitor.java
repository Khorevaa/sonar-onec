package org.antowski.plugins.onec.api.visitors;

import com.google.common.base.Preconditions;
import org.antowski.onec.tree.impl.OneCTree;
import org.antowski.plugins.onec.api.tree.Tree;
import org.antowski.plugins.onec.api.tree.Tree.Kind;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class SubscriptionVisitor implements TreeVisitor {

    private TreeVisitorContext context;
    private Collection<Tree.Kind> nodesToVisit;

    public abstract List<Kind> nodesToVisit();

    public void visitNode(Tree tree) {
        // Default behavior : do nothing.
    }

    public void leaveNode(Tree tree) {
        // Default behavior : do nothing.
    }

    public void visitFile(Tree Tree) {
        // default behaviour is to do nothing
    }

    public void leaveFile(Tree Tree) {
        // default behaviour is to do nothing
    }

    @Override
    public TreeVisitorContext getContext() {
        Preconditions.checkState(context != null, "this#scanTree(context) should be called to initialise the context before accessing it");
        return context;
    }

    @Override
    public final void scanTree(TreeVisitorContext context) {
        this.context = context;
        visitFile(context.getTopTree());
        scanTree(context.getTopTree());
        leaveFile(context.getTopTree());
    }

    public void scanTree(Tree tree) {
        nodesToVisit = nodesToVisit();
        visit(tree);
    }

    private void visit(Tree tree) {
        boolean isSubscribed = isSubscribed(tree);
        if (isSubscribed) {
            visitNode(tree);
        }
        visitChildren(tree);
        if (isSubscribed) {
            leaveNode(tree);
        }
    }

    protected boolean isSubscribed(Tree tree) {
        return nodesToVisit.contains(((OneCTree) tree).getKind());
    }

    private void visitChildren(Tree tree) {
        OneCTree onecTree = (OneCTree) tree;

        if (!onecTree.isLeaf()) {
            for (Iterator<Tree> iter = onecTree.childrenIterator(); iter.hasNext(); ) {
                Tree next = iter.next();

                if (next != null) {
                    visit(next);
                }
            }
        }
    }

}
