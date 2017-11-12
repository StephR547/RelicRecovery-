package org.firstinspires.ftc.teamcode.mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by StephanieRamirez on 9/22/17.
 */
@TeleOp(name = "MecanumTeleop")
public class MecanumTeleop extends LinearOpMode {

    private MecanumHardware robot = new MecanumHardware();
    int elevatorPosition;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            double forward = -gamepad1.left_stick_y;
            double strafeRight = gamepad1.left_stick_x;
            double rotateRight = gamepad1.right_stick_x;

          /* double heading = robot.imu.getheading();
            double headingInRadians = Math.toRadians(heading);

            double temp = forward * Math.cos(headingInRadians) + strafeRight * Math.sin(headingInRadians);
            strafeRight = -forward * Math.sin(headingInRadians) + strafeRight * Math.cos(headingInRadians);
            forward = temp;
            */

            double frontLeftPower = forward + strafeRight + rotateRight;
            double frontRightPower = forward - strafeRight - rotateRight;
            double backLeftPower = forward - strafeRight + rotateRight;
            double backRightPower = forward + strafeRight - rotateRight;

            double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));

            if (max > 1) {
                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.backRightMotor.setPower(backRightPower);

            servosControls();
            elevatorControls();
        }

    }

    public void servosControls() {

        double topWheels = (-gamepad2.left_stick_y / 2) + 0.5;

        robot.topLeftWheel.setPosition(topWheels);
        robot.topRightWheel.setPosition(topWheels);

        double bottomWheels = (-gamepad2.right_stick_y / 2) + 0.5;

        robot.bottomLeftWheel.setPosition(bottomWheels);
        robot.bottomRightWheel.setPosition(bottomWheels);

        //Bottom Pincher
        if (gamepad2.right_bumper){
            robot.bottomPincher.setPosition(0);
        }else if (gamepad2.right_trigger >= .5) {
            robot.bottomPincher.setPosition(1);
        }else {
            robot.bottomPincher.setPosition(.5);
        }

        //Top Pincher
        if (gamepad2.left_bumper){
            robot.topPincher.setPosition(0);
        }else if (gamepad2.left_trigger >= .5) {
            robot.topPincher.setPosition(1);
        }else {
            robot.topPincher.setPosition(.5);
        }

        //Top Pusher
        if (gamepad2.y){
            robot.topPusher.setPosition(1);
        }else if (gamepad2.x){
            robot.topPusher.setPosition(0);
        }else {
            robot.topPusher.setPosition(.5);
        }

        //Bottom Pusher
        if (gamepad2.b){
            robot.bottomPusher.setPosition(1);
        }else if (gamepad2.a){
            robot.bottomPusher.setPosition(0);
        }else {
            robot.bottomPusher.setPosition(.5);
        }

    }
    public void elevatorControls(){

        if (gamepad2.dpad_up){
            robot.elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.elevatorMotor.setPower(1);
            elevatorPosition = robot.elevatorMotor.getCurrentPosition();
        }else if (gamepad2.dpad_down){
            robot.elevatorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.elevatorMotor.setPower(-1);
            elevatorPosition = robot.elevatorMotor.getCurrentPosition();
        }else {
            robot.elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.elevatorMotor.setTargetPosition(elevatorPosition);
        }
    }
}