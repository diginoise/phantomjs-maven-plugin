package com.github.klieber.phantomjs.archive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchiveProperties {

  private final String classifier;
  private final String extension;
  private final String executable;

  @JsonCreator
  public ArchiveProperties(@JsonProperty("classifier") String classifier,
                           @JsonProperty("extension") String extension,
                           @JsonProperty("executable") String executable) {
    this.classifier = classifier;
    this.extension = extension;
    this.executable = executable;
  }

  public String getClassifier() {
    return classifier;
  }

  public String getExtension() {
    return extension;
  }

  public String getExecutable() {
    return executable;
  }
}
