/**
 * Copyright 2014-2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//[START all]
package model;

import com.example.guestbook.OfyHelper;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import util.Service;

/**
 * The @Entity tells Objectify about our entity.  We also register it in {@link OfyHelper}
 * Our primary key @Id is set automatically by the Google Datastore for us.
 *
 * We add a @Parent to tell the object about its ancestor. We are doing this to support many
 * guestbooks.  Objectify, unlike the AppEngine library requires that you specify the fields you
 * want to index using @Index.  Only indexing the fields you need can lead to substantial gains in
 * performance -- though if not indexing your data from the start will require indexing it later.
 *
 * NOTE - all the properties are PUBLIC so that can keep the code simple.
 **/
@Entity
public class Record {

    // if deserialized from gson, these two fields need to be set manually
    @Id public String id;

    // if you want to use objectify.filter, @index is a must
    @Index public String currentWeek;
    @Index public String category;
    public String title;
    public String speaker;
    public String date;
    public String room;
    public String remark;
    public String timestamp;

    /**
     * Default constructor is necessary for serialization
     */
    public Record() {
        this("null", "null", "null", "null", "null", "null", "null");
    }

    public Record(String category, String title, String date, String speaker, String room, String remark, String timestamp) {
        this.id = Service.getAndIncrementNextId();
        this.currentWeek = Service.getCurrentWeekInMillis();
        this.category = category;
        this.title = title;
        this.date = date;
        this.speaker = speaker;
        this.room = room;
        this.remark = remark;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", currentWeek='" + currentWeek + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", speaker='" + speaker + '\'' +
                ", date='" + date + '\'' +
                ", room='" + room + '\'' +
                ", remark='" + remark + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    /*
      @Override
      public String toString() {
         * we transfer id to the client side as a string since
         * javascript has bad support for long
        return "{ \"id\": \"" + id + "\", \"key\": \"" + key + "\", \"value\": \"" + value + "\" }";
      }

      public void escapeQuotes() {
        key = key.replace("\"", "\\\"");
        value = value.replace("\"", "\\\"");
      }
    */

}