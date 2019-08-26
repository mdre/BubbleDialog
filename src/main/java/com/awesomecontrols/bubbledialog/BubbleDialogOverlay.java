package com.awesomecontrols.bubbledialog;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag("bubble-dialog-overlay")
//@StyleSheet("frontend://bower_components/menubar/cards.css")
@JsModule("./bubbledialog/bubble-dialog-overlay.html")
class BubbleDialogOverlay extends PolymerTemplate<TemplateModel>  {
    private final static Logger LOGGER = Logger.getLogger(BubbleDialogOverlay.class .getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.FINER);
        }
    }
    
    @Id("overlay")
    Div overlay;
    BubbleDialog bubbleDialog;
    
    public BubbleDialogOverlay() {
    }
    
    public void addComponent(BubbleDialog qp) {
        bubbleDialog = qp;
        overlay.add(bubbleDialog);
    }
    
    /**
     * Close and remove the overlay
     */
    public void hide() {
        UI.getCurrent().remove(this);
    }
    
    @ClientCallable
    private void onOverlayClick() {
        LOGGER.log(Level.INFO, "Overlay Click detectado!");
        this.bubbleDialog.hide();
    }
    
    
}

