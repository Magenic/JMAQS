/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.utilities.performance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magenic.jmaqs.utilities.logging.ConsoleLogger;
import com.magenic.jmaqs.utilities.logging.Logger;
import com.magenic.jmaqs.utilities.logging.LoggingConfig;
import com.magenic.jmaqs.utilities.logging.MessageType;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Response timer collection class
 * Object to be owned by Test Class (Object),
 * and passed to page Constructors to insert Performance Timers
 */
public class PerfTimerCollection {
    /**
     * List object to store Timers
     */
    private Map<String, PerfTimer> openTimerList = new HashMap<>();

    /**
     * List object to store Timers
     * @param logger Logger to use
     * @param fullyQualifiedTestName Test name
     */
    public PerfTimerCollection(Logger logger, String fullyQualifiedTestName) {
        this.log = logger;
        this.testName = fullyQualifiedTestName;
    }

    /**
     * List object to store Timers
     * @param fullyQualifiedTestName Test name
     */
    public PerfTimerCollection(String fullyQualifiedTestName) {
        this.log = new ConsoleLogger();
        this.testName = fullyQualifiedTestName;
    }

    /**
     *  Initializes a new instance of the PerfTimerCollection class
     */
    public PerfTimerCollection() {
        this.log = new ConsoleLogger();
    }

    /**
     * Gets and sets the list if response time tests
     */
    List<PerfTimer> timerList;

    /**
     * sets the list of response time tests
     * @param timerList the timer list of the Perf Timer
     */
    public void setTimerList(List<PerfTimer> timerList) {
        this.timerList = timerList;
    }

    /**
     * Gets the list if response time tests
     * @return the timer list
     */
    public List<PerfTimer> getTimerList() {
        return timerList;
    }

    /**
     * Gets or sets the File name
     */
    String fileName;

    /**
     * Sets the File name
     * @param fileName file name to be set
     */
    public void setFilename(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the File name
     * @return the file name
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Gets or sets the test name
     */
    String testName;

    /**
     * Sets the test name
     * @param testName name of the test to be set
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * Gets the test name
     * @return test name string
     */
    public String getTestName() {
        return this.testName;
    }

    /**
     * Gets or sets the generic payload string
     */
    String perfPayloadString;

    /**
     * Sets the generic payload string
     * @param perfPayloadString the payload string to be set
     */
    public void setPerfPayloadString(String perfPayloadString) {
        this.perfPayloadString = perfPayloadString;
    }

    /**
     * Gets the generic payload string
     * @return the perfPayloadString
     */
    public String getPerfPayloadString() {
        return perfPayloadString;
    }

    /**
     * Gets and sets the logger
     */
    protected Logger log;

    /**
     * Sets the logger
     * @param log log to be set
     */
    private void setLog(Logger log) {
        this.log = log;
    }

    /**
     * Sets the logger
     * @return Logger to be returned
     */
    public Logger getLog() {
        return log;
    }

    /**
     * Sets the logger
     * @param timerName Name of the timer
     */
    void startTimer(String timerName) {
        this.startTimer("", timerName);
    }

    /**
     * Method to start a timer with a specified name and for a specific context
     * @param contextName Name of the context
     * @param timerName Name of the timer
     */
    void startTimer(String contextName, String timerName) {
        if (this.openTimerList.containsKey(timerName)) {
            throw new IllegalArgumentException("Timer already Started: " + timerName);
        } else {
            this.log.logMessage(MessageType.INFORMATION, "Starting response timer: {0}", timerName);
            PerfTimer timer = new PerfTimer();
            ///timer.setTimerName = timerName;
            timer.setTimerName(timerName);
            ///timer.timerContext = contextName;
            timer.setTimerContext(contextName);
            ///timer.StartTime = Date..UtcNow;

            ///SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date now = new Date();
            timer.setStartTime(now);
            this.openTimerList.put(timerName, timer);
        }
    }

    /**
     * Method to stop an existing timer with a specified name for a test
     * @param timerName Name of the timer
     */
    void endTimer(String timerName) {
        Date date = new Date();
        if (!this.openTimerList.containsKey(timerName)) {
            throw new IllegalArgumentException("Response time test does not exist");
        } else {
            this.log.logMessage(MessageType.INFORMATION, "Stopping response time test: {0}", timerName);
            //this.openTimerList.put(date, );
            this.openTimerList.get(timerName).setEndTime(date);
            this.openTimerList.get(timerName).setDuration(this.openTimerList.get(timerName).getEndTime().getTime() - this.openTimerList.get(timerName).getStartTime().getTime());
            this.timerList.add(this.openTimerList.get(timerName));
            this.openTimerList.remove(timerName);
        }
    }

    /**
     * Method to Write the Performance Timer Collection to disk
     * @param log The current test Logger
     */
    public void write(Logger log) {
        // Only run if the response times is greater than 0
        if (this.timerList.size() > 0) {
            // Locks the writer if other tests are using it
            ReentrantLock lock = new ReentrantLock();
            lock.lock();

            try {
                // If filename doesn't exist, we haven't created the file yet
                if (this.fileName == null) {
                    Date now = new Date();
                    this.fileName = "PerformanceTimerResults" + "-" + this.testName + "-" + now.toString().replace(':', '-') + ".xml";
                }

                log.logMessage(MessageType.INFORMATION, "filename: " + LoggingConfig.getLogDirectory() + "\\" + this.fileName);

                // TODO: Complete method, XML Serialization needs to be completed
                //XmlWriterSettings settings = new XmlWriterSettings();
                ObjectMapper mapper = new ObjectMapper();
                XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(this.fileName)));

                /**
                XmlStreamWriter settings = new XmlStreamWriter();
                settings.WriteEndDocumentOnClose = true;
                settings.Indent = true;
                XmlStreamWriter writer = XmlStreamWriter.create(String.format("{0}\\{1}", LoggingConfig.getLogDirectory(), this.fileName), settings);

                XMLSerializer x = new XMLSerializer(this.getType());
                x.setOutputFormat(writer);

                // Object writer = new Object(){ LoggingConfig.getLogDirectory() + "\\" + this.fileName, settings}
                */

                Object object = LoggingConfig.getLogDirectory() + "\\" + this.fileName;
                encoder.writeObject(object);

                encoder.flush();
                encoder.close();

            } catch (Exception e) {
                log.logMessage(MessageType.ERROR, "Could not save response time file.  Error was: {0}", e.getMessage());
            }

            lock.unlock();
        }
    }

    /**
     * Method to Read in the Performance Timer Collection from disk
     * @param filepath filepath of of the perf Timer Log
     * @return PerfTimerCollection initialized from file path
     * @throws IOException throws exception if filepath is not string
     **/
    static PerfTimerCollection loadPerfTimerCollection(String filepath) throws IOException {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filepath)));

        PerfTimerCollection perfTimerCollection = (PerfTimerCollection)decoder.readObject();
        decoder.close();
        return perfTimerCollection;
    }
}