package organisation.structure.exercise.facade.test.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import organisation.structure.exercise.core.configuration.annotation.Facade;
import organisation.structure.exercise.facade.test.TestFacade;
import organisation.structure.exercise.facade.test.local.LocalTestFacade;

@Slf4j
@Facade
public class DefaultTestFacade implements TestFacade {

    @Autowired
    private LocalTestFacade localTestFacade;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("Application is ready - ensuring DataSource is properly initialized");

        localTestFacade.localTestAnalysis();
        localTestFacade.localExtendedTestAnalysis();
    }

    @EventListener(org.springframework.context.event.ContextClosedEvent.class)
    public void onContextClosed() {
        log.info("Application context is closing - ensuring proper cleanup");
    }


}
