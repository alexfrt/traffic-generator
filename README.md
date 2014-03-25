Traffic Generator
===
A simple Java application that generates TCP traffic between a server and N hosts in a computer network.

Basic concepts
----
It contains two main modules, server and client. The server is able to support as many clients that your PC and network supports.

When the server is started, it listen to a specified TCP port for new clients to be connected. The server just send random data to all clients, at a specific bitrate, while the clients just receive and measure how many bits has arrived at the last second, printing the results on stdout.

TODO list
----
* Convert to a Maven application
* Include UDP traffic
* Support bitrate variation
* Measure losses
* Support user file transfer
* Check the need of use [Netty] instead of Java Socket API

Version
----
0.1

License
----
MIT

[Netty]:http://netty.io