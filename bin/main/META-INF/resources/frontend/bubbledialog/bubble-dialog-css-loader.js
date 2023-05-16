//import { registerStyles, css, unsafeCSS } from '@vaadin/vaadin-themable-mixin/register-styles.js';
import styles from 'bubbledialog/bubble-dialog.css';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
  <dom-module id="bubble-dialog-css" theme-for="bubble-dialog">
    <template><style>${styles}</style></template>
  </dom-module>`;
document.head.appendChild($_documentContainer.content);


//registerStyles('bubble-dialog', css`
//}
//`,{include: ['bubble-dialog-css']});

//const $_documentContainer = document.createElement('template');
//
//$_documentContainer.innerHTML = `
//  <dom-module id="rich-text-editor-css">
//    <template><style>${styles}</style></template>
//  </dom-module>`;
//document.head.appendChild($_documentContainer.content);


