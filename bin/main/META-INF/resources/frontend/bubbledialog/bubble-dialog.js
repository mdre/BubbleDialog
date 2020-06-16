import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';


/**
 * `bubble-dialog`
 * A BubbleDialog element
 *
 * @customElement
 * @polymer
 */
class BubbleDialog extends PolymerElement {
    static get template() {
        return html `
        <style include="bubble-dialog-custom">
            /* definir bubble-dialog-custom en la app para personalizar el componente */
            /* BUBBLE */
            .bubble {
                box-shadow: 0 1px 3px rgba(black,.2);
                background-color: #fff;
                border: 1px solid;
                position: absolute;
                z-index: 1201 !important;
                border-color: #bbb #bbb #a8a8a8;
                padding: 2px;
                /*width: 255px;*/
                line-height: 17px;
                visibility: hidden; 
                opacity: 0;
                -moz-transition: all 0.218s;
                -o-transition: all 0.218s;
                -webkit-transition: all 0.218s;
                transition: all 0.218s;
                
            }

            .bubble.active {
                visibility: visible;
                opacity: 1;
            }

            
            /* bubble arrow */
            .bubble-arrow {
                position: absolute;
                
                width: 8px; 
                height: 8px; 
                
                background-color: #fff;
                border: 1px solid #bbb;
            }



            /*-------------------------------------------------*/
            .arrow-up {
                
                clip-path: polygon(50% 0, 0 100%, 100% 100%);
                border-bottom: 2px solid transparent #fff;
                background: #bbb;
            }
            
            .arrow-up-inside {
                position: absolute;
                top: -0px;
                bottom: -3px;
                left: 0px;
                right: 0px;
                clip-path: polygon(50% 0, 0 100% , 100% 100%);
                background: #fff;
            }
            /*-------------------------------------------------*/
            
            /*-------------------------------------------------*/
            .arrow-right {
                clip-path: polygon(0 0, 0% 100%, 100% 0);
                border-right: 2px solid #bbb;
                background: #bbb;
            }
            
            .arrow-right-inside {
                position: absolute;
                top: 0px;
                bottom: -1px;
                left: -3px;
                right: 1px;
                clip-path: polygon(0 0, 0% 100%, 100% 0);
                background: #fff;
            }
            /*-------------------------------------------------*/
            
            /*-------------------------------------------------*/
            .arrow-left {
                /*
                las posiciones son en % horizontal y vertical dentro del recuadro del 
                div. Así, 100% 0, representa que se mueve a la esquina superior derecha.
                */
                clip-path: polygon(100% 0, 0 0 , 100% 100%);
                
                border-right: 2px solid #fff;
                background: #bbb;
                /* 
                width: 0; 
                height: 0;
                
                border-top: 8px solid transparent;
                border-bottom: 8px solid transparent; 
                
                border-right: 20px solid #fff; */
            }

            .arrow-left-inside {
                position: absolute;
                top: 0px;
                bottom: 0px;
                left: 0px;
                right: -3px;
                clip-path: polygon(100% 0, 0 0 , 100% 100%);
                background: #fff;
            }
            /*-------------------------------------------------*/
            
            /*-------------------------------------------------*/
            .arrow-down {
                clip-path: polygon(0 0, 50% 100%, 100% 0);
                border-top: 2px solid #fff;
                background: #bbb;
            }
            
            .arrow-down-inside {
                position: absolute;
                top: -2px;
                bottom: 0px;
                left: 1px;
                right: 1px;
                clip-path: polygon(0 0, 50% 100%, 100% 0);
                background: #fff;
            }
            /*-------------------------------------------------*/
            
            .bubble-content {
                overflow: auto;
                width: inherit;
                height: inherit;
            }
            
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