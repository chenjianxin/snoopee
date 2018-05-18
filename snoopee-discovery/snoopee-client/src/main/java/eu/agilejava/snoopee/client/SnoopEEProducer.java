/*
 * The MIT License
 *
 * Copyright 2015 Ivar Grimstad (ivar.grimstad@gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.agilejava.snoopee.client;

import eu.agilejava.snoopee.annotation.SnoopEE;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * CDI Producer for SnoopEEServiceClient.
 *
 * @author Ivar Grimstad (ivar.grimstad@gmail.com)
 */
@ApplicationScoped
public class SnoopEEProducer {

    private static final Logger LOGGER = Logger.getLogger("eu.agilejava.snoopee");

    @ConfigProperty(name = "snoopeeService", defaultValue = "http://localhost:8081/snoopee-service/")
    @Inject
    private String serviceUrl;

    /**
     * Creates a SnoopEEServiceClient for the named service.
     *
     * @param ip The injection point
     * @return a configured SnoopEE service client
     */
    @SnoopEE
    @Produces
    @Dependent
    public SnoopEEServiceClient lookup(InjectionPoint ip) {

        final String applicationName = ip.getAnnotated().getAnnotation(SnoopEE.class).serviceName();

        LOGGER.config(() -> "producing " + applicationName);
        LOGGER.config(() -> "Service URL: " + serviceUrl);

        return new SnoopEEServiceClient.Builder(applicationName)
                .serviceUrl(serviceUrl)
                .build();
    }
}
