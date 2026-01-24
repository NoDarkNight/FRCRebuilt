package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.Constants;
import frc.robot.testingdashboard.SubsystemBase;
import frc.robot.testingdashboard.TDNumber;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase{

    private static Shooter m_Shooter = null;

    private SparkFlex m_leftFlywheelMotor;
    private SparkFlex m_rightFlywheelMotor;
    private SparkFlex m_turretRotationMotor;
    private SparkFlex m_hoodAngleMotor;
    private SparkFlex m_chimneyMotor;

    private SparkFlexConfig m_leftConfig;

    private double m_shootP;
    private double m_shootI;
    private double m_shootD;
    private TDNumber m_TDshootP;
    private TDNumber m_TDshootI;
    private TDNumber m_TDshootD;

    private TDNumber m_TDvelocity;
    private TDNumber m_TDmeasuredVelocity;
    private TDNumber m_TDmeasuredCurrent;


    private Shooter(){
        super("Shooter");

        m_leftFlywheelMotor = new SparkFlex(cfgInt("leftFlywheelCANid"), MotorType.kBrushless);
        m_rightFlywheelMotor = new SparkFlex(cfgInt("rightFlywheelCANid"), MotorType.kBrushless);
        m_turretRotationMotor = new SparkFlex(cfgInt("turretRotationCANid"), MotorType.kBrushless);
        m_hoodAngleMotor = new SparkFlex(cfgInt("hoodAngleMotorCANid"), MotorType.kBrushless);
        m_chimneyMotor = new SparkFlex(cfgInt("chimneyMotorCANid"), MotorType.kBrushless);

        m_TDshootP = new TDNumber(this, getName(), "P");
        m_TDshootI = new TDNumber(this, getName(), "I");
        m_TDshootD = new TDNumber(this, getName(), "D");
        m_TDshootP.set(cfgInt("kP"));
        m_TDshootI.set(cfgInt("kI"));
        m_TDshootD.set(cfgInt("kD"));

        m_shootP = m_TDshootP.get();
        m_shootI = m_TDshootI.get();
        m_shootD = m_TDshootD.get();
        
        m_leftConfig = new SparkFlexConfig();
        m_leftConfig.closedLoop.pid(m_shootP, m_shootI, m_shootD);
        m_leftConfig.encoder.velocityConversionFactor(Constants.ShooterConstants.kVelocityFactor);

        SparkFlexConfig rightConfig = new SparkFlexConfig();
        rightConfig.follow(cfgInt("leftFlywheelCANid"));
        rightConfig.inverted(true);
        m_rightFlywheelMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_TDvelocity = new TDNumber(this, getName(), "Target Velocity");
        m_TDvelocity.set(0);

        m_TDmeasuredVelocity = new TDNumber(this, getName(), "Measured Velocity");
        m_TDmeasuredCurrent = new TDNumber(this, getName(), "Measured Current");

        if (RobotMap.S_ENABLED == true){

        }

        
    }
    public static Shooter getInstance() {
        if (m_Shooter == null) {
            m_Shooter = new Shooter();
        }
        return m_Shooter;
    } 
    
}
