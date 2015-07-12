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

public class Sample {

    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        this.sampleText = sampleText;
    }

    private String sampleText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    private transient DaoSession daoSession;

    private transient SampleDao sampleDao;

    public Sample(Long id, String sampleText) {
        this.sampleText = sampleText;
        this.id = id;
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        sampleDao = daoSession == null ? null : daoSession.getSampleDao();
    }

    public void delete() {
        if (sampleDao != null) {
            sampleDao.delete(this);
        }
    }

    public void update() {
        if (sampleDao != null) {
            sampleDao.update(this);
        }
    }

    public void refresh() {
        if (sampleDao != null) {
            sampleDao.refresh(this);
        }
    }

    public void insert() {
        if (sampleDao != null) {
            sampleDao.insert(this);
        }
    }

}
