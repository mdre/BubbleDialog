/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.awesomecontrols.bubbledialog;

import com.vaadin.flow.dom.Element;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
public interface IBubbleDialogModel extends TemplateModel {
    double getTop();
    double getLeft();
    
    String getTargetId();
    String setTargetId(Element targetid);
    
}
