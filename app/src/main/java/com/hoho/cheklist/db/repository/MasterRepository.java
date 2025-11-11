package com.hoho.cheklist.db.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MasterRepository {

    private final AppDBHelper helper;

    public MasterRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    public JSONArray findP1Section() {
        SQLiteDatabase db = helper.getReadableDatabase();
        JSONArray arr = new JSONArray();

        try (Cursor c = db.rawQuery(
                "SELECT id, section_no, category, main_question, target, description, reference_basis, use_yn, sort_order " +
                        "FROM p1_section_master WHERE use_yn = 1 ORDER BY sort_order", null)) {

            while (c.moveToNext()) {
                JSONObject o = new JSONObject();
                o.put("id", c.getLong(0));
                o.put("sectionNo", c.getInt(1));
                o.put("category", nullToEmpty(c.getString(2)));
                o.put("mainQuestion", nullToEmpty(c.getString(3)));
                o.put("target", nullToEmpty(c.getString(4)));
                o.put("description", nullToEmpty(c.getString(5)));
                o.put("referenceBasis", nullToEmpty(c.getString(6)));
                o.put("useYn", c.getInt(7));
                o.put("sortOrder", c.getInt(8));
                arr.put(o);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return arr;
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
