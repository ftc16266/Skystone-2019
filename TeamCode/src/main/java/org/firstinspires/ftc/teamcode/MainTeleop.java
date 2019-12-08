package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystem.RadicalOpMode;
import org.firstinspires.ftc.teamcode.subsystem.Subsystem;
import org.firstinspires.ftc.teamcode.subsystem.system.SubsystemDriverControl;

@TeleOp(name = "Main TeleOp", group = "Mecanum")
public class MainTeleop extends RadicalOpMode {
  @Override
  public void extendedInit() {
    Subsystem driverControl = new SubsystemDriverControl(robot, this);

    subsystemHandler.add(driverControl);
  }
}
