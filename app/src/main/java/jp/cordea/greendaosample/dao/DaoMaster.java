/*
 * Copyright 2015 Yoshihiro Tanaka
 *
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
 */

package jp.cordea.greendaosample.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

public class DaoMaster extends AbstractDaoMaster {
        public static final int SCHEMA_VERSION = 1000;

        public DaoMaster(SQLiteDatabase db) {
                super(db, SCHEMA_VERSION);
                registerDaoClass(SampleDao.class);
        }

        public static void createAllTable(SQLiteDatabase db) {
                SampleDao.createTable(db);
        }

        public static void removeAllTable(SQLiteDatabase db) {
                SampleDao.dropTable(db);
        }

        public static class DevOpenHelper extends SQLiteOpenHelper {

                public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
                        super(context, name, factory, SCHEMA_VERSION, errorHandler);
                }

                @Override
                public void onCreate(SQLiteDatabase db) {
                        createAllTable(db);
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        removeAllTable(db);
                }
        }

        @Override
        public DaoSession newSession() {
                return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
        }

        @Override
        public DaoSession newSession(IdentityScopeType type) {
                return new DaoSession(db, type, daoConfigMap);
        }
}
