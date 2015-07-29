
### SplainTime (extract branch: in progress)

I needed a dead simple time tracking app. By 'dead simple' I don't mean some predictive mapping of my favored clients and a process listener that knows which programs were in focus at any given time. I mean the simplest I can get away with: SplainTime is a text entry box that outputs a timestamp and a time difference in a text file with the date on it. It also has a button to open the text file.

[put a picture here]

### Run

    java -jar SplainTime.jar

Nope, no arguments.

### License

Though I like the spirit of WTFPL, SplainTime is released as [Fair License](http://fairlicense.org/) So,

SplainTime &copy; Nicholas Prado  
Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.

DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.

### TODO

* use a cache to restore closed sessions
* interpret time adjust flag
* interpret subtask flag
* more tests