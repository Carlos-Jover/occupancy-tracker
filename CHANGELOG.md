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

## [0.9.0] - Custom Operating Hours

### Added
- Added a new class 'OperatingHours'.
- Added a method for customizing the operating hours.
- Added a menu option to customize the operating hours.
- Added a menu option to view the current operating hours.

## [0.10.0] - Open and Closed Hours Detection

### Added
- Added a method for checking whether a time is within the operating hours.

### Changed
- Updated the menu option for viewing operating hours to display whether the business is currently open or closed.

## [0.11.0] - Time-Weighted Average Occupancy

### Added
- Added calculation of elapsed time between occupancy events.
- Added time-weighted average occupancy calculations.

## [0.12.0] - Time-Weighted Average Occupancy Within Operating Hours and Test Simulation

### Added
- Added filtering so average occupancy only includes time within operating hours.
- Added a method for running a random traffic test simulation.
- Added a method that returns the time-weighted average occupancy.

## [0.13.0] - Historical Event Data Loading

### Added
- Added parsing of saved event records back into `EventRecord` objects.
- Added loading of saved event history into an `ArrayList` for historical data.
- Added the ability to retrieve historical event records from a specific date.

## [0.14.0] - Create OccupancyAnalytics Class

### Added
- Added the 'OccupancyAnalytics' class.
- Added a new menu option for analytics.
- Created 'AnalyticsTest' class for testing out the analytics.

### Changed
- Moved the time-weighted average method into the 'OccupancyAnalytics' class.

## [0.15.0] - Historical Analytics by Date

### Added
- Added historical average occupancy analysis for a user-selected date.
- Added an analytics submenu to support multiple analytics options.
- Added validation for invalid date input.

### Changed
- Updated date parsing to use strict validation to prevent invalid dates from being automatically adjusted.

## [0.16.0] Peak Occupancy by Date and Daily Traffic by Date

### Added
- Added menu options to view peak occupancy and daily traffic.
- Added peak occupancy analysis for a user-selected date.
- Added daily traffic analysis that calculates entries, exits, and total traffic and displays them.

## [0.17.0] Busiest Hour by Date

### Added
- Added a menu option to view the busiest hour of the day.
- Added busiest hour analysis that calculates average occupancy per hour and displays the busiest hour along with its average.

## [0.18.0] Busiest Day of the Week

### Added
- Added a method to calculate the busiest day of the week and the average daily traffic.
- Added a submenu option to display the busiest day of week and average.
- Created 'BusiestDayResult' class.
- Created 'BusiestDayTest' class.

## [0.19.0] Periods of High Occupancy by Date

### Added
- Added a method to calculate the percentage of an event's occupancy relative to the high-occupancy threshold.
- Added a submenu option to display the periods of high occupancy and the amount of time spent in high occupancy for a user-selected date.

## [0.20.0] Beginning of the Occupancy Tracker GUI

### Added
- Added an 'OccupancyTrackerGUI' class.
- Added the display for the occupancy counter and occupancy counter percentage.
- Added enter and exit buttons.

## [0.21.0] Reset Button and GUI Reorganization

### Added
- Added a reset occupancy button to the GUI.
- Added a `refreshDisplay` helper method to update occupancy information in the GUI.

### Changed
- Moved the `returnLevelOfOccupancy` method to the `Tracker` class.
- Reorganized the GUI using panels and layout managers instead of manually positioning components.
- Changed GUI components from static fields to instance fields.
- Changed the GUI to receive and use a `Tracker` object.
- Updated the GUI layout and spacing for a cleaner display and consistent button sizing.