package com.awesomecontrols.bubbledialog;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.Style;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag("bubble-dialog")
//@StyleSheet("frontend://bower_components/bubbledialog/cards.css")
@HtmlImport("bower_components/bubbledialog/bubble-dialog.html")
public class BubbleDialog extends PolymerTemplate<IBubbleDialogModel> implements  HasSize, HasTheme, HasStyle, HasComponents {

    private final static Logger LOGGER = Logger.getLogger(BubbleDialog.class .getName());
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
    
    double top;
    double left;
    
    int bubblePadding = 2;
    
    int timeout;
    
    int arrowLength = 8;
    int arrowBaseWidth = 8;
    double targetMiddle;
    
    int arrowWidth = 8;
    int arrowHeight = 8;
    
    
    Element targetId;
    
    
    public enum Align {
        RIGHT,
        BOTTOM,
        UP,
        LEFT
    }
    
    Align alignTo = Align.RIGHT;
    
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
     * @param target is the ID of the target component
     * @param content  the bubbleContent content.
     */
    public BubbleDialog(Element target, Component content) {
        this.targetId = target;
        
        
        this.content = content;
        
        this.bubbleContent.removeAll();
        this.bubbleContent.add(this.content);
        
    }
    
    
    
    /**
     * Set the bubbleContent content 
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
    
//    public void setPopupForComponentId(String target) {
//        this.targetId = target;
//    }
    
    /**
     * Show the bubbleContent
     */
    public void show() {
        LOGGER.log(Level.FINER, "llamando a updatePositionAndShow...");
        UI.getCurrent().add(this);
        
        LOGGER.log(Level.FINER, "targetId: "+this.targetId);
        getElement().callFunction("updatePositionAndShow",this.targetId);
        
        this.fireVisibilityChangeEvent();
    }
    
    /**
     * Hide the bubbleContent
     */
    public void hide() {
        LOGGER.log(Level.FINER, "hide bubble");
        bubble.removeClassName("active");
        //getElement().callFunction("hide");
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
        LOGGER.log(Level.FINER, "top: "+top+" right: "+right+" bottom: "+bottom+" left: "+left);
        // agregar el overlay
        double popupTop = top;
        double popupLeft = right;
        
        switch (this.alignTo) {
            case RIGHT:
                popupTop = top + this.y_offset;
                popupLeft = right + this.x_offset + this.arrowLength;
                this.targetMiddle = (top + bottom)/2 - this.arrowBaseWidth/2 - top;
                
                this.arrowHeight = this.arrowBaseWidth;
                this.arrowWidth = this.arrowLength;
                
                bubbleArrow.getStyle().set("left","-"+(arrowLength+bubblePadding+1)+"px");
                bubbleArrow.getStyle().set("top",""+targetMiddle+"px");
                bubbleArrow.getStyle().set("width",""+arrowWidth+"px");
                bubbleArrow.getStyle().set("height",""+arrowHeight+"px");
                
                break;
                
            case BOTTOM:
                popupTop = bottom + this.y_offset;
                popupLeft = right + this.x_offset + this.arrowLength;
                
                
                
                break;
                
            case LEFT:
                popupTop = bottom + this.y_offset;
                popupLeft = left + this.x_offset;
                
                break;
                
            case UP:
                popupTop = top + this.y_offset;
                popupLeft = left + this.x_offset;
                break;
        }
        
        
        this.setPosition(popupTop,popupLeft);
        this.bubble.addClassName("active");
        this.visibilityState = true;
        
        if (this.timeout>0) {
            getElement().callFunction("hideTimeout",this.bubble,this.timeout);
        }
    }
    
    private void setPosition(double top, double left) {
        this.top = top;
        this.left = left;
        bubble.getStyle().set("top", ""+top+"px");
        bubble.getStyle().set("left", ""+left+"px");
        
    }
    
    /**
     * Set the timeout in millis to automatic hide the bubble.
     * Setting it to zero disable the effect. This is the default value;
     * @param t in millis
     * @return a self reference
     */
    public BubbleDialog setTimeout(int t) {
        this.timeout = t;
        return this;
    }
    
    /**
     * Return the current timeout.
     * @return 
     */
    public int getTimeout() {
        return this.timeout;
    }
    
    /**
     * Set the component align based on target ID
     * @param align enum with the available target aligns
     * @return this
     */
    private BubbleDialog setAlign(Align align) {
        this.alignTo = align;
        return this;
    }
    
    /**
     * Set the x offset to be added to the align 
     * @param offset in pixels
     * @return this
     */
    public BubbleDialog setXOffset(int offset) {
        this.x_offset = offset;
        return this;
    }
    
    /**
     * Set the y offset to be added to the align 
     * @param offset in pixels
     * @return this 
     */
    public BubbleDialog setYOffset(int offset) {
        this.y_offset = offset;
        return this;
    }
    
    public boolean isVisible() {
        LOGGER.log(Level.FINER, "active: " +this.bubble.getClassNames().contains("active"));
        return this.bubble.getClassNames().contains("active");
    }

    public int getArrowLength() {
        return arrowLength;
    }

    public BubbleDialog setArrowLength(int arrowLength) {
        this.arrowLength = arrowLength;
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
    
    /**
     * Add a visibility change listener.
     * @param event listener
     * @return this
     */
    public BubbleDialog addVisibilityChangeListener(IBubbleDialogVisibilityEvent event) {
        this.visibilityEventListeners.add(event);
        return this;
    }
    
    /**
     * Remove the event listener.
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

