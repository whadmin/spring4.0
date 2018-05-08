package com.spring.ioc.appliction.event.model.annotation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Component
public class AnnotationListener {

	@EventListener(condition = "#blogModifiedEvent.importantChange")
	public BlogModifiedResponseEvent blogModifiedWithResponse(BlogModifiedEvent blogModifiedEvent) {
	    
	    return new BlogModifiedResponseEvent();
	}
}
