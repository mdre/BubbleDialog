package com.awesomecontrols.bubbledialog;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.Style;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag("quick-popup")
//@StyleSheet("frontend://bower_components/menubar/cards.css")
@JsModule("./bubbledialgo/bubble-dialog.js")
public class BubbleDialog extends PolymerTemplate<IBubbleDialogModel> implements  HasSize, HasTheme, HasStyle, HasComponents {

    private final static Logger LOGGER = Logger.getLogger(BubbleDialog.class .getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.FINER);
        }
    }
    
    @Id("bubble-content")
    Div bubbleContent;
    
    
    double top;
    double left;
    
    
    Element targetId;
    
    
    public enum Align {
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT,
        TOP_LEFT
    }
    
    Align alignTo = Align.TOP_RIGHT;
    
    int x_offset = 0;
    int y_offset = 0;
    
    /**
     * track the visibility state off the component.
     */
    private boolean visibilityState = false;
    private List<IBubbleDialogVisibilityEvent> visibilityEventListeners;
    
    /**
     * the content to be shown
     */
    Component content;
    
    // overlay utilziado para mostrar el QuickPopup
    BubbleDialogOverlay overlay;
    
    /**
     * Create a bubbleContent near to the target component
     * @param target is the ID of the target component
     * @param content  the bubbleContent content.
     */
    public BubbleDialog(Element target, Component content) {
        this.targetId = target;
        
        
        overlay = new BubbleDialogOverlay();
        this.content = content;
        
        this.bubbleContent.removeAll();
        this.bubbleContent.add(this.content);
        
        this.overlay.addComponent(this);
    }
    
    private void setPosition(double top, double left) {
        this.top = top;
        this.left = left;
        bubbleContent.getStyle().set("top", ""+top+"px");
        bubbleContent.getStyle().set("left", ""+left+"px");
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
        UI.getCurrent().add(overlay);
        
        LOGGER.log(Level.FINER, "targetId: "+this.targetId);
        getElement().callFunction("updatePositionAndShow",this.targetId);
        
        this.fireVisibilityChangeEvent();
    }
    
    /**
     * Hide the bubbleContent
     */
    public void hide() {
        this.overlay.hide();
        this.fireVisibilityChangeEvent();
    }
    
    @ClientCallable
    private void targetPosition(double top, double right, double bottom, double left) {
        LOGGER.log(Level.FINER, "showInternal!!!!");
        // agregar el overlay
        double popupTop = top;
        double popupLeft = right;
        
        switch (this.alignTo) {
            case TOP_RIGHT:
                popupTop = top + this.y_offset;
                popupLeft = right + this.x_offset;
                break;
                
            case BOTTOM_RIGHT:
                popupTop = bottom + this.y_offset;
                popupLeft = right + this.x_offset;
                break;
                
            case BOTTOM_LEFT:
                popupTop = bottom + this.y_offset;
                popupLeft = left + this.x_offset;
                break;
                
            case TOP_LEFT:
                popupTop = top + this.y_offset;
                popupLeft = left + this.x_offset;
                break;
        }
        
        this.setPosition(popupTop,popupLeft);
    }
    
    /**
     * Set the component align based on target ID
     * @param align enum with the available target aligns
     * @return this
     */
    public BubbleDialog setAlign(Align align) {
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
        return this.visibilityState;
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
        if (this.visibilityEventListeners == null) {
            this.visibilityEventListeners = new ArrayList<>();
        }
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

