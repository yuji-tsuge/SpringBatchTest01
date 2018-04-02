package com.accenture.aris.core.support.message;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class SpringMessageResourceMessages implements Messages, ApplicationListener<ApplicationEvent> {

	protected ApplicationContext context = null;
	
	public SpringMessageResourceMessages() {
		// nothing
	}
	
	@Override
	public String getMessage(String code) {
		assert(context != null);
		return code + ":" + context.getMessage(code, null, Locale.getDefault());
	}
	
	@Override
    public String getMessage(String code, Object... args) {
        assert(context != null);
        return code + ":" + context.getMessage(code, args, Locale.getDefault());
    }

    @Override
    public String getSimpleMessage(String code) {
        assert(context != null);
        return context.getMessage(code, null, Locale.getDefault());
    }

    @Override
    public String getSimpleMessage(String code, Object... args) {
        assert(context != null);
        return context.getMessage(code, args, Locale.getDefault());
    }

    @Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ContextRefreshedEvent) {
			context = ((ContextRefreshedEvent)event).getApplicationContext();
			return;
		}
		if(event instanceof ContextClosedEvent) {
			context = null;
		}
	}
}
