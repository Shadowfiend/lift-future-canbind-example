Lift Future CanBind Example
===========================

An example that shows just how easy it is to provide support for different
right-hand-sides for Lift CSS selector transforms. Here we combine that with
Lift's `lazy-load` snippet to make it so you can have a Scala `Future` or a
Lift `LAFuture` on the right hand side of a CSS selector transform. In these
cases, we will send the page down and when the future completes its value will
be used to render whatever depends on it.

All of the code to do this is in the
[FutureBinds.scala](src/main/scala/code/snippet/FutureBinds.scala) file.
Importing `FutureBinds._` will provide the aforementioned support. The demo
app's bindings are in
[HelloWorld.scala](src/main/scala/code/snippet/HelloWorld.scala), and
demonstrte both `LAFuture` and `Future` use.

30 LOC. Man I love this framework.
