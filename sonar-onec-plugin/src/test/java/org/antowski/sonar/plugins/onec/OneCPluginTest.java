package org.antowski.sonar.plugins.onec;

import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

import static org.assertj.core.api.Assertions.assertThat;

public class OneCPluginTest {

    @Test
    public void testGetExtensions() {

        Plugin.Context context = new Plugin.Context(SonarRuntimeImpl.forSonarQube(Version.create(6, 2), SonarQubeSide.SERVER));
        new OneCPlugin().define(context);

        assertThat(context.getExtensions()).hasSize(4);

    }

}
