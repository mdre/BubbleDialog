import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import "./BubbleDialogOverlayEvents.js"

/**
 * `bubble-dialog-overlay`
 * An overlay element
 *
 * @customElement
 * @polymer
 */
class BubbleDialogOverlay extends PolymerElement {
    
    static get template() {
        return html`
                <template>
                    <style>
                        .overlay {
                            z-index: 200;
                            position: fixed;
                            /*
                              Despite of what the names say, <vaadin-overlay> is just a container
                              for position/sizing/alignment. The actual overlay is the overlay part.
                            */
                            /*
                              Default position constraints: the entire viewport. Note: themes can
                              override this to introduce gaps between the overlay and the viewport.
                            */
                            top: 0;
                            right: 0;
                            bottom: 0;
                            left: 0;
                            /*background-color: #666666;
                            opacity: 0.5;*/
                        }
                    </style> 

                    <div id="overlay" 
                         class="overlay"
                         onClick="onOverlayClick(this, event);"
                         >
                    </div>
                </template>
                `
    }

    static get is() {
        return 'bubble-dialog-overlay';
    }

}
        
window.customElements.define(BubbleDialogOverlay.is, BubbleDialogOverlay);
        