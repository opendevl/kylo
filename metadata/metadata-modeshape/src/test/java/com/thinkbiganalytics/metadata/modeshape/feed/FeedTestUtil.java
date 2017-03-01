package com.thinkbiganalytics.metadata.modeshape.feed;

/*-
 * #%L
 * thinkbig-metadata-modeshape
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

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.thinkbiganalytics.metadata.api.category.Category;
import com.thinkbiganalytics.metadata.api.category.CategoryProvider;
import com.thinkbiganalytics.metadata.api.feed.Feed;
import com.thinkbiganalytics.metadata.api.feed.FeedProvider;
import com.thinkbiganalytics.metadata.api.template.FeedManagerTemplate;
import com.thinkbiganalytics.metadata.api.template.FeedManagerTemplateProvider;
import com.thinkbiganalytics.metadata.modeshape.JcrMetadataAccess;
import com.thinkbiganalytics.metadata.modeshape.category.JcrCategory;

/**
 */
@Component
public class FeedTestUtil {


    @Inject
    CategoryProvider categoryProvider;

    @Inject
    FeedProvider feedProvider;

    @Inject
    FeedManagerTemplateProvider feedManagerTemplateProvider;

    @Inject
    CategoryProvider feedManagerCategoryProvider;

    @Inject
    private JcrMetadataAccess metadata;


    /**
     * must be called within metdata.commit()
     */
    public Category findOrCreateCategory(String categorySystemName) {
        Category category = categoryProvider.findBySystemName(categorySystemName);
        if (category == null) {
            JcrCategory cat = (JcrCategory) categoryProvider.ensureCategory(categorySystemName);
            cat.setDescription(categorySystemName + " desc");
            cat.setTitle(categorySystemName);
            categoryProvider.update(cat);
            category = cat;
        }
        return category;
    }

    public Feed findOrCreateFeed(String categorySystemName, String feedSystemName, String feedTemplate) {
        Category category = findOrCreateCategory(categorySystemName);
        Feed feed = feedProvider.ensureFeed(category.getId(), feedSystemName);
        feed.setDisplayName(feedSystemName);
        FeedManagerTemplate template = findOrCreateTemplate(feedTemplate);
        feed.setTemplate(template);
        return feedProvider.update(feed);
    }

    public Feed findFeed(String categorySystemName, String feedSystemName) {
        Feed feed = feedProvider.findBySystemName(categorySystemName, feedSystemName);
        return feed;
    }

    /**
     * returns a FeedManagerTemplate. Must be called within a metadata.commit() call
     */
    public FeedManagerTemplate findOrCreateTemplate(String templateName) {
        FeedManagerTemplate template = feedManagerTemplateProvider.findByName(templateName);
        if (template == null) {
            template = feedManagerTemplateProvider.ensureTemplate(templateName);
            return template;
        } else {
            return template;
        }
    }

}
