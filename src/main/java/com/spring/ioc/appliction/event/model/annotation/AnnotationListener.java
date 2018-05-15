package com.spring.ioc.appliction.event.model.annotation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnnotationListener {

	@EventListener(condition = "#blogModifiedEvent.importantChange")
	public BlogModifiedResponseEvent blogModifiedEventExcute(BlogModifiedEvent blogModifiedEvent) {

		return new BlogModifiedResponseEvent();
	}

	@EventListener
	public void BlogModifiedResponseEventExcute(BlogModifiedResponseEvent blogModifiedResponseEvent) {
		System.out.println("BlogModifiedResponseEvent");
	}
}
