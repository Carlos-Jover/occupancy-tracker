# Occupancy Tracker

A Java application that tracks the number of people inside a building and provides real-time occupancy information and analytics.

The project is being developed from a console-based occupancy tracker into an automated occupancy management system capable of using camera detection to track people entering and leaving a location.

## Status

Version 1 is currently in development.

The core manual occupancy tracking system is functional. Current development is focused on occupancy analytics and operating-hour support before moving into automated traffic simulation, a user interface, and camera-based occupancy detection.

## Current Features

- Record people entering and exiting a location
- Display current occupancy
- Display occupancy as a percentage of a configurable reference occupancy
- Display a visual occupancy percentage bar
- Classify occupancy as low, moderate, or high
- Manually correct the occupancy count
- Reset the occupancy counter
- Configure the reference occupancy level
- Record timestamped occupancy events
- Save event history to a file
- Restore occupancy after an unexpected program shutdown
- Track peak occupancy and the time it occurred
- Configure and display operating hours
- Determine whether the business is currently open or closed
- Calculate a time-weighted average occupancy from recorded events

## Version 1 Goals

### Core Tracking
- [x] Manual occupancy tracking
- [x] Occupancy percentage and level
- [x] Manual corrections
- [x] Event history
- [x] Crash recovery

### Analytics
- [x] Peak occupancy
- [x] Basic time-weighted average occupancy
- [ ] Restrict analytics to operating hours
- [ ] Basic historical/time-range analytics

### Operating Hours
- [x] Configurable operating hours
- [x] Open/closed detection
- [ ] Integrate operating hours with analytics

### Testing and Automation
- [ ] Simulate people entering and exiting over time
- [ ] Use simulated traffic to test long-running behavior

### User Interface
- [ ] Build a basic graphical interface

### Camera Tracking
- [ ] Detect people entering and exiting using a camera
- [ ] Connect camera detections to the occupancy tracker

### Finalization
- [ ] Test the complete Version 1 system
- [ ] Document setup and usage
- [ ] Add screenshots/demo material

## Technologies

- Java
- IntelliJ IDEA
- Git
- GitHub

## Project Purpose

The Occupancy Tracker is designed to monitor how many people are currently inside a building and provide useful information about how a location is being used.

The system began as a manual Java console application. Version 1 is being developed toward automated camera-based occupancy tracking with persistent event history, basic occupancy analytics, configurable operating hours, and a user interface.

The project is also being used to explore how a basic occupancy counter can develop into a larger real-world software system.

## Future Versions

Features outside the core Version 1 scope may include:

### Analytics
- Advanced historical analytics
- Occupancy trends
- Staffing recommendations
- More customizable reporting

### Data
- Database storage

### Web
- Web dashboard
- Remote occupancy viewing

### Expansion
- Multiple buildings or locations
- Accounts and permissions
- Additional business configuration