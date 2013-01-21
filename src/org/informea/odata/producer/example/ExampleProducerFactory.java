/* Copyright 2011 UNEP (http://www.unep.org)
 * This file is part of InforMEA Toolkit project.
 * InforMEA Toolkit is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * InforMEA Toolkit is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with
 * InforMEA Toolkit. If not, see http://www.gnu.org/licenses/.
 */
package org.informea.odata.producer.example;

import java.util.Properties;
import java.util.logging.Logger;

import javax.ws.rs.ext.Provider;

import org.odata4j.producer.ODataProducer;

import org.odata4j.producer.ODataProducerFactory;

/**
 * This is the provider declaration for Example service.
 * It is used by the web application to initialize the service endpoint.
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 0.6
 * Servlet is informea-example
 */
@Provider
public class ExampleProducerFactory implements ODataProducerFactory {

		private static final Logger log = Logger.getLogger(ExampleProducerFactory.class.getName());
        private static ODataProducer instance;

        @Override
        public ODataProducer create(Properties props) {
            if ( instance == null ) {
                log.info("[EXAMPLE]: Instantiating the Example OData service provider endpoint");
                instance = new ExampleProducer();
            }
        	return instance;
    }
}
