package net.xmercerweiss.saveovertime;

import java.util.Set;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class Main
{
    private static final String DOLLAR_FMT = "$%,d.%02d";

    private static final String SAVE_TOTAL_MSG_FMT = 
        "To earn %s by %s, you'll need to save...\n";

    private static final String SAVE_PER_UNIT_MSG_FMT = 
        "\t%s per %s (%d, %s)\n";

    private static final String USE_MSG =
        "save <+F> <yyyy-MM-dd> <yyyy-MM-dd>"; 

    private static final String HELP_MSG = 
        """
        Save over time:
            %s

            Calculates how much money must be saved every {day|week|month} between two dates to hit a goal.

            +F - A positive floating point number.
            1st Date - The date one begins saving money. Must come before the second date.
            2nd Date - The date by which the goal will be reached.

            Dates MUST be given in precisely the listed format.
        """.formatted(USE_MSG);

    private static final String BAD_DATES_MSG = "Bad dates:\n\tFirst date must come before second date; both dates must match yyyy-MM-dd";

    private static final String BAD_USE_MSG = 
        "Bad usage:\n\texpected \"%s\"".formatted(USE_MSG);

    private static final Set<String> HELP_ARGS = Set.of(
        "help",
        "-help",
        "--help",
        "h",
        "-h"
    );

    private static final ChronoUnit[] UNITS = {
        ChronoUnit.DAYS,
        ChronoUnit.WEEKS,
        ChronoUnit.MONTHS,
        ChronoUnit.YEARS
    };

    private static final DateTimeFormatter LOCAL_DATE_FMT = 
        DateTimeFormatter.ofPattern("LLLL d yyyy");
    
    public static void main(String[] args)
    {
        casefoldAndCheckOptions(args);
        try
        {
            long n = (long) Math.abs(Double.parseDouble(args[0]) * 100);
            LocalDate first = LocalDate.parse(args[1]);
            LocalDate second = LocalDate.parse(args[2]);
            if (!second.isAfter(first))
                throw new DateTimeException("");
            System.out.printf(
                SAVE_TOTAL_MSG_FMT, 
                asDollars(n),
                second.format(LOCAL_DATE_FMT)
            );
            for (ChronoUnit u : UNITS)
            {
                long unitsBetween = first.until(second, u);
                if (unitsBetween <= 0)
                    break;
                long cents = (long) Math.ceil((double) n / unitsBetween);
                System.out.printf(
                    SAVE_PER_UNIT_MSG_FMT, 
                    asDollars(cents), 
                    u.toString().toLowerCase().replace("s", ""),
                    unitsBetween,
                    asDollars(cents*unitsBetween)
                );
            }
        }
        catch (DateTimeException dte)
        {
            System.err.println(BAD_DATES_MSG);
            System.exit(1);
        }
        catch (Exception e)
        {
            System.err.println(BAD_USE_MSG);
            System.exit(1);
        }
    }

    private static void casefoldAndCheckOptions(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            args[i] = args[i].toLowerCase();
            if (HELP_ARGS.contains(args[i]))
            {
                System.out.println(HELP_MSG);
                System.exit(0);
            }
        }
    }

    private static String asDollars(long cents)
    {
        if (cents <= 0)
            return "less than $0.01";
        return DOLLAR_FMT.formatted(cents / 100, cents % 100);
    }
}

