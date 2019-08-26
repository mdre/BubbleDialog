import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
//<link rel="import" href="card-styles.html">
import "./card-styles.js"
import "./BubbleDialogEvents.js"

/**
 * `bubble-dialog`
 * A BubbleDialog element
 *
 * @customElement
 * @polymer
 */
class BubbleDialog extends PolymerElement {
    static get template() {
        return html`
            <style include="card-styles">
                
                /* BUBBLE */
                .bubble {
                  box-shadow: 0 1px 3px rgba(black,.2);
                  background-color: #fff;
                  border: 1px solid;
                  position: absolute;
                  z-index: 1201 !important;
                  border-color: #bbb #bbb #a8a8a8;
                  padding: 16px;
                  width: 255px;
                  line-height: 17px;
                  visibility: hidden; 
                  opacity: 0;
                  left: 16px; 
                  top: 48px; 
                  @include transition(all 0.218s);
                  &.active {
                    visibility: visible; 
                    opacity: 1;
                  }
                }

                /* bubble arrow */
                .bubble-arrow {
                  position: absolute;
                  top: 20px;
                  &:before, &:after {
                    content: "";
                    position: absolute;
                    height: 0;
                    width: 0;
                  }
                  &:before {
                    border: 9px solid;
                    border-color: transparent #bbb;
                    top: -9px;
                  }
                  &:after {
                    border: 9px solid;
                    border-color: transparent #fff;
                    top: -8px;
                  }
                }

                .bubble-arrowleft {
                  left: -8px;
                  &:before, &:after {
                    border-left-width: 0;
                  }
                } 
            </style> 

            <div id="bubble" 
                 class="bubble card card-1"
                 <!--onClick="onBubbleClick(this, event);"-->
                 >
                <div id="bubble-content"></div>
                <div class="bubble-arrow bubble-arrowleft"></div>
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
        console.log("Searching ",targetid,"...");
        var rect = targetid.getBoundingClientRect();
        console.log(rect.top, rect.right, rect.bottom, rect.left);

        this.$server.targetPosition(rect.top, rect.right, rect.bottom, rect.left);
    }
}

window.customElements.define(BubbleDialog.is, BubbleDialog);
