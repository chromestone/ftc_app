package org.firstinspires.ftc.teamcode.qorobotics.util;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by derekzhang on 11/12/16.
 */

public class Ticker {

    private ElapsedTime period;

    public Ticker() {

        period = new ElapsedTime();
    }

    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
