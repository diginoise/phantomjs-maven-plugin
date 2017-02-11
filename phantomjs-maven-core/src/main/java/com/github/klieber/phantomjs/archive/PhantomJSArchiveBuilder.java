/*
 * Copyright (c) 2013 Kyle Lieber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.klieber.phantomjs.archive;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.klieber.phantomjs.util.VersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class PhantomJSArchiveBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJSArchiveBuilder.class);

	private final OperatingSystem operatingSystem;
	private final String version;

	public PhantomJSArchiveBuilder(OperatingSystem operatingSystem,
																 String version) {
		this.operatingSystem = operatingSystem;
		this.version  = version;
	}

	private boolean contains(ArchiveCondition condition) {
    return
        VersionUtil.isWithin(this.version.replaceAll("[^0-9.]", ""), condition.getVersion()) &&
        contains(operatingSystem.getName(), condition.getOs()) &&
        contains(operatingSystem.getArchitecture(), condition.getArchitecture()) &&
        contains(operatingSystem.getDistribution(), condition.getDistribution()) &&
        withinVersion(operatingSystem.getDistributionVersion(), condition.getDistributionVersion());
  }

  private boolean contains(String actualValue, String partialValue) {
    return partialValue == null ||
        (actualValue != null && actualValue.toLowerCase().contains(partialValue.toLowerCase()));
  }

  private boolean withinVersion(String distroVersion, String versionSpec) {
    return versionSpec == null ||
        (distroVersion != null && VersionUtil.isWithin(distroVersion, versionSpec));
  }

	public PhantomJSArchive build() {

	  PhantomJSArchive archive = null;

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    URL resource = PhantomJSArchiveBuilder.class.getResource("/archive-mapping.yaml");
    try {
      ArchiveMappings archiveMappings = mapper.readValue(resource, ArchiveMappings.class);
      for (ArchiveMapping archiveMapping : archiveMappings.getMappings()) {
        if (contains(archiveMapping.getCondition())) {
          archive = new PhantomJSArchiveImpl(archiveMapping.getArchive(), this.version);
          break;
        }
      }
    } catch (IOException e) {
      LOGGER.error("Unable to read archive-mapping.yaml", e);
    }
  	if (archive == null) {
  		throw new IllegalArgumentException("unknown platform: " + operatingSystem.getName());
  	}
  	return archive;
	}
}
