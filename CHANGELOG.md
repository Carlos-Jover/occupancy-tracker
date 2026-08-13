# Changelog

All notable changes to this project will be documented in this file.

## [0.1.0] - Initial Project Setup

### Added
- Created the initial project architecture.
- Added the `Main`, `Tracker`, and `EventRecord` classes.
- Implemented occupancy tracking with enter, exit, and reset functionality.
- Added occupancy percentage calculation.
- Added event history recording for occupancy changes.
- Added validation for occupancy configuration values.
- Updated the README with the project purpose, Version 1 goals, and future roadmap.

## [0.2.0] - Event History Added

### Added
- Added a menu option to view occupancy event history.
- Added formatted timestamps to occupancy events.
- Added a message when no events have been recorded.

### Changed
- Improved event record formatting for readability.

## [0.3.0] - Occupancy Percentage Bar Added and Customizing High Occupancy

### Added
- Added a visual occupancy percentage bar.
- Added low, moderate, and high occupancy levels.
- Added a menu option to configure the high occupancy level.

### Changed
- Capped the displayed occupancy percentage at 100%.

## [0.4.0] Manual Occupancy Correction Added and Input Verification

### Added
- Added manual occupancy correction.
- Added a menu option to manually correct the current occupancy.
- Added input validation to prevent non-integer input from crashing the program.
- Added validation for occupancy values that cannot be negative.

### Changed
- Improved menu input handling for invalid values.

## [0.5.0] - Persistent Event History

### Added
- Added automatic event history saving to a file.
- Added system start and system stop events.

### Changed
- Added the date to event records.

## [0.6.0] - Code Refactoring

### Added

- Added reusable integer input validation.
- Added methods for displaying the occupancy percentage bar, occupancy level, and event history.

### Changed

- Refactored the Main class to reduce repeated code.
- Simplified integer input validation.
- Improved the occupancy percentage bar calculation.

## [0.7.0] - Startup State Restoration

### Added
- Added occupancy restoration after an unexpected program shutdown.
- Added detection of normal shutdowns versus unexpected shutdowns.
- Added `SYSTEM RESTORED` event recording when occupancy is recovered.

### Changed
- Updated startup behavior to restore the previous occupancy after an unexpected shutdown.
- Normal startup now begins at zero after a recorded `SYSTEM STOP`.

## [0.8.0] - Peak Occupancy Tracking

### Added

- Added peak occupancy tracking for the current session.
- Added date and time tracking for when peak occupancy occurs.
- Added peak occupancy information to the occupancy display.
- Added peak occupancy tracking after crash restoration and manual occupancy correction.