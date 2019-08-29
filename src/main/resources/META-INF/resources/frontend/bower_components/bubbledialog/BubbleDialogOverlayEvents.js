function onOverlayClick(menuElement, event) {
    var flowElement = menuElement.parentNode.host;
    flowElement.$server.onOverlayClick();
}


