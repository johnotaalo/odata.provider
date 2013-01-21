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
package org.informea.odata.producer.toolkit;

import java.util.Properties;
import java.util.logging.Logger;

import org.odata4j.producer.ODataProducerFactory;
import org.odata4j.producer.ODataProducer;


/**
 * Producer factory. Instantiated by servlet engine via web.xml
 * <br />
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.4.0, 10/28/2011
 * @since 1.3.3
 */
public class ProducerFactory implements ODataProducerFactory {

    private static final Logger log = Logger.getLogger(ProducerFactory.class.getName());
    private static ODataProducer instance;

    @Override
    public ODataProducer create(Properties props) {
        if ( instance == null ) {
            log.info("[TOOLKIT]: Instantiating the Toolkit OData service provider endpoint");
            instance = new Producer();
        }
        return instance;
    }

}
