package com.magenic.jmaqs.utilities.performance;

import java.util.Date;

/**
 * Response timer class - holds a single response timer
 */
public class PerfTimer {

  /**
  * Gets or sets the name of the Page associated with the Timer
  */
  private String timerContext;

  /**
  * Sets the name of the Page associated with the Timer
  * @param timerContext string of context to set the time context
  */
  void setTimerContext(String timerContext) {
    this.timerContext = timerContext;
  }

  /**
  * Sets the name of the Page associated with the Timer
  */
  String getTimerContext() {
    return timerContext;
  }

  /**
  *  Gets or sets the Timer Name
  */
  private String timerName;

  /**
  *  Sets the Timer Name
  * @param timerName name of the timer
  */
  void setTimerName(String timerName) {
    this.timerName = timerName;
  }

  /**
  *  Gets the Timer Name
  */
  public String getTimerName() {
    return this.timerName;
  }

  /**
  *  Gets or sets the start time
  */
  private Date startTime;

  /**
  *  Sets the start time
  * @param startTime the start time to be set
  */
  void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
  *  Gets the start time
  */
  Date getStartTime() {
    return this.startTime;
  }

  /**
  *  Gets or sets the end time
  */
  private Date endTime;

  /**
  *  Sets the end time
  * @param endTime end time to be set
  */
  void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /**
  *  Gets the end time
  */
  Date getEndTime() {
    return this.endTime;
  }

  /**
  * Gets or sets the duration
  * [XmlIgnore]
  */
  private long duration;

  /**
  * Sets the duration
  * @param duration the duration to be set
  */
  void setDuration(long duration) {
    this.duration = duration;
  }

  /**
  * Gets the duration
  */
  public long getDuration() {
    return this.duration;
  }

  /**
  * Sets the DurationTicks
  * @param seconds duration in seconds to be set
  */
  public void setDurationTicks(long seconds) {
    this.duration = seconds;
    /// set { this.duration = new TimeSpan(value); }
  }

  /**
  * Gets the Duration Ticks in seconds.
  */
  public long getDurationTicks() {
  return this.duration;
  }
}