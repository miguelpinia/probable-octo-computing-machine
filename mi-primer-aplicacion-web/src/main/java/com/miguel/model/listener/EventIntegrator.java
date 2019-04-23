package com.miguel.model.listener;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 *
 * @author miguel
 */
public class EventIntegrator implements Integrator {

    public static final EventIntegrator INSTANCE = new EventIntegrator();

    @Override
    public void integrate(Metadata mtdt, SessionFactoryImplementor sfi, SessionFactoryServiceRegistry sfsr) {
        final EventListenerRegistry eventListenerRegistry = sfsr.getService(EventListenerRegistry.class);
        eventListenerRegistry.appendListeners(EventType.POST_INSERT, UsuarioInsertEventListener.getInstance());
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sfi, SessionFactoryServiceRegistry sfsr) {
    }

}
