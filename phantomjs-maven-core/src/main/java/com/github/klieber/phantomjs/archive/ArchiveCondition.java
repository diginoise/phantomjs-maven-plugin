package com.github.klieber.phantomjs.archive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchiveCondition {

  private final String version;
  private final String os;
  private final String architecture;
  private final String distribution;
  private final String distributionVersion;

  @JsonCreator
  public ArchiveCondition(@JsonProperty("version") String version,
                          @JsonProperty("os") String os,
                          @JsonProperty("architecture") String architecture,
                          @JsonProperty("distribution") String distribution,
                          @JsonProperty("distributionVersion") String distributionVersion) {
    this.version = version;
    this.os = os;
    this.architecture = architecture;
    this.distribution = distribution;
    this.distributionVersion = distributionVersion;
  }

  public String getVersion() {
    return version;
  }

  public String getOs() {
    return os;
  }

  public String getArchitecture() {
    return architecture;
  }

  public String getDistribution() {
    return distribution;
  }

  public String getDistributionVersion() {
    return distributionVersion;
  }
}
