package com.spring.ioc.appliction.subAppliction.annotationConfigWebApplicationContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.springframework.context.annotation.ScopedProxyMode.*;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

public class AnnotationScopeMetadataResolverTests {

    private AnnotationScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    @Scope("singleton")
    private static class AnnotatedWithSingletonScope {
    }

    @Test
    public void resolveScopeMetadataShouldNotApplyScopedProxyModeToSingleton() {
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(AnnotatedWithSingletonScope.class);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(bd);
        assertThat(scopeMetadata).as("resolveScopeMetadata(..) must *never* return null.").isNotNull();
        assertThat(scopeMetadata.getScopeName()).isEqualTo(BeanDefinition.SCOPE_SINGLETON);
        assertThat(scopeMetadata.getScopedProxyMode()).isEqualTo(NO);
    }

    @Scope("prototype")
    private static class AnnotatedWithPrototypeScope {
    }

    @Test
    public void resolveScopeMetadataShouldApplyScopedProxyModeToPrototype() {
        this.scopeMetadataResolver = new AnnotationScopeMetadataResolver(INTERFACES);
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(AnnotatedWithPrototypeScope.class);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(bd);
        assertThat(scopeMetadata).as("resolveScopeMetadata(..) must *never* return null.").isNotNull();
        assertThat(scopeMetadata.getScopeName()).isEqualTo(BeanDefinition.SCOPE_PROTOTYPE);
        assertThat(scopeMetadata.getScopedProxyMode()).isEqualTo(INTERFACES);
    }

    @Scope(scopeName = "request", proxyMode = TARGET_CLASS)
    private static class AnnotatedWithScopedProxy {
    }

    @Test
    public void resolveScopeMetadataShouldReadScopedProxyModeFromAnnotation() {
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(AnnotatedWithScopedProxy.class);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(bd);
        assertThat(scopeMetadata).as("resolveScopeMetadata(..) must *never* return null.").isNotNull();
        assertThat(scopeMetadata.getScopeName()).isEqualTo("request");
        assertThat(scopeMetadata.getScopedProxyMode()).isEqualTo(TARGET_CLASS);
    }

    @Test
    public void customRequestScope() {
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(AnnotatedWithCustomRequestScope.class);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(bd);
        assertThat(scopeMetadata).as("resolveScopeMetadata(..) must *never* return null.").isNotNull();
        assertThat(scopeMetadata.getScopeName()).isEqualTo("request");
        assertThat(scopeMetadata.getScopedProxyMode()).isEqualTo(NO);
    }

    @Test
    public void customRequestScopeViaAsm() throws IOException {
        MetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory();
        MetadataReader reader = readerFactory.getMetadataReader(AnnotatedWithCustomRequestScope.class.getName());
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(reader.getAnnotationMetadata());
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(bd);
        assertThat(scopeMetadata).as("resolveScopeMetadata(..) must *never* return null.").isNotNull();
        assertThat(scopeMetadata.getScopeName()).isEqualTo("request");
        assertThat(scopeMetadata.getScopedProxyMode()).isEqualTo(NO);
    }

    @Test
    public void customRequestScopeWithAttribute() {
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(
                AnnotatedWithCustomRequestScopeWithAttributeOverride.class);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(bd);
        assertThat(scopeMetadata).as("resolveScopeMetadata(..) must *never* return null.").isNotNull();
        assertThat(scopeMetadata.getScopeName()).isEqualTo("request");
        assertThat(scopeMetadata.getScopedProxyMode()).isEqualTo(TARGET_CLASS);
    }

    @Test
    public void customRequestScopeWithAttributeViaAsm() throws IOException {
        MetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory();
        MetadataReader reader = readerFactory.getMetadataReader(AnnotatedWithCustomRequestScopeWithAttributeOverride.class.getName());
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(reader.getAnnotationMetadata());
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(bd);
        assertThat(scopeMetadata).as("resolveScopeMetadata(..) must *never* return null.").isNotNull();
        assertThat(scopeMetadata.getScopeName()).isEqualTo("request");
        assertThat(scopeMetadata.getScopedProxyMode()).isEqualTo(TARGET_CLASS);
    }

    @Test
    public void ctorWithNullScopedProxyMode() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new AnnotationScopeMetadataResolver(null));
    }

    @Test
    public void setScopeAnnotationTypeWithNullType() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                scopeMetadataResolver.setScopeAnnotationType(null));
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Scope("request")
    @interface CustomRequestScope {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Scope("request")
    @interface CustomRequestScopeWithAttributeOverride {

        ScopedProxyMode proxyMode();
    }







    @CustomRequestScope
    private static class AnnotatedWithCustomRequestScope {
    }

    @CustomRequestScopeWithAttributeOverride(proxyMode = TARGET_CLASS)
    private static class AnnotatedWithCustomRequestScopeWithAttributeOverride {
    }
}
