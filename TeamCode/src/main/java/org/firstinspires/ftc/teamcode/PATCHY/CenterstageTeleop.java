package org.firstinspires.ftc.teamcode.PATCHY;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.DigitalChannel;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.hardwareCS;

import org.firstinspires.ftc.teamcode.RoadrunnerUtilStuff.drive.SampleMecanumDrive;
//


@TeleOp(name = "CenterstageTeleop", group = "Linear Opmode")
//@Disabled
public class CenterstageTeleop extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();

    hardwareCS robot = new hardwareCS();

    //ElapsedTime servoTime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.addLine("Have fun!");
        telemetry.addLine("Get your TeleOp! Hot and fresh!");
        telemetry.update();
        robot.init(hardwareMap);

        //start
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

             //GAMEPAD 1 CONTROLS

            drive.setWeightedDrivePower(
                    new Pose2d(
                            gamepad1.left_stick_y * (1 - (gamepad1.right_trigger * 0.6)),
                            gamepad1.left_stick_x * .8 * (1 - (gamepad1.right_trigger * 0.6)),
                            gamepad1.right_stick_x * (1 - (gamepad1.right_trigger * 0.6))
                    )
            );

            // adding gamepad two controls here fellas
            /*
             Controls:
                Intake:
                    A = Take in pixels
                    B = Shoot them out
                    X = Kill Intake power
                    Right Bumper = Pull servo arms back in
                Hanger:
                    DPad.Up = Extend hanger
                    Dpad.Down = Retract hanger
                    Dpad.Right = Kill Hanger power
             */

            //intake
            if (gamepad2.a) {
                robot.mtrI.setDirection(DcMotor.Direction.REVERSE);
                robot.mtrI.setPower(0.75);
            }
            if (gamepad2.b) {
                robot.mtrI.setDirection(DcMotor.Direction.FORWARD);
                robot.mtrI.setPower(0.75);
            }
            if (gamepad2.x) {
                robot.mtrI.setPower(0);
            }
            //intake servos
            /*
            if (gamepad2.right_bumper) {

             intakeRight.setPosition(0.5);
             servoTime.reset();

                if (servoTime.time() > 0.5) {
                    intakeLeft.setPosition(0.0);
                }

             }  else if (!gamepad2.right_bumper){

                intakeLeft.setPosition(0.5);
                servoTime.reset();

                 if (servoTime.time() > 0.5) {
                    intakeRight.setPosition(0.0);
                 }

             }
             */
            //hanging
            if (gamepad2.dpad_up) { //&& !limitSwitch.isPressed() && runtime.time() > 60) { <- comment back in when ready
                robot.mtrHang.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.mtrHang.setPower(0.3);

            } else if (gamepad2.dpad_down) {//&& limitSwitch.isPressed() && runtime.time() > 60) { <- comment back in when ready
                robot.mtrHang.setDirection(DcMotor.Direction.FORWARD);
                robot.mtrHang.setZeroPowerBehavior(BRAKE);
                robot.mtrHang.setPower(0.5);
            } else if (gamepad2.dpad_right) {
                robot.mtrHang.setPower(0);
            }

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x:", poseEstimate.getX());
            telemetry.addData("y:", poseEstimate.getY());
            telemetry.addData("heading:", poseEstimate.getHeading());

            telemetry.update();
            drive.update();

        }
    }

}
