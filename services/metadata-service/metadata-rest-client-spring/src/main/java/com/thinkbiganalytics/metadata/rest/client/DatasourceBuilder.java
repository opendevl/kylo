/**
 * 
 */
package com.thinkbiganalytics.metadata.rest.client;

/*-
 * #%L
 * thinkbig-metadata-rest-client-spring
 * %%
 * Copyright (C) 2017 ThinkBig Analytics
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.thinkbiganalytics.metadata.rest.model.data.Datasource;

/**
 *
 * @author Sean Felten
 */
public interface DatasourceBuilder<B extends DatasourceBuilder<B, D>, D extends Datasource> {

    B description(String descr);
    B ownder(String owner);
    B encrypted(boolean flag);
    B compressed(boolean flag);
    
    D build();
    D post();
}