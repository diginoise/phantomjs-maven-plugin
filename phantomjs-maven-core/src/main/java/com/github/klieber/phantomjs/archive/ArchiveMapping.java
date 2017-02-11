package com.github.klieber.phantomjs.archive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchiveMapping {

  private final ArchiveCondition condition;
  private final ArchiveProperties archive;

  @JsonCreator
  public ArchiveMapping(@JsonProperty("condition") ArchiveCondition condition,
                        @JsonProperty("archive") ArchiveProperties archive) {
    this.condition = condition;
    this.archive = archive;
  }

  public ArchiveCondition getCondition() {
    return condition;
  }

  public ArchiveProperties getArchive() {
    return archive;
  }
}
