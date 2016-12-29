# YQBoots Actuator
YQBoots Actuator does the health checking and metrics analysing to the Spring MVC-based application which enables the actuator features in [Spring Boot](https://www.spring.io/spring-boot).

[Spring Boot](https://www.spring.io/spring-boot) includes a number of additional features to help you monitor and manage your application when it’s pushed to production. You can choose to manage and monitor your application using HTTP endpoints, with JMX or even by remote shell(SSH or Telnet). Auditing, health and metrics gathering can be automatically applied to your application.

YQBoots Actuator brings the UI screen of the [Spring Boot](https://www.spring.io/spring-boot)  HTTP endpoints, you can view the health status of the Web, Database, Memory, Disk, etc. Also it uses a timer (5 Seconds) to refresh the latest status of the detailed application.

Further more, you can register more applications which enable the actuator features of [Spring Boot](https://www.spring.io/spring-boot).

YQBoots Actuator enables the following endpoints:

* Application Information - Displays arbitrary application info.
* Health - Shows application health information (when the application is secure, a simple "status" when accessed over an unauthenticated connection or full message details when authenticated).
* Metrics - Shows "metrics" information for the current application.
* Environments - Exposes properties from Spring's <code>ConfigurableEnvironment</code>.
* Config Properties - Displays a collated list of all <code>@ConfigurationProperties</code>.
* Mappings - Displays a collated list of all <code>@RequestMapping</code> paths.
* Beans - Displays a complete list of all the Spring beans in your application.
* Auto Configuration - Displays an auto-configuration report showing all autoconfiguration candidates and the reason why they "were" or "were not" applied.
* Trace - Displays trace information (by default the last 100 HTTP requests).
* Dump - Performs a thread dump.

