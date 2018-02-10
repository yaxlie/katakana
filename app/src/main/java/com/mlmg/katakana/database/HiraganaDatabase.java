package com.mlmg.katakana.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mlmg.katakana.Letter;

import java.util.Random;

/**
 * Created by Marcin on 08.11.2017.
 */

public class HiraganaDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "katakana.data";

    public HiraganaDatabase(Context context) {
        super(context, DATABASE_NAME, null, 4);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS hiragana (uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "letter_h TEXT, " +
                "letter_l TEXT, " +
                "category INTEGER, " +
                "exp INTEGER)");
        onCreateDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS hiragana");
        onCreate(db);
    }

    private void insert(SQLiteDatabase db, String h, String l, int category) {
        ContentValues values;
        values = new ContentValues();
        values.put("letter_h", h);
        values.put("letter_l", l);
        values.put("category", category);
        values.put("exp", 0);
        db.insert("hiragana", null, values);
    }

    private void onCreateDb(SQLiteDatabase db){
        insert(db, "ン", "N", HiraganaTable.Category.A);
        insert(db, "ワ", "WA", HiraganaTable.Category.A);
        insert(db, "ラ", "RA", HiraganaTable.Category.A);
        insert(db, "ヤ", "YA", HiraganaTable.Category.A);
        insert(db, "マ", "MA", HiraganaTable.Category.A);
        insert(db, "ハ", "HA", HiraganaTable.Category.A);
        insert(db, "ナ", "NA", HiraganaTable.Category.A);
        insert(db, "タ", "TA", HiraganaTable.Category.A);
        insert(db, "サ", "SA", HiraganaTable.Category.A);
        insert(db, "カ", "KA", HiraganaTable.Category.A);
        insert(db, "ア", "A", HiraganaTable.Category.A);

        insert(db, "ウィ", "WI", HiraganaTable.Category.I);
        insert(db, "リ", "RI", HiraganaTable.Category.I);
        insert(db, "ミ", "MI", HiraganaTable.Category.I);
        insert(db, "ヒ", "HI", HiraganaTable.Category.I);
        insert(db, "ニ", "NI", HiraganaTable.Category.I);
        insert(db, "チ", "CHI", HiraganaTable.Category.I);
        insert(db, "シ", "SHI", HiraganaTable.Category.I);
        insert(db, "キ", "KI", HiraganaTable.Category.I);
        insert(db, "イ", "I", HiraganaTable.Category.I);

        insert(db, "ル", "RU", HiraganaTable.Category.U);
        insert(db, "ユ", "YU", HiraganaTable.Category.U);
        insert(db, "ム", "MU", HiraganaTable.Category.U);
        insert(db, "フ", "FU", HiraganaTable.Category.U);
        insert(db, "ヌ", "NU", HiraganaTable.Category.U);
        insert(db, "ツ", "TSU", HiraganaTable.Category.U);
        insert(db, "ス", "SU", HiraganaTable.Category.U);
        insert(db, "ク", "KU", HiraganaTable.Category.U);
        insert(db, "ウ", "U", HiraganaTable.Category.U);

        insert(db, "ヱ", "WE", HiraganaTable.Category.E);
        insert(db, "レ", "RE", HiraganaTable.Category.E);
        insert(db, "メ", "ME", HiraganaTable.Category.E);
        insert(db, "ヘ", "HE", HiraganaTable.Category.E);
        insert(db, "ネ", "NE", HiraganaTable.Category.E);
        insert(db, "テ", "TE", HiraganaTable.Category.E);
        insert(db, "セ", "SE", HiraganaTable.Category.E);
        insert(db, "ケ", "KE", HiraganaTable.Category.E);
        insert(db, "エ", "E", HiraganaTable.Category.E);

        insert(db, "ヲ", "WO", HiraganaTable.Category.O);
        insert(db, "ロ", "RO", HiraganaTable.Category.O);
        insert(db, "ヨ", "YO", HiraganaTable.Category.O);
        insert(db, "モ", "MO", HiraganaTable.Category.O);
        insert(db, "ホ", "HO", HiraganaTable.Category.O);
        insert(db, "ノ", "NO", HiraganaTable.Category.O);
        insert(db, "ト", "TO", HiraganaTable.Category.O);
        insert(db, "ソ", "SO", HiraganaTable.Category.O);
        insert(db, "コ", "KO", HiraganaTable.Category.O);
        insert(db, "オ", "O", HiraganaTable.Category.O);

    }

    public Letter getRandomAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from hiragana", null);
        int limit = res.getCount();
        Random rand = new Random();
        int i = rand.nextInt(limit) + 1;
        Letter letter = null;

        if(res.move(i)) {
            letter = new Letter(res.getInt(0), res.getString(1), res.getString(2), res.getInt(3), res.getInt(4));
        }
        res.close();
        return letter;
    }

    public Letter getLetter(String romaji) {
        romaji = "'" + romaji.toUpperCase() + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from hiragana where letter_l like "+romaji , null);
        Letter letter = null;

        if(res.moveToNext()) {
            letter = new Letter(res.getInt(0), res.getString(1), res.getString(2), res.getInt(3), res.getInt(4));
        }
        res.close();
        return letter;
    }

    public Letter getRandomCategory(int category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from hiragana where category = CAST("+category+" as TEXT)" , null);
        int limit = res.getCount();
        Random rand = new Random();
        int i = rand.nextInt(limit) + 1;
        Letter letter = null;

        if(res.move(i)) {
            letter = new Letter(res.getInt(0), res.getString(1), res.getString(2), res.getInt(3), res.getInt(4));
        }
        res.close();
        return letter;
    }

    public Cursor getAllFromCategory(int category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from hiragana where category = " + Integer.toString(category), null);
        return res;
    }

    public int getSizeCategory(int category){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from hiragana where category = CAST("+category+" as TEXT)" , null);
        return res.getCount();
    }
}