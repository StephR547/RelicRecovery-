package org.firstinspires.ftc.teamcode.glyph.vacuum;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by StephanieRamirez on 12/2/17.
 */

public class VacuumValve {
    public Servo servo;

    public VacuumValve (Servo servo){
        this.servo = servo;
    }
    public void release (){
        servo.setPosition(.2);
    }
    public void close (){
        servo.setPosition(.8);
    }
    public void stop (){
        servo.setPosition(.5);
    }
}
