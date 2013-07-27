Using jlibudev on the RaspberryPI
============

The main issue with using jlibudev on arm processors is that the jna jar is not packaged with linux arm native libraries. To deal with this one can install jna from the raspberrypi package manager.

The following launches the HardwareMonitorExample:
	
	#> sudo apt-get install libjna-java libudev-dev maven openjdk-6-jdk
	#> git clone https://github.com/nigelb/jlibudev.git
	#> cd jlibudev
	#> mvn package
	#> mvn dependency:copy-dependencies -DoutputDirectory=target/deps
	#> java -cp /usr/share/java/jna-3.2.7.jar:target/deps/jnaerator-runtime-0.10.jar:target/deps/log4j-1.2.14.jar:target/deps/purejavacomm-0.0.17.jar:target/deps/jna-jnaerator-0..jar:target/deps/ochafik-util-0.10.jar:target/jlibudev-0.0.1-175-SNAPSHOT.jar jlibudev.examples.HardwareMonitorExample

	Enumerated: /devices/virtual/tty/console
	Enumerated: /devices/virtual/tty/ptmx
	Enumerated: /devices/virtual/tty/tty
	Enumerated: /devices/virtual/tty/tty0
	Enumerated: /devices/virtual/tty/tty1
	Enumerated: /devices/virtual/tty/tty10
	Enumerated: /devices/virtual/tty/tty11
	Enumerated: /devices/virtual/tty/tty12
	Enumerated: /devices/virtual/tty/tty13
	Enumerated: /devices/virtual/tty/tty14
	Enumerated: /devices/virtual/tty/tty15
	Enumerated: /devices/virtual/tty/tty16
	Enumerated: /devices/virtual/tty/tty17
	Enumerated: /devices/virtual/tty/tty18
	Enumerated: /devices/virtual/tty/tty19

	.
	.
	.




