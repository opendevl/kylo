package com.thinkbiganalytics.jobrepo.query.model;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Represents all of the information about a job that was executed in a JSON friendly format.  The native spring objects for this
 * are not JSON friendly.
 */
@SuppressWarnings("UnusedDeclaration")
public class DefaultExecutedJob implements Serializable, ExecutedJob {

  private static final long serialVersionUID = 3327858118326404823L;
  private long instanceId;
  private long executionId;
  private String jobName;
  private List<Throwable> exceptions;
  private DateTime createTime;
  private DateTime endTime;
  private Map<String, Object> executionContext;
  private String exitCode;
  private String exitStatus;
  private String jobConfigurationName;
  private Long jobId;
  private Map<String, Object> jobParameters;
  private DateTime lastUpdated;
  private DateTime startTime;
  private boolean isLatest;
  private ExecutionStatus status;
  private List<ExecutedStep> executedSteps;
  private Long runTime;
  private Long timeSinceEndTime;
  private String jobType;
  private String feedName;

  public DefaultExecutedJob() {

  }

  public DefaultExecutedJob(ExecutedJob job) {
    this.instanceId = job.getInstanceId();
    this.executionId = job.getExecutionId();
    this.jobName = job.getJobName();
    this.exceptions = job.getExceptions();
    this.createTime = job.getCreateTime();
    this.endTime = job.getEndTime();
    this.executionContext = job.getExecutionContext();
    this.exitCode = job.getExitCode();
    this.exitStatus = job.getExitStatus();
    this.jobConfigurationName = job.getJobConfigurationName();
    this.jobId = job.getJobId();
    this.jobParameters = job.getJobParameters();
    this.lastUpdated = job.getLastUpdated();
    this.startTime = job.getStartTime();
    this.status = job.getStatus();
    this.executedSteps = job.getExecutedSteps();
    this.runTime = job.getRunTime();
    this.timeSinceEndTime = job.getTimeSinceEndTime();
    this.jobType = job.getJobType();
    this.feedName = job.getFeedName();
  }

  @Override
  public long getInstanceId() {
    return instanceId;
  }

  @Override
  public void setInstanceId(final long instanceId) {
    this.instanceId = instanceId;
  }

  @Override
  public long getExecutionId() {
    return executionId;
  }

  @Override
  public void setExecutionId(final long executionId) {
    this.executionId = executionId;
  }

  @Override
  public void setJobId(final Long jobId) {
    this.jobId = jobId;
  }

  @Override
  public String getJobName() {
    return jobName;
  }

  @Override
  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  @Override
  public List<Throwable> getExceptions() {
    return exceptions;
  }

  @Override
  public void setExceptions(final List<Throwable> exceptions) {
    this.exceptions = exceptions;
  }

  @Override
  public DateTime getCreateTime() {
    return createTime;
  }

  @Override
  public void setCreateTime(final DateTime createTime) {
    this.createTime = createTime;
  }

  @Override
  public DateTime getEndTime() {
    return endTime;
  }

  @Override
  public void setEndTime(final DateTime endTime) {
    this.endTime = endTime;
  }

  @Override
  public Map<String, Object> getExecutionContext() {
    return executionContext;
  }

  @Override
  public void setExecutionContext(final Map<String, Object> executionContext) {
    this.executionContext = executionContext;
  }

  @Override
  public String getExitCode() {
    return exitCode;
  }

  @Override
  public void setExitCode(final String exitCode) {
    this.exitCode = exitCode;
  }

  @Override
  public String getExitStatus() {
    return exitStatus;
  }

  @Override
  public void setExitStatus(String exitStatus) {
    this.exitStatus = exitStatus;
  }

  @Override
  public String getJobConfigurationName() {
    return jobConfigurationName;
  }

  @Override
  public void setJobConfigurationName(final String jobConfigurationName) {
    this.jobConfigurationName = jobConfigurationName;
  }

  @Override
  public Long getJobId() {
    return jobId;
  }

  @Override
  public Map<String, Object> getJobParameters() {
    return jobParameters;
  }

  @Override
  public void setJobParameters(final Map<String, Object> jobParameters) {
    this.jobParameters = jobParameters;
  }

  @Override
  public DateTime getLastUpdated() {
    return lastUpdated;
  }

  @Override
  public void setLastUpdated(final DateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  @Override
  public DateTime getStartTime() {
    return startTime;
  }

  @Override
  public void setStartTime(final DateTime startTime) {
    this.startTime = startTime;
  }

  @Override
  public ExecutionStatus getStatus() {
    return status;
  }

  @Override
  public void setStatus(final ExecutionStatus status) {
    this.status = status;
  }

  @Override
  public List<ExecutedStep> getExecutedSteps() {
    return executedSteps;
  }

  @Override
  public void setExecutedSteps(final List<ExecutedStep> executedSteps) {
    this.executedSteps = executedSteps;
  }

  @Override
  public Long getRunTime() {
    return runTime;
  }

  @Override
  public void setRunTime(Long runTime) {
    this.runTime = runTime;
  }

  @Override
  public Long getTimeSinceEndTime() {
    return timeSinceEndTime;
  }

  @Override
  public void setTimeSinceEndTime(Long timeSinceEndTime) {
    this.timeSinceEndTime = timeSinceEndTime;
  }


  @Override
  public String getJobType() {
    return jobType;
  }

  @Override
  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  @Override
  public boolean isLatest() {
    return isLatest;
  }

  @Override
  public void setIsLatest(boolean isLatest) {
    this.isLatest = isLatest;
  }

  @Override
  public String getFeedName() {
    return feedName;
  }

  @Override
  public void setFeedName(String feedName) {
    this.feedName = feedName;
  }

  @Override
  public String getDisplayStatus() {
    String displayStatus = this.status.name();
    if (ExecutionStatus.FAILED.equals(this.status) || (ExecutionStatus.COMPLETED.equals(this.status) && "FAILED"
        .equalsIgnoreCase(this.exitCode))) {
      displayStatus = "FAILED";
    }

    if (displayStatus.equalsIgnoreCase("FAILED") && "STOPPED".equalsIgnoreCase(this.exitCode)) {
      displayStatus = "STOPPED";
    }
    return displayStatus;
  }

}