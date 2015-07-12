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

package jp.cordea.greendaosample;

import android.app.Activity;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import jp.cordea.greendaosample.dao.DaoMaster;
import jp.cordea.greendaosample.dao.DaoSession;
import jp.cordea.greendaosample.dao.Sample;
import jp.cordea.greendaosample.dao.SampleDao;

public class MainActivity extends Activity {

    SampleDao sampleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "sample-db", null, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
                Log.e("db.err", "database error");
            }
        });

        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        sampleDao = daoSession.getSampleDao();

        insertSamples();

        long c = sampleDao.queryBuilder().count();
        List<Sample> list = sampleDao.queryBuilder().where(SampleDao.Properties.Id.between(1, 50)).build().forCurrentThread().list();
        Log.i("sample", String.format("number of sample: %d", c));
        for (Sample sample : list) {
            Log.i("sample", String.format("id: %d  text: %s", sample.getId(), sample.getSampleText()));
        }
    }

    private void insertSamples () {
        for (int i = 0; i < 100; i++) {
            Sample sample = new Sample(
                    (long) i,
                    String.format("Sample-%d", i)
            );
            sample.insert();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
