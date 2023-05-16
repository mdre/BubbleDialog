import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import 'bubbledialog/bubble-dialog-css-loader.js';

/**
 * `bubble-dialog`
 * A BubbleDialog element
 *
 * @customElement
 * @polymer
 */
class BubbleDialog extends ThemableMixin(PolymerElement) {
    static get template() {
        return html`
        <style>
        </style> 
        
        <div id="bubble" class="bubble active">
            <div id="bubble-content" class="bubble-content"></div>
            <div id="bubble-arrow" class="bubble-arrow">
                <div id="bubble-arrow-inside" class=""></div>
            </div>
        </div>
        `;
    }
    
    static get is() {
        return 'bubble-dialog';
    }
    
    static get properties() {
        return {
            prop1: {
                type: String,
                value: 'bubble-dialog'
            },
            targetid: {
                type: String,
                value: ''
            }
        };
    }

    updatePositionAndShow(targetid) {
        //console.log("***************************************");
        //console.log(this);
        //var targetid = this.getProperty("targetid");

        // 
        console.log("Searching ", targetid, "...");
        var rect = targetid.getBoundingClientRect();
        console.log(rect.top, rect.right, rect.bottom, rect.left);

        this.$server.targetPosition(rect.top, rect.right, rect.bottom, rect.left);
    }

    guessSize(targetid) {

    }

    hideTimeout(bubble, timeout) {
        setTimeout(function() {
            bubble.classList.remove("active");
        }, timeout);
    }

    hide() {
        bubble.classList.remove("active");
    }

};

customElements.define(BubbleDialog.is, BubbleDialog);