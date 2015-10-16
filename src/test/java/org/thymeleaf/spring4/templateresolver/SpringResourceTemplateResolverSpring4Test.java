/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.spring4.templateresolver;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.resource.IResource;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.thymeleaf.testing.templateengine.util.ResourceUtils;
import org.thymeleaf.util.ClassLoaderUtils;


public final class SpringResourceTemplateResolverSpring4Test {



    @Test
    public void testResolveTemplate() throws Exception {

        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        final IEngineConfiguration configuration = templateEngine.getConfiguration();

        final String templateLocation = "spring4/templateresolver/test.html";

        final ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring4/templateresolver/applicationContext.xml");

        final SpringResourceTemplateResolver resolver =
                (SpringResourceTemplateResolver) context.getBean("springResourceTemplateResolver");

        final TemplateMode templateMode = resolver.getTemplateMode();
        Assert.assertEquals(TemplateMode.HTML, templateMode);

        final TemplateResolution resolution = resolver.resolveTemplate(configuration, "classpath:" + templateLocation);

        final IResourceResolver resourceResolver = resolution.getResourceResolver();
        final IResource resource = resourceResolver.resolveResource(configuration, resolution.getResourceName(), "US-ASCII");

        final String testResource = resource.readFully().replace("\r","");

        final String expected =
                ResourceUtils.read(
                        ClassLoaderUtils.getClassLoader(SpringResourceTemplateResolverSpring4Test.class).getResourceAsStream(templateLocation),
                        "US-ASCII", true).replace("\r","");

        Assert.assertEquals(expected, testResource);

    }

    
}
