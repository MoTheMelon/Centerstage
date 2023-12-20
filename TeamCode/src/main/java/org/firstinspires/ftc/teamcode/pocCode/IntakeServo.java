package org.firstinspires.ftc.teamcode.pocCode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
//

@TeleOp(name = "Intake Servo", group = "Linear Opmode")

public class IntakeServo extends LinearOpMode {

    Servo intakeLeft;
    Servo intakeRight;
    ElapsedTime runtime = new ElapsedTime();
    ElapsedTime servoTime = new ElapsedTime();

    Character lastBtn = null;

    @Override
    public void runOpMode() throws InterruptedException {
        intakeLeft = hardwareMap.get(Servo.class, "leftSvr");
        intakeRight = hardwareMap.get(Servo.class, "rightSvr");

        waitForStart();
        telemetry.addData("left servo ", intakeLeft.getPosition());
        telemetry.addData("right servo ", intakeRight.getPosition());
        telemetry.addData("servo time: ", servoTime.time());
        telemetry.update();

        //outside pos
        intakeRight.setPosition(0.9);
        intakeLeft.setPosition(0.15);

        while (opModeIsActive())
            if (gamepad1.a) {
                intakeLeft.setPosition(intakeLeft.getPosition() - (gamepad1.left_stick_y * 0.001));
                intakeRight.setPosition(intakeRight.getPosition() - (gamepad1.right_stick_y * 0.001));
                lastBtn = 'a';
            }
            //middle pos
            if (gamepad1.b) {
                intakeRight.setPosition(.82);
                servoTime.reset();
                lastBtn = 'b';

            }
            //inside pos
            if (gamepad1.y) {
                intakeLeft.setPosition(.557);
                servoTime.reset();
                lastBtn = 'y';

            }

            //after x seconds have passed, what button was last pressed? then set position.
            if (servoTime.time() > .75) {

                switch (lastBtn) {

                    case 'b':
                        intakeLeft.setPosition(0.25);
                        break;
                    case 'y':
                        intakeRight.setPosition(0.672);
                        break;
                    default:
                        lastBtn = null;
                        break;

                }

        }
        telemetry.addData("left servo ", intakeLeft.getPosition());
        telemetry.addData("right servo ", intakeRight.getPosition());
        telemetry.addData("servo time: ", servoTime.time());
        telemetry.update();
    }
}
