package com.spring.common.edit;

import com.spring.common.classmetadata.AnnotationMetadataTests;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;



public class RelaxedDataBinderTests {

    private ConversionService conversionService;

    @Test
    public void testBindString() throws Exception {
        VanillaTarget target = new VanillaTarget();
        bind(target, "foo: bar");
        assertThat(target.getFoo(), equalTo("bar"));
    }

    private BindingResult bind(Object target, String values) throws Exception {
        return bind(target, values, null);
    }

    private BindingResult bind(Object target, String values, String namePrefix)
            throws Exception {
        return bind(getBinder(target, namePrefix), target, values);
    }

    private RelaxedDataBinder getBinder(Object target, String namePrefix) {
        RelaxedDataBinder binder = new RelaxedDataBinder(target, namePrefix);
        binder.setIgnoreUnknownFields(false);
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.afterPropertiesSet();
        binder.setValidator(validatorFactoryBean);
        binder.setConversionService(this.conversionService);
        return binder;
    }

    private BindingResult bind(DataBinder binder, Object target, String values)
            throws Exception {
        Properties properties = PropertiesLoaderUtils
                .loadProperties(new ByteArrayResource(values.getBytes()));
        binder.bind(new MutablePropertyValues(properties));
        binder.validate();

        return binder.getBindingResult();
    }


    public static class VanillaTarget {

        private String foo;

        private char[] bar;

        private int value;

        private String foo_bar;

        private String fooBaz;

        private Bingo bingo;

        private List<Bingo> bingos;

        private List<Object> objects;

        private String mixedUPPER;

        public char[] getBar() {
            return this.bar;
        }

        public void setBar(char[] bar) {
            this.bar = bar;
        }

        public int getValue() {
            return this.value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getFoo() {
            return this.foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getFoo_bar() {
            return this.foo_bar;
        }

        public void setFoo_bar(String foo_bar) {
            this.foo_bar = foo_bar;
        }

        public String getFooBaz() {
            return this.fooBaz;
        }

        public void setFooBaz(String fooBaz) {
            this.fooBaz = fooBaz;
        }

        public Bingo getBingo() {
            return this.bingo;
        }

        public void setBingo(Bingo bingo) {
            this.bingo = bingo;
        }

        public List<Bingo> getBingos() {
            return this.bingos;
        }

        public void setBingos(List<Bingo> bingos) {
            this.bingos = bingos;
        }

        public List<Object> getObjects() {
            return this.objects;
        }

        public void setObjects(List<Object> objects) {
            this.objects = objects;
        }

        public String getMixedUPPER() {
            return this.mixedUPPER;
        }

        public void setMixedUPPER(String mixedUPPER) {
            this.mixedUPPER = mixedUPPER;
        }

    }

    enum Bingo {

        THIS, or, THAT, THE_OTHER, THAT_OTHER

    }

    public static class ValidatedTarget {

        @NotNull
        private String foo;

        public String getFoo() {
            return this.foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

    }
}
