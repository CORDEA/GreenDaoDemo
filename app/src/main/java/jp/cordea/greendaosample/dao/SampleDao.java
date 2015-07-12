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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class SampleDao extends AbstractDao<Sample, Long> {

    /** important */
    public static final String TABLENAME = "SAMPLE";

    private DaoSession daoSession;

    public SampleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "id");
        public final static Property SampleText = new Property(1, String.class, "sampleText", false, "SAMPLE_TEXT");
    }

    public static void createTable(SQLiteDatabase db) {
        Log.i("tag", "createTable");
        String query = "CREATE TABLE IF NOT EXISTS " + TABLENAME
                + " ("
                + " id INTEGER PRIMARY KEY NOT NULL,"
                + " SAMPLE_TEXT NVARCHAR(255)"
                + " );";
        db.execSQL(query);
    }

    public static void dropTable(SQLiteDatabase db) {
        String query = "DROP TABLE IF EXISTS " + TABLENAME;
        db.execSQL(query);
    }

    @Override
    protected void attachEntity(Sample entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    protected Sample readEntity(Cursor cursor, int offset) {
        Sample sample = new Sample(
                cursor.getLong(offset + 0),
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1)
        );
        return sample;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, Sample entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setSampleText(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, Sample entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        stmt.bindLong(1, id);

        String sampleText = entity.getSampleText();
        if (sampleText != null) {
            stmt.bindString(2, sampleText);
        }
    }

    @Override
    protected Long updateKeyAfterInsert(Sample entity, long rowId) {
        return rowId;
    }

    @Override
    protected Long getKey(Sample entity) {
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return false;
    }
}
