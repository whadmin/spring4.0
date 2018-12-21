package com.spring.common.environment;


import java.lang.reflect.Field;
import java.security.AccessControlException;
import java.security.Permission;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import org.springframework.core.SpringProperties;
import org.springframework.core.env.*;
import org.springframework.mock.env.MockPropertySource;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.core.env.AbstractEnvironment.*;

/**
 * Unit tests for {@link StandardEnvironment}.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 */
@SuppressWarnings("deprecation")
public class StandardEnvironmentTests {

    protected static final String RESERVED_DEFAULT_PROFILE_NAME = "default";

    private static final String ALLOWED_PROPERTY_NAME = "theanswer";
    private static final String ALLOWED_PROPERTY_VALUE = "42";

    private static final String DISALLOWED_PROPERTY_NAME = "verboten";
    private static final String DISALLOWED_PROPERTY_VALUE = "secret";

    private static final String STRING_PROPERTY_NAME = "stringPropName";
    private static final String STRING_PROPERTY_VALUE = "stringPropValue";
    private static final Object NON_STRING_PROPERTY_NAME = new Object();
    private static final Object NON_STRING_PROPERTY_VALUE = new Object();

    private final ConfigurableEnvironment environment = new StandardEnvironment();


    @Test
    public void merge() {
        ConfigurableEnvironment child = new StandardEnvironment();
        child.setActiveProfiles("c1", "c2");
        child.getPropertySources().addLast(
                new MockPropertySource("childMock")
                        .withProperty("childKey", "childVal")
                        .withProperty("bothKey", "childBothVal"));

        ConfigurableEnvironment parent = new StandardEnvironment();
        parent.setActiveProfiles("p1", "p2");
        parent.getPropertySources().addLast(
                new MockPropertySource("parentMock")
                        .withProperty("parentKey", "parentVal")
                        .withProperty("bothKey", "parentBothVal"));

        assertThat(child.getProperty("childKey"), is("childVal"));
        assertThat(child.getProperty("parentKey"), nullValue());
        assertThat(child.getProperty("bothKey"), is("childBothVal"));

        assertThat(parent.getProperty("childKey"), nullValue());
        assertThat(parent.getProperty("parentKey"), is("parentVal"));
        assertThat(parent.getProperty("bothKey"), is("parentBothVal"));

        assertThat(child.getActiveProfiles(), equalTo(new String[]{"c1","c2"}));
        assertThat(parent.getActiveProfiles(), equalTo(new String[]{"p1","p2"}));

        child.merge(parent);

        assertThat(child.getProperty("childKey"), is("childVal"));
        assertThat(child.getProperty("parentKey"), is("parentVal"));
        assertThat(child.getProperty("bothKey"), is("childBothVal"));

        assertThat(parent.getProperty("childKey"), nullValue());
        assertThat(parent.getProperty("parentKey"), is("parentVal"));
        assertThat(parent.getProperty("bothKey"), is("parentBothVal"));

        assertThat(child.getActiveProfiles(), equalTo(new String[]{"c1","c2","p1","p2"}));
        assertThat(parent.getActiveProfiles(), equalTo(new String[]{"p1","p2"}));
    }

    /***  getPropertySources **/
    @Test
    public void propertySourceOrder() {
        ConfigurableEnvironment env = new StandardEnvironment();
        MutablePropertySources sources = env.getPropertySources();
        assertThat(sources.precedenceOf(PropertySource.named(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME)), equalTo(0));
        assertThat(sources.precedenceOf(PropertySource.named(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)), equalTo(1));
        assertThat(sources.size(), is(2));
    }

    @Test
    public void propertySourceTypes() {
        ConfigurableEnvironment env = new StandardEnvironment();
        MutablePropertySources sources = env.getPropertySources();
        assertThat(sources.get(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME), instanceOf(SystemEnvironmentPropertySource.class));
    }

    /********* activeProfiles star  *********/
    @Test
    public void activeProfilesIsEmptyByDefault() {
        assertThat(environment.getActiveProfiles().length, is(0));
    }

    @Test
    public void setActiveProfiles() {
        environment.setActiveProfiles("local", "embedded");
        String[] activeProfiles = environment.getActiveProfiles();
        assertThat(Arrays.asList(activeProfiles), hasItems("local", "embedded"));
        assertThat(activeProfiles.length, is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setActiveProfiles_withNullProfileArray() {
        environment.setActiveProfiles((String[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setActiveProfiles_withNullProfile() {
        environment.setActiveProfiles((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setActiveProfiles_withEmptyProfile() {
        environment.setActiveProfiles("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setActiveProfiles_withNotOperator() {
        environment.setActiveProfiles("p1", "!p2");
    }


    @Test
    public void addActiveProfile() {
        assertThat(environment.getActiveProfiles().length, is(0));
        environment.setActiveProfiles("local", "embedded");
        assertThat(Arrays.asList(environment.getActiveProfiles()), hasItems("local", "embedded"));
        assertThat(environment.getActiveProfiles().length, is(2));
        environment.addActiveProfile("p1");
        assertThat(Arrays.asList(environment.getActiveProfiles()), hasItems("p1"));
        assertThat(environment.getActiveProfiles().length, is(3));
        environment.addActiveProfile("p2");
        environment.addActiveProfile("p3");
        assertThat(Arrays.asList(environment.getActiveProfiles()), hasItems("p2", "p3"));
        assertThat(environment.getActiveProfiles().length, is(5));
    }


    /*** 通过属性名称ACTIVE_PROFILES_PROPERTY_NAME 设置activeProfiles  ***/
    @Test
    public void addActiveProfile_whenActiveProfilesPropertyIsAlreadySet() {
        ConfigurableEnvironment env = new StandardEnvironment();
        assertThat(env.getProperty(ACTIVE_PROFILES_PROPERTY_NAME), nullValue());
        env.getPropertySources().addFirst(new MockPropertySource().withProperty(ACTIVE_PROFILES_PROPERTY_NAME, "p1"));
        assertThat(env.getProperty(ACTIVE_PROFILES_PROPERTY_NAME), equalTo("p1"));
        env.addActiveProfile("p2");
        assertThat(env.getActiveProfiles(), arrayContaining("p1", "p2"));
        assertThat(env.getActiveProfiles(), arrayContaining("p2", "p1"));
    }

    @Test
    public void getActiveProfiles_systemPropertiesEmpty() {
        assertThat(environment.getActiveProfiles().length, is(0));
        System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "");
        assertThat(environment.getActiveProfiles().length, is(0));
        System.getProperties().remove(ACTIVE_PROFILES_PROPERTY_NAME);
    }

    @Test
    public void getActiveProfiles_fromSystemProperties() {
        System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "foo");
        assertThat(Arrays.asList(environment.getActiveProfiles()), hasItem("foo"));
        System.getProperties().remove(ACTIVE_PROFILES_PROPERTY_NAME);
    }

    @Test
    public void getActiveProfiles_fromSystemProperties_withMultipleProfiles() {
        System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "foo,bar");
        assertThat(Arrays.asList(environment.getActiveProfiles()), hasItems("foo", "bar"));
        System.getProperties().remove(ACTIVE_PROFILES_PROPERTY_NAME);
    }

    @Test
    public void getActiveProfiles_fromSystemProperties_withMulitpleProfiles_withWhitespace() {
        System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, " bar , baz "); // notice whitespace
        assertThat(Arrays.asList(environment.getActiveProfiles()), hasItems("bar", "baz"));
        System.getProperties().remove(ACTIVE_PROFILES_PROPERTY_NAME);
    }
    /********* activeProfiles end  *********/





    /********* defaultProfiles star  *********/

    @Test
    public void defaultProfilesContainsDefaultProfileByDefault() {
        assertThat(environment.getDefaultProfiles().length, is(1));
        assertThat(environment.getDefaultProfiles()[0], equalTo("default"));
    }

    @Test
    public void getDefaultProfiles() {
        assertThat(environment.getDefaultProfiles(), equalTo(new String[]{RESERVED_DEFAULT_PROFILE_NAME}));
        environment.getPropertySources().addFirst(new MockPropertySource().withProperty(DEFAULT_PROFILES_PROPERTY_NAME, "pd1"));
        assertThat(environment.getDefaultProfiles().length, is(1));
        assertThat(Arrays.asList(environment.getDefaultProfiles()), hasItem("pd1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void defaultProfileWithCircularPlaceholder() {
        System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "${spring.profiles.default}");
        try {
            environment.getDefaultProfiles();
        } finally {
            System.getProperties().remove(DEFAULT_PROFILES_PROPERTY_NAME);
        }
    }

    @Test
    public void setDefaultProfiles() {
        environment.setDefaultProfiles();
        assertThat(environment.getDefaultProfiles().length, is(0));
        environment.setDefaultProfiles("pd1");
        assertThat(Arrays.asList(environment.getDefaultProfiles()), hasItem("pd1"));
        environment.setDefaultProfiles("pd2", "pd3");
        assertThat(Arrays.asList(environment.getDefaultProfiles()), not(hasItem("pd1")));
        assertThat(Arrays.asList(environment.getDefaultProfiles()), hasItems("pd2", "pd3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDefaultProfiles_withNullProfileArray() {
        environment.setDefaultProfiles((String[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDefaultProfiles_withNullProfile() {
        environment.setDefaultProfiles((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDefaultProfiles_withEmptyProfile() {
        environment.setDefaultProfiles("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDefaultProfiles_withNotOperator() {
        environment.setDefaultProfiles("d1", "!d2");
    }


    @Test
    public void reservedDefaultProfile() {
        assertThat(environment.getDefaultProfiles(), equalTo(new String[]{RESERVED_DEFAULT_PROFILE_NAME}));
        System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "d0");
        assertThat(environment.getDefaultProfiles(), equalTo(new String[]{"d0"}));
        environment.setDefaultProfiles("d1", "d2");
        assertThat(environment.getDefaultProfiles(), equalTo(new String[]{"d1", "d2"}));
        System.getProperties().remove(DEFAULT_PROFILES_PROPERTY_NAME);
    }

    /********* defaultProfiles end  *********/



    /********* acceptsProfiles 指定的环境是否处于活动状态 star  *********/
    @Test(expected = IllegalArgumentException.class)
    public void acceptsProfiles_withEmptyArgumentList() {
        environment.acceptsProfiles();
    }

    @Test(expected = IllegalArgumentException.class)
    public void acceptsProfiles_withNullArgumentList() {
        environment.acceptsProfiles((String[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void acceptsProfiles_withNullArgument() {
        environment.acceptsProfiles((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void acceptsProfiles_withEmptyArgument() {
        environment.acceptsProfiles("");
    }

    @Test
    public void acceptsProfiles_activeProfileSetProgrammatically() {
        assertThat(environment.acceptsProfiles("p1", "p2"), is(false));
        environment.setActiveProfiles("p1");
        assertThat(environment.acceptsProfiles("p1", "p2"), is(true));
        environment.setActiveProfiles("p2");
        assertThat(environment.acceptsProfiles("p1", "p2"), is(true));
        environment.setActiveProfiles("p1", "p2");
        assertThat(environment.acceptsProfiles("p1", "p2"), is(true));
    }

    @Test
    public void acceptsProfiles_activeProfileSetViaProperty() {
        assertThat(environment.acceptsProfiles("p1"), is(false));
        environment.getPropertySources().addFirst(new MockPropertySource().withProperty(ACTIVE_PROFILES_PROPERTY_NAME, "p1"));
        assertThat(environment.acceptsProfiles("p1"), is(true));
    }

    @Test
    public void acceptsProfiles_defaultProfile() {
        assertThat(environment.acceptsProfiles("pd"), is(false));
        environment.setDefaultProfiles("pd");
        assertThat(environment.acceptsProfiles("pd"), is(true));
        environment.setActiveProfiles("p1");
        assertThat(environment.acceptsProfiles("pd"), is(false));
        assertThat(environment.acceptsProfiles("p1"), is(true));
    }

    @Test
    public void acceptsProfiles_withNotOperator() {
        assertThat(environment.acceptsProfiles("p1"), is(false));
        assertThat(environment.acceptsProfiles("!p1"), is(true));
        environment.addActiveProfile("p1");
        assertThat(environment.acceptsProfiles("p1"), is(true));
        assertThat(environment.acceptsProfiles("!p1"), is(false));
    }



    @Test(expected = IllegalArgumentException.class)
    public void acceptsProfiles_withInvalidNotOperator() {
        environment.acceptsProfiles("p1", "!");
    }
    /********* acceptsProfiles 指定的环境是否处于活动状态 end  *********/

    @Test
    public void environmentSubclass_withCustomProfileValidation() {
        ConfigurableEnvironment env = new AbstractEnvironment() {
            @Override
            protected void validateProfile(String profile) {
                super.validateProfile(profile);
                if (profile.contains("-")) {
                    throw new IllegalArgumentException(
                            "Invalid profile [" + profile + "]: must not contain dash character");
                }
            }
        };

        env.addActiveProfile("validProfile"); // succeeds

        try {
            env.addActiveProfile("invalid-profile");
            fail("expected validation exception");
        }
        catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(),
                    equalTo("Invalid profile [invalid-profile]: must not contain dash character"));
        }
    }

    @Test
    public void suppressGetenvAccessThroughSystemProperty() {
        System.setProperty("spring.getenv.ignore", "true");
        assertTrue(environment.getSystemEnvironment().isEmpty());
        System.clearProperty("spring.getenv.ignore");
    }

    @Test
    public void suppressGetenvAccessThroughSpringProperty() {
        SpringProperties.setProperty("spring.getenv.ignore", "true");
        assertTrue(environment.getSystemEnvironment().isEmpty());
        SpringProperties.setProperty("spring.getenv.ignore", null);
    }

    @Test
    public void suppressGetenvAccessThroughSpringFlag() {
        SpringProperties.setFlag("spring.getenv.ignore");
        assertTrue(environment.getSystemEnvironment().isEmpty());
        SpringProperties.setProperty("spring.getenv.ignore", null);
    }



}

