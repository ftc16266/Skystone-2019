package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.subsystem.HardwareSubsystem;
import org.firstinspires.ftc.teamcode.subsystem.RadicalOpMode;

public class SubsystemFoundationGrabber extends HardwareSubsystem {

  private final Servo leftServo, rightServo;
  private final String[] servoIds = new String[] { "servoFoundationLeft", "servoFoundationRight" };

  private final double LEFT_MIN = 0;
  private final double LEFT_MAX = 1;

  private final double RIGHT_MIN = 0;
  private final double RIGHT_MAX = 1;

  public SubsystemFoundationGrabber(Robot robot, RadicalOpMode opMode) {
    super(robot, opMode);

    leftServo = robot.hwMap.get(Servo.class, servoIds[0]);
    rightServo = robot.hwMap.get(Servo.class, servoIds[1]);

    leftServo.scaleRange(LEFT_MIN, LEFT_MAX);
    rightServo.scaleRange(RIGHT_MIN, RIGHT_MAX);
  }

  @Override
  public void onMount() {
    raise();
  }

  public void drop() {
    leftServo.setPosition(1);
    rightServo.setPosition(0);
  }

  public void raise() {
    leftServo.setPosition(0);
    rightServo.setPosition(1);
  }
}