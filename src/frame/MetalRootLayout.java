package frame;

import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JRootPane;

/**
 *
 * @author Curt
 */
/**
 * A custom layout manager that is responsible for the layout of
 * layeredPane, glassPane, menuBar and titlePane, if one has been
 * installed.
 */
final class MetalRootLayout implements LayoutManager2 {

    /**
     * Returns the amount of space the layout would like to have.
     *
     * @param the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's preferred size
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension cpd;
        Dimension mbd;
        Dimension tpd;
        int cpWidth = 0;
        int cpHeight = 0;
        int mbWidth = 0;
        int mbHeight = 0;
        int tpWidth = 0;
        int tpHeight = 0;
        Insets i = parent.getInsets();
        JRootPane root = (JRootPane) parent;
        if (root.getContentPane() != null) {
            cpd = root.getContentPane().getPreferredSize();
        } else {
            cpd = root.getSize();
        }
        if (cpd != null) {
            cpWidth = cpd.width;
            cpHeight = cpd.height;
        }
        if (root.getMenuBar() != null) {
            mbd = root.getMenuBar().getPreferredSize();
            if (mbd != null) {
                mbWidth = mbd.width;
                mbHeight = mbd.height;
            }
        }
        if (root.getWindowDecorationStyle() != JRootPane.NONE && (root.getUI() instanceof MetalRootPaneUI)) {
            JComponent titlePane = ((MetalRootPaneUI) root.getUI()).getTitlePane();
            if (titlePane != null) {
                tpd = titlePane.getPreferredSize();
                if (tpd != null) {
                    tpWidth = tpd.width;
                    tpHeight = tpd.height;
                }
            }
        }
        return new Dimension(Math.max(Math.max(cpWidth, mbWidth), tpWidth) + i.left + i.right, cpHeight + mbHeight + tpWidth + i.top + i.bottom);
    }

    /**
     * Returns the minimum amount of space the layout needs.
     *
     * @param the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's minimum size
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension cpd;
        Dimension mbd;
        Dimension tpd;
        int cpWidth = 0;
        int cpHeight = 0;
        int mbWidth = 0;
        int mbHeight = 0;
        int tpWidth = 0;
        int tpHeight = 0;
        Insets i = parent.getInsets();
        JRootPane root = (JRootPane) parent;
        if (root.getContentPane() != null) {
            cpd = root.getContentPane().getMinimumSize();
        } else {
            cpd = root.getSize();
        }
        if (cpd != null) {
            cpWidth = cpd.width;
            cpHeight = cpd.height;
        }
        if (root.getMenuBar() != null) {
            mbd = root.getMenuBar().getMinimumSize();
            if (mbd != null) {
                mbWidth = mbd.width;
                mbHeight = mbd.height;
            }
        }
        if (root.getWindowDecorationStyle() != JRootPane.NONE && (root.getUI() instanceof MetalRootPaneUI)) {
            JComponent titlePane = ((MetalRootPaneUI) root.getUI()).getTitlePane();
            if (titlePane != null) {
                tpd = titlePane.getMinimumSize();
                if (tpd != null) {
                    tpWidth = tpd.width;
                    tpHeight = tpd.height;
                }
            }
        }
        return new Dimension(Math.max(Math.max(cpWidth, mbWidth), tpWidth) + i.left + i.right, cpHeight + mbHeight + tpWidth + i.top + i.bottom);
    }

    /**
     * Returns the maximum amount of space the layout can use.
     *
     * @param the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's maximum size
     */
    @Override
    public Dimension maximumLayoutSize(Container target) {
        Dimension cpd;
        Dimension mbd;
        Dimension tpd;
        int cpWidth = Integer.MAX_VALUE;
        int cpHeight = Integer.MAX_VALUE;
        int mbWidth = Integer.MAX_VALUE;
        int mbHeight = Integer.MAX_VALUE;
        int tpWidth = Integer.MAX_VALUE;
        int tpHeight = Integer.MAX_VALUE;
        Insets i = target.getInsets();
        JRootPane root = (JRootPane) target;
        if (root.getContentPane() != null) {
            cpd = root.getContentPane().getMaximumSize();
            if (cpd != null) {
                cpWidth = cpd.width;
                cpHeight = cpd.height;
            }
        }
        if (root.getMenuBar() != null) {
            mbd = root.getMenuBar().getMaximumSize();
            if (mbd != null) {
                mbWidth = mbd.width;
                mbHeight = mbd.height;
            }
        }
        if (root.getWindowDecorationStyle() != JRootPane.NONE && (root.getUI() instanceof MetalRootPaneUI)) {
            JComponent titlePane = ((MetalRootPaneUI) root.getUI()).getTitlePane();
            if (titlePane != null) {
                tpd = titlePane.getMaximumSize();
                if (tpd != null) {
                    tpWidth = tpd.width;
                    tpHeight = tpd.height;
                }
            }
        }
        int maxHeight = Math.max(Math.max(cpHeight, mbHeight), tpHeight);
        // Only overflows if 3 real non-MAX_VALUE heights, sum to > MAX_VALUE
        // Only will happen if sums to more than 2 billion units.  Not likely.
        if (maxHeight != Integer.MAX_VALUE) {
            maxHeight = cpHeight + mbHeight + tpHeight + i.top + i.bottom;
        }
        int maxWidth = Math.max(Math.max(cpWidth, mbWidth), tpWidth);
        // Similar overflow comment as above
        if (maxWidth != Integer.MAX_VALUE) {
            maxWidth += i.left + i.right;
        }
        return new Dimension(maxWidth, maxHeight);
    }

    /**
     * Instructs the layout manager to perform the layout for the specified
     * container.
     *
     * @param the Container for which this layout manager is being used
     */
    @Override
    public void layoutContainer(Container parent) {
        JRootPane root = (JRootPane) parent;
        Rectangle b = root.getBounds();
        Insets i = root.getInsets();
        int nextY = 0;
        int w = b.width - i.right - i.left;
        int h = b.height - i.top - i.bottom;
        if (root.getLayeredPane() != null) {
            root.getLayeredPane().setBounds(i.left, i.top, w, h);
        }
        if (root.getGlassPane() != null) {
            root.getGlassPane().setBounds(i.left, i.top, w, h);
        }
        // Note: This is laying out the children in the layeredPane,
        // technically, these are not our children.
        if (root.getWindowDecorationStyle() != JRootPane.NONE && (root.getUI() instanceof MetalRootPaneUI)) {
            JComponent titlePane = ((MetalRootPaneUI) root.getUI()).getTitlePane();
            if (titlePane != null) {
                Dimension tpd = titlePane.getPreferredSize();
                if (tpd != null) {
                    int tpHeight = tpd.height;
                    titlePane.setBounds(0, 0, w, tpHeight);
                    nextY += tpHeight;
                }
            }
        }
        if (root.getMenuBar() != null) {
            Dimension mbd = root.getMenuBar().getPreferredSize();
            root.getMenuBar().setBounds(0, nextY, w, mbd.height);
            nextY += mbd.height;
        }
        if (root.getContentPane() != null) {
            Dimension cpd = root.getContentPane().getPreferredSize();
            root.getContentPane().setBounds(0, nextY, w, h < nextY ? 0 : h - nextY);
        }
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.0F;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.0F;
    }

    @Override
    public void invalidateLayout(Container target) {
    }
    
}
