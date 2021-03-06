
### SplainTime

I needed a dead simple time tracking app. By 'dead simple' I don't mean some predictive mapping of my favored clients and a process listener that knows which programs were in focus at any given time. I mean the simplest I can get away with: SplainTime is a text entry box that outputs a timestamp and a time difference in a text file with the date on it. It also has a button to open the text file.

![windows screenshot](https://farm1.staticflickr.com/430/19979150661_40037d989c_o.jpg)  
On windows, pic hosted by flickr.

### Run

    java -jar SplainTime.jar
    or
    *double click an executable jar*

Enter a description of what you are doing. If you missed entering on time, you can adjust by starting a description with either a negative number (minutes) or a negative 12-hour time (sans AM/PM). So, "-33 innuendo battle" will log a time 33 minutes in the past. "-8:30 roundly defeated" will log 8:30 and try to adjust the am/pm to be a time earlier than now. Because of that, it can not, currently, honor a future clock time. However, it will honor positive minutes in the future.

I've designed/use splaintime to run different sessions each day, by naming the resulting file with today's date. To wrap the day, press the _finish_ button until it counts down to zero.

Also, if you mess up an entry and a minute hasn't elapsed, entering "j8x" will remove the last tag.

SplainTime checks for default values in a file called _Splaintime.properties_

![output](https://farm4.staticflickr.com/3834/19351574364_91f8ce13c0_o.png)  
Output, pic hosted by flickr

### License

Though I like the spirit of WTFPL, SplainTime is released as [Fair License](http://fairlicense.org/) So,

SplainTime &copy; [Nicholas Prado](www.nzen.ws)  
Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.

DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.

### TODO

* interpret subtask flag
* more tests

![linux screenshot](https://farm1.staticflickr.com/422/19787232209_fd6d79d140_o_d.png)  
On linux, hosted by flickr.
