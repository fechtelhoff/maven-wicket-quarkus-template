# CDI - Implementierung

von Matthias Drummer
-> 	[mattdru/wicket-quarkus-cdi: CDI Quarkus Integration for Apache Wicket (GitHub)](https://github.com/mattdru/wicket-quarkus-cdi)

Wird in der Klasse `WicketApplication` eingebunden mittels:
```java
new CdiConfiguration().configure(this);
```

`CdiConfiguration` ersetzt hierbei die Implementierung von Wicket.
