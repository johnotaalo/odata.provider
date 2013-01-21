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
package org.informea.odata.producer;

import java.util.List;
import junit.framework.TestCase;

import org.core4j.Enumerable;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.core.OEntity;
import org.odata4j.core.OProperty;
import org.odata4j.edm.EdmAssociation;
import org.odata4j.edm.EdmAssociationSet;
import org.odata4j.edm.EdmComplexType;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.edm.EdmEntityContainer;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmFunctionImport;
import org.odata4j.edm.EdmFunctionParameter;
import org.odata4j.edm.EdmNavigationProperty;
import org.odata4j.edm.EdmProperty;
import org.odata4j.edm.EdmSchema;

/**
 * Base class for InforMEA API services
 * @author Cristian Romanescu {@code cristian.romanescu _at_ eaudeweb.ro}
 * @version 1.1, 04/04/2011
 * @since 0.5
 */
public class BaseTest extends TestCase {

    protected ODataConsumer c = null;

    protected static void report(String msg) {
        System.out.println(msg);
    }

    protected static void report(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    protected static void reportEntity(String caption, OEntity entity){
        report(caption);
        for(OProperty<?> p : entity.getProperties()){
            report("  %s: %s",p.getName(),p.getValue());
        }
    }
    protected static int reportEntities(ODataConsumer c, String entitySet, int limit){
        report("entitySet: " + entitySet);
        Enumerable<OEntity> entities =  c.getEntities(entitySet).execute().take(limit);
        return reportEntities(entitySet, entities);
    }
    protected static int reportEntities(String entitySet, Enumerable<OEntity> entities){

        int count = 0;

        for(OEntity e :entities){
            reportEntity(entitySet + " entity" + count,e);
            count++;
        }
        report("total count: %s \n\n" , count);

        return count;
    }

    private static void reportProperties(List<EdmProperty> properties){
        for(EdmProperty property : properties){
            String p = String.format("Property Name=%s Type=%s Nullable=%s",property.name,property.type,property.nullable);
            if (property.maxLength != null)
                p = p + " MaxLength="+ property.maxLength;
            if (property.unicode != null)
                p = p + " Unicode="+ property.unicode;
            if (property.fixedLength != null)
                p = p + " FixedLength="+ property.fixedLength;

            if (property.storeGeneratedPattern != null)
                p = p + " StoreGeneratedPattern="+ property.storeGeneratedPattern;

            if (property.fcTargetPath != null)
                p = p + " TargetPath="+ property.fcTargetPath;
            if (property.fcContentKind != null)
                p = p + " ContentKind="+ property.fcContentKind;
            if (property.fcKeepInContent != null)
                p = p + " KeepInContent="+ property.fcKeepInContent;
            if (property.fcEpmContentKind != null)
                p = p + " EpmContentKind="+ property.fcEpmContentKind;
            if (property.fcEpmKeepInContent != null)
                p = p + " EpmKeepInContent="+ property.fcEpmKeepInContent;
            report("    "+ p);
        }
    }

    protected static void reportMetadata(EdmDataServices services){

        for(EdmSchema schema : services.getSchemas()){
            report("Schema Namespace=%s",schema.namespace);

            for(EdmEntityType et : schema.entityTypes){
                String ets = String.format("  EntityType Name=%s",et.name);
                if (et.hasStream != null)
                    ets = ets + " HasStream="+et.hasStream;
                report(ets);

                for(String key : et.keys){
                    report("    Key PropertyRef Name=%s",key);
                }

                reportProperties(et.properties);
                for(EdmNavigationProperty np : et.navigationProperties){
                    report("    NavigationProperty Name=%s Relationship=%s FromRole=%s ToRole=%s",
                            np.name,np.relationship.getFQNamespaceName(),np.fromRole.role,np.toRole.role);
                }

            }
            for(EdmComplexType ct : schema.complexTypes){
                report("  ComplexType Name=%s",ct.name);

                reportProperties(ct.properties);

            }
            for(EdmAssociation assoc : schema.associations){
                report("  Association Name=%s",assoc.name);
                report("    End Role=%s Type=%s Multiplicity=%s",assoc.end1.role,assoc.end1.type.getFQNamespaceName(),assoc.end1.multiplicity);
                report("    End Role=%s Type=%s Multiplicity=%s",assoc.end2.role,assoc.end2.type.getFQNamespaceName(),assoc.end2.multiplicity);
            }
            for(EdmEntityContainer ec : schema.entityContainers){
                report("  EntityContainer Name=%s IsDefault=%s LazyLoadingEnabled=%s",ec.name,ec.isDefault,ec.lazyLoadingEnabled);

                for(EdmEntitySet ees : ec.entitySets){
                    report("    EntitySet Name=%s EntityType=%s",ees.name,ees.type.getFQNamespaceName());
                }

                for(EdmAssociationSet eas : ec.associationSets){
                    report("    AssociationSet Name=%s Association=%s",eas.name,eas.association.getFQNamespaceName());
                    report("      End Role=%s EntitySet=%s",eas.end1.role.role,eas.end1.entitySet.name);
                    report("      End Role=%s EntitySet=%s",eas.end2.role.role,eas.end2.entitySet.name);
                }

                for(EdmFunctionImport efi : ec.functionImports){
                    report("    FunctionImport Name=%s EntitySet=%s ReturnType=%s HttpMethod=%s",
                            efi.name,efi.entitySet==null?null:efi.entitySet.name,efi.returnType,efi.httpMethod);
                    for(EdmFunctionParameter efp : efi.parameters){
                        report("      Parameter Name=%s Type=%s Mode=%s",efp.name,efp.type,efp.mode);
                    }
                }
            }
        }
    }


}
