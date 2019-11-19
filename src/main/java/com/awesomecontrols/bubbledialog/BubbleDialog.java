package com.awesomecontrols.bubbledialog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.ClassList;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.Style;

@Tag("bubble-dialog")
// @StyleSheet("frontend://bower_components/bubbledialog/cards.css")
@JsModule("./bubbledialog/bubble-dialog.js")
public class BubbleDialog extends PolymerTemplate<IBubbleDialogModel> implements HasTheme, HasStyle, HasComponents {

    /**
     *
     */
    private static final long serialVersionUID = 5630472247035116753L;

    private final static Logger LOGGER = Logger.getLogger(BubbleDialog.class.getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.FINER);
        }
    }
    @Id("bubble")
    Div bubble;

    @Id("bubble-content")
    Div bubbleContent;

    @Id("bubble-arrow")
    Div bubbleArrow;

    @Id("bubble-arrow-inside")
    Div bubbleArrowInside;

    double top;
    double left;
    double height;
    double width;

    int bubblePadding = 2;

    int timeout;

    int arrowRightLength = 8; // arrow size when bubble open to the rigth
    int arrowLeftLength = 8; // arrow size when bubble open to the left
    int arrowBottomLength = 8; // arrow size when bubble open to the bottom
    int arrowTopLength = 8; // // arrow size when bubble open to the top

    int arrowBaseWidth = 8;
    double targetMiddle;
    int arrowOffset = 0;

    int arrowWidth = 8;
    int arrowHeight = 8;

    Element targetId;

    public enum Align {
        TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT, UP_RIGHT, UP_LEFT, TOP_LEFT
    }

    Align alignTo = Align.TOP_RIGHT;

    int x_offset = 0;
    int y_offset = 0;

    /**
     * track the visibility state off the component.
     */
    private boolean visibilityState = false;
    private List<IBubbleDialogVisibilityEvent> visibilityEventListeners = new ArrayList<>();;

    /**
     * the content to be shown
     */
    Component content;

    /**
     * Create a bubbleContent near to the target component
     * 
     * @param target  is the ID of the target component
     * @param content the bubbleContent content.
     */
    public BubbleDialog(Element target, Component content) {
        this.targetId = target;

        this.content = content;

        this.bubbleContent.removeAll();
        this.bubbleContent.add(this.content);

    }

    /**
     * Set the bubbleContent content
     * 
     * @param content to shown
     */
    public void setContent(Component content) {
        this.bubbleContent.removeAll();
        this.bubbleContent.add(content);
    }

    /**
     * Remove de bubbleContent content
     */
    public void clearContent() {
        this.bubbleContent.removeAll();
    }

    // public void setPopupForComponentId(String target) {
    // this.targetId = target;
    // }

    /**
     * Show the bubbleContent
     */
    public void show() {
        LOGGER.log(Level.FINER, "llamando a updatePositionAndShow...");
        UI.getCurrent().add(this);

        LOGGER.log(Level.FINER, "targetId: " + this.targetId);
        getElement().callFunction("updatePositionAndShow", this.targetId);

        this.fireVisibilityChangeEvent();
    }

    /**
     * Hide the bubbleContent
     */
    public void hide() {
        LOGGER.log(Level.FINER, "hide bubble");
        bubble.removeClassName("active");
        // getElement().callFunction("hide");
        this.visibilityState = false;
        this.fireVisibilityChangeEvent();
    }

    /**
     * Toggle the visibility state of the bubble
     */
    public void toggle() {
        LOGGER.log(Level.FINER, "toggle visible");
        if (this.isVisible()) {
            LOGGER.log(Level.FINER, "hidding");
            this.hide();
        } else {
            LOGGER.log(Level.FINER, "showing");
            this.show();
        }
    }

    @ClientCallable
    private void targetPosition(double top, double right, double bottom, double left) {
        LOGGER.log(Level.FINER, "top: " + top + " right: " + right + " bottom: " + bottom + " left: " + left);
        LOGGER.log(Level.FINER, "bundle width: " + this.width + " height: " + this.height);
        // agregar el overlay
        double popupTop = top;
        double popupLeft = right;

        // quitar las referencias del arrow existente.
        bubbleArrow.removeClassName("arrow-up");
        bubbleArrow.removeClassName("arrow-down");
        bubbleArrow.removeClassName("arrow-left");
        bubbleArrow.removeClassName("arrow-right");

        // limpiar el posicionamiento
        bubbleArrow.getStyle().remove("left");
        bubbleArrow.getStyle().remove("right");
        bubbleArrow.getStyle().remove("top");
        bubbleArrow.getStyle().remove("bottom");
        bubbleArrow.getStyle().remove("height");
        bubbleArrow.getStyle().remove("width");

        LOGGER.log(Level.FINER, "current align: " + this.alignTo);
        switch (this.alignTo) {
        case TOP_RIGHT:
            popupTop = top + this.y_offset;
            popupLeft = right + this.x_offset + this.arrowRightLength + (bubblePadding * 2);
            this.targetMiddle = (top + bottom) / 2 - this.arrowBaseWidth / 2 - top + arrowOffset;

            this.arrowHeight = this.arrowBaseWidth;
            this.arrowWidth = this.arrowRightLength;

            bubbleArrow.setClassName("bubble-arrow arrow-left");
            bubbleArrowInside.setClassName("arrow-left-inside");

            bubbleArrow.getStyle().set("left", "-" + (arrowRightLength + bubblePadding + 1) + "px");
            bubbleArrow.getStyle().set("top", "" + targetMiddle + "px");
            bubbleArrow.getStyle().set("width", "" + arrowWidth + "px");
            bubbleArrow.getStyle().set("height", "" + arrowHeight + "px");

            break;

        case BOTTOM_RIGHT:
            popupTop = bottom + this.y_offset + this.arrowBottomLength;
            popupLeft = right + this.x_offset - this.width;

            this.targetMiddle = (left + right) / 2 - this.arrowBaseWidth / 2 - left;

            this.arrowHeight = this.arrowBottomLength;
            this.arrowWidth = this.arrowBaseWidth;

            bubbleArrow.setClassName("bubble-arrow arrow-up");
            bubbleArrowInside.setClassName("arrow-up-inside");

            bubbleArrow.getStyle().set("right", (8 + arrowOffset) + "px");
            bubbleArrow.getStyle().set("top", "-" + (this.arrowBottomLength + 2) + "px");
            bubbleArrow.getStyle().set("width", "" + arrowWidth + "px");
            bubbleArrow.getStyle().set("height", "" + arrowHeight + "px");

            break;

        case BOTTOM_LEFT:
            popupTop = bottom + this.y_offset + this.arrowBottomLength;
            popupLeft = left + this.x_offset;

            this.targetMiddle = (left + right) / 2 - this.arrowBaseWidth / 2 - left;

            this.arrowHeight = this.arrowBottomLength;
            this.arrowWidth = this.arrowBaseWidth;

            bubbleArrow.setClassName("bubble-arrow arrow-up");
            bubbleArrowInside.setClassName("arrow-up-inside");

            bubbleArrow.getStyle().set("left", (8 + arrowOffset) + "px");
            bubbleArrow.getStyle().set("top", "-" + (this.arrowBottomLength + 2) + "px");
            bubbleArrow.getStyle().set("width", "" + arrowWidth + "px");
            bubbleArrow.getStyle().set("height", "" + arrowHeight + "px");
            break;

        case TOP_LEFT:
            popupTop = top + this.y_offset;
            popupLeft = left + this.x_offset - this.arrowRightLength - (bubblePadding * 2) - this.width;

            this.targetMiddle = (top + bottom) / 2 - this.arrowBaseWidth / 2 - top + arrowOffset;

            this.arrowHeight = this.arrowBaseWidth;
            this.arrowWidth = this.arrowRightLength;

            bubbleArrow.setClassName("bubble-arrow arrow-right");
            bubbleArrowInside.setClassName("arrow-right-inside");

            bubbleArrow.getStyle().set("right", "-" + (arrowRightLength + (bubblePadding * 2) - 1) + "px");
            bubbleArrow.getStyle().set("top", "" + targetMiddle + "px");
            bubbleArrow.getStyle().set("width", "" + arrowWidth + "px");
            bubbleArrow.getStyle().set("height", "" + arrowHeight + "px");

            break;

        case UP_LEFT:
            popupTop = top + this.y_offset - this.arrowTopLength - this.height - (bubblePadding * 2);
            popupLeft = left + this.x_offset;
            LOGGER.log(Level.FINER, "height: " + this.height + " arrowTopLength: " + this.arrowTopLength);
            LOGGER.log(Level.FINER, "Bubble top: " + popupTop + " left: " + popupLeft);

            this.targetMiddle = (left + right) / 2 - this.arrowBaseWidth / 2 - left;

            this.arrowHeight = this.arrowBottomLength;
            this.arrowWidth = this.arrowBaseWidth;

            bubbleArrow.setClassName("bubble-arrow arrow-down");
            bubbleArrowInside.setClassName("arrow-down-inside");

            bubbleArrow.getStyle().set("left", (8 + arrowOffset) + "px");
            bubbleArrow.getStyle().set("bottom", "-" + (this.arrowBottomLength + 1) + "px");
            bubbleArrow.getStyle().set("width", "" + arrowWidth + "px");
            bubbleArrow.getStyle().set("height", "" + arrowHeight + "px");
            break;

        case UP_RIGHT:
            popupTop = top + this.y_offset - this.arrowTopLength - this.height - (bubblePadding * 2);
            popupLeft = right + this.x_offset - this.width;
            LOGGER.log(Level.FINER, "height: " + this.height + " arrowTopLength: " + this.arrowTopLength);
            LOGGER.log(Level.FINER, "Bubble top: " + popupTop + " left: " + popupLeft);

            this.targetMiddle = (left + right) / 2 - this.arrowBaseWidth / 2 - left;

            this.arrowHeight = this.arrowBottomLength;
            this.arrowWidth = this.arrowBaseWidth;

            bubbleArrow.setClassName("bubble-arrow arrow-down");
            bubbleArrowInside.setClassName("arrow-down-inside");

            bubbleArrow.getStyle().set("right", (8 + arrowOffset) + "px");
            bubbleArrow.getStyle().set("bottom", "-" + (this.arrowBottomLength + 1) + "px");
            bubbleArrow.getStyle().set("width", "" + arrowWidth + "px");
            bubbleArrow.getStyle().set("height", "" + arrowHeight + "px");
            break;

        }

        this.setPosition(popupTop, popupLeft);
        this.bubble.addClassName("active");
        this.visibilityState = true;

        if (this.timeout > 0) {
            getElement().callFunction("hideTimeout", this.bubble, this.timeout);
        }
    }

    /**
     * Return the current align.
     * 
     * @return align
     */
    public Align getAlign() {
        return this.alignTo;
    }

    /**
     * Set the offset of the arrow.
     * 
     * @param o
     * @return
     */
    public BubbleDialog setArrowOffset(int o) {
        this.arrowOffset = o;
        return this;
    }

    private void setPosition(double top, double left) {
        this.top = top;
        this.left = left;
        bubble.getStyle().set("top", "" + top + "px");
        bubble.getStyle().set("left", "" + left + "px");

    }

    /**
     * Set the timeout in millis to automatic hide the bubble. Setting it to zero
     * disable the effect. This is the default value;
     * 
     * @param t in millis
     * @return a self reference
     */
    public BubbleDialog setTimeout(int t) {
        this.timeout = t;
        return this;
    }

    /**
     * Return the current timeout.
     * 
     * @return
     */
    public int getTimeout() {
        return this.timeout;
    }

    /**
     * Set the component align based on target ID
     * 
     * @param align enum with the available target aligns
     * @return this
     */
    public BubbleDialog setAlign(Align align) {
        this.alignTo = align;
        return this;
    }

    /**
     * Set the x offset to be added to the align
     * 
     * @param offset in pixels
     * @return this
     */
    public BubbleDialog setXOffset(int offset) {
        this.x_offset = offset;
        return this;
    }

    /**
     * Set the y offset to be added to the align
     * 
     * @param offset in pixels
     * @return this
     */
    public BubbleDialog setYOffset(int offset) {
        this.y_offset = offset;
        return this;
    }

    public boolean isVisible() {
        LOGGER.log(Level.FINER, "active: " + this.bubble.getClassNames().contains("active"));
        return this.bubble.getClassNames().contains("active");
    }

    public int getArrowLength() {
        int len = 8;
        switch (this.alignTo) {
        case TOP_RIGHT:
            len = arrowRightLength;
        case BOTTOM_RIGHT:
        case BOTTOM_LEFT:
            len = arrowBottomLength;
            break;
        case TOP_LEFT:
            len = arrowLeftLength;
        case UP_LEFT:
        case UP_RIGHT:
            len = arrowTopLength;
        }
        return len;
    }

    /**
     * Set the lenght of the arrow when the bubble is aligned to the right of the
     * target component. The arrown point to the left.
     * 
     * @param arrowLength
     * @return
     */
    public BubbleDialog setArrowRightLength(int arrowLength) {
        this.arrowRightLength = arrowLength;
        return this;
    }

    /**
     * Set the lenght of the arrow when the bubble is aligned bellow the target
     * component. The arrow point to up.
     * 
     * @param arrowLength
     * @return
     */
    public BubbleDialog setArrowBottomLength(int arrowLength) {
        this.arrowBottomLength = arrowLength;
        return this;
    }

    /**
     * Set the lenght of the arrow when the bubble is aligned to the left of the
     * target component. * The arrow point to the right.
     * 
     * @param arrowLength
     * @return
     */
    public BubbleDialog setArrowLeftLength(int arrowLength) {
        this.arrowLeftLength = arrowLength;
        return this;
    }

    /**
     * Set the lenght of the arrow when the bubble is aligned above the target
     * component. * The arrow point to down.
     * 
     * @param arrowLength
     * @return
     */
    public BubbleDialog setArrowTopLength(int arrowLength) {
        this.arrowTopLength = arrowLength;
        return this;
    }

    public int getArrowBaseWidth() {
        return arrowBaseWidth;
    }

    public BubbleDialog setArrowBaseWidth(int arrowBaseWidth) {
        this.arrowBaseWidth = arrowBaseWidth;
        return this;
    }

    @Override
    public Style getStyle() {
        return bubbleContent.getStyle();
    }

    public double getHeight() {
        return this.height;
    }

    public BubbleDialog setHeight(double h) {
        this.height = h;
        this.bubble.getStyle().set("height", height + "px");
        return this;
    }

    public double getWidth() {
        return this.width;
    }

    public BubbleDialog setWidth(double w) {
        this.width = w;
        this.bubble.getStyle().set("width", w + "px");
        return this;
    }

    @Override
    public void removeClassNames(String... classNames) {
        this.bubble.removeClassNames(classNames); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addClassNames(String... classNames) {
        this.bubble.addClassNames(classNames); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasClassName(String className) {
        return this.bubble.hasClassName(className); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setClassName(String className, boolean set) {
        this.bubble.setClassName(className, set); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClassList getClassNames() {
        return this.bubble.getClassNames(); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getClassName() {
        return this.bubble.getClassName(); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setClassName(String className) {
        this.bubble.setClassName(className); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeClassName(String className) {
        return this.bubble.removeClassName(className); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addClassName(String className) {
        this.bubble.addClassName(className); // To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Add a visibility change listener.
     * 
     * @param event listener
     * @return this
     */
    public BubbleDialog addVisibilityChangeListener(IBubbleDialogVisibilityEvent event) {
        this.visibilityEventListeners.add(event);
        return this;
    }

    /**
     * Remove the event listener.
     * 
     * @param event listener
     * @return true if the listener exist and was removed.
     */
    public boolean removeVisibilityChangeListener(IBubbleDialogVisibilityEvent event) {
        this.visibilityEventListeners.remove(event);
        return this.visibilityEventListeners.remove(event);
    }

    private void fireVisibilityChangeEvent() {
        for (IBubbleDialogVisibilityEvent visibilityEventListener : this.visibilityEventListeners) {
            visibilityEventListener.visibilityChanged();
        }
    }
}
