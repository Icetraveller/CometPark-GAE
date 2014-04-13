/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cometpark.server.db;

import com.cometpark.server.db.models.Lot;
import com.cometpark.server.db.models.Spot;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/** Static initializer for Objectify ORM service.
 *
 * <p>To use this class: com.google.android.apps.iosched.gcm.server.db.OfyService.ofy;
 *
 * <p>This is responsible for registering model classes with Objectify before any references
 * access to the ObjectifyService takes place. All models *must* be registered here, as Objectify
 * doesn't do classpath scanning (by design).
 */
public class OfyService {
    static {
//        factory().register(Device.class);
//        factory().register(MulticastMessage.class);
    	factory().register(Spot.class);
    	factory().register(Lot.class);
    	factory().register(Lot.Location.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}