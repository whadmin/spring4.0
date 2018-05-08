package com.spring.ioc.appliction.event.model.annotation;

public class BlogModifiedEvent extends BaseBlogEvent{
    private final Blog blog;
    private final boolean importantChange;
    public BlogModifiedEvent(Blog blog) {
        this(blog, false);
    }
    public BlogModifiedEvent(Blog blog, boolean importantChange) {
        this.blog = blog;
        this.importantChange = importantChange;
    }
    public Blog getBlog() {
        return blog;
    }
    public boolean isImportantChange() {
        return importantChange;
    }
}

