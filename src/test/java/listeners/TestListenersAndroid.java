package listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

import static core.FailedTestsToolsAndroid.attachAll;

public class TestListenersAndroid implements TestWatcher {

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
    }

    public void testSuccessful(ExtensionContext context) {
    }

    public void testAborted(ExtensionContext context, Throwable cause) {
    }

    public void testFailed(ExtensionContext context, Throwable cause) {
        attachAll();
    }


}
