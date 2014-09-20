Dominion
========

This is the Dominion (http://en.wikipedia.org/wiki/Dominion_(card_game)) Game implemented with Java 8. I implemented this Project in a project at the University of Applied Science Munich.

Program Architecture
-------

This implementation is created based on the MVC principle. For this solution was a strict package structure created.

 - database: This package contains all the game data with read-write rights or only read rights.
 - logic: This package contains the logical doings of the cards and single game steps.
 - ui: This package contains the cpu and humand playable classes.

Code Style
--------

For a nice and effective code implementation of the project <a href="http://pmd.sourceforge.net/">PMD</a>, <a href="http://checkstyle.sourceforge.net/">checkstyle</a> and <a href="http://www.harukizaemon.com/simian/">simian</a> was used.

Usage
--------

The game is implemented with a few "artificial intelligence" and random CPU-Players. It is possible to play the game through the network with telnet. As human player there is also the option to play with a nice user interface which is created with <a href="http://docs.oracle.com/javase/8/javase-clienttechnologies.htm">Java-FX</a>.

![alt tag](https://raw.githubusercontent.com/FHellmann/Dominion/master/screenshots/JavaFx.png)

If you wanne play the game, just see the help below.

<pre>
You have the following options:
	-h or -?                will show you the help.
	-r <filename>           allows you to record the game to a file. This file will be stored in the temp-directory/Dominion/.
	-p <filepath>           allows you to automaticly replay the game from a recorded game file.allows you to automaticly replay the game from a recorded game file. This will grab the file from the temp-directory/Dominion/<filename>.
	-v                      allows you to see the running game and it's players. Possible to use if only roboters play.
	ConsolePlayer=<name>    You will play in the command line.
	JavaFxPlayer=<name>     You will play in an graphical user interface made with Java-FX.
	NetPlayer=<name>        Someone else can play with you throw the network. (Port=2014)
	RobotDefender=<name>    A roboter played by the cpu.
	RobotMilitia=<name>     A roboter played by the cpu.
	RobotSorcerer=<name>    A roboter played by the cpu.
	RobotX=<name>           A roboter played by the cpu.
	Robot1=<name>           A roboter played by the cpu.

All players have to get different names.
</pre>
