package org.firstinspires.ftc.teamcode.teleop;

import com.ftc16626.missioncontrol.util.profiles.PilotProfile;
import com.ftc16626.missioncontrol.util.profiles.StickControl;
import com.ftc16626.missioncontrol.util.profiles.StickResponseCurve;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystem.RadicalOpMode;
import org.firstinspires.ftc.teamcode.teleop.subsystem.SubsystemTeleCapstone;
import org.firstinspires.ftc.teamcode.teleop.subsystem.SubsystemTeleDrive;
import org.firstinspires.ftc.teamcode.teleop.subsystem.SubsystemTeleFoundationGrabber;
import org.firstinspires.ftc.teamcode.teleop.subsystem.SubsystemTeleIntake;
import org.firstinspires.ftc.teamcode.teleop.subsystem.SubsystemTeleLift;
import org.firstinspires.ftc.teamcode.teleop.subsystem.SubsystemTeleStoneGuide;
import org.firstinspires.ftc.teamcode.teleop.subsystem.SubsystemTeleVirtual4Bar;
import org.firstinspires.ftc.teamcode.util.gamepadextended.DriverInterface;
import org.firstinspires.ftc.teamcode.util.gamepadextended.listener.GamepadEventName;
import org.firstinspires.ftc.teamcode.util.gamepadextended.listener.GamepadEventType;
import org.firstinspires.ftc.teamcode.util.gamepadextended.listener.GamepadListener;
import org.firstinspires.ftc.teamcode.util.gamepadextended.listener.GamepadType;

@TeleOp
public class MainTeleop extends RadicalOpMode implements GamepadListener {
  private SubsystemTeleDrive subsystemTeleDrive;
  private SubsystemTeleIntake subsystemTeleIntake;
  private SubsystemTeleFoundationGrabber subsystemTeleFoundationGrabber;
  private SubsystemTeleVirtual4Bar subsystemTeleVirtual4Bar;
  private SubsystemTeleLift subsystemTeleLift;
  private SubsystemTeleStoneGuide subsystemTeleStoneGuide;
  private SubsystemTeleCapstone subsystemTeleCapstone;

  private DriverInterface driverInterface;

  private PilotProfile enzoProfile = new PilotProfile("Enzo's Profile",
      StickControl.STRAFE_LEFT_TURN_RIGHT_STICK, false, false,
      true, false, StickResponseCurve.RAW, false);

  @Override
  public void onInit() {
    driverInterface = new DriverInterface(gamepad1, gamepad2, this);
    driverInterface.driver.setProfile(enzoProfile);

    subsystemTeleDrive = new SubsystemTeleDrive(robot, driverInterface);
    subsystemTeleIntake = new SubsystemTeleIntake(robot, driverInterface);
    subsystemTeleFoundationGrabber = new SubsystemTeleFoundationGrabber(robot, driverInterface);
    subsystemTeleVirtual4Bar = new SubsystemTeleVirtual4Bar(robot, driverInterface);
    subsystemTeleStoneGuide = new SubsystemTeleStoneGuide(robot, driverInterface);
    subsystemTeleLift = new SubsystemTeleLift(robot, driverInterface);
    subsystemTeleCapstone = new SubsystemTeleCapstone(robot, driverInterface);

    subsystemHandler.add(robot);
    subsystemHandler.add(subsystemTeleDrive);
    subsystemHandler.add(subsystemTeleIntake);
    subsystemHandler.add(subsystemTeleFoundationGrabber);
    subsystemHandler.add(subsystemTeleVirtual4Bar);
    subsystemHandler.add(subsystemTeleStoneGuide);
    subsystemHandler.add(subsystemTeleLift);
    subsystemHandler.add(subsystemTeleCapstone);
  }

  @Override
  public void update() {
    driverInterface.update();
  }

  @Override
  public void actionPerformed(GamepadEventName eventName, GamepadEventType eventType,
      GamepadType gamepadType) {

  }
}
