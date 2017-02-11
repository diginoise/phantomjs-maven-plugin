package com.github.klieber.phantomjs.archive;

public class PhantomJSArchiveImpl implements PhantomJSArchive {

  private static final String BASENAME = "phantomjs";

  private final ArchiveProperties archiveProperties;
  private final String version;

  public PhantomJSArchiveImpl(ArchiveProperties archiveProperties,
                              String version) {
    this.archiveProperties = archiveProperties;
    this.version = version;
  }

  @Override
  public String getExtension() {
    return this.archiveProperties.getExtension();
  }

  @Override
  public String getArchiveName() {
    return this.getNameWithoutExtension()
        .append(".")
        .append(this.getExtension())
        .toString();
  }

  @Override
  public String getPathToExecutable() {
    return this.getNameWithoutExtension()
        .append("/")
        .append(this.archiveProperties.getExecutable())
        .toString();
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public String getClassifier() {
    return this.archiveProperties.getClassifier();
  }

  private StringBuilder getNameWithoutExtension() {
    return new StringBuilder()
        .append(BASENAME)
        .append("-")
        .append(this.version)
        .append("-")
        .append(this.getClassifier());
  }
}
