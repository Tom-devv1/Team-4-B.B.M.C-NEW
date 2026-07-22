# Gemini Experimental Agents

This directory contains experimental FTC OpModes and classes generated and refined by Gemini. These scripts focus on modularity, precise movement, and collaborative control.

## Files and Roles

### 1. `Shooter.java`
A helper class for the robot's shooter mechanism.
- **Hardware:** `shooterMotor` (DcMotor).
- **Features:** 
  - `init()`: Initializes the motor with `FLOAT` zero-power behavior.
  - `run()`: Accepts a trigger value (0.0 - 1.0) or a boolean override for full power.

### 2. `tellyOP_handoff.java` (TeleOp)
A unique TeleOp mode that allows two players to swap roles (Driver and Operator) dynamically during a match.
- **Controls:**
  - **Button A:** Claim "Active Driver" status.
  - **Left/Right Sticks:** Drive, strafe, and turn (Mecanum).
  - **Bumpers/Triggers:** Control intake and shooter (Operator role).
- **Handoff Logic:**
  - Gamepad 1 starts as the driver.
  - When control is transferred, the taking-over controller rumbles for 0.5s.
  - The controller losing control receives two short "blips".

### 3. `tellyop_autoleft.java` & `tellyop_autoright.java` (Autonomous)
Two precise encoder-based autonomous OpModes for starting on different sides.
- **Sequence:**
  1. **Move Forward:** 10 cm.
  2. **Turn:** 90 degrees (Left or Right depending on the file).
  3. **Move Forward:** 30 cm.
  4. **Attack:** Spins up the shooter flywheel for 1.5s, then runs the intake for 1.0s to launch the element.
- **Hardware Profile:**
  - Configured for GoBilda 5202/5203 series motors (537.7 counts/rev).
  - Configured for 104mm wheels.
  - `TRACK_WIDTH_CM` is set to 36.0cm for turning calculations.

## Recent Updates & Bug Fixes
- **Branching:** Split `tellyop_auto.java` into `tellyop_autoleft.java` and `tellyop_autoright.java` to support both field orientations.
- **Logic Correction:** Fixed a bug in `tellyOP_handoff.java` where rumble feedback occurred regardless of who held control.
- **Precision:** Replaced time-based movement with `RUN_TO_POSITION` encoder logic.
- **Safety:** Integrated `opModeIsActive()` checks throughout loops to ensure immediate robot shutdown on "Stop".
- **Telemetry:** Added live feedback for driver roles and autonomous progress.

---
*Note: This code is experimental. Always test on a blocks-and-mats field before full-speed deployment.*
