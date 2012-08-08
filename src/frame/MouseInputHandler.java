/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Curt
 */
class MouseInputHandler implements MouseInputListener {
    /**
     * Set to true if the drag operation is moving the window.
     */
    private boolean isMovingWindow;
    /**
     * Used to determine the corner the resize is occuring from.
     */
    private int dragCursor;
    /**
     * X location the mouse went down on for a drag operation.
     */
    private int dragOffsetX;
    /**
     * Y location the mouse went down on for a drag operation.
     */
    private int dragOffsetY;
    /**
     * Width of the window when the drag started.
     */
    private int dragWidth;
    /**
     * Height of the window when the drag started.
     */
    private int dragHeight;
    private final Window window;
    private final MetalRootPaneUI outer;

    MouseInputHandler(Window window, final MetalRootPaneUI outer) {
        this.window = window;
        this.outer = outer;
    }

    @Override
    public void mousePressed(MouseEvent ev) {
        JRootPane rootPane = outer.getRootPane();
        if (rootPane.getWindowDecorationStyle() == JRootPane.NONE) {
            return;
        }
        Point dragWindowOffset = ev.getPoint();
        Window w = (Window) ev.getSource();
        if (w != null) {
            w.toFront();
        }
        Point convertedDragWindowOffset = SwingUtilities.convertPoint(w, dragWindowOffset, outer.getTitlePane());
        Frame f = null;
        Dialog d = null;
        if (w instanceof Frame) {
            f = (Frame) w;
        } else if (w instanceof Dialog) {
            d = (Dialog) w;
        }
        int frameState = (f != null) ? f.getExtendedState() : 0;
        if (outer.getTitlePane() != null && outer.getTitlePane().contains(convertedDragWindowOffset)) {
            if ((f != null && ((frameState & Frame.MAXIMIZED_BOTH) == 0) || (d != null)) && dragWindowOffset.y >= MetalRootPaneUI.BORDER_DRAG_THICKNESS && dragWindowOffset.x >= MetalRootPaneUI.BORDER_DRAG_THICKNESS && dragWindowOffset.x < w.getWidth() - MetalRootPaneUI.BORDER_DRAG_THICKNESS) {
                isMovingWindow = true;
                dragOffsetX = dragWindowOffset.x;
                dragOffsetY = dragWindowOffset.y;
            }
        } else if (f != null && f.isResizable() && ((frameState & Frame.MAXIMIZED_BOTH) == 0) || (d != null && d.isResizable())) {
            dragOffsetX = dragWindowOffset.x;
            dragOffsetY = dragWindowOffset.y;
            dragWidth = w.getWidth();
            dragHeight = w.getHeight();
            dragCursor = getCursor(calculateCorner(w, dragWindowOffset.x, dragWindowOffset.y));
        }
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
        if (dragCursor != 0 && window != null && !window.isValid()) {
            // Some Window systems validate as you resize, others won't,
            // thus the check for validity before repainting.
            window.validate();
            outer.getRootPane().repaint();
        }
        isMovingWindow = false;
        dragCursor = 0;
    }

    @Override
    public void mouseMoved(MouseEvent ev) {
        JRootPane root = outer.getRootPane();
        if (root.getWindowDecorationStyle() == JRootPane.NONE) {
            return;
        }
        Window w = (Window) ev.getSource();
        Frame f = null;
        Dialog d = null;
        if (w instanceof Frame) {
            f = (Frame) w;
        } else if (w instanceof Dialog) {
            d = (Dialog) w;
        }
        // Update the cursor
        int cursor = getCursor(calculateCorner(w, ev.getX(), ev.getY()));
        if (cursor != 0 && ((f != null && (f.isResizable() && (f.getExtendedState() & Frame.MAXIMIZED_BOTH) == 0)) || (d != null && d.isResizable()))) {
            w.setCursor(Cursor.getPredefinedCursor(cursor));
        } else {
            w.setCursor(outer.lastCursor);
        }
    }

    private void adjust(Rectangle bounds, Dimension min, int deltaX, int deltaY, int deltaWidth, int deltaHeight) {
        bounds.x += deltaX;
        bounds.y += deltaY;
        bounds.width += deltaWidth;
        bounds.height += deltaHeight;
        if (min != null) {
            if (bounds.width < min.width) {
                int correction = min.width - bounds.width;
                if (deltaX != 0) {
                    bounds.x -= correction;
                }
                bounds.width = min.width;
            }
            if (bounds.height < min.height) {
                int correction = min.height - bounds.height;
                if (deltaY != 0) {
                    bounds.y -= correction;
                }
                bounds.height = min.height;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent ev) {
        Window w = (Window) ev.getSource();
        Point pt = ev.getPoint();
        if (isMovingWindow) {
            Point eventLocationOnScreen = ev.getLocationOnScreen();
            w.setLocation(eventLocationOnScreen.x - dragOffsetX, eventLocationOnScreen.y - dragOffsetY);
        } else if (dragCursor != 0) {
            Rectangle r = w.getBounds();
            Rectangle startBounds = new Rectangle(r);
            Dimension min = w.getMinimumSize();
            switch (dragCursor) {
                case Cursor.E_RESIZE_CURSOR:
                    adjust(r, min, 0, 0, pt.x + (dragWidth - dragOffsetX) - r.width, 0);
                    break;
                case Cursor.S_RESIZE_CURSOR:
                    adjust(r, min, 0, 0, 0, pt.y + (dragHeight - dragOffsetY) - r.height);
                    break;
                case Cursor.N_RESIZE_CURSOR:
                    adjust(r, min, 0, pt.y - dragOffsetY, 0, -(pt.y - dragOffsetY));
                    break;
                case Cursor.W_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX, 0, -(pt.x - dragOffsetX), 0);
                    break;
                case Cursor.NE_RESIZE_CURSOR:
                    adjust(r, min, 0, pt.y - dragOffsetY, pt.x + (dragWidth - dragOffsetX) - r.width, -(pt.y - dragOffsetY));
                    break;
                case Cursor.SE_RESIZE_CURSOR:
                    adjust(r, min, 0, 0, pt.x + (dragWidth - dragOffsetX) - r.width, pt.y + (dragHeight - dragOffsetY) - r.height);
                    break;
                case Cursor.NW_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX, pt.y - dragOffsetY, -(pt.x - dragOffsetX), -(pt.y - dragOffsetY));
                    break;
                case Cursor.SW_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX, 0, -(pt.x - dragOffsetX), pt.y + (dragHeight - dragOffsetY) - r.height);
                    break;
                default:
                    break;
            }
            if (!r.equals(startBounds)) {
                w.setBounds(r);
                // Defer repaint/validate on mouseReleased unless dynamic
                // layout is active.
                if (Toolkit.getDefaultToolkit().isDynamicLayoutActive()) {
                    w.validate();
                    outer.getRootPane().repaint();
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent ev) {
        Window w = (Window) ev.getSource();
        outer.lastCursor = w.getCursor();
        mouseMoved(ev);
    }

    @Override
    public void mouseExited(MouseEvent ev) {
        Window w = (Window) ev.getSource();
        w.setCursor(outer.lastCursor);
    }

    @Override
    public void mouseClicked(MouseEvent ev) {
        Window w = (Window) ev.getSource();
        Frame f = null;
        if (w instanceof Frame) {
            f = (Frame) w;
        } else {
            return;
        }
        Point convertedPoint = SwingUtilities.convertPoint(w, ev.getPoint(), outer.getTitlePane());
        int state = f.getExtendedState();
        if (outer.getTitlePane() != null && outer.getTitlePane().contains(convertedPoint)) {
            if ((ev.getClickCount() % 2) == 0 && ((ev.getModifiers() & InputEvent.BUTTON1_MASK) != 0)) {
                if (f.isResizable()) {
                    if ((state & Frame.MAXIMIZED_BOTH) != 0) {
                        f.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
                    } else {
                        f.setExtendedState(state | Frame.MAXIMIZED_BOTH);
                    }
                }
            }
        }
    }

    /**
     * Returns the corner that contains the point <code>x</code>,
     * <code>y</code>, or -1 if the position doesn't match a corner.
     */
    private int calculateCorner(Window w, int x, int y) {
        Insets insets = w.getInsets();
        int xPosition = calculatePosition(x - insets.left, w.getWidth() - insets.left - insets.right);
        int yPosition = calculatePosition(y - insets.top, w.getHeight() - insets.top - insets.bottom);
        if (xPosition == -1 || yPosition == -1) {
            return -1;
        }
        return yPosition * 5 + xPosition;
    }

    /**
     * Maps from positions to cursor type. Refer to calculateCorner and
     * calculatePosition for details of this.
     */
    static final int[] cursorMapping = new int[]
    { Cursor.NW_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.N_RESIZE_CURSOR,
             Cursor.NE_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
      Cursor.NW_RESIZE_CURSOR, 0, 0, 0, Cursor.NE_RESIZE_CURSOR,
      Cursor.W_RESIZE_CURSOR, 0, 0, 0, Cursor.E_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR, 0, 0, 0, Cursor.SE_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR,
             Cursor.SE_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
    };

    /**
     * Returns the Cursor to render for the specified corner. This returns
     * 0 if the corner doesn't map to a valid Cursor
     */
    private int getCursor(int corner) {
        if (corner == -1) {
            return 0;
        }
        return cursorMapping[corner];
    }

    /**
     * Returns an integer indicating the position of <code>spot</code>
     * in <code>width</code>. The return value will be:
     * 0 if < BORDER_DRAG_THICKNESS
     * 1 if < CORNER_DRAG_WIDTH
     * 2 if >= CORNER_DRAG_WIDTH && < width - BORDER_DRAG_THICKNESS
     * 3 if >= width - CORNER_DRAG_WIDTH
     * 4 if >= width - BORDER_DRAG_THICKNESS
     * 5 otherwise
     */
    private int calculatePosition(int spot, int width) {
        if (spot < MetalRootPaneUI.BORDER_DRAG_THICKNESS) {
            return 0;
        }
        if (spot < MetalRootPaneUI.CORNER_DRAG_WIDTH) {
            return 1;
        }
        if (spot >= (width - MetalRootPaneUI.BORDER_DRAG_THICKNESS)) {
            return 4;
        }
        if (spot >= (width - MetalRootPaneUI.CORNER_DRAG_WIDTH)) {
            return 3;
        }
        return 2;
    }
    
}
