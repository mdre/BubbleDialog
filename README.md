# BubbleDialog
BubbleDialog compoment for Vaadin Flow 14+  

Show a bubble dialog near to the target component.

## Versions

* Version 0.+, 1.+ is compatible with Vaadin 14.+
* Version 2.+ is compatible with Vaadin 23.+

## Example:

```Java
Button btn = new Button("B");
btn.addClickListener((event) -> {
    bd.toggle();
});
btn.setThemeName("contained primary");
VerticalLayout lyBubble = new VerticalLayout();
lyBubble.add(new Label("bubble label"));
lyBubble.add(new Label("msg 1"));
lyBubble.add(new Label("msg 2"));
lyBubble.add(new Label("msg3"));
bd = new BubbleDialog(btn.getElement(), lyBubble).setArrowLength(55).setTimeout(10000);

```

