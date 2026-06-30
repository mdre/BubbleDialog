import { LitElement, html, unsafeCSS } from 'lit';
import styles from './bubble-dialog.css?inline';

/**
 * `bubble-dialog`
 * A BubbleDialog element
 *
 * @customElement
 */
class BubbleDialog extends LitElement {

    // Renderiza en shadow DOM (default de Lit). LitTemplate + @Id requieren que los
    // elementos estén en el renderRoot (shadow). Un createRenderRoot() => this (light DOM)
    // rompe el binding @Id y la inyección del tema (theme-for) en Vaadin 24/25, dejando el
    // árbol de Flow inconsistente al adjuntar el contenido (TypeError en el renderer).
    static styles = unsafeCSS(styles);

    render() {
        return html`
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
        this.renderRoot?.querySelector('#bubble')?.classList.remove("active");
    }

};

customElements.define(BubbleDialog.is, BubbleDialog);