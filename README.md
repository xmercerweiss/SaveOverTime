# SaveOverTime
This is a simple Java program that lets you calculate how much money you 
need to save every day, week, month, or year to let you meet a goal by a
certain date. I just made this one evening for my own use, but I might as
well save it up here :P

## Installation
Pick a spot in your file system (we'll call it `DIR/`) and create `DIR/net/xmercerweiss/saveovertime`, then save `Main.class`
into that location. Afterwards, add the following line to your `~/.bash_aliases` file:
```bash
alias 'save=java -cp ~/PATH/TO/DIR net.xmercerweiss.saveovertime.Main'
```

## Usage
Assuming you added the above alias and rebooted your terminal, you should be able to
run the program using the `save` command. Congratulations! Below is the `help` page
for said command:
```
Save over time:
    save <+F> <yyyy-MM-dd> <yyyy-MM-dd>

    Calculates how much money must be saved every {day|week|month} between two dates to hit a goal.

    +F - A positive floating point number.
    1st Date - The date one begins saving money. Must come before the second date.
    2nd Date - The date by which the goal will be reached.

    Dates MUST be given in precisely the listed format.
```

## License
This project is licensed under the permissive Zero-Clause BSD License. Copyright 2025, Xavier Mercerweiss.
 
