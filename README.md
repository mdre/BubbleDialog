# BubbleDialog
BubbleDialog compoment for Vaadin Flow 10+  

Show a bubble dialog near to the target component.

Example:

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

TODO:
- Let the bubble align left, top, bottom.
