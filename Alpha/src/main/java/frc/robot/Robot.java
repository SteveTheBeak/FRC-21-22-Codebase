package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
<<<<<<< Updated upstream
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// // import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.PWMVictorSPX;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.PWMTalonSRX;

import java.util.DoubleSummaryStatistics;

import javax.swing.plaf.TreeUI;

import edu.wpi.first.wpilibj.AnalogGyro;
=======
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
>>>>>>> Stashed changes

import com.ctre.phoenix.motorcontrol.can.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Robot extends TimedRobot {

  //Motors
  TalonSRX talonLeft = new TalonSRX(5);
  VictorSPX victorLeft = new VictorSPX(4);

  TalonSRX talonRight = new TalonSRX(3);
  VictorSPX victorRight = new VictorSPX(6);

<<<<<<< Updated upstream
  // NPM ports 2,3 are used for controlling motors
  //private DifferentialDrive drive = new DifferentialDrive(new PWMTalonSRX(0), new PWMTalonSRX(3));
  private DifferentialDrive drive = new DifferentialDrive(new PWMVictorSPX(3), new PWMVictorSPX(2));
=======
  //Pneumatics
  Compressor comp = new Compressor(0);
  DoubleSolenoid arm = new DoubleSolenoid(2,5);
  DoubleSolenoid floats = new DoubleSolenoid(4,3);
  DoubleSolenoid shield1 = new DoubleSolenoid(6,1);
  DoubleSolenoid shield2 = new DoubleSolenoid(0,7);
>>>>>>> Stashed changes

  //data
  private final Timer timer = new Timer();
  private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  private double angle = 0;

  //control
  private final XboxController c = new XboxController(0);

  @Override
  public void robotInit() {
    comp.setClosedLoopControl(false);
    arm.set(Value.kForward);
    gyro.calibrate();
  }

  @Override
  public void robotPeriodic() {
    angle = gyro.getAngle();
    angle%=360;
    if (angle < -180)
      angle+=360;
    else if (angle > 180)
      angle-=360;
  }

  @Override
  public void autonomousInit() {
    timer.reset();
    timer.start();
  }

  private void garen(){
    double turnSpeed = -angle/225.0; //Max speed of 0.8
    if (Math.abs(angle) < 10) {
      turnSpeed = 0;
    }
    double leftSpeed = turnSpeed;
    double rightSpeed = turnSpeed;

    victorLeft.set(ControlMode.PercentOutput, leftSpeed);
    talonLeft.set(ControlMode.PercentOutput, leftSpeed);
    victorRight.set(ControlMode.PercentOutput, rightSpeed);
    talonRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  @Override
  public void autonomousPeriodic() { garen(); }

  @Override
  public void teleopInit() {
    gyro.reset();
  }   

  double aTarget = 0, bTarget = 0; // a for a button, b for bumpers"
  boolean compressing = false;

<<<<<<< Updated upstream
  /** This function is called periodically during operator control. */
  double forwardSpeed = 0;
  boolean mode = false;
  @Override
  public void teleopPeriodic() {
    //System.out.println("manual control");


    double turnSpeed = stick.getX();
    double targetSpeed = stick.getY();
  
    System.out.println(mode);
    if(stick.getRawButton(2)) //B on controller
    {
      mode = true;
    }
    if(stick.getRawButton(2) && mode == true)
    {
      mode = false;
    }

    if(mode)
    {
      drive.arcadeDrive(2*targetSpeed, 0.5*turnSpeed);
    }

    if(!mode)
    {
      drive.arcadeDrive(targetSpeed, 0.5*turnSpeed);

    }
=======
  @Override
  public void teleopPeriodic() {
>>>>>>> Stashed changes
    
    double turnSpeed = c.getX(Hand.kLeft)*Math.abs(c.getX(Hand.kLeft))/3;
    double forwardSpeed = -c.getY(Hand.kLeft)*Math.abs(c.getY(Hand.kLeft));
    double aTurn = aTarget-angle, bTurn = bTarget-angle;
    if (aTurn > 180) 
      aTurn -= 360;
    if (aTurn < -180) 
      aTurn += 360;
    if (bTurn > 180) 
      bTurn -= 360;
    if (bTurn < -180) 
      bTurn += 360;
    if (c.getStickButtonPressed(Hand.kLeft)) //Start driving straight
      aTarget = angle;
    if (c.getStickButton(Hand.kLeft)) //Continue driving straight
      turnSpeed = Math.max(-0.4,Math.min((aTurn)/80.0,0.4)); //Max speed of 0.997
    if (c.getBumperPressed(Hand.kLeft)) //saves orientation
      bTarget = angle;
    if (c.getBumper(Hand.kRight)) //orients to saved rotation
      turnSpeed = Math.max(-0.4,Math.min((bTurn)/80.0,0.4));  //Max speed of 0.997
    if (c.getStartButtonPressed()) {
      compressing = !compressing;
      comp.setClosedLoopControl(compressing);
    }
    if (c.getXButtonPressed()) 
      arm.set(Value.kReverse);
    if (c.getXButtonReleased())
      arm.set(Value.kForward);
    if (c.getYButtonPressed()) 
      floats.set(Value.kReverse);
    if (c.getYButtonReleased())
      floats.set(Value.kForward);
    if (c.getAButtonPressed()) 
      shield1.set(Value.kReverse);
    if (c.getAButtonReleased())
      shield1.set(Value.kForward);
    if (c.getBButtonPressed()) 
      shield2.set(Value.kReverse);
    if (c.getBButtonReleased())
      shield2.set(Value.kForward);
    System.out.println("angle: " + angle);

    double leftSpeed = (forwardSpeed+turnSpeed)/0.5;
    double rightSpeed = -(forwardSpeed-turnSpeed)/0.5;

    victorLeft.set(ControlMode.PercentOutput, leftSpeed);
    talonLeft.set(ControlMode.PercentOutput, leftSpeed);
    victorRight.set(ControlMode.PercentOutput, rightSpeed);
    talonRight.set(ControlMode.PercentOutput, rightSpeed);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
