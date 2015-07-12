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

import android.database.sqlite.SQLiteDatabase;

import java.util.AbstractCollection;
import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;


public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sampleDaoConfig;

    public SampleDao getSampleDao() {
        return sampleDao;
    }

    private final SampleDao sampleDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sampleDaoConfig = daoConfigMap.get(SampleDao.class).clone();
        sampleDaoConfig.initIdentityScope(type);

        sampleDao = new SampleDao(sampleDaoConfig, this);

        registerDao(Sample.class, sampleDao);
    }

    public void clear() {
        sampleDaoConfig.getIdentityScope().clear();
    }
}
